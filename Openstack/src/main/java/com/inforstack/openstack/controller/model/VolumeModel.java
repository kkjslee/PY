package com.inforstack.openstack.controller.model;

import java.util.Date;

import com.inforstack.openstack.utils.OpenstackUtil;

public class VolumeModel {

	private String id;

	private Integer subOrderId;

	private String name;

	private Integer size;

	private String zone;

	private String snapshot;

	private String source;

	private Date created;

	private String status;

	private AttachmentModel attachment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(Integer subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
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

	public AttachmentModel getAttachment() {
		return attachment;
	}

	public void setAttachment(AttachmentModel attachment) {
		this.attachment = attachment;
	}

	public String getStatusDisplay() {
		if (status != null) {
			return OpenstackUtil.getMessage(status + ".label");
		} else {
			return null;
		}
	}

}
