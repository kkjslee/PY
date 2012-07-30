package com.infosense.ibilling.server.ws;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

import com.infosense.ibilling.server.process.AgeingDTOEx;


/**
 * AgeingWS
 */
public class AgeingWS implements WSSecured, Serializable {
	
	@NotNull(message="validation.error.notnull")
	private Integer statusId = null;
    private String statusStr = null;
    @NotNull(message="validation.error.notnull")
    private String welcomeMessage = null;
    @NotNull(message="validation.error.notnull")
    private String failedLoginMessage = null;
    private Boolean inUse = null;
    private Integer days;
    private Integer entityId;
    
    /**
     * Default constructor
     */
    public AgeingWS(){}
    
    /**
     * Constructor from AgeingDTOEx
     */
	public AgeingWS(AgeingDTOEx dto) {
		super();
		this.statusId = dto.getStatusId();
		this.statusStr = dto.getStatusStr();
		this.welcomeMessage = dto.getWelcomeMessage();
		this.failedLoginMessage = dto.getFailedLoginMessage();
		this.inUse = dto.getInUse();
		this.days = dto.getDays();
		this.entityId= (null != dto.getCompany()) ? dto.getCompany().getId() : null;
	}
	
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	public String getFailedLoginMessage() {
		return failedLoginMessage;
	}
	public void setFailedLoginMessage(String failedLoginMessage) {
		this.failedLoginMessage = failedLoginMessage;
	}
	public Boolean getInUse() {
		return inUse;
	}
	public void setInUse(Boolean inUse) {
		this.inUse = inUse;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	
	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Integer getOwningEntityId() {
        return getEntityId();
    }
    /**
     * Unsupported, web-service security enforced using {@link #getOwningEntityId()}
     * @return null
     */
    public Integer getOwningUserId() {
        return null;
    }

	
	public String toString() {
		return "AgeingWS [statusId=" + statusId + ", statusStr=" + statusStr
				+ ", welcomeMessage=" + welcomeMessage
				+ ", failedLoginMessage=" + failedLoginMessage + ", inUse="
				+ inUse + ", days=" + days + "]";
	}

}
