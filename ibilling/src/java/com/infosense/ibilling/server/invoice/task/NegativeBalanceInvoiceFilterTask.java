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

package com.infosense.ibilling.server.invoice.task;

import com.infosense.ibilling.server.invoice.db.InvoiceDTO;
import com.infosense.ibilling.server.pluggableTask.InvoiceFilterTask;
import com.infosense.ibilling.server.pluggableTask.PluggableTask;
import com.infosense.ibilling.server.pluggableTask.TaskException;
import com.infosense.ibilling.server.process.db.BillingProcessDTO;

import java.math.BigDecimal;

/**
 * Only allows invoices with a negative balance to be carried over.
 */
public class NegativeBalanceInvoiceFilterTask extends PluggableTask
        implements InvoiceFilterTask {

    public boolean isApplicable(InvoiceDTO invoice, BillingProcessDTO process) 
            throws TaskException {

        if (BigDecimal.ZERO.compareTo(invoice.getBalance()) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
