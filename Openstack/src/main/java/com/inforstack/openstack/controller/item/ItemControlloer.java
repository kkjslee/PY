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
import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.ItemService;

@Controller
@RequestMapping(value = "/item")
public class ItemControlloer {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ImageService imageService;

	@Autowired
	private FlavorService flavorService;
	
	@RequestMapping(value = "/categoryList", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<CategoryModel> listCategory(Model model, boolean excludeDisabled, boolean withItems) {
		ArrayList<CategoryModel> models = new ArrayList<CategoryModel>();
		List<Category> categories = this.itemService.listAllCategory(excludeDisabled);
		for (Category category : categories) {
			CategoryModel cm = new CategoryModel();
			cm.setId(category.getId());
			cm.setName(category.getName().getI18nContent());
			cm.setEnable(category.getEnable());
			models.add(cm);
		}
		return models;
	}

}
