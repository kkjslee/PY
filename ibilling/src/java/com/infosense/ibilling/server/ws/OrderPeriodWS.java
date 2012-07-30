package com.infosense.ibilling.server.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.infosense.ibilling.server.order.db.OrderPeriodDTO;

/**
 * OrderPeriodWS
 */

public class OrderPeriodWS implements Serializable {

    private Integer id;
    private Integer entityId;
    
    private Integer periodUnitId;
    
    @Min(value = 1, message = "validation.error.min,1")
    @Digits(integer=3, fraction=0, message="validation.error.not.a.number")
    private Integer value;
    private Integer versionNum;
    
    private List<InternationalDescriptionWS> descriptions = new ArrayList<InternationalDescriptionWS>();

    public OrderPeriodWS() {
    }

    public OrderPeriodWS(Integer id, Integer entityId, Integer periodUnitId, Integer value) {
       this.id = id;
       this.entityId = entityId;
       this.periodUnitId= periodUnitId;
       this.value = value;
    }

    public OrderPeriodWS(OrderPeriodDTO dto) {
    	this.id= dto.getId();
    	this.entityId= dto.getCompany().getId();
    	this.value=dto.getValue();
    	this.periodUnitId= dto.getPeriodUnit().getId();
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Integer getPeriodUnitId() {
		return periodUnitId;
	}

	public void setPeriodUnitId(Integer periodUnitId) {
		this.periodUnitId = periodUnitId;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
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

	public String toString() {
		return "OrderPeriodWS [id=" + id + ", entityId=" + entityId
				+ ", periodUnitId=" + periodUnitId + ", value=" + value
				+ ", descriptions=" + descriptions + "]";
	}

	public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }
    
}


