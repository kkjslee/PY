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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@TableGenerator(
        name = "payment_info_cheque_GEN", 
        table = "ibilling_seqs", 
        pkColumnName = "name", 
        valueColumnName = "next_id", 
        pkColumnValue = "payment_info_cheque", 
        allocationSize = 100)
@Table(name = "payment_info_cheque")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentInfoChequeDTO implements Serializable {

    private int id;
    private PaymentDTO payment;
    private String bank;
    private String chequeNumber;
    private Date date;
    private int versionNum;

    public PaymentInfoChequeDTO() {
    }

    public PaymentInfoChequeDTO(int id) {
        this.id = id;
    }

    public PaymentInfoChequeDTO(int id, PaymentDTO payment, String bank,
            String chequeNumber, Date chequeDate) {
        this.id = id;
        this.payment = payment;
        this.bank = bank;
        this.chequeNumber = chequeNumber;
        this.date = chequeDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "payment_info_cheque_GEN")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    public PaymentDTO getPayment() {
        return this.payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    @Column(name = "bank", length = 50)
    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Column(name = "cheque_number", length = 50)
    public String getNumber() {
        return this.chequeNumber;
    }

    public void setNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    @Column(name = "cheque_date", length = 13)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date chequeDate) {
        this.date = chequeDate;
    }

    @Version
    @Column(name = "OPTLOCK")
    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }
}
