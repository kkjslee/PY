-- You can use this file to load seed data into the database using SQL statements
insert into Language(id, name, language, country) values (1, 'English', 'en', 'US')
insert into Language(id, name, language, country) values (2, '中文', 'zh', 'CN')

insert into Configuration(id, name, value) values(1, 'openstack.tenant.admin', 'ec73d58df01b43e48aa41549953df091')
insert into Configuration(id, name, value) values(2, 'openstack.tenant.demo', '4f1cb88c9e4449ca90ae33ef20c38c34')
insert into Configuration(id, name, value) values(3, 'openstack.user.admin.name', 'admin')
insert into Configuration(id, name, value) values(4, 'openstack.user.admin.pass', '5f4dcc3b5aa765d61d8327deb882cf99')
insert into Configuration(id, name, value) values(5, 'openstack.endpoint.tokens', 'http://192.168.1.145:5000/v2.0/tokens')
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

insert into Resource(id, url, permission) values (2, '/admin/instance/modules/index', 'admin');
insert into Resource(id, url, permission) values (3, '/admin/instance/scripts/bootstrap', 'admin');
insert into Resource(id, url, permission) values (4, '/admin/instance/scripts/template', 'admin');
insert into Resource(id, url, permission) values (5, '/admin/instance/getPagerInstanceList', 'admin');
insert into Resource(id, url, permission) values (6, '/admin/instance/imcontrol', 'admin');
insert into Resource(id, url, permission) values (7, '/admin/instance/createInstance', 'admin');
insert into Resource(id, url, permission) values (8, '/admin/instance/getInstance', 'admin');

insert into Resource(id, url, permission) values (9, '/admin/image/getPagerImageList', 'admin');
insert into Resource(id, url, permission) values (10, '/admin/image/imglist', 'admin');
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


insert into Resource(id, url, permission) values (24, '/admin/image/imgList', 'admin');
insert into Resource(id, url, permission) values (25, '/admin/flavor/flavorList', 'admin');