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
package com.infosense.ibilling.server.payment.blacklist;

import java.util.Collection;
import java.util.ResourceBundle;
import java.util.List;

import com.infosense.ibilling.server.payment.PaymentDTOEx;
import com.infosense.ibilling.server.payment.blacklist.db.BlacklistDAS;
import com.infosense.ibilling.server.payment.blacklist.db.BlacklistDTO;
import com.infosense.ibilling.server.user.db.CreditCardDTO;
import com.infosense.ibilling.server.user.db.UserDAS;
import com.infosense.ibilling.server.user.db.UserDTO;
import com.infosense.ibilling.server.util.Util;

import java.util.ArrayList;

/**
 * Filters credit card numbers.
 */
public class CreditCardFilter implements BlacklistFilter {

    public Result checkPayment(PaymentDTOEx paymentInfo) {
        if (paymentInfo.getCreditCard() != null) {
            List<CreditCardDTO> creditCards = new ArrayList<CreditCardDTO>(1);
            // need to convert EJB 2 entity DTO type to Hibernate DTO type
            CreditCardDTO creditCard = new CreditCardDTO();
            // DB compares encrypted data
            creditCard.setNumber(paymentInfo.getCreditCard().getNumber());
            creditCards.add(creditCard);

            return checkCreditCard(paymentInfo.getUserId(), 
                    creditCards);
        }
        // not paying by credit card, so accept?
        return new Result(false, null);
    }

    public Result checkUser(Integer userId) {
        UserDTO user = new UserDAS().find(userId);
        return checkCreditCard(userId, user.getCreditCards());
    }

    public Result checkCreditCard(Integer userId, Collection<CreditCardDTO> creditCards) {
        if (creditCards.isEmpty()) {
            return new Result(false, null);
        }

        // create a list of credit card numbers
        List<String> ccNumbers = new ArrayList<String>(creditCards.size());
        for (CreditCardDTO cc : creditCards) {
            // it needs the encrypted numbers because it will use a query with them later
            ccNumbers.add(cc.getRawNumber());
        }

        Integer entityId = new UserDAS().find(userId).getCompany().getId();
        List<BlacklistDTO> blacklist = new BlacklistDAS().filterByCcNumbers(
                entityId, ccNumbers);

        if (!blacklist.isEmpty()) {
            ResourceBundle bundle = Util.getEntityNotificationsBundle(userId);
            return new Result(true, 
                    bundle.getString("payment.blacklist.cc_number_filter"));
        }

        return new Result(false, null);
    }

    public String getName() {
        return "Credit card number blacklist filter";
    }
}
