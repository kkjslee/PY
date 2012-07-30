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

package com.infosense.ibilling.server.user.tasks;

import java.math.BigDecimal;
import java.util.List;
import java.util.List;

import com.infosense.ibilling.server.item.PricingField;
import com.infosense.ibilling.server.item.db.ItemDTO;
import com.infosense.ibilling.server.pluggableTask.TaskException;
import com.infosense.ibilling.server.user.db.CustomerDTO;
import com.infosense.ibilling.server.ws.ValidatePurchaseWS;

/**
 * Pluggable task type category for validatePurchase API method.
 */
public interface IValidatePurchaseTask {
    public ValidatePurchaseWS validate(CustomerDTO customer, 
            List<ItemDTO> items, List<BigDecimal> amounts, 
            ValidatePurchaseWS result, List<List<PricingField>> fields) 
            throws TaskException;
}
