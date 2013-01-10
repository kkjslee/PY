package com.inforstack.openstack.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.controller.RootController;
import com.inforstack.openstack.controller.model.TenantModel;
import com.inforstack.openstack.controller.model.UserModel;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	private static final Log log = LogFactory.getLog(UserController.class);
	private static final String BASE = "user/";
	
	@Autowired
	private RootController rootController;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String register(Model model) {
		log.debug("visit register page");
		return BASE + "register";
	}
	
	@RequestMapping(value = "/doReg", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody Map<String, Object> doRegister(@Valid UserModel userModel, BindingResult result,
			TenantModel tenantModel, BindingResult result2, Model model){
		log.debug("register user");
		
		Map<String, Object> ret = new HashMap<String, Object>();
		if(result.hasErrors()){
			ret.put("error", result.getAllErrors().get(0).getDefaultMessage());
			return ret;
		}
		
		User user = userModel.getUser();
		Tenant tenant = tenantModel.getTenant();
		
		User u = userService.registerUser(user, tenant);
		if(u == null){
			log.debug("Register user failed");
			ret.put("error", "error");
		}else{
			log.debug("Register user successfully");
			ret.put("success", "success");
		}
		
		return ret;
	}

}
