package com.inforstack.openstack.security.group;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.i18n.I18n;
import com.inforstack.openstack.i18n.I18nService;
import com.inforstack.openstack.i18n.link.I18nLink;
import com.inforstack.openstack.i18n.link.I18nLinkService;
import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.security.permission.PermissionService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;
import com.inforstack.openstack.utils.StringUtil;

@Service("securityGroupService")
@Transactional
public class SecurityGroupServiceImpl implements SecurityGroupService{
	
	private static final Log log = LogFactory.getLog(SecurityGroupServiceImpl.class);
	
	@Autowired
	private SecurityGroupDao securityGroupDao;
	@Autowired
	private I18nService i18nService;
	@Autowired
	private I18nLinkService i18nLinkService;
	@Autowired
	private PermissionService permissionService;

	@Override
	public SecurityGroup createSecurityGroup(SecurityGroup securityGroup) {
		if(securityGroup == null){
			log.info("Create security group failed for null is passed");
			return null;
		}
		
		log.debug("Create security group with name : " + securityGroup.getName());
		securityGroupDao.persist(securityGroup);
		log.debug("Create successfully");
		return securityGroup;
	}
	
	@Override
	public SecurityGroup createSecurityGroup(String name, Integer languageId, String description) {
		if(StringUtils.isNullOrEmpty(name) || languageId==null || description==null){
			log.info("Create security group failed for passed name/language/description is null or empty");
			return null;
		}
		
		log.debug("Create security group with name : " + name + ", language : " + languageId + ", description : " + description);
		
		I18n i18n = i18nService.createI18n(languageId, description,
				Constants.TABLE_SECURITY_GROUP,
				Constants.COLUMN_SECURITY_GROUP_DESCRIPTION);
		if(i18n==null){
			log.info("Create security group failed for create i18n failed");
			return null;
		}
		Tenant tenant = SecurityUtils.getTenant();
		if(tenant == null){
			log.error("Create secuirty group failed for tenant is not found");
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("tenant.not.found"));
		}
		SecurityGroup sg = new SecurityGroup();
		sg.setDescription(i18n.getI18nLink());
		sg.setName(name);
		sg.setTenant(tenant);
		SecurityGroupService self = (SecurityGroupService)OpenstackUtil.getBean("securityGroupService");
		sg = self.createSecurityGroup(sg);
		if(sg==null){
			log.debug("Create failed");
		}else{
			log.debug("Create successfully");
		}
		
