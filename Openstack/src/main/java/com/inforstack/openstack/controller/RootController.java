package com.inforstack.openstack.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inforstack.openstack.utils.OpenstackUtil;

@Controller
public class RootController {

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied() {
		return "403";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String visitRoot(Model model) {
		return visitUser(model);
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public String visitUser(Model model) {
		model.addAttribute("enterpoint", "user");
		return "loginForm";
	}

	@RequestMapping(value = "/user/loginfailed", method = RequestMethod.GET)
	public String userLoginFailed(Model model) {
		model.addAttribute("errorMessage", OpenstackUtil.getMessage("login.failed"));
		return visitUser(model);
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	public String visitAdmin(Model model) {
		model.addAttribute("enterpoint", "admin");
		return "loginForm";
	}

	@RequestMapping(value = "/admin/loginfailed", method = RequestMethod.GET)
	public String adminLoginFailed(Model model) {
		model.addAttribute("errorMessage", OpenstackUtil.getMessage("login.failed"));
		return visitAdmin(model);
	}

	@RequestMapping(value = "/agent/login", method = RequestMethod.GET)
	public String visitAgent(Model model) {
		model.addAttribute("enterpoint", "agent");
		return "loginForm";
	}

	@RequestMapping(value = "/agent/loginfailed", method = RequestMethod.GET)
	public String agentLoginFailed(Model model) {
		model.addAttribute("errorMessage", OpenstackUtil.getMessage("login.failed"));
		return visitAgent(model);
	}

}
