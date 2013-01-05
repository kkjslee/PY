package com.inforstack.openstack.api.host;

public class ProjectResource {
	
	private String project;
	
	private String host;
	
	private int memory_mb;
	
	private int cpu;
	
	private int disk_gb;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getMemory_mb() {
		return memory_mb;
	}

	public void setMemory_mb(int memory_mb) {
		this.memory_mb = memory_mb;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getDisk_gb() {
		return disk_gb;
	}

	public void setDisk_gb(int disk_gb) {
		this.disk_gb = disk_gb;
	}

}
