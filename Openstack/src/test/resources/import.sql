-- You can use this file to load seed data into the database using SQL statements
insert into Language(id, name, language, country) values (1, 'English', 'en', 'US')
insert into Language(id, name, language, country) values (2, '中文', 'zh', 'CN')

insert into Configuration(name, value) values('openstack.endpoint.token', 'http://192.168.1.145:5000/v2.0/tokens')
insert into Configuration(name, value) values('openstack.tenant.admin', '8384b45a9ad34a5da3a0a2f8b12b99bb')
insert into Configuration(name, value) values('openstack.user.admin.name', 'admin')
insert into Configuration(name, value) values('openstack.user.admin.pass', 'inforstack')