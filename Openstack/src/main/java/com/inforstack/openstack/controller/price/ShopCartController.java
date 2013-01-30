package com.inforstack.openstack.controller.price;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.rule.RuleService;
import com.inforstack.openstack.rule.RuleType;

@Controller
@RequestMapping(value = "/shopcart")
public class ShopCartController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private RuleService ruleService;
	
	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> test(Model model) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		List<RuleType> types = this.ruleService.listRuleType();
		for (RuleType type : types) {
			returnValue.put(Integer.toString(type.getId()), type.getName());
		}
		returnValue.put("success", "success");
		return returnValue;
	}

}
