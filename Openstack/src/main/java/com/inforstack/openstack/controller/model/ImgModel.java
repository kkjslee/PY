package com.inforstack.openstack.controller.model;

/**
 * this is for json resposne
 * 
 * @author shaw
 * 
 */
public class ImgModel {
  private String imgId;
  private String imgName;

  public ImgModel() {
    super();
    // TODO Auto-generated constructor stub
  }

  public String getImgId() {
    return imgId;
  }

  public void setImgId(String imgId) {
    this.imgId = imgId;
  }

  public String getImgName() {
    return imgName;
  }

  public void setImgName(String imgName) {
    this.imgName = imgName;
  }

}
