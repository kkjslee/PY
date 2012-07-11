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

package com.infosense.ibilling.server.mediation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.infosense.ibilling.common.InvalidArgumentException;
import com.infosense.ibilling.server.mediation.db.*;
import com.infosense.ibilling.server.mediation.task.IMediationProcess;
import com.infosense.ibilling.server.order.db.OrderLineDTO;
import com.infosense.ibilling.server.pluggableTask.TaskException;

/**
 * @author emilc
 */
public interface IMediationSessionBean {

    public void trigger(Integer entityId);

    public MediationProcess createProcessRecord(MediationConfiguration cfg);

    public MediationProcess updateProcessRecord(MediationProcess process, Date enddate);

    public boolean isProcessing(Integer entityId);    

    public List<MediationProcess> getAll(Integer entityId);

    public Map<MediationRecordStatusDTO, Long> getNumberOfRecordsByStatuses(Integer entityId);

    public List<MediationConfiguration> getAllConfigurations(Integer entityId);

    public void createConfiguration(MediationConfiguration cfg);

    public List<MediationConfiguration> updateAllConfiguration(Integer executorId, List<MediationConfiguration> configurations) throws InvalidArgumentException;

    public void delete(Integer executorId, Integer cfgId);

    public boolean hasBeenProcessed(MediationProcess process, Record thisGroup);

    public void normalizeRecordGroup(IMediationProcess processTask,
                                     Integer executorId,
                                     MediationProcess process,
                                     List<Record> thisGroup,
                                     Integer entityId,
                                     MediationConfiguration cfg) throws TaskException;

    public void saveEventRecordLines(List<OrderLineDTO> newLines,
                                     MediationRecordDTO record,
                                     Date eventDate,
                                     String description);

    public List<MediationRecordLineDTO> getMediationRecordLinesForOrder(Integer orderId);

    public List<MediationRecordDTO> getMediationRecordsByMediationProcess(Integer mediationProcessId);
}
