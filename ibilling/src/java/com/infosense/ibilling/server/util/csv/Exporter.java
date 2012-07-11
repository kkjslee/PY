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

package com.infosense.ibilling.server.util.csv;

import java.util.List;

/**
 * Exporter
 *
 * @author Brian Cowdery
 * @since 03/03/11
 */
public interface Exporter<T extends Exportable> {

    public Class<T> getType();
    public String export(List<? extends Exportable> list);

}
