package com.inforstack.openstack.api.nova.host;

public class Hypervisor {
	
	public static final class CPUInfo {

		public static final class Topology {
			
			private int cores;
			
			private int threads;
			
			private int sockets;

			public int getCores() {
				return cores;
			}

			public void setCores(int cores) {
				this.cores = cores;
			}

			public int getThreads() {
				return threads;
			}

			public void setThreads(int threads) {
				this.threads = threads;
			}

			public int getSockets() {
				return sockets;
			}

			public void setSockets(int sockets) {
				this.sockets = sockets;
			}
			
		}
		
		private String vendor;
		
		private String model;
		
		private String arch;
		
		private String[] fearures;
		
		private Topology topology;

		public String getVendor() {
			return vendor;
		}

		public void setVendor(String vendor) {
			this.vendor = vendor;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getArch() {
			return arch;
		}

		public void setArch(String arch) {
			this.arch = arch;
		}

		public String[] getFearures() {
			return fearures;
		}

		public void setFearures(String[] fearures) {
			this.fearures = fearures;
		}

		public Topology getTopology() {
			return topology;
		}

		public void setTopology(Topology topology) {
			this.topology = topology;
		}
		
	}
	
	private int id;
	
	private Host host;
	
	private String hypervisor_type;
	
	private String hypervisor_version;
	
	private int running_vms;
	
	private CPUInfo cpu_info;
	
	private int vcpus_used;
	
	private int vcpus;
	
	private int local_gb_used;
	
	private int local_gb;
	
	private int free_disck_gb;
	
	private int disk_available_least;
	
	private int memory_mb_used;
	
	private int memory_mb;
	
	private int free_ram_mb;
	
	private int current_workload;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public String getHypervisor_type() {
		return hypervisor_type;
	}

	public void setHypervisor_type(String hypervisor_type) {
		this.hypervisor_type = hypervisor_type;
	}

	public String getHypervisor_version() {
		return hypervisor_version;
	}

	public void setHypervisor_version(String hypervisor_version) {
		this.hypervisor_version = hypervisor_version;
	}

	public int getRunning_vms() {
		return running_vms;
	}

	public void setRunning_vms(int running_vms) {
		this.running_vms = running_vms;
	}

	public CPUInfo getCpu_info() {
		return cpu_info;
	}

	public void setCpu_info(CPUInfo cpu_info) {
		this.cpu_info = cpu_info;
	}

	public int getVcpus_used() {
		return vcpus_used;
	}

	public void setVcpus_used(int vcpus_used) {
		this.vcpus_used = vcpus_used;
	}

	public int getVcpus() {
		return vcpus;
	}

	public void setVcpus(int vcpus) {
		this.vcpus = vcpus;
	}

	public int getLocal_gb_used() {
		return local_gb_used;
	}

	public void setLocal_gb_used(int local_gb_used) {
		this.local_gb_used = local_gb_used;
	}

	public int getLocal_gb() {
		return local_gb;
	}

	public void setLocal_gb(int local_gb) {
		this.local_gb = local_gb;
	}

	public int getFree_disck_gb() {
		return free_disck_gb;
	}

	public void setFree_disck_gb(int free_disck_gb) {
		this.free_disck_gb = free_disck_gb;
	}

	public int getDisk_available_least() {
		return disk_available_least;
	}

	public void setDisk_available_least(int disk_available_least) {
		this.disk_available_least = disk_available_least;
	}

	public int getMemory_mb_used() {
		return memory_mb_used;
	}

	public void setMemory_mb_used(int memory_mb_used) {
		this.memory_mb_used = memory_mb_used;
	}

	public int getMemory_mb() {
		return memory_mb;
	}

	public void setMemory_mb(int memory_mb) {
		this.memory_mb = memory_mb;
	}

	public int getFree_ram_mb() {
		return free_ram_mb;
	}

	public void setFree_ram_mb(int free_ram_mb) {
		this.free_ram_mb = free_ram_mb;
	}

	public int getCurrent_workload() {
		return current_workload;
	}

	public void setCurrent_workload(int current_workload) {
		this.current_workload = current_workload;
	}

}
