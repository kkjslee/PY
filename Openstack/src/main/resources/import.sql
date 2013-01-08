-- You can use this file to load seed data into the database using SQL statements
insert into Language(id, name, language, country) values (1, 'English', 'en', 'US')
insert into Language(id, name, language, country) values (2, '中文', 'zh', 'CN')

insert into Configuration(id, name, value) values(1, 'openstack.tenant.admin', '8384b45a9ad34a5da3a0a2f8b12b99bb')
insert into Configuration(id, name, value) values(2, 'openstack.tenant.demo', 'b7c22b6d6610409e9f7d18ad5761112f')
insert into Configuration(id, name, value) values(3, 'openstack.user.admin.name', 'admin')
insert into Configuration(id, name, value) values(4, 'openstack.user.admin.pass', '5f4dcc3b5aa765d61d8327deb882cf99')
insert into Configuration(id, name, value) values(5, 'openstack.endpoint.tokens', 'http://192.168.1.145:5000/v2.0/tokens')
insert into Configuration(id, name, value) values(6, 'openstack.endpoint.tenants', 'http://192.168.1.145:35357/v2.0/tenants')
insert into Configuration(id, name, value) values(7, 'openstack.endpoint.tenant', 'http://192.168.1.145:35357/v2.0/tenants/{tenant}')
insert into Configuration(id, name, value) values(8, 'openstack.endpoint.users', 'http://192.168.1.145:35357/v2.0/users')
insert into Configuration(id, name, value) values(9, 'openstack.endpoint.user', 'http://192.168.1.145:35357/v2.0/users/{user}')
insert into Configuration(id, name, value) values(10, 'openstack.endpoint.hosts', 'http://192.168.1.145:8774/v2/{tenant}/os-hosts')
insert into Configuration(id, name, value) values(11, 'openstack.endpoint.host.describe', 'http://192.168.1.145:8774/v2/{tenant}/os-hosts/{host}')
insert into Configuration(id, name, value) values(12, 'openstack.endpoint.flavors', 'http://192.168.1.145:8774/v2/{tenant}/flavors')
insert into Configuration(id, name, value) values(13, 'openstack.endpoint.flavors.detail', 'http://192.168.1.145:8774/v2/{tenant}/flavors/detail')
insert into Configuration(id, name, value) values(14, 'openstack.endpoint.flavor', 'http://192.168.1.145:8774/v2/{tenant}/flavors/{flavor}')
insert into Configuration(id, name, value) values(15, 'openstack.endpoint.images', 'http://192.168.1.145:8774/v2/{tenant}/images')
insert into Configuration(id, name, value) values(16, 'openstack.endpoint.images.detail', 'http://192.168.1.145:8774/v2/{tenant}/images/detail')
insert into Configuration(id, name, value) values(17, 'openstack.endpoint.image', 'http://192.168.1.145:8774/v2/{tenant}/images/{image}')
insert into Configuration(id, name, value) values(18, 'openstack.endpoint.servers', 'http://192.168.1.145:8774/v2/{tenant}/servers')
insert into Configuration(id, name, value) values(19, 'openstack.endpoint.servers.detail', 'http://192.168.1.145:8774/v2/{tenant}/servers/detail')
insert into Configuration(id, name, value) values(20, 'openstack.endpoint.server', 'http://192.168.1.145:8774/v2/{tenant}/servers/{server}')
insert into Configuration(id, name, value) values(21, 'openstack.endpoint.server.action', 'http://192.168.1.145:8774/v2/{tenant}/servers/{server}/action')

insert into Role(id, name) values (1, 'admin')
insert into Role(id, name) values (2, 'user')
insert into Role(id, name) values (3, 'agent')

insert into Tenant(id, name, dipalyName, role_id, ageing) values (1, 'admin', 'admin', 1, 1)

insert into Agent(uuid, commission, tenant_id) values ('1', 0.1, 1)

insert into User(id, name, password, firstName, lastName, role_id, status, ageing) values (1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin', 'admin', 1, 1, 1)

insert into Resource(id, url, permission) values (1, '/admin', 'admin')