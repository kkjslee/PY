-- You can use this file to load seed data into the database using SQL statements
Insert into Language(id, name, language, country) values (1, 'English', 'en', 'US')
Insert into Language(id, name, language, country) values (2, '中文', 'zh', 'CN')

insert into Configuration(id, name, value) values(1, 'openstack.endpoint.token', 'http://192.168.1.145:5000/v2.0/tokens')