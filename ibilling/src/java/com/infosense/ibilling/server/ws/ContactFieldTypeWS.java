package com.infosense.ibilling.server.ws;

import java.util.ArrayList;
import java.util.List;

import com.infosense.ibilling.server.user.contact.db.ContactFieldTypeDAS;
import com.infosense.ibilling.server.user.contact.db.ContactFieldTypeDTO;
import com.infosense.ibilling.server.user.db.CompanyDTO;

public class ContactFieldTypeWS  implements java.io.Serializable {


	private Integer id;
	private Integer companyId;
	private String promptKey;
	private String dataType;
	private Integer readOnly;
	private List<InternationalDescriptionWS> descriptions = new ArrayList<InternationalDescriptionWS>();

    public ContactFieldTypeWS() {
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getPromptKey() {
		return promptKey;
	}

	public void setPromptKey(String promptKey) {
		this.promptKey = promptKey;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Integer readOnly) {
		this.readOnly = readOnly;
	}

	public List<InternationalDescriptionWS> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<InternationalDescriptionWS> descriptions) {
		this.descriptions = descriptions;
	}

	public InternationalDescriptionWS getDescription(Integer languageId) {
        for (InternationalDescriptionWS description : descriptions)
            if (description.getLanguageId().equals(languageId))
                return description;
        return null;
    }
	
	@Override
	public String toString() {
		return "ContactFieldTypeWS [id=" + id + ", companyId=" + companyId
				+ ", promptKey=" + promptKey + ", dataType=" + dataType
				+ ", readOnly=" + readOnly + ", descriptions=" + descriptions + "]";
	}
	
	public ContactFieldTypeDTO getDTO() { 
		
		ContactFieldTypeDTO dto= this.id == null ? new ContactFieldTypeDTO() : new ContactFieldTypeDAS().find(this.id);
		if ( null != dto ) { 
			dto.setDataType(this.dataType);
			dto.setEntity(new CompanyDTO(this.companyId));
			dto.setReadOnly(this.readOnly);
			dto.setVersionNum(0);
			//since Prompt key is not null
			if (null == this.promptKey) {
			    dto.setPromptKey("placeholder_text");
			} else {
			    dto.setPromptKey(this.promptKey);
			}
			if (this.descriptions != null && this.descriptions.size() > 0 ) {
				dto.setDescription(((InternationalDescriptionWS)this.descriptions.get(0)).getContent(), ((InternationalDescriptionWS)this.descriptions.get(0)).getLanguageId());
			}
		}
		return dto;
	}
	
}


