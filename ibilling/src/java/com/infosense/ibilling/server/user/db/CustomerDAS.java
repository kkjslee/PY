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

package com.infosense.ibilling.server.user.db;

import com.infosense.ibilling.server.invoice.db.InvoiceDeliveryMethodDAS;
import com.infosense.ibilling.server.util.Constants;
import com.infosense.ibilling.server.util.db.AbstractDAS;

public class CustomerDAS extends AbstractDAS<CustomerDTO> {
    public CustomerDTO create() {
        CustomerDTO newCustomer = new CustomerDTO();
        newCustomer.setInvoiceDeliveryMethod(new InvoiceDeliveryMethodDAS()
                .find(Constants.D_METHOD_EMAIL));
        newCustomer.setExcludeAging(0);
        return save(newCustomer);
    }
}