		return sg;
	}
	
	@Override
	public SecurityGroup createSecuirtyGroup(I18nLink i18nLink, String name) {
		if(i18nLink==null || StringUtil.isNullOrEmpty(name)){
			log.info("Create security group failed for passed name/i18nLink is null or empty");
			return null;
		}
		log.debug("Create security group with i18n link : " + i18nLink.getId() + ", name : " + name);
		
		Tenant tenant = SecurityUtils.getTenant();
		if(tenant == null){
			log.error("Create secuirty group failed for tenant is not found");
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("tenant.not.found"));
		}
		
		SecurityGroup sg = new SecurityGroup();
		sg.setDescription(i18nLink);
		sg.setName(name);
		sg.setTenant(tenant);
		SecurityGroupService self = (SecurityGroupService)OpenstackUtil.getBean("securityGroupService");
		sg = self.createSecurityGroup(sg);
		if(sg==null){
			log.debug("Create failed");
		}else{
			log.debug("Create successfully");
		}
		
		return sg;
	}
	
	@Override
	public SecurityGroup createSecurityGroup(String name, Map<Integer, String> descriptionMap) {
		if(StringUtils.isNullOrEmpty(name) || descriptionMap==null || descriptionMap.isEmpty()){
			log.info("Create security group failed for passed name/descriptionMap is null or empty");
			return null;
		}
		
		log.debug("Create security group with name : " + name);
		List<I18n> i18nLst = i18nService.createI18n(descriptionMap,
				Constants.TABLE_SECURITY_GROUP,
				Constants.COLUMN_SECURITY_GROUP_DESCRIPTION);
		if(i18nLst == null || i18nLst.isEmpty()){
			log.info("Create security group failed for create i18n failed");
			return null;
		}
		SecurityGroupService self = (SecurityGroupService)OpenstackUtil.getBean("securityGroupService");
		SecurityGroup sg = self.createSecuirtyGroup(i18nLst.get(0).getI18nLink(), name);
		if(sg == null){
			log.info("Create failed");
		}else{
			log.info("Create successfully");
		}
		
		return sg;
	}
	
	@Override
	public SecurityGroup updateSecurityGroup(SecurityGroup securityGroup){
		if(securityGroup == null){
			log.info("Update security group failed for null is passed");
			return null;
		}
		
		log.debug("Update security group with name : " + securityGroup.getName());
		securityGroupDao.merge(securityGroup);
		log.debug("Update successfully");
		return securityGroup;
	}

	@Override
	public SecurityGroup updateSecurityGroup(Integer securityGroupId,
			Map<Integer, String> descriptionMap, Boolean valid) {
		if(securityGroupId == null){
			log.info("Update security group failed for passed id is null");
			return null;
		}
		
		log.debug("Update security group with id : " + securityGroupId);
		SecurityGroup sg = securityGroupDao.findById(securityGroupId);
		if(sg==null){
			log.info("Update security group failed for no security group found by id : " + securityGroupId);
			return null;
		}
		
		if(descriptionMap != null && !descriptionMap.isEmpty()){
			I18nLink link = sg.getDescription();
			if(link == null){
				List<I18n> i18nLst = i18nService.createI18n(descriptionMap,
						Constants.TABLE_SECURITY_GROUP,
						Constants.COLUMN_SECURITY_GROUP_DESCRIPTION);
				if(i18nLst == null || i18nLst.isEmpty()){
					log.info("Update security group failed for create i18n failed");
					return null;
				}
				link = i18nLst.get(0).getI18nLink();
			}else{
				i18nService.updateOrCreateI18n(link, descriptionMap);
			}
			sg.setDescription(link);
		}
		if(valid != null){
			sg.setValid(valid);
		}
		
		log.debug("Update successfully");
		return sg;
	}
	
	@Override
	public SecurityGroup removeSecurityGroup(SecurityGroup securityGroup){
		if(securityGroup == null){
			log.info("Remove security group failed for passed securityGroup is null");
			return null;
		}
		
		log.debug("Remove security group with name : " + securityGroup.getName());
		securityGroupDao.remove(securityGroup);
		log.debug("Remove successfully");
		return securityGroup;
	}

	@Override
	public SecurityGroup removeSecurityGroup(Integer securityGroupId) {
		if(securityGroupId == null){
			log.info("Remove security group failed for passed securityGroupId is null");
			return null;
		}
		
		log.debug("Remove security group by id : " + securityGroupId);
		SecurityGroupService self = (SecurityGroupService)OpenstackUtil.getBean("securityGroupService");
		SecurityGroup sg = securityGroupDao.findById(securityGroupId);
		if(sg == null){
			log.info("Remove security group failed for no security group instance found by id " + securityGroupId);
			return null;
		}
		sg = self.removeSecurityGroup(sg);
		log.debug("Remove successfully");
		return sg;
	}

	@Override
	public SecurityGroup addPermissions(SecurityGroup securityGroup,
			List<Permission> permissions) {
		if(securityGroup == null){
			log.info("Add permissions failed for passed security group is null");
			return null;
		}
		
		log.debug("Add permissions to security group with name : " + securityGroup.getName());
		securityGroup.setPermissions(permissions);
		log.debug("Add seccessfully");
		return securityGroup;
	}

	@Override
	public SecurityGroup addPermissionsByPermissionId(
			SecurityGroup securityGroup, List<Integer> permissions) {
		if(securityGroup == null){
			log.info("Add permissions failed for passed security group is null");
			return null;
		}
		log.debug("Add permissions to security group with name : " + securityGroup.getName());
		for(Integer pId : permissions){
			Permission p = permissionService.findPermissionById(pId);
			if(p==null){
				log.warn("No permission found for permission id : " + pId);
			}else{
				securityGroup.getPermissions().add(p);
			}
		}
		log.debug("Add seccessfully");
		return securityGroup;
	}

	@Override
	public SecurityGroup addPermissionsByPermissionId(Integer securityGroupId,
			List<Integer> permissions) {
		if(securityGroupId == null){
			log.info("Add permissions failed for passed security group id is null");
			return null;
		}
		
		log.debug("Add permissions to security group with id : " + securityGroupId);
		SecurityGroup sg = securityGroupDao.findById(securityGroupId);
		if(sg == null){
			log.info("Add permission failed for no security group found with id : " + securityGroupId);
			return null;
		}
		
		SecurityGroupService self = (SecurityGroupService)OpenstackUtil.getBean("securityGroupService");
		sg = self.addPermissionsByPermissionId(sg, permissions);
		if(sg==null){
			log.debug("Add permissions failed");
		}else{
			log.debug("Add permissions successfully");
		}
		
		return sg;
	}

	@Override
	public SecurityGroup updatePermissions(SecurityGroup securityGroup,
			List<Permission> permissions) {
		if(securityGroup == null){
			log.info("Update permissions failed for passed security group is null");
			return null;
		}
		
		log.debug("Update permissions to security group with name " + securityGroup.getName());
		securityGroup.getPermissions().clear();
		securityGroup.setPermissions(permissions);
		log.debug("Update permissions successfully");
		
		return securityGroup;
	}

	@Override
	public SecurityGroup updatePermissionsByPermissionId(
			SecurityGroup securityGroup, List<Integer> permissions) {
		if(securityGroup == null){
			log.info("Update permissions failed for passed security group is null");
			return null;
		}
		log.debug("Update permissions to security group with name : " + securityGroup.getName());
		securityGroup.getPermissions().clear();
		SecurityGroupService self = (SecurityGroupService)OpenstackUtil.getBean("securityGroupService");
		securityGroup = self.addPermissionsByPermissionId(securityGroup, permissions);
		if(securityGroup == null){
			log.debug("Update Permission failed for add permission to security group failed");
			throw new ApplicationRuntimeException("Add perssion to security group failed");
		}
		log.debug("Update successfully");
		return securityGroup;
	}

	@Override
	public SecurityGroup updatePermissionsByPermissionId(
			Integer securityGroupId, List<Integer> permissions) {
		if(securityGroupId == null){
			log.info("Update permissions failed for passed security group id is null");
			return null;
		}
		
		log.debug("Update permissions to security group with id : " + securityGroupId);
		SecurityGroup sg = securityGroupDao.findById(securityGroupId);
		if(sg == null){
			log.info("Update permission failed for no security group found with id : " + securityGroupId);
			return null;
		}
		SecurityGroupService self = (SecurityGroupService)OpenstackUtil.getBean("securityGroupService");
		sg = self.updatePermissionsByPermissionId(sg, permissions);
		
		if(sg==null){
			log.debug("Update permissions failed");
		}else{
			log.debug("Update permissions successfully");
		}
		
		return sg;
	}
}
