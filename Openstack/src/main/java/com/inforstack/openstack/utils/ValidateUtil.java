package com.inforstack.openstack.utils;

import java.util.Map;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.image.Image;

public class ValidateUtil {

  public static boolean checkValidFlavor(Flavor flavor) {
    if (flavor != null) {
      if (!flavor.isDisabled()) {
        return true;
      }
    }
    return false;
  }

  public static boolean checkValidImg(Image img) {
    if (img != null) {
      if (img.getStatus().equalsIgnoreCase(Constants.IMG_STATUS_ACTIVE)
          && img.getProgress() == Constants.IMG_PROGRESS_ACTIVE) {
        Map<String, String> mdata = img.getMetadata();
        if (mdata.containsKey(Constants.IMG_METADATA_KEY_KERNEL)
            && mdata.containsKey(Constants.IMG_METADATA_KEY_RAMDISK)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 
   * @param validator
   * @param objectName
   * @param model
   * @return errorMsg else return null
   */
  public static String validModel(Validator validator, String objectName, Object model) {
    ObjectError firstError = null;

    BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(model, objectName);
    validator.validate(model, bindingResult);
    if (bindingResult.hasErrors()) {
      firstError = bindingResult.getAllErrors().get(0);
    }

    if (firstError != null) {
      if (firstError instanceof FieldError) {
        FieldError fe = ((FieldError) firstError);
        String errorMsg = OpenstackUtil.getMessage(fe.getField() + ".label")
            + firstError.getDefaultMessage();
        errorMsg += "(" + OpenstackUtil.getMessage(fe.getObjectName() + ".label") + ")";
        errorMsg += " : " + fe.getRejectedValue();
        return errorMsg;
      }
    }
    return null;
  }
}
