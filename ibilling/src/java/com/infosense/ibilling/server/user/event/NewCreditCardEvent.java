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

package com.infosense.ibilling.server.user.event;

import com.infosense.ibilling.server.system.event.Event;
import com.infosense.ibilling.server.user.db.CreditCardDTO;

/**
 *
 * @author emilc
 */
public class NewCreditCardEvent implements Event {

    private final CreditCardDTO creditCard;
    private final Integer entityId;

    public CreditCardDTO getCreditCard() {
        return creditCard;
    }

    public NewCreditCardEvent(CreditCardDTO creditCard, Integer entityId) {
        this.creditCard = creditCard;
        this.entityId = entityId;
    }

    public String getName() {
        return "New CreditCard event";
    }

    public final Integer getEntityId() {
        return entityId;
    }
}
