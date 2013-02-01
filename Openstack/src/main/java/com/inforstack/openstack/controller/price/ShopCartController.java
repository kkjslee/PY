package com.inforstack.openstack.controller.price;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.controller.model.CartModel;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.rule.RuleService;
import com.inforstack.openstack.rule.RuleType;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.SecurityUtils;

@Controller
@RequestMapping(value = "/shopcart")
public class ShopCartController {
	
	private static final Logger log = new Logger(ShopCartController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private RuleService ruleService;
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> update(Model model, @RequestBody CartModel cartModel) {
		Map<String, Object> returnValue = new HashMap<String, Object>();

		ArrayList<Object> context = new ArrayList<Object>();
		
		User user = this.userService.findByName(SecurityUtils.getUserName());
		Tenant tenant = null;
		if (user != null) {
			tenant = this.tenantService.findTenantById(SecurityUtils.getTenant().getId());
		}
		if (user != null && tenant != null) {
			context.add(user);
			context.add(tenant);
			context.add(cartModel);
		}
				
		List<RuleType> types = this.ruleService.listRuleType();
		if (types != null) {
			for (RuleType type : types) {
				returnValue.put(Integer.toString(type.getId()), type.getName());
			}
		}
		
		returnValue.put("cart", cartModel);
		
		returnValue.put("success", "success");
		return returnValue;
	}

}
