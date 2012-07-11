/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */

/*
 * Created on Dec 10, 2004
 *
 */
package com.infosense.ibilling.server.pluggableTask;

import java.util.GregorianCalendar;


import org.apache.log4j.Logger;

import com.infosense.ibilling.server.order.db.OrderDTO;
import com.infosense.ibilling.server.process.BillingProcessBL;
import com.infosense.ibilling.server.process.db.BillingProcessDTO;
import com.infosense.ibilling.server.util.Constants;
import com.infosense.ibilling.server.util.PreferenceBL;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Emil
 *
 */
public class OrderFilterAnticipatedTask extends BasicOrderFilterTask {
    
    private static final Logger LOG = Logger.getLogger(OrderFilterAnticipatedTask.class);
    
    public boolean isApplicable(OrderDTO order, 
            BillingProcessDTO process) throws TaskException {
        // by default, keep it in null 
        billingUntil = null;
        try {
            PreferenceBL preference = new PreferenceBL();
            try {
                preference.set(process.getEntity().getId(), 
                        Constants.PREFERENCE_USE_ORDER_ANTICIPATION);
            } catch (EmptyResultDataAccessException e) {
                // I like the default
            }
            if (preference.getInt() == 0 ) {
                LOG.warn("OrderAnticipated task is called, but this " +
                        "entity has the preference off");
            } else if (order.getAnticipatePeriods() != null && 
                    order.getAnticipatePeriods().intValue() > 0) {
                LOG.debug("Using anticipated order. Org billingUntil = " +
                        billingUntil + " ant periods " + 
                        order.getAnticipatePeriods());
                // calculate an extended end of billing process
                billingUntil = BillingProcessBL.getEndOfProcessPeriod(process);
                
                // move it forward by the number of ant months
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(billingUntil);
                cal.add(GregorianCalendar.MONTH,
                        order.getAnticipatePeriods().intValue());
                billingUntil = cal.getTime();
            }
        } catch (Exception e) {
            LOG.error("Exception:", e);
            throw new TaskException(e);
        }
        
        return super.isApplicable(order, process);
    }
}
