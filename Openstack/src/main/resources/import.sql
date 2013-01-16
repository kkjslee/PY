-- You can use this file to load seed data into the database using SQL statements
insert into Language(id, name, language, country) values (1, 'English', 'en', 'US')
insert into Language(id, name, language, country) values (2, '中文', 'zh', 'CN')

insert into Configuration(id, name, value) values(1, 'openstack.tenant.admin', 'ec73d58df01b43e48aa41549953df091')
insert into Configuration(id, name, value) values(2, 'openstack.tenant.demo', '4f1cb88c9e4449ca90ae33ef20c38c34')
insert into Configuration(id, name, value) values(3, 'openstack.user.admin.name', 'admin')
insert into Configuration(id, name, value) values(4, 'openstack.user.admin.pass', '5f4dcc3b5aa765d61d8327deb882cf99')
insert into Configuration(id, name, value) values(5, 'openstack.endpoint.tokens', 'http://192.168.1.145:5000/v2.0/tokens')
insert into Configuration(id, name, value) values(6, 'openstack.endpoint.tenants', 'http://192.168.1.145:35357/v2.0/tenants')
insert into Configuration(id, name, value) values(7, 'openstack.endpoint.tenant', 'http://192.168.1.145:35357/v2.0/tenants/{tenant}')
insert into Configuration(id, name, value) values(8, 'openstack.endpoint.users', 'http://192.168.1.145:35357/v2.0/users')
insert into Configuration(id, name, value) values(9, 'openstack.endpoint.user', 'http://192.168.1.145:35357/v2.0/users/{user}')
insert into Configuration(id, name, value) values(10, 'openstack.endpoint.role', 'http://192.168.1.145:35357/v2.0/tenants/{tenant}/users/{user}/roles/OS-KSADM/{role}')
insert into Configuration(id, name, value) values(11, 'openstack.endpoint.hosts', 'http://192.168.1.145:8774/v2/{tenant}/os-hosts')
insert into Configuration(id, name, value) values(12, 'openstack.endpoint.host.describe', 'http://192.168.1.145:8774/v2/{tenant}/os-hosts/{host}')
insert into Configuration(id, name, value) values(13, 'openstack.endpoint.flavors', 'http://192.168.1.145:8774/v2/{tenant}/flavors')
insert into Configuration(id, name, value) values(14, 'openstack.endpoint.flavors.detail', 'http://192.168.1.145:8774/v2/{tenant}/flavors/detail')
insert into Configuration(id, name, value) values(15, 'openstack.endpoint.flavor', 'http://192.168.1.145:8774/v2/{tenant}/flavors/{flavor}')
insert into Configuration(id, name, value) values(16, 'openstack.endpoint.images', 'http://192.168.1.145:8774/v2/{tenant}/images')
insert into Configuration(id, name, value) values(17, 'openstack.endpoint.images.detail', 'http://192.168.1.145:8774/v2/{tenant}/images/detail')
insert into Configuration(id, name, value) values(18, 'openstack.endpoint.image', 'http://192.168.1.145:8774/v2/{tenant}/images/{image}')
insert into Configuration(id, name, value) values(19, 'openstack.endpoint.servers', 'http://192.168.1.145:8774/v2/{tenant}/servers')
insert into Configuration(id, name, value) values(20, 'openstack.endpoint.servers.detail', 'http://192.168.1.145:8774/v2/{tenant}/servers/detail')
insert into Configuration(id, name, value) values(21, 'openstack.endpoint.server', 'http://192.168.1.145:8774/v2/{tenant}/servers/{server}')
insert into Configuration(id, name, value) values(22, 'openstack.endpoint.server.action', 'http://192.168.1.145:8774/v2/{tenant}/servers/{server}/action')
insert into Configuration(id, name, value) values(23, 'openstack.role.admin', '8442ba0ed8314961afda3ac43db505a6')
insert into Configuration(id, name, value) values(24, 'openstack.role.reseller', 'e86c7037af3046f0a207ddcdf0cb9e67')
insert into Configuration(id, name, value) values(25, 'openstack.role.member', '214afcc000ca4c8cb67ac05ceb563bae')
insert into Configuration(id, name, value) values(26, 'openstack.cache.expire', '2')

insert into Role(id, name) values (1, 'admin')
insert into Role(id, name) values (2, 'user')
insert into Role(id, name) values (3, 'agent')

insert into Tenant(id, name, dipalyName, role_id, ageing) values (1, 'admin', 'admin', 1, 1)

insert into Agent(uuid, commission, tenant_id) values ('1', 0.1, 1)

insert into User(id, name, password, firstName, lastName, role_id, defaultTenantId, status, ageing) values (1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin', 'admin', 1, 1, 1, 1)

insert into tenant_user(user_id, tenant_id) values (1, 1)

insert into Resource(id, url, permission) values (1, '/admin', 'admin')