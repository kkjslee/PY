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

package com.infosense.ibilling.server.payment.db;

import java.util.Calendar;

import com.infosense.ibilling.server.util.db.AbstractDAS;

/**
 * 
 * @author abimael
 *
 */
public class PaymentAuthorizationDAS extends AbstractDAS<PaymentAuthorizationDTO> {

    public PaymentAuthorizationDTO create(String processor, String code1) {
        
        PaymentAuthorizationDTO auto = new PaymentAuthorizationDTO();
        auto.setProcessor(processor);
        auto.setCode1(code1);
        auto.setCreateDate(Calendar.getInstance().getTime());
        
        return save(auto);
    }

}
