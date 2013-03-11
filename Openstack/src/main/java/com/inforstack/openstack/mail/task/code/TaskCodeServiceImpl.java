package com.inforstack.openstack.mail.task.code;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.h2.util.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.utils.CryptoUtil;
import com.inforstack.openstack.utils.MapUtil;

@Service
@Transactional
public class TaskCodeServiceImpl implements TaskCodeService {
	
	@Autowired
	private TaskCodeDao dao;
	
	@Override
	public TaskCode findTaskCode(String mailCode, String random){
		return dao.findByCodeAndRandom(mailCode, random);
	}
	
	@Override
	public TaskCode createTaskCode(String mailCode, String entityId, Map<String, String> properties){
		TaskCode tc = new TaskCode();
		tc.setCode(mailCode);
		tc.setCreateTime(new Date());
		tc.setEntityId(entityId);
		tc.setProperties(MapUtil.buildPropStr(properties));
		tc.setRandom(CryptoUtil.xorHex(UUID.randomUUID().toString(), mailCode));
		
		dao.persist(tc);
		return tc;
	}
}
