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

package com.infosense.ibilling.server.provisioning.task.mmsc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyCustomerRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyCustomerRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://mmschandlerfacade.efs.teliasonera.se/}efsBaseMSISDNRequest">
 *       &lt;sequence>
 *         &lt;element name="mmsCapability" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyCustomerRequest", propOrder = {
    "mmsCapability"
})
public class ModifyCustomerRequest
    extends EfsBaseMSISDNRequest
{

    protected String mmsCapability;

    /**
     * Gets the value of the mmsCapability property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMmsCapability() {
        return mmsCapability;
    }

    /**
     * Sets the value of the mmsCapability property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMmsCapability(String value) {
        this.mmsCapability = value;
    }

}
