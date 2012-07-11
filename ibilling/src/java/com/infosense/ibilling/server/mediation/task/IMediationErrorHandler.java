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

package com.infosense.ibilling.server.mediation.task;

import com.infosense.ibilling.server.mediation.Record;
import com.infosense.ibilling.server.mediation.db.MediationConfiguration;
import com.infosense.ibilling.server.pluggableTask.TaskException;

import java.util.Date;
import java.util.List;

public interface IMediationErrorHandler {

    public void process(Record record, List<String> errors, Date processingTime, MediationConfiguration mediationConfiguration)
            throws TaskException;
}
