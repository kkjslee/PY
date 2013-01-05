package com.inforstack.openstack.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.inforstack.openstack.tenant.agent.Agent;
import com.inforstack.openstack.tenant.agent.AgentService;
import com.inforstack.openstack.utils.Constants;

@Controller
@RequestMapping(value="/")
public class RootController {
	
	@Autowired
	private AgentService agentService;
	
	@RequestMapping(value="/403", method=RequestMethod.GET)
	public String accessDenied(){
		return "403";
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String visitRoot(Model model){
		return visitUser(model);
	}
	
	@RequestMapping(value="/user/login/{agent_id}", method=RequestMethod.GET)
	public String visitUserFromAgent(Model model, HttpServletRequest request, 
			@PathVariable("agent_id") String agentId){
		Agent agent = agentService.findAgentById(agentId);
		if(agent != null){
			WebUtils.setSessionAttribute(request, Constants.SESSION_ATTRIBUTE_NAME_AGENT, agent);
		}
		return visitUser(model);
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.GET)
	public String visitUser(Model model){
		model.addAttribute("enterpoint", "user");
		return "loginForm";
	}
	
	@RequestMapping(value="/admin/login", method=RequestMethod.GET)
	public String visitAdmin(Model model){
		model.addAttribute("enterpoint", "admin");
		return "loginForm";
	}
	
	@RequestMapping(value="/agent/login", method=RequestMethod.GET)
	public String visitAgent(Model model){
		model.addAttribute("enterpoint", "agent");
		return "loginForm";
	}
}
