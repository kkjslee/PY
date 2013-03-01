package com.inforstack.openstack.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.api.nova.image.Image;
import com.inforstack.openstack.controller.model.ImageModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.StringUtil;
import com.inforstack.openstack.utils.ValidateUtil;

@Controller
@RequestMapping(value = "/admin/image")
public class ImageController {

	private static final Logger log = new Logger(ImageController.class);

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private Validator validator;

	private final String IMAGE_MODULE_HOME = "admin/modules/Image";

	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		return IMAGE_MODULE_HOME + "/index";
	}

	@RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
	public String bootstrap(Model model) {
		model.addAttribute(Constants.PAGER_PAGE_INDEX,
				Constants.DEFAULT_PAGE_INDEX);
		model.addAttribute(Constants.PAGER_PAGE_SIZE,
				Constants.DEFAULT_PAGE_SIZE);
		return IMAGE_MODULE_HOME + "/scripts/bootstrap";
	}

	@RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
	public String template(Model model) {
		return IMAGE_MODULE_HOME + "/scripts/template";
	}

	@RequestMapping(value = "/getPagerImageList", method = RequestMethod.POST)
	public String getPagerImages(Model model, Integer pageIndex,
			Integer pageSize) {
		int pageIdx = -1;
		int pageSze = 0;
		if (pageIndex == null || pageIndex == 0) {
			log.info("no pageindex passed, set default value 1");
			pageIdx = Constants.DEFAULT_PAGE_INDEX;
		} else {
			pageIdx = pageIndex;
		}
		if (pageSize == null) {
			log.info("no page size passed, set default value 20");
			pageSze = Constants.DEFAULT_PAGE_SIZE;
		} else {
			pageSze = pageSize;
		}
		getImages(model, pageIdx, pageSze, false);
		return IMAGE_MODULE_HOME + "/tr";
	}

	// this not valid image meta data
	@RequestMapping(value = "/getPagerAllImageList", method = RequestMethod.POST)
	public String getPagerAllImages(Model model, Integer pageIndex,
			Integer pageSize) {
		int pageIdx = -1;
		int pageSze = 0;
		if (pageIndex == null || pageIndex == 0) {
			log.info("no pageindex passed, set default value 1");
			pageIdx = Constants.DEFAULT_PAGE_INDEX;
		} else {
			pageIdx = pageIndex;
		}
		if (pageSize == null) {
			log.info("no page size passed, set default value 20");
			pageSze = Constants.DEFAULT_PAGE_SIZE;
		} else {
			pageSze = pageSize;
		}
		getImages(model, pageIdx, pageSze, false);
		return IMAGE_MODULE_HOME + "/tr";
	}

	private List<ImageModel> getImages(Model model, int pageIdx, int pageSze,
			boolean needCheck) {
		List<ImageModel> imgList = new ArrayList<ImageModel>();
		try {
			List<Image> images = this.itemService.listOpenStackImage(1);
			if (images != null) {
				ImageModel imgModel = null;
				for (Image img : images) {
					imgModel = new ImageModel();
					imgModel.setImgId(img.getId());
					imgModel.setImgName(img.getName());
					imgModel.setCreated(img.getCreated());
					imgModel.setMinDisk(img.getMinDisk());
					imgModel.setMinRam(img.getMinRam());
					imgModel.setProgress(img.getProgress());
					imgModel.setStatus(img.getStatus());
					imgModel.setTenant(img.getTenant());
					imgModel.setUpdated(img.getUpdated());
					imgModel.setUser(img.getUser());
					if (needCheck) {
						if (!ValidateUtil.checkValidImg(img)) {
							continue;
						}
					}
					imgList.add(imgModel);
				}

				PagerModel<ImageModel> page = new PagerModel<ImageModel>(
						imgList, pageSze);
				imgList = page.getPagedData(pageIdx);
				model.addAttribute("pageIndex", pageIdx);
				model.addAttribute("pageSize", pageSze);
				model.addAttribute("pageTotal", page.getTotalRecord());
				model.addAttribute("dataList", imgList);
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgList;
	}

	// this is for instance selection
	@RequestMapping(value = "/imgList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<ImageModel> listImages(Model model) {
		List<ImageModel> imgModels = new ArrayList<ImageModel>();
		try {
			List<Image> images = this.itemService.listOpenStackImage(1);
			ImageModel imgModel = null;
			for (Image img : images) {
				if (ValidateUtil.checkValidImg(img)) {
					imgModel = new ImageModel();
					imgModel.setImgId(img.getId());
					imgModel.setImgName(img.getName());
					imgModels.add(imgModel);
				}

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		return imgModels;
	}

	@RequestMapping(value = "/createImage", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> createImage(Model model, ImageModel imgModel) {

		Map<String, Object> ret = new HashMap<String, Object>();
		String errorMsg = ValidateUtil.validModel(validator, "admin", imgModel);
		if (errorMsg != null) {
			ret.put(Constants.JSON_ERROR_STATUS, errorMsg);
			return ret;
		}
//		try {
//			// to do
			ret.put(Constants.JSON_SUCCESS_STATUS, "success");
//		} catch (ApplicationException e) {
//			ret.put(Constants.JSON_ERROR_STATUS, e.getMessage());
//			return ret;
//		}

		return ret;
	}

	@RequestMapping(value = "/retrieveImage", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Image getImage(Model model, String imgId) {

		if (StringUtil.isNullOrEmpty(imgId)) {
			return null;
		}
		Image image = null;
		try {
			image = this.itemService.getOpenStackImage(1, imgId);
		} catch (ApplicationException e) {
			return null;
		}
		// to do
		return image;
	}
}
