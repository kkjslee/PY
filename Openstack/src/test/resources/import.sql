-- You can use this file to load seed data into the database using SQL statements
insert into Language(id, name, language, country) values (1, 'English', 'en', 'US')
insert into Language(id, name, language, country) values (2, '中文', 'zh', 'CN')

insert into Configuration(name, value) values('openstack.endpoint.token', 'http://192.168.1.145:5000/v2.0/tokens')
insert into Configuration(name, value) values('openstack.endpoint.tenant', 'http://192.168.1.145:5000/v2.0/tenants')
insert into Configuration(name, value) values('openstack.endpoint.user', 'http://192.168.1.145:5000/v2.0/users')
insert into Configuration(name, value) values('openstack.endpoint.hosts', 'http://192.168.1.145:8774/v2/{tenant}/os-hosts')
insert into Configuration(name, value) values('openstack.endpoint.host.describe', 'http://192.168.1.145:8774/v2/{tenant}/os-hosts/{host}')
insert into Configuration(name, value) values('openstack.tenant.admin', '8384b45a9ad34a5da3a0a2f8b12b99bb')
insert into Configuration(name, value) values('openstack.user.admin.name', 'admin')
insert into Configuration(name, value) values('openstack.user.admin.pass', 'inforstack')

insert into Role(id, name) values (1, 'admin')
insert into Role(id, name) values (2, 'user')
insert into Role(id, name) values (3, 'agent')

insert into Tenant(id, name, dipalyName, role_id, deleted) values(1, 'admin', 'admin', 1, false)

insert into Agent(uuid, commission, tenant_id)('1', 0.1, 1)

insert into User(id, name, password, realname, role_id, valid, deleted) values (1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin', 1, true, false)

insert into Resource(id, url, permission) values (1, '/admin', 'admin')