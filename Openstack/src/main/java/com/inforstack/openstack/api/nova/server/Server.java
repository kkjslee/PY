package com.inforstack.openstack.api.nova.server;

import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.image.Image;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Server {
	
	private String id;
	
	private String name;
	
	private String status;
	
	@JsonProperty("user_id")
	private String user;
	
	@JsonProperty("tenant_id")
	private String tenant;
	
	private String hostId;
	
	@JsonProperty("OS-EXT-SRV-ATTR:host")
	private String hostName;
	
	@JsonProperty("key_name")
	private String key;
	
	private Image image;
	
	private Flavor flavor;
	
	private Date updated;
	
	private Map<String, String> metadata;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Flavor getFlavor() {
		return flavor;
	}

	public void setFlavor(Flavor flavor) {
		this.flavor = flavor;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

}
