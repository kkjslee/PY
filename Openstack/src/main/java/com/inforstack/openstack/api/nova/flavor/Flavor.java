package com.inforstack.openstack.api.nova.flavor;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Flavor {
	
	private String id;
	
	private String name;
	
	private int vcpus;
	
	private int ram;
	
	private int disk;
	
	@JsonProperty("OS-FLV-EXT-DATA:ephemeral")
	private int ephemeral;
	
	@JsonProperty("rxtx_factor")
	private int factor;
	
	private String swap;
	
	@JsonProperty("OS-FLV-DISABLED:disabled")
	private boolean disabled;
	
	@JsonProperty("os-flavor-access:is_public")
	private boolean isPublic;

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

	public int getVcpus() {
		return vcpus;
	}

	public void setVcpus(int vcpus) {
		this.vcpus = vcpus;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	public int getEphemeral() {
		return ephemeral;
	}

	public void setEphemeral(int ephemeral) {
		this.ephemeral = ephemeral;
	}

	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}

	public String getSwap() {
		return swap;
	}

	public void setSwap(String swap) {
		this.swap = swap;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

}
