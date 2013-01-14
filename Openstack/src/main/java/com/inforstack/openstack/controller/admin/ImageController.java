package com.inforstack.openstack.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.image.Image;
import com.inforstack.openstack.api.nova.image.ImageService;
import com.inforstack.openstack.controller.model.ImgModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.StringUtil;
import com.inforstack.openstack.utils.ValidateUtil;

@Controller
@RequestMapping(value = "/admin/image")
public class ImageController {

  @Autowired
  private ImageService imageService;

  @Autowired
  private KeystoneService keystoneService;

  @Autowired
  private Validator validator;

  @RequestMapping(value = "/getPagerImageList", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  List<ImgModel> getPagerImages(Model model, int pageIndex, int pageSize) {
    List<ImgModel> imgList = new ArrayList<ImgModel>();
    try {
      Image[] images = imageService.listImages();
      ImgModel imgModel = null;
      for (Image img : images) {
        if (ValidateUtil.checkValidImg(img)) {
          imgModel = new ImgModel();
          imgModel.setImgId(img.getId());
          imgModel.setImgName(img.getName());
          imgList.add(imgModel);
        }
      }
    } catch (OpenstackAPIException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PagerModel<ImgModel> page = new PagerModel<ImgModel>(imgList, pageSize);
    imgList = page.getPagedData(pageIndex);
    model.addAttribute("pageIndex", pageIndex);
    model.addAttribute("pageSize", pageSize);
    return imgList;
  }

  @RequestMapping(value = "/createImage", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  Map<String, Object> createImage(Model model, ImgModel imgModel) {

    Map<String, Object> ret = new HashMap<String, Object>();
    String errorMsg = ValidateUtil.validModel(validator, "admin", imgModel);
    if (errorMsg != null) {
      ret.put(Constants.JSON_ERROR_STATUS, errorMsg);
      return ret;
    }
    try {
      Access access = keystoneService.getAdminAccess();
    } catch (OpenstackAPIException e) {
      ret.put(Constants.JSON_ERROR_STATUS, e.getMessage());
      return ret;
    }
    // to do
    ret.put(Constants.JSON_SUCCESS_STATUS, "success");
    return ret;
  }

  @RequestMapping(value = "/retrieveImage", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  String createVM(Model model, String imgId) {

    if (StringUtil.isNullOrEmpty(imgId)) {
      return Constants.JSON_STATUS_FAILED;
    }
    try {
      Image image = imageService.getImage(imgId);
    } catch (OpenstackAPIException e) {
      return Constants.JSON_STATUS_EXCEPTION;
    }
    // to do
    return Constants.JSON_STATUS_DONE;
  }
}
