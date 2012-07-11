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

package com.infosense.ibilling.server.order.task;

import com.infosense.ibilling.server.item.tasks.RulesItemManager;
import com.infosense.ibilling.server.order.db.OrderDTO;
import com.infosense.ibilling.server.pluggableTask.OrderProcessingTask;
import com.infosense.ibilling.server.pluggableTask.TaskException;

/**
 * 
 * This allows for rule processing for order line totals. It does not do the basic calculations
 * (price * quantity), thus it requires the BasicLineTotalTask to be configured, typically after
 *
 */
public class RulesLineTotalTask extends RulesItemManager 
        implements OrderProcessingTask {
    
    public void doProcessing(OrderDTO order) throws TaskException {
        helperOrder = new OrderManager(order, order.getBaseUserByUserId().getLanguage().getId(), 
                order.getBaseUserByUserId().getUserId(), order.getBaseUserByUserId().getEntity().getId(), 
                order.getBaseUserByUserId().getCurrency().getId());
        
        processRules(order);        
        
    }

}
