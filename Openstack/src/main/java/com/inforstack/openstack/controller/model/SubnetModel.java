package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.inforstack.openstack.api.quantum.Subnet.AllocationPool;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.StringUtil;

public class SubnetModel {

	private String id;

	@Size(min = 6, max = 45, message = "{size.not.valid}")
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$", message = "{not.valid}")
	private String name;

	private String tenant;

	private String network;

	private int ipVersion;

	private String gateway;

	private boolean disableGateway;

	private boolean enableDHCP;

	private String cidr;

	private String dnsNamesString;

	private String hostRoutesString;

	private AllocationPool[] pools;

	private String poolString;

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

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public int getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(int ipVersion) {
		this.ipVersion = ipVersion;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public AllocationPool[] getPoolsFormat() {
		AllocationPool[] poolsTemp = null;
		if (!StringUtil.isNullOrEmpty(poolString, true)) {
			poolString = poolString.replaceAll("\\s*", "");
			String[] poolArray = poolString.split(Constants.POOLS_SPLITTER);
			String[] ipArray = null;
			if (poolArray != null && poolArray.length > 0) {
				int length = poolArray.length;
				poolsTemp = new AllocationPool[length];
				AllocationPool pool = null;
				int j = 0;
				for (int i = 0; i < length; i++) {
					if (poolArray[i].contains(",")) {
						ipArray = poolArray[i].split(Constants.IP_SPLITTER);
						// TODO ip validation
						if (ipArray.length == 2) {
							pool = new AllocationPool();
							pool.setStart(ipArray[0]);
							pool.setEnd(ipArray[1]);
							poolsTemp[j] = pool;
							j++;
						}
					}

				}

			}
			return poolsTemp;
		} else {
			return pools;
		}

	}

	public AllocationPool[] getPools() {
		return pools;
	}

	public void setPools(AllocationPool[] pools) {
		this.pools = pools;
	}

	public boolean getDisableGateway() {
		return disableGateway;
	}

	public void setDisableGateway(boolean disableGateway) {
		this.disableGateway = disableGateway;
	}

	public boolean getEnableDHCP() {
		return enableDHCP;
	}

	public void setEnableDHCP(boolean enableDHCP) {
		this.enableDHCP = enableDHCP;
	}

	public String getPoolStringDisplay() {
		String poolStringTemp = "";
		if (pools != null && pools.length > 0) {
			int length = pools.length;
			for (int i = 0; i < length; i++) {
				poolStringTemp = poolStringTemp + pools[i].getStart()
						+ Constants.IP_SPLITTER + pools[i].getEnd();
				if (i < length - 1) {
					poolStringTemp = poolStringTemp + Constants.POOLS_SPLITTER;
				}
			}
			return poolStringTemp;
		} else {
			return poolString;
		}

	}

	public String getPoolString() {
		return poolString;
	}

	public void setPoolString(String poolString) {
		this.poolString = poolString;
	}

	public String getDnsNamesString() {
		return dnsNamesString;
	}

	public void setDnsNamesString(String dnsNamesString) {
		this.dnsNamesString = dnsNamesString;
	}

	public String getHostRoutesString() {
		return hostRoutesString;
	}

	public void setHostRoutesString(String hostRoutesString) {
		this.hostRoutesString = hostRoutesString;
	}

}
