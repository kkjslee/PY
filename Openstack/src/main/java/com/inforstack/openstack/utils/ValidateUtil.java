package com.inforstack.openstack.utils;

import java.util.Map;

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
}
