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

package com.infosense.ibilling.server.pluggableTask;

import java.util.Date;

import com.infosense.ibilling.server.order.db.OrderDTO;
import com.infosense.ibilling.server.process.PeriodOfTime;

import java.util.List;

public interface OrderPeriodTask {
    Date calculateStart(OrderDTO order) throws TaskException;

    Date calculateEnd(OrderDTO order, Date processDate, int maxPeriods,
            Date periodStart) throws TaskException;

    public List<PeriodOfTime> getPeriods(); // the actual dates of the periods in between the main start/end
}
