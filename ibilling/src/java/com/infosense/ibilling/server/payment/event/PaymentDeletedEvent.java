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

package com.infosense.ibilling.server.payment.event;

import com.infosense.ibilling.server.payment.db.PaymentDTO;
import com.infosense.ibilling.server.system.event.Event;

/**
 *
 * @author emilc
 */
public class PaymentDeletedEvent implements Event {

    private final PaymentDTO payment;
    private final Integer entityId;

    public PaymentDeletedEvent(Integer entityId, PaymentDTO payment) {
        this.payment = payment;
        this.entityId = entityId;
    }

    public final Integer getEntityId() {
        return entityId;
    }

    public final PaymentDTO getPayment() {
        return payment;
    }

    public String getName() {
        return "Payment Deleted";
    }

}
