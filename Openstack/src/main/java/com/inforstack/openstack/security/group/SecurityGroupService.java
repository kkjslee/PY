package com.inforstack.openstack.security.group;

import java.util.List;
import java.util.Map;

import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.i18n.link.I18nLink;
import com.inforstack.openstack.security.permission.Permission;

public interface SecurityGroupService {
	
	/**
	 * create security group
	 * @param securityGroup managed security group
	 * @return null if create failed
	 */
	public SecurityGroup createSecurityGroup(SecurityGroup securityGroup);
	
	/**
	 * create security group
	 * @param i18nLink managed i18n link
	 * @param name
	 * @return null if failed
	 * @throws ApplicationRuntimeException
	 */
	public SecurityGroup createSecuirtyGroup(I18nLink i18nLink, String name);
	
	/**
	 * create security group
	 * @param name
	 * @param languageId
	 * @param description
	 * @return null if failed
	 * @throws ApplicationException
	 */
	public SecurityGroup createSecurityGroup(String name, int languageId, String description);
	
	/**
	 * create secuirty group
	 * @param name
	 * @param descriptionMap
	 * @return null if failed
	 * @throws ApplicationException
	 */
	public SecurityGroup createSecurityGroup(String name, Map<Integer, String> descriptionMap);
	
	/**
	 * update secuirty group
	 * @param securityGroup managed security group
	 * @return null if failed
	 */
	public SecurityGroup updateSecurityGroup(SecurityGroup securityGroup);
	
	/**
	 * update security group
	 * @param securityGroupId
	 * @param descriptionMap
	 * @param valid
	 * @return null if failed
	 */
	public SecurityGroup updateSecurityGroup(int securityGroupId, Map<Integer, String> descriptionMap, Boolean valid);
	
	/**
	 * remove seucirity group
	 * @param securityGroup managed security group
	 * @return null if failed
	 */
	public SecurityGroup removeSecurityGroup(SecurityGroup securityGroup);
	
	/**
	 * remove security group by id
	 * @param securityGroupId
	 * @return null if failed
	 */
	public SecurityGroup removeSecurityGroup(int securityGroupId);
	
	/**
	 * add permission to security group
	 * @param securityGroup managed security group
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup addPermissions(SecurityGroup securityGroup, List<Permission> permissions);
	
	/**
	 * add permission to security group
	 * @param securityGroup managed security group
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup addPermissionsByPermissionId(SecurityGroup securityGroup, List<Integer> permissions);
	
	/**
	 * add permissions to security group
	 * @param securityGroupId
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup addPermissionsByPermissionId(int securityGroupId, List<Integer> permissions);
	
	/**
	 * 
	 * @param securityGroup managed security group
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup updatePermissions(SecurityGroup securityGroup, List<Permission> permissions);
	
	/**
	 * update security group permissions
	 * @param securityGroup managed security group
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup updatePermissionsByPermissionId(SecurityGroup securityGroup, List<Integer> permissions);
	
	/**
	 * update security group permissions
	 * @param securityGroupId
	 * @param permissions
	 * @return null if failed
	 */
	public SecurityGroup updatePermissionsByPermissionId(int securityGroupId, List<Integer> permissions);
}
