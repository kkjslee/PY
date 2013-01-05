-- You can use this file to load seed data into the database using SQL statements
Insert into Language(id, name, language, country) values (1, 'English', 'en', 'US')
Insert into Language(id, name, language, country) values (2, '中文', 'zh', 'CN')

insert into Configuration(id, name, value) values(1, 'openstack.endpoint.token', 'http://192.168.1.145:5000/v2.0/tokens')

insert into Role(id, name) values (1, 'admin')
insert into Role(id, name) values (2, 'user')
insert into Role(id, name) values (3, 'agent')

insert into Tenant(id, name, dipalyName, role_id, deleted) values(1, 'admin', 'admin', 1, false)

insert into Agent(uuid, commission, tenant_id)('1', 0.1, 1)

insert into User(id, name, password, realname, role_id, valid, deleted) values (1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin', 1, true, false)

insert into Resource(id, url, permission) values (1, '/admin', 'admin')