package com.inforstack.openstack.api.cinder;

import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Volume {
	
	private String id;
	
	@JsonProperty("display_name")
	private String name;
	
	@JsonProperty("display_description")
	private String description;
	
	private int size;
	
	private boolean bootable;
	
	@JsonProperty("volume_type")
	private String type;
	
	private Map<String, String> metadata;
	
	@JsonProperty("availability_zone")
	private String zone;
	
	@JsonProperty("snapshot_id")
	private String snapshot;
	
	@JsonProperty("source_volid")
	private String source;
	
	@JsonProperty("created_at")
	private Date created;
	
	private String status;
	
	private Attachment[] attachments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
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

	public Attachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(Attachment[] attachments) {
		this.attachments = attachments;
	}

}
