package com.inforstack.openstack.controller.model;

import java.util.Date;
import java.util.Map;

public class VolumeModel {

	private String id;
	
	private String subOrderId;
	
	private String name;

	private String description;
	
	private Integer size;
	
	private boolean bootable;
	
	private String type;
	
	private Map<String, String> metadata;
	
	private String zone;
	
	private String snapshot;
	
	private String source;
	
	private Date created;
	
	private String status;
	
	private AttachmentModel[] attachments;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public boolean isBootable() {
		return bootable;
	}

	public void setBootable(boolean bootable) {
		this.bootable = bootable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AttachmentModel[] getAttachments() {
		return attachments;
	}

	public void setAttachments(AttachmentModel[] attachments) {
		this.attachments = attachments;
	}
	
}
