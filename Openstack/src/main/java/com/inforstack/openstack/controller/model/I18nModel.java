package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.inforstack.openstack.utils.Constants;

public class I18nModel {
	
	@Range(min = Constants.LANGUAGE_EN, max = Constants.LANGUAGE_CH, message = "{size.not.valid}")
	private Integer languageId;
	
	@Size(min = Constants.DEFAULT_NAME_MIN_LENGTH, max = Constants.DEFAULT_NAME_MAX_LENGTH, message = "{size.not.valid}")
	@Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "{not.valid}")
	private String content;
	
	public I18nModel() {
		
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
