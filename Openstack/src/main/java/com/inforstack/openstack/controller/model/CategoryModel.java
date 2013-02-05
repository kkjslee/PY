package com.inforstack.openstack.controller.model;

import com.inforstack.openstack.utils.OpenstackUtil;

public class CategoryModel {

  private Integer id;

  private I18nModel[] name;

  private Boolean enable;

  private ItemSpecificationModel[] itemSpecifications;

  private String currentLocaleName;

  public CategoryModel() {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public I18nModel[] getName() {
    return name;
  }

  public void setName(I18nModel[] name) {
    this.name = name;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public Boolean getEnable() {
    return enable;
  }

  public ItemSpecificationModel[] getItemSpecifications() {
    return itemSpecifications;
  }

  public void setItemSpecifications(ItemSpecificationModel[] itemSpecifications) {
    this.itemSpecifications = itemSpecifications;
  }

  public String getEnabledDesc() {
    if (enable == true) {
      return OpenstackUtil.getMessage("category.status.enabled");
    } else {
      return OpenstackUtil.getMessage("category.status.disabled");
    }
  }

  public String getCurrentLocaleName() {
    Integer languageId = OpenstackUtil.getLanguage().getId();
    for (I18nModel model : name) {
      if (model.getLanguageId() == languageId) {
        currentLocaleName = model.getContent();
        break;
      }
    }
    return currentLocaleName;
  }

  public void setCurrentLocaleName(String currentLocaleName) {
    this.currentLocaleName = currentLocaleName;
  }
}
