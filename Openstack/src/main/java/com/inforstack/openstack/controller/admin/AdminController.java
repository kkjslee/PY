package com.inforstack.openstack.controller.admin;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.utils.JSONUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

  @RequestMapping(method = RequestMethod.GET)
  public String home(Model model) {
    return "admin/home";
  }

  @RequestMapping(value = "/modules/entry/index", method = RequestMethod.GET)
  public String entry(Model model) {
    return "admin/modules/Entry/index";
  }
  
  @RequestMapping(value = "/modules/entry/edit", method = RequestMethod.GET)
  public String entryEdit(Model model, HttpServletRequest request, HttpServletResponse response) {
	  String destFilePath = request.getServletContext().getRealPath("/resources/common/entry");
	  File contentFile = new File(destFilePath, "content.txt");
	  String content = StringUtil.file2String(contentFile, "UTF-8");
	  model.addAttribute("content", content);
    return "admin/modules/Entry/edit";
  }
  
  @RequestMapping(value = "/modules/entry/save", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
	Map<String, Object> entrySave(Model model,String html, String content, HttpServletRequest request, HttpServletResponse response) {
	  try {
		  String destFilePath = request.getServletContext().getRealPath("/resources/common/entry");
		  File htmlFile = new File(destFilePath, "html.txt");
		  StringUtil.string2File(html, htmlFile);
		  File contentFile = new File(destFilePath, "content.txt");
		  StringUtil.string2File(content, contentFile);
		  return JSONUtil.jsonSuccess(model, OpenstackUtil.getMessage("operation.success"));
	} catch (Exception e) {
		return JSONUtil.jsonError("operation.failed");
	}
    
  }

  @RequestMapping(value = "/scripts/navinit", method = RequestMethod.GET)
  public String naviInit(Model model) {
    return "admin/scripts/navinit";
  }

  @RequestMapping(value = "/scripts/bootloader", method = RequestMethod.GET)
  public String bootloader(Model model) {
    return "admin/scripts/bootloader";
  }

  @RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
  public String bootstrap(Model model) {
    return "admin/scripts/bootstrap";
  }

  @RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
  public String template(Model model) {
    return "admin/scripts/template";
  }

}
