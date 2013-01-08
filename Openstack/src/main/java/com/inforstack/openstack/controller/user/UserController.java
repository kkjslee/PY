package com.inforstack.openstack.controller.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value = "/doReg", method = RequestMethod.GET)
	public String doRegister(Model model, UserModel userModel, TenantModel tenantModel){
		log.debug("register user");
		User user = userModel.getUser();
		Tenant tenant = tenantModel.getTenant();
		
		User u = userService.registerUser(user, tenant);
		if(u == null){
			log.debug("Register user failed");
			return register(model);
		}else{
			log.debug("Register user successfully");
			return rootController.visitUser(model);
		}
	}

}
