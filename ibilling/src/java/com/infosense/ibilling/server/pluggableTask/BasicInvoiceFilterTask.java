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
 * Created on Apr 28, 2003
 */
package com.infosense.ibilling.server.pluggableTask;

import com.infosense.ibilling.server.invoice.db.InvoiceDTO;
import com.infosense.ibilling.server.process.db.BillingProcessDTO;

/**
 * This filter simply verifies that this invoice is being process after
 * its due date
 */
public class BasicInvoiceFilterTask
    extends PluggableTask
    implements InvoiceFilterTask {

    public boolean isApplicable(InvoiceDTO invoice, 
            BillingProcessDTO process) throws TaskException {

        /*
        if (process.getBillingDate().after(Util.truncateDate(
                invoice.getDueDate()))) {
            return true;
        } else {
            return false;
        }
        */
        
        // change: we always delegate, even when is not yet overdue
        // this ensures the 'last invoice holds them all' policy
        return true;
    }

}
