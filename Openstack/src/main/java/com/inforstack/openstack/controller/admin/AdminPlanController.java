package com.inforstack.openstack.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.controller.model.OrderPeriodModel;
import com.inforstack.openstack.controller.model.PlanModel;
import com.inforstack.openstack.order.period.OrderPeriod;
import com.inforstack.openstack.order.period.OrderPeriodService;
import com.inforstack.openstack.utils.StringUtil;

@Controller
@RequestMapping(value = "/admin/plan")
public class AdminPlanController {

	@Autowired
	private OrderPeriodService orderPeriodService;

	@RequestMapping(value = "/planList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<PlanModel> listPlans(Model model) {
		List<PlanModel> planModels = new ArrayList<PlanModel>();
		List<OrderPeriod> plans = orderPeriodService.listAll(false);
		PlanModel planModel = null;
		for (OrderPeriod plan : plans) {
			planModel = new PlanModel();
			planModel.setId(plan.getId());
			planModel.setName(plan.getName().getI18nContent());
			planModels.add(planModel);
		}

		return planModels;
	}
	
	@RequestMapping(value = "/retrievePlan", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody OrderPeriodModel getPlan(Model model, String planId) {
		if (StringUtil.isNullOrEmpty(planId)) {
			return null;
		}
		
		OrderPeriodModel pModel = null;
		
		OrderPeriod period = this.orderPeriodService.findPeriodById(Integer.parseInt(planId));
		if (period != null) {
			pModel = new OrderPeriodModel();
			pModel.setId(period.getId());
			pModel.setName(period.getName().getI18nContent());
			pModel.setCreateTime(period.getCreateTime());
			pModel.setDeleted(period.getDeleted());
			pModel.setPeriodQuantity(period.getPeriodQuantity());
			pModel.setPeriodType(period.getPeriodType());
		}
		 
		return pModel;
	}
	
}
