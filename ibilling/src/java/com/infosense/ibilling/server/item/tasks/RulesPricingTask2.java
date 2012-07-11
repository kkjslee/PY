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

import com.infosense.ibilling.server.item.PricingField;
import com.infosense.ibilling.server.order.OrderBL;
import com.infosense.ibilling.server.order.db.OrderDTO;
import com.infosense.ibilling.server.order.db.OrderLineDTO;
import com.infosense.ibilling.server.pluggableTask.TaskException;
import com.infosense.ibilling.server.rule.RulesBaseTask;
import com.infosense.ibilling.server.user.ContactBL;
import com.infosense.ibilling.server.user.ContactDTOEx;
import com.infosense.ibilling.server.user.UserDTOEx;
import com.infosense.ibilling.server.user.contact.db.ContactFieldDTO;
import com.infosense.ibilling.server.util.DTOFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author emilc
 */
public class RulesPricingTask2 extends RulesBaseTask implements IPricing {

    protected Logger getLog() {
        return Logger.getLogger(RulesPricingTask2.class);
    }
    
    public BigDecimal getPrice(Integer itemId, BigDecimal quantity, Integer userId, Integer currencyId,
            List<PricingField> fields, BigDecimal defaultPrice, OrderDTO pricingOrder, boolean singlePurchase)
            throws TaskException {

        // the result goes in the memory context
        PricingResult result = new PricingResult(itemId, quantity, userId, currencyId);
        rulesMemoryContext.add(result);

        if (fields != null && !fields.isEmpty()) {
            // bind the pricing fields to this result
            result.setPricingFieldsResultId(result.getId());
            for (PricingField field : fields) {
                field.setResultId(result.getId());
            }
            rulesMemoryContext.addAll(fields);
        }

        try {
            if (userId != null) {
                UserDTOEx user = DTOFactory.getUserDTOEx(userId);
                rulesMemoryContext.add(user);
                ContactBL contact = new ContactBL();
                contact.set(userId);
                ContactDTOEx contactDTO = contact.getDTO();
                rulesMemoryContext.add(contactDTO);
                for (ContactFieldDTO field: (Collection<ContactFieldDTO>) contactDTO.getFieldsTable().values()) {
                    rulesMemoryContext.add(field);
                }
            }
        } catch (Exception e) {
            throw new TaskException(e);
        }

        executeRules();

        // the rules might not have any price for this. Use the default then.
        if (result.getPrice() == null) {
            result.setPrice(defaultPrice); // set the default
        }
        return result.getPrice();
    }
}
