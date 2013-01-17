package com.inforstack.openstack.security.group;

import java.util.List;
import java.util.Map;

import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.i18n.link.I18nLink;
import com.inforstack.openstack.security.permission.Permission;

public interface SecurityGroupService {
	
	/**
	 * 
	 * @param securityGroup managed security group
	 * @return null if create failed
	 */
	public SecurityGroup createSecurityGroup(SecurityGroup securityGroup);
	
	/**
	 * 
	 * @param i18nLink managed i18n link
	 * @param name
	 * @return null if failed
	 * @throws ApplicationRuntimeException
	 */
	public SecurityGroup createSecuirtyGroup(I18nLink i18nLink, String name);
	
	/**
	 * 
	 * @param name
	 * @param languageId
	 * @param description
	 * @return null if failed
	 * @throws ApplicationException
	 */
	public SecurityGroup createSecurityGroup(String name, Integer languageId, String description);
	
	/**
	 * 
	 * @param name
	 * @param descriptionMap
	 * @return null if failed
	 * @throws ApplicationException
	 */
	public SecurityGroup createSecurityGroup(String name, Map<Integer, String> descriptionMap);
	
	/**
	 * 
	 * @param securityGroup managed security group
	 * @return null if failed
	 */
	public SecurityGroup updateSecurityGroup(SecurityGroup securityGroup);
	
	/**
	 * 
	 * @param securityGroupId
	 * @param descriptionMap
	 * @param valid
	 * @return null if failed
	 */
	public SecurityGroup updateSecurityGroup(Integer securityGroupId, Map<Integer, String> descriptionMap, Boolean valid);
	
	/**
	 * 
	 * @param securityGroup managed security group
	 * @return null if failed
	 */
	public SecurityGroup removeSecurityGroup(SecurityGroup securityGroup);
	
	/**
	 * 
	 * @param securityGroupId
	 * @return null if failed
	 */
	public SecurityGroup removeSecurityGroup(Integer securityGroupId);
	
	/**
	 * 
	 * @param securityGroup managed security group
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup addPermissions(SecurityGroup securityGroup, List<Permission> permissions);
	
	/**
	 * 
	 * @param securityGroup managed security group
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup addPermissionsByPermissionId(SecurityGroup securityGroup, List<Integer> permissions);
	
	/**
	 * 
	 * @param securityGroupId
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup addPermissionsByPermissionId(Integer securityGroupId, List<Integer> permissions);
	
	/**
	 * 
	 * @param securityGroup managed security group
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup updatePermissions(SecurityGroup securityGroup, List<Permission> permissions);
	
	/**
	 * 
	 * @param securityGroup managed security group
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup updatePermissionsByPermissionId(SecurityGroup securityGroup, List<Integer> permissions);
	
	/**
	 * 
	 * @param securityGroupId
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup updatePermissionsByPermissionId(Integer securityGroupId, List<Integer> permissions);
}
