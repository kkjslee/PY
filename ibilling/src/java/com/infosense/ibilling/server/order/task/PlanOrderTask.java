package com.infosense.ibilling.server.order.task;

import java.util.HashMap;

import com.infosense.ibilling.server.order.db.OrderDTO;
import com.infosense.ibilling.server.pluggableTask.OrderProcessingTask;
import com.infosense.ibilling.server.pluggableTask.TaskException;
import com.infosense.ibilling.server.pluggableTask.admin.ParameterDescription;

/**
 * 
 *
 */
public class PlanOrderTask extends RulesItemManager2 implements OrderProcessingTask {

	public static final ParameterDescription PARAM_QUALITY =  new ParameterDescription("Quality", false, ParameterDescription.Type.INT);
	public static final ParameterDescription PARAM_PLAN_ID =  new ParameterDescription("Plan", false, ParameterDescription.Type.INT);
	public static final ParameterDescription PARAM_ITEM_ID =  new ParameterDescription("Item", false, ParameterDescription.Type.INT);
	
	//initializer for pluggable params
    { 
        descriptions.add(PARAM_QUALITY);
        descriptions.add(PARAM_PLAN_ID);
        descriptions.add(PARAM_ITEM_ID);
    }

	@Override
	public void doProcessing(OrderDTO order) throws TaskException {
		processRules(order, order.getBaseUserByUserId().getUserId());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void processRules(OrderDTO order, Integer userId) throws TaskException {
		Integer quality = Integer.valueOf(this.parameters.get("Quality"));
		Integer planId = Integer.valueOf(this.parameters.get("Plan"));
		Integer itemId = Integer.valueOf(this.parameters.get("Item"));
		
		HashMap map = new HashMap();
		map.put("quality", quality);
		map.put("plan", planId);
		map.put("item", itemId);
		rulesMemoryContext.add(map);
		
		super.processRules(order, userId);
	}

}
