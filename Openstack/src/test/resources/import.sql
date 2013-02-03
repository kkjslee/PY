-- You can use this file to load seed data into the database using SQL statements
insert into Language(id, name, language, country) values (1, 'English', 'en', 'US')
insert into Language(id, name, language, country) values (2, '中文', 'zh', 'CN')

insert into Configuration(id, name, value) values(1, 'openstack.tenant.admin', 'ca80cde2ad1e40f5813208d4082769ac')
insert into Configuration(id, name, value) values(2, 'openstack.tenant.demo', '72917ad5646e4ef0be11d63c106ff066')
insert into Configuration(id, name, value) values(3, 'openstack.user.admin.name', 'admin')
insert into Configuration(id, name, value) values(4, 'openstack.user.admin.pass', 'Password9988!')
insert into Configuration(id, name, value) values(5, 'openstack.endpoint.tokens', 'http://192.168.1.29:5000/v2.0/tokens')
insert into Configuration(id, name, value) values(6, 'openstack.endpoint.tenants', '/tenants')
insert into Configuration(id, name, value) values(7, 'openstack.endpoint.tenant', '/tenants/{tenant}')
insert into Configuration(id, name, value) values(8, 'openstack.endpoint.users', '/users')
insert into Configuration(id, name, value) values(9, 'openstack.endpoint.user', '/users/{user}')
insert into Configuration(id, name, value) values(10, 'openstack.endpoint.role', '/tenants/{tenant}/users/{user}/roles/OS-KSADM/{role}')
insert into Configuration(id, name, value) values(11, 'openstack.endpoint.hosts', '/os-hosts')
insert into Configuration(id, name, value) values(12, 'openstack.endpoint.host.describe', '/os-hosts/{host}')
insert into Configuration(id, name, value) values(13, 'openstack.endpoint.flavors', '/flavors')
insert into Configuration(id, name, value) values(14, 'openstack.endpoint.flavors.detail', '/flavors/detail')
insert into Configuration(id, name, value) values(15, 'openstack.endpoint.flavor', '/flavors/{flavor}')
insert into Configuration(id, name, value) values(16, 'openstack.endpoint.images', '/images')
insert into Configuration(id, name, value) values(17, 'openstack.endpoint.images.detail', '/images/detail')
insert into Configuration(id, name, value) values(18, 'openstack.endpoint.image', '/images/{image}')
insert into Configuration(id, name, value) values(19, 'openstack.endpoint.servers', '/servers')
insert into Configuration(id, name, value) values(20, 'openstack.endpoint.servers.detail', '/servers/detail')
insert into Configuration(id, name, value) values(21, 'openstack.endpoint.server', '/servers/{server}')
insert into Configuration(id, name, value) values(22, 'openstack.endpoint.server.action', '/servers/{server}/action')
insert into Configuration(id, name, value) values(23, 'openstack.role.admin', '9cdcb290f2d0466790c64f3aa5b41cb9')
insert into Configuration(id, name, value) values(24, 'openstack.role.reseller', '9cdcb290f2d0466790c64f3aa5b41cb9')
insert into Configuration(id, name, value) values(25, 'openstack.role.member', '9cdcb290f2d0466790c64f3aa5b41cb9')
insert into Configuration(id, name, value) values(26, 'openstack.cache.expire', '2')

insert into Role(id, name) values (1, 'admin')
insert into Role(id, name) values (2, 'user')
insert into Role(id, name) values (3, 'agent')

insert into Tenant(id, name, display_name, role_id, ageing) values (1, 'admin', 'admin', 1, 1)

