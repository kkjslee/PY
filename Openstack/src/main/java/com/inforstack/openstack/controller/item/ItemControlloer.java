package com.inforstack.openstack.controller.item;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.api.nova.image.ImageService;
import com.inforstack.openstack.controller.model.CategoryModel;
import com.inforstack.openstack.controller.model.I18nModel;
import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.utils.OpenstackUtil;

@Controller
@RequestMapping(value = "/item")
public class ItemControlloer {
	
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
	
	@RequestMapping(value = "/category/add", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody CategoryModel addCategory(Model model, CategoryModel categoryModel) {
		CategoryModel cm = new CategoryModel();
		
		I18nModel[] name = categoryModel.getName();
		if (name != null && name.length >= 1) {
			Category category = this.itemService.addCategory(name[0].getLanguageId(), name[0].getContent(), categoryModel.isEnable());
			if (category != null) {
				for (int idx = 1; idx < name.length; idx++) {
					this.itemService.updateCategory(category, name[idx].getLanguageId(), name[idx].getContent(), categoryModel.isEnable());
				}
				
				cm.setId(category.getId());
				I18nModel[] n = new I18nModel[1];
				n[0] = new I18nModel();
				n[0].setLanguageId(OpenstackUtil.getLanguage().getId());
				n[0].setContent(category.getName().getI18nContent());
				cm.setName(n);
				cm.setEnable(category.getEnable());
			}
		}
		return cm;
	}

}
