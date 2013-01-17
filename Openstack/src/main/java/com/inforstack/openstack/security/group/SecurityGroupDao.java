package com.inforstack.openstack.security.group;

public interface SecurityGroupDao {

	public void persist(SecurityGroup securityGroup);

	public void merge(SecurityGroup securityGroup);

	public SecurityGroup findById(Integer securityGroupId);

	public void remove(SecurityGroup securityGroup);

}
