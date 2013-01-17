package com.inforstack.openstack.controller.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.api.nova.image.ImageService;
import com.inforstack.openstack.controller.model.CategoryModel;
import com.inforstack.openstack.controller.model.I18nModel;
import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.utils.OpenstackUtil;

@Controller
@RequestMapping(value = "/item")
public class ItemControlloer {
	
	private static final Log log = LogFactory.getLog(ItemControlloer.class);
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ImageService imageService;

	@Autowired
	private FlavorService flavorService;
	
	@RequestMapping(value = "/category/list", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CategoryModel> listCategory(Model model, boolean excludeDisabled, boolean withItems) {
		ArrayList<CategoryModel> models = new ArrayList<CategoryModel>();
		Integer languageId = OpenstackUtil.getLanguage().getId();
		List<Category> categories = this.itemService.listAllCategory(excludeDisabled);
		for (Category category : categories) {
			I18nModel[] name = new I18nModel[1];
			name[0] = new I18nModel();
			name[0].setLanguageId(languageId);
			name[0].setContent(category.getName().getI18nContent());
			
			CategoryModel cm = new CategoryModel();
			cm.setId(category.getId());
			cm.setName(name);
			cm.setEnable(category.getEnable());
			models.add(cm);
		}
		return models;
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> createCategory(Model model, @RequestBody CategoryModel categoryModel) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		if (categoryModel != null) {
			try {
				Category category = this.itemService.createCategory(categoryModel);
				if (category != null) {
					returnValue.put("success", "success");
				}
			} catch (ApplicationException e) {
				log.error(e.getMessage(), e);
			}
			
		}
		return returnValue;
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody Map<String, Object> updateCategory(Model model, @RequestBody CategoryModel categoryModel) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		if (categoryModel != null) {
			try {
				this.itemService.updateCategory(categoryModel);
				returnValue.put("success", "success");
			} catch (ApplicationException e) {
				log.error(e.getMessage(), e);
			}
		}
		return returnValue;
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody Map<String, Object> removeCategory(Model model, @RequestParam("id") Integer categoryId) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		if (categoryId != null) {
			try {
				this.itemService.removeCategory(categoryId);
				returnValue.put("success", "success");
			} catch (ApplicationException e) {
				log.error(e.getMessage(), e);
			}
		}
		return returnValue;
	}

}
