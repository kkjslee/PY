package com.inforstack.openstack.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.controller.model.PlanModel;
import com.inforstack.openstack.order.period.OrderPeriod;
import com.inforstack.openstack.order.period.OrderPeriodService;

@Controller
@RequestMapping(value = "/user/plan")
public class PlanController {
	
	@Autowired
	private OrderPeriodService imageService;

	@RequestMapping(value = "/planList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<PlanModel> listPlans(Model model) {
		List<PlanModel> planModels = new ArrayList<PlanModel>();
		List<OrderPeriod> plans = imageService.listAll(false);
		PlanModel planModel = null;
		for (OrderPeriod plan : plans) {
			planModel = new PlanModel();
			planModel.setId(plan.getId());
			planModel.setName(plan.getName().getI18nContent());
			planModels.add(planModel);

		}

		return planModels;
	}
}
