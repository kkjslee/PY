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

package com.infosense.ibilling.server.billing.task;

import com.infosense.ibilling.common.Util;
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskException;
import com.infosense.ibilling.server.process.IBillingProcessSessionBean;
import com.infosense.ibilling.server.process.task.AbstractBackwardSimpleScheduledTask;
import com.infosense.ibilling.server.util.Context;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;

import java.util.Date;

/**
 * Scheduled billing process plug-in, executing the billing process on a simple schedule.
 *
 * This plug-in accepts the standard {@link AbstractSimpleScheduledTask} plug-in parameters
 * for scheduling. If these parameters are omitted (all parameters are not defined or blank)
 * the plug-in will be scheduled using the jbilling.properties "process.time" and
 * "process.frequency" values.
 *
 * @see com.infosense.ibilling.server.process.task.AbstractBackwardSimpleScheduledTask
 *
 * @author
 * @since
 */
public class BillingProcessTask extends AbstractBackwardSimpleScheduledTask {
    private static final Logger LOG = Logger.getLogger(BillingProcessTask.class);

    private static final String PROPERTY_RUN_BILLING = "process.run_billing";

    public String getTaskName() {
        return "billing process: " + getScheduleString();
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
    super.execute(context);//_init(context);

        IBillingProcessSessionBean billing = (IBillingProcessSessionBean) Context.getBean(Context.Name.BILLING_PROCESS_SESSION);

        if (Util.getSysPropBooleanTrue(PROPERTY_RUN_BILLING)) {
        LOG.info("Starting billing at " + new Date() + " for " + getEntityId());
            billing.trigger(new Date(), getEntityId());
            LOG.info("Ended billing at " + new Date());
        }
    }

    /**
     * Returns the scheduled trigger for the billing process. If the plug-in is missing
     * the {@link com.infosense.ibilling.server.process.task.AbstractSimpleScheduledTask}
     * parameters use the the default jbilling.properties process schedule instead.
     *
     * @return billing trigger for scheduling
     * @throws PluggableTaskException thrown if properties or plug-in parameters could not be parsed
     */
    @Override
    public SimpleTrigger getTrigger() throws PluggableTaskException {
        SimpleTrigger trigger = super.getTrigger();

        // trigger start time and frequency using jbilling.properties unless plug-in
        // parameters have been explicitly set to define the billing schedule
        if (useProperties()) {
            LOG.debug("Scheduling billing process from jbilling.properties ...");
            trigger= setTriggerFromProperties(trigger);
        } else {
            LOG.debug("Scheduling billing process using plug-in parameters ...");
        }

        return trigger;
    }

}
