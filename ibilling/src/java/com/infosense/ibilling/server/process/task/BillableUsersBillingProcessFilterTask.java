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

package com.infosense.ibilling.server.process.task;

import org.hibernate.ScrollableResults;

import com.infosense.ibilling.server.pluggableTask.PluggableTask;
import com.infosense.ibilling.server.process.db.BillingProcessDAS;

import java.util.Date;

/**
 * Basic filter task for returning the appropriate customers to run through the billing cycle
 * The task returns all active customers who have orders that are billable
 * 
 * @author Kevin Salerno
 *
 */
public class BillableUsersBillingProcessFilterTask extends PluggableTask
    implements IBillingProcessFilterTask {

    public ScrollableResults findUsersToProcess(Integer theEntityId, Date billingDate){
        return new BillingProcessDAS().findBillableUsersToProcess(theEntityId, billingDate);              
    }
}
