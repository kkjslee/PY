package com.inforstack.openstack.api.host;

import org.codehaus.jackson.annotate.JsonProperty;

public class HostDescribe {
	
	public static final class ProjectResource {
		
		private String project;
		
		private String host;
		
		@JsonProperty("memory_mb")
		private int memory;
		
		private int cpu;
		
		@JsonProperty("disk_gb")
		private int disk;

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

		public int getMemory() {
			return memory;
		}

		public void setMemory(int memory) {
			this.memory = memory;
		}

		public int getCpu() {
			return cpu;
		}

		public void setCpu(int cpu) {
			this.cpu = cpu;
		}

		public int getDisk() {
			return disk;
		}

		public void setDisk(int disk) {
			this.disk = disk;
		}

	}
	
	private ProjectResource resource;

	public ProjectResource getResource() {
		return resource;
	}

	public void setResource(ProjectResource resource) {
		this.resource = resource;
	}

}
