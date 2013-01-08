package com.inforstack.openstack.tenant.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AgentServiceImpl implements AgentService {
	
	@Autowired
	private AgentDao agentDao;

	@Override
	public Agent findAgentById(String agentId) {
		return agentDao.findById(agentId);
	}

	@Override
	public Agent findAgentTenantByAgentId(String agentId) {
		Agent agent = agentDao.findById(agentId);
		if(agent != null){
			agent.getTenant().getId();
		}
		return agent;
	}

}