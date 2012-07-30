package com.infosense.ibilling.server.ws;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.infosense.ibilling.server.entity.AchDTO;
import com.infosense.ibilling.server.entity.CreditCardDTO;
import com.infosense.ibilling.server.entity.PaymentAuthorizationDTO;
import com.infosense.ibilling.server.entity.PaymentInfoChequeDTO;


/**
 * PaymentWS
 */
public class PaymentWS implements WSSecured, Serializable {

    @NotNull(message="validation.error.notnull")
    private Integer userId = null;

    @Valid
    private PaymentInfoChequeDTO cheque = null;
    @Valid
    private CreditCardDTO creditCard = null;
    @Valid
    private AchDTO ach = null;

    private String method = null;
    private Integer invoiceIds[] = null;

    // refund specific fields
    private Integer paymentId = null; // this is the payment refunded / to refund
    private PaymentAuthorizationDTO authorization = null;

    //missing properties from PaymentDTO
    @NotEmpty(message="validation.error.notnull")
    @Digits(integer=22, fraction=10, message="validation.error.not.a.number")
    private String amount;
    @NotNull(message="validation.error.notnull")
    private Integer isRefund;
    @NotNull(message="validation.error.notnull")
    private Integer paymentMethodId;
    @NotNull(message="validation.error.notnull")
    private Date paymentDate;
    @NotNull(message="validation.error.notnull")
    private Integer currencyId;
    private int id;
    private Integer isPreauth;
    private Integer attempt;
    private String balance;
    private Date createDatetime;
    private Date updateDatetime;
    private int deleted;
    private Integer resultId;
    @Size(min = 0, max = 500, message = "validation.error.size,0,500")
    private String paymentNotes = null;
    private Integer paymentPeriod;
    
    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }
    
    public PaymentWS() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public PaymentInfoChequeDTO getCheque() {
        return cheque;
    }

    public void setCheque(PaymentInfoChequeDTO cheque) {
        this.cheque = cheque;
    }

    public CreditCardDTO getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardDTO creditCard) {
        this.creditCard = creditCard;
    }

    public AchDTO getAch() {
        return ach;
    }

    public void setAch(AchDTO ach) {
        this.ach = ach;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer[] getInvoiceIds() {
        return invoiceIds;
    }

    public void setInvoiceIds(Integer[] invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentAuthorizationDTO getAuthorizationId() {
        return authorization;
    }

    public void setAuthorization(PaymentAuthorizationDTO authorization) {
        this.authorization = authorization;
    }

    // required by CXF
    public void setAuthorizationId(PaymentAuthorizationDTO authorization) {
        this.authorization = authorization;
    }

    public String getAmount() {
        return amount;
    }

    public BigDecimal getAmountAsDecimal() {
        return amount != null ? new BigDecimal(amount) : null;
    }

    public void setAmountAsDecimal(BigDecimal amount) {
        setAmount(amount);
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = (amount != null ? amount.toString() : null);
    }

    public Integer getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Integer isRefund) {
        this.isRefund = isRefund;
    }

    public Integer getMethodId() {
        return paymentMethodId;
    }

    public void setMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIsPreauth() {
        return isPreauth;
    }

    public void setIsPreauth(Integer isPreauth) {
        this.isPreauth = isPreauth;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public String getBalance() {
        return balance;
    }

    public BigDecimal getBalanceAsDecimal() {
        return balance != null ? new BigDecimal(balance) : null;
    }

    public void setBalanceAsDecimal(BigDecimal balance) {
        setBalance(balance);
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = (balance != null ? balance.toString() : null);        
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public void setPaymentNotes(String paymentNotes){
        this.paymentNotes = paymentNotes;
    }
    
    public String getPaymentNotes(){
        return paymentNotes;
    }
    
    public void setPaymentPeriod(Integer paymentPeriod){
        this.paymentPeriod = paymentPeriod;
    }
    
    public Integer getPaymentPeriod(){
        return paymentPeriod;
    }

    /**
     * Unsupported, web-service security enforced using {@link #getOwningUserId()}
     * @return null
     */
    public Integer getOwningEntityId() {
        return null;
    }

    public Integer getOwningUserId() {
        return getUserId();
    }

    @Override
    public String toString() {
        return "PaymentWS{"
               + "id=" + id
               + ", userId=" + userId
               + ", paymentMethodId=" + paymentMethodId
               + ", method='" + method + '\''
               + ", amount='" + amount + '\''
               + ", balance='" + balance + '\''
               + ", isRefund=" + isRefund
               + ", isPreauth=" + isPreauth
               + ", paymentDate=" + paymentDate
               + ", deleted=" + deleted               
               + '}';
    }
}
