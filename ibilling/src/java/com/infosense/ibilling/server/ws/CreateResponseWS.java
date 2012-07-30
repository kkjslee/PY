package com.infosense.ibilling.server.ws;

import java.io.Serializable;

import com.infosense.ibilling.server.payment.PaymentAuthorizationDTOEx;

public class CreateResponseWS implements Serializable {
    private Integer userId = null;
    private Integer orderId = null;
    private Integer invoiceId = null;
    private Integer paymentId = null;
    private PaymentAuthorizationDTOEx paymentResult = null;
    
    public Integer getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Integer getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
    public PaymentAuthorizationDTOEx getPaymentResult() {
        return paymentResult;
    }
    public void setPaymentResult(PaymentAuthorizationDTOEx paymentResult) {
        this.paymentResult = paymentResult;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String toString() {
        return "user id = " + userId + " invoice id = " + invoiceId +
                " order id = " + orderId + " paymentId = " + paymentId +
                " payment result = " + paymentResult;
    }
}
