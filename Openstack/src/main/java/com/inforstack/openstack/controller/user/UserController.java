package com.inforstack.openstack.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.controller.RootController;
import com.inforstack.openstack.controller.model.UserModel;
import com.inforstack.openstack.controller.model.UserTenantModel;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	private static final Logger log = new Logger(UserController.class);
	private static final String BASE = "user/";

	@Autowired
	private RootController rootController;
	@Autowired
	private Validator validator;

	@Autowired
	private UserService userService;

	@Autowired
	private KeystoneService keystoneService;

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String register(Model model) {
		log.debug("visit register page");
		model.addAttribute("users", userService.listAll());
		return BASE + "register";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		return BASE + "home";
	}

	@RequestMapping(value = "/modules/entry/index", method = RequestMethod.GET)
	public String entry(Model model) {
		return "user/modules/Entry/index";
	}

	@RequestMapping(value = "/scripts/navinit", method = RequestMethod.GET)
	public String naviInit(Model model) {
		return "user/scripts/navinit";
	}

	@RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
	public String bootstrap(Model model) {
		return "user/scripts/bootstrap";
	}

	@RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
	public String template(Model model) {
		return "user/scripts/template";
	}

	@RequestMapping(value = "/doReg", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> doRegister(@Valid UserModel userModel,
			BindingResult result, UserTenantModel tenantModel, Model model,
			HttpServletRequest req) {
		log.debug("register user");

		Map<String, Object> ret = new HashMap<String, Object>();

		if (StringUtil.isNullOrEmpty(tenantModel.getTenantDisplayName())) {
			ret.put("error",
					OpenstackUtil.getMessage("tenantDisplayName.label")
							+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(tenantModel.getTenantPhone())) {
			ret.put("error", OpenstackUtil.getMessage("tenantPhone.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(tenantModel.getTenantEmail())) {
			ret.put("error", OpenstackUtil.getMessage("tenantEmail.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(tenantModel.getTenantCountry())) {
			ret.put("error", OpenstackUtil.getMessage("tenantCountry.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(tenantModel.getTenantProvince())) {
			ret.put("error", OpenstackUtil.getMessage("tenantProvince.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(tenantModel.getTenantCity())) {
			ret.put("error", OpenstackUtil.getMessage("tenantCity.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(tenantModel.getTenantAddress())) {
			ret.put("error", OpenstackUtil.getMessage("tenantAddress.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(tenantModel.getTenantPostcode())) {
			ret.put("error", OpenstackUtil.getMessage("tenantPsotcode.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(userModel.getUsername())) {
			ret.put("error", OpenstackUtil.getMessage("username.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(userModel.getPassword())) {
			ret.put("error", OpenstackUtil.getMessage("password.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(userModel.getQuestion())) {
			ret.put("error", OpenstackUtil.getMessage("question.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(userModel.getAnswer())) {
			ret.put("error", OpenstackUtil.getMessage("answer.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(userModel.getFirstname())) {
			ret.put("error", OpenstackUtil.getMessage("firstname.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(userModel.getLastname())) {
			ret.put("error", OpenstackUtil.getMessage("lastname.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(userModel.getEmail())) {
			ret.put("error", OpenstackUtil.getMessage("email.label")
					+ OpenstackUtil.getMessage("not.null.empty"));
		} else if (StringUtil.isNullOrEmpty(userModel.getPhone())
				&& StringUtil.isNullOrEmpty(userModel.getMobile())) {
			ret.put("error",
					OpenstackUtil.getMessage("mobile.label") + "/"
							+ OpenstackUtil.getMessage("phone.label")
							+ OpenstackUtil.getMessage("not.null.empty"));
		}

		if (ret.isEmpty() == false) {
			return ret;
		}

		ObjectError firstError = null;

		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(
				tenantModel, "tenant");
		validator.validate(tenantModel, bindingResult);
		if (bindingResult.hasErrors()) {
			firstError = bindingResult.getAllErrors().get(0);
		}
		if (firstError == null) {
			bindingResult = new BeanPropertyBindingResult(userModel, "user");
			validator.validate(userModel, bindingResult);
			if (bindingResult.hasErrors()) {
				firstError = bindingResult.getAllErrors().get(0);
			}
		}

		if (firstError != null) {
			if (firstError instanceof FieldError) {
				FieldError fe = ((FieldError) firstError);
				String errorMsg = OpenstackUtil.getMessage(fe.getField()
						+ ".label")
						+ firstError.getDefaultMessage();
				errorMsg += "("
						+ OpenstackUtil.getMessage(fe.getObjectName()
								+ ".label") + ")";
				errorMsg += " : " + fe.getRejectedValue();
				ret.put("error", errorMsg);
				return ret;
			} else {
				ret.put("error", firstError.getDefaultMessage());
			}
		}

		User user = userModel.getUser();
		Tenant tenant = tenantModel.getTenant();
		tenant.setName(user.getUsername());

		boolean success = true;
		try {
			userService.registerUser(user, tenant, Constants.ROLE_USER);
		} catch (Exception e) {
			success = false;
			log.error(e.getMessage(), e);
		}

		if (success == false) {
			try {
				keystoneService.removeUserAndTenant(user.getOpenstackUser()
						.getId(), tenant.getOpenstatckTenant().getId());
			} catch (OpenstackAPIException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (success) {
			log.debug("Register user successfully");
			ret.put("success", "success");
		} else {
			log.debug("Register user failed");
			ret.put("error", "error");
		}

		return ret;
	}

}
