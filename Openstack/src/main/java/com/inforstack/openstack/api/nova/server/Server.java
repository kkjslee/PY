package com.inforstack.openstack.api.nova.server;

import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.image.Image;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Server {
	
	public static final class File {
		
		private String path;
		
		private String contents;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getContents() {
			return contents;
		}

		public void setContents(String contents) {
			this.contents = contents;
		}
		
	}
	
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
	
	private String imageRef;
	
	private Image image;
	
	private String flavorRef;
	
	private Flavor flavor;
	
	private Addresses addresses;
	
	@JsonProperty("security_groups")
	private SecurityGroup[] securityGroups;
	
	private File[] personality;
	
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

	public String getImageRef() {
		return imageRef;
	}

	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getFlavorRef() {
		return flavorRef;
	}

	public void setFlavorRef(String flavorRef) {
		this.flavorRef = flavorRef;
	}

	public Flavor getFlavor() {
		return flavor;
	}

	public void setFlavor(Flavor flavor) {
		this.flavor = flavor;
	}

	public Addresses getAddresses() {
		return addresses;
	}

	public void setAddresses(Addresses addresses) {
		this.addresses = addresses;
	}

	public SecurityGroup[] getSecurityGroups() {
		return securityGroups;
	}

	public void setSecurityGroups(SecurityGroup[] securityGroups) {
		this.securityGroups = securityGroups;
	}

	public File[] getPersonality() {
		return personality;
	}

	public void setPersonality(File[] personality) {
		this.personality = personality;
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
