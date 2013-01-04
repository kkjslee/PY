package com.inforstack.openstack.api.host;

public class CPUInfo {

	class Topology {
		
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