insert into User(id, name, password, firstName, lastName, role_id, default_tenant_id, status, ageing) values (1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin', 'admin', 1, 1, 1, 1)

insert into tenant_user(user_id, tenant_id) values (1, 1)

insert into Dictionary(id, dict_key, code, language_id, value) values (1, 'period.type', '1', 1, 'Year')
insert into Dictionary(id, dict_key, code, language_id, value) values (2, 'period.type', '1', 2, '年')
insert into Dictionary(id, dict_key, code, language_id, value) values (3, 'period.type', '2', 1, 'Month')
insert into Dictionary(id, dict_key, code, language_id, value) values (4, 'period.type', '2', 2, '月')
insert into Dictionary(id, dict_key, code, language_id, value) values (5, 'period.type', '4', 1, 'Week')
insert into Dictionary(id, dict_key, code, language_id, value) values (6, 'period.type', '4', 2, '周')
insert into Dictionary(id, dict_key, code, language_id, value) values (7, 'period.type', '5', 1, 'Day')
insert into Dictionary(id, dict_key, code, language_id, value) values (8, 'period.type', '5', 2, '天')
insert into Dictionary(id, dict_key, code, language_id, value) values (9, 'period.type', '11', 1, 'Hour')
insert into Dictionary(id, dict_key, code, language_id, value) values (10, 'period.type', '11', 2, '小时')
insert into Dictionary(id, dict_key, code, language_id, value) values (11, 'period.hour', '12', 1, 'Minute')
insert into Dictionary(id, dict_key, code, language_id, value) values (12, 'period.hour', '12', 2, '分钟')

insert into Resource(id, url, permission) values (1, '/admin', 'admin')

insert into Resource(id, url, permission) values (2, '/admin/instance/modules/index', 'admin');
insert into Resource(id, url, permission) values (3, '/admin/instance/scripts/bootstrap', 'admin');
insert into Resource(id, url, permission) values (4, '/admin/instance/scripts/template', 'admin');
insert into Resource(id, url, permission) values (5, '/admin/instance/getPagerInstanceList', 'admin');
insert into Resource(id, url, permission) values (6, '/admin/instance/imcontrol', 'admin');
insert into Resource(id, url, permission) values (7, '/admin/instance/createInstance', 'admin');
insert into Resource(id, url, permission) values (8, '/admin/instance/getInstance', 'admin');

insert into Resource(id, url, permission) values (9, '/admin/image/getPagerImageList', 'admin');
insert into Resource(id, url, permission) values (10, '/admin/image/imgList', 'admin');
insert into Resource(id, url, permission) values (11, '/admin/image/createImage', 'admin');
insert into Resource(id, url, permission) values (12, '/admin/image/retrieveImage', 'admin');

insert into Resource(id, url, permission) values (13, '/admin/flavor/modules/index', 'admin');
insert into Resource(id, url, permission) values (14, '/admin/flavor/scripts/bootstrap', 'admin');
insert into Resource(id, url, permission) values (15, '/admin/flavor/scripts/template', 'admin');
insert into Resource(id, url, permission) values (16, '/admin/flavor/getPagerFlavorList', 'admin');
insert into Resource(id, url, permission) values (17, '/admin/flavor/createFlavor', 'admin');
insert into Resource(id, url, permission) values (18, '/admin/flavor/removeFlavor', 'admin');
insert into Resource(id, url, permission) values (19, '/admin/flavor/getFlavorDetails', 'admin');

insert into Resource(id, url, permission) values (20, '/admin/scripts/navinit', 'admin');
insert into Resource(id, url, permission) values (21, '/admin/scripts/bootstrap', 'admin');
insert into Resource(id, url, permission) values (22, '/admin/scripts/template', 'admin');
insert into Resource(id, url, permission) values (23, '/admin/modules/entry/index', 'admin');

insert into Resource(id, url, permission) values (24, '/admin/image/modules/index', 'admin');
insert into Resource(id, url, permission) values (25, '/admin/flavor/flavorList', 'admin');
insert into Resource(id, url, permission) values (26, '/admin/image/scripts/bootstrap', 'admin');
insert into Resource(id, url, permission) values (27, '/admin/image/scripts/template', 'admin');
insert into Resource(id, url, permission) values (28, '/admin/image/getPagerAllImageList', 'admin');
insert into Resource(id, url, permission) values (29, '/admin/flavor/getPagerAllFlavorList', 'admin');
insert into Resource(id, url, permission) values (30, '/admin/flavor/nameCheck', 'admin');

insert into Resource(id, url, permission) values (31, '/user', 'user')
insert into Resource(id, url, permission) values (32, '/user/scripts/navinit', 'user');
insert into Resource(id, url, permission) values (33, '/user/scripts/bootstrap', 'user');
insert into Resource(id, url, permission) values (34, '/user/scripts/template', 'user');
insert into Resource(id, url, permission) values (35, '/user/modules/entry/index', 'user');
