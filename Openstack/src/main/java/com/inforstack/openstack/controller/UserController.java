package com.inforstack.openstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/user")
public class UserController {

  @RequestMapping(value = "/reg", method = RequestMethod.GET)
  public String register(Model model) {
    model.addAttribute("enterpoint", "user");
    return "register";
  }

}
