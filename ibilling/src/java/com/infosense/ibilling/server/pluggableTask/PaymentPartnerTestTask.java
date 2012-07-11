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

package com.infosense.ibilling.server.pluggableTask;

import com.infosense.ibilling.server.payment.PaymentDTOEx;
import com.infosense.ibilling.server.payment.db.PaymentAuthorizationDTO;
import com.infosense.ibilling.server.payment.db.PaymentResultDAS;
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskException;
import com.infosense.ibilling.server.util.Constants;

/**
 * @author Emil
 */
public class PaymentPartnerTestTask
    extends PluggableTask
    implements PaymentTask {

    /* (non-Javadoc)
     * @see com.infosense.ibilling.server.pluggableTask.PaymentTask#process(com.sapienter.betty.server.payment.PaymentDTOEx)
     */
    public boolean process(PaymentDTOEx paymentInfo)
            throws PluggableTaskException {
        if (paymentInfo.getPayoutId() == null) {
            return true;
        }
        paymentInfo.setPaymentResult(new PaymentResultDAS().find(Constants.RESULT_OK));
        return false;
    }

    /* (non-Javadoc)
     * @see com.infosense.ibilling.server.pluggableTask.PaymentTask#failure(java.lang.Integer, java.lang.Integer)
     */
    public void failure(Integer userId, Integer retry) {
        // TODO Auto-generated method stub

    }
    
    public boolean preAuth(PaymentDTOEx paymentInfo) 
        throws PluggableTaskException {
        return true;

    }

    public boolean confirmPreAuth(PaymentAuthorizationDTO auth, PaymentDTOEx paymentInfo) throws PluggableTaskException {
        // TODO Auto-generated method stub
        return true;
    }
}
