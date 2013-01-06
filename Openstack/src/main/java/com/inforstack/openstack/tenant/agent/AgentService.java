package com.inforstack.openstack.tenant.agent;

public interface AgentService {

	public Agent findAgentById(String agentId);

	public Agent findAgentTenantByAgentId(String agentId);

}
