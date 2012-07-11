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
package com.infosense.ibilling.server.item.tasks;

import java.math.BigDecimal;
import java.util.List;

import com.infosense.ibilling.server.mediation.Record;
import com.infosense.ibilling.server.order.db.OrderDTO;
import com.infosense.ibilling.server.pluggableTask.TaskException;

public interface IItemPurchaseManager {
    public void addItem(Integer itemID, BigDecimal quantity, Integer language, 
            Integer userId, Integer entityId, Integer currencyId, OrderDTO order,
            List<Record> records)
            throws TaskException ;
}
