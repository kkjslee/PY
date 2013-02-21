-- You can use this file to load seed data into the database using SQL statements
insert into Language(id, name, language, country) values (1, 'English', 'en', 'US');
insert into Language(id, name, language, country) values (2, '中文', 'zh', 'CN');

insert into Configuration(id, name, value) values(1, 'openstack.tenant.admin', 'd04b95d3c3b84132ae1b939ecbb74cb5');
insert into Configuration(id, name, value) values(2, 'openstack.tenant.demo', 'ab0d65f7ac0a4a1eb14d746b5e5ebbfc');
insert into Configuration(id, name, value) values(3, 'openstack.user.admin.name', 'admin');
insert into Configuration(id, name, value) values(4, 'openstack.user.admin.pass', '5f4dcc3b5aa765d61d8327deb882cf99');
insert into Configuration(id, name, value) values(5, 'openstack.endpoint.tokens', 'http://192.168.1.145:5000/v2.0/tokens');
insert into Configuration(id, name, value) values(6, 'openstack.endpoint.tenants', '/tenants');
insert into Configuration(id, name, value) values(7, 'openstack.endpoint.tenant', '/tenants/{tenant}');
insert into Configuration(id, name, value) values(8, 'openstack.endpoint.users', '/users');
insert into Configuration(id, name, value) values(9, 'openstack.endpoint.user', '/users/{user}');
insert into Configuration(id, name, value) values(10, 'openstack.endpoint.role', '/tenants/{tenant}/users/{user}/roles/OS-KSADM/{role}');
insert into Configuration(id, name, value) values(11, 'openstack.endpoint.hosts', '/os-hosts');
insert into Configuration(id, name, value) values(12, 'openstack.endpoint.host.describe', '/os-hosts/{host}');
insert into Configuration(id, name, value) values(13, 'openstack.endpoint.flavors', '/flavors');
insert into Configuration(id, name, value) values(14, 'openstack.endpoint.flavors.detail', '/flavors/detail');
insert into Configuration(id, name, value) values(15, 'openstack.endpoint.flavor', '/flavors/{flavor}');
insert into Configuration(id, name, value) values(16, 'openstack.endpoint.images', '/images');
insert into Configuration(id, name, value) values(17, 'openstack.endpoint.images.detail', '/images/detail');
insert into Configuration(id, name, value) values(18, 'openstack.endpoint.image', '/images/{image}');
insert into Configuration(id, name, value) values(19, 'openstack.endpoint.servers', '/servers');
insert into Configuration(id, name, value) values(20, 'openstack.endpoint.servers.detail', '/servers/detail');
insert into Configuration(id, name, value) values(21, 'openstack.endpoint.server', '/servers/{server}');
insert into Configuration(id, name, value) values(22, 'openstack.endpoint.server.action', '/servers/{server}/action');
insert into Configuration(id, name, value) values(23, 'openstack.endpoint.networks', 'v2.0/networks');
insert into Configuration(id, name, value) values(24, 'openstack.endpoint.network', 'v2.0/networks/{network}');
insert into Configuration(id, name, value) values(25, 'openstack.endpoint.subnets', 'v2.0/subnets');
insert into Configuration(id, name, value) values(26, 'openstack.endpoint.subnet', 'v2.0/subnets/{subnet}');
insert into Configuration(id, name, value) values(27, 'openstack.endpoint.ports', 'v2.0/ports');
insert into Configuration(id, name, value) values(28, 'openstack.endpoint.port', 'v2.0/ports/{port}');
insert into Configuration(id, name, value) values(29, 'openstack.endpoint.volumes', '/volumes');
insert into Configuration(id, name, value) values(30, 'openstack.endpoint.volumes.detail', '/volumes/detail');
insert into Configuration(id, name, value) values(31, 'openstack.endpoint.volume', '/volumes/{volume}');
insert into Configuration(id, name, value) values(32, 'openstack.endpoint.volumetypes', '/types');
insert into Configuration(id, name, value) values(33, 'openstack.endpoint.volumetype', '/types/{type}');
insert into Configuration(id, name, value) values(34, 'openstack.endpoint.volumesnapshots', '/snapshots');
insert into Configuration(id, name, value) values(35, 'openstack.endpoint.volumesnapshots.detail', '/snapshots/detail');
insert into Configuration(id, name, value) values(36, 'openstack.endpoint.volumesnapshot', '/snapshots/{snapshot}');

insert into Configuration(id, name, value) values(50, 'openstack.role.admin', 'faefa2a247154e50ba2d0489c37dd2c5');
insert into Configuration(id, name, value) values(51, 'openstack.role.reseller', 'd1769816ef74437a8cb41199fcf1055f');
insert into Configuration(id, name, value) values(52, 'openstack.role.member', '8321e47acbc64c3796af5b01ce969836');
insert into Configuration(id, name, value) values(53, 'openstack.cache.expire', '2');

insert into Role(id, name) values (1, 'admin');
insert into Role(id, name) values (2, 'user');
insert into Role(id, name) values (3, 'agent');

insert into Tenant(id, name, display_name, role_id, ageing) values (1, 'admin', 'admin', 1, 1);

insert into User(id, user_name, password, first_name, last_name, role_id, default_tenant_id, status, ageing) values (1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin', 'admin', 1, 1, 1, 1);

insert into tenant_user(user_id, tenant_id) values (1, 1);

insert into Dictionary(id, dict_key, code, language_id, value) values (1, 'period.type', '1', 1, 'Year');
insert into Dictionary(id, dict_key, code, language_id, value) values (2, 'period.type', '1', 2, '年');
insert into Dictionary(id, dict_key, code, language_id, value) values (3, 'period.type', '2', 1, 'Month');
insert into Dictionary(id, dict_key, code, language_id, value) values (4, 'period.type', '2', 2, '月');
insert into Dictionary(id, dict_key, code, language_id, value) values (5, 'period.type', '4', 1, 'Week');
insert into Dictionary(id, dict_key, code, language_id, value) values (6, 'period.type', '4', 2, '周');
insert into Dictionary(id, dict_key, code, language_id, value) values (7, 'period.type', '5', 1, 'Day');
insert into Dictionary(id, dict_key, code, language_id, value) values (8, 'period.type', '5', 2, '天');
insert into Dictionary(id, dict_key, code, language_id, value) values (9, 'period.type', '11', 1, 'Hour');
insert into Dictionary(id, dict_key, code, language_id, value) values (10, 'period.type', '11', 2, '小时');
insert into Dictionary(id, dict_key, code, language_id, value) values (11, 'period.hour', '12', 1, 'Minute');
insert into Dictionary(id, dict_key, code, language_id, value) values (12, 'period.hour', '12', 2, '分钟');

insert into Resource(id, url, permission) values (1, '/admin', 'admin');

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

insert into Resource(id, url, permission) values (31, '/user', 'user');
insert into Resource(id, url, permission) values (32, '/user/scripts/navinit', 'user');
insert into Resource(id, url, permission) values (33, '/user/scripts/bootstrap', 'user');
insert into Resource(id, url, permission) values (34, '/user/scripts/template', 'user');
insert into Resource(id, url, permission) values (35, '/user/modules/entry/index', 'user');

insert into Resource(id, url, permission) values (36, '/admin/category/modules/index', 'admin');
insert into Resource(id, url, permission) values (37, '/admin/category/scripts/bootstrap', 'admin');
insert into Resource(id, url, permission) values (38, '/admin/category/scripts/template', 'admin');
insert into Resource(id, url, permission) values (39, '/admin/category/listForJsp', 'admin');
insert into Resource(id, url, permission) values (40, '/admin/category/create', 'admin');
insert into Resource(id, url, permission) values (41, '/admin/category/update', 'admin');
insert into Resource(id, url, permission) values (42, '/admin/category/remove', 'admin');

insert into Resource(id, url, permission) values (43, '/admin/product/modules/index', 'admin');
insert into Resource(id, url, permission) values (44, '/admin/product/scripts/bootstrap', 'admin');
insert into Resource(id, url, permission) values (45, '/admin/product/scripts/template', 'admin');
insert into Resource(id, url, permission) values (46, '/admin/product/remove', 'admin');
insert into Resource(id, url, permission) values (47, '/admin/product/editPrice', 'admin');
insert into Resource(id, url, permission) values (48, '/admin/product/listForJsp', 'admin');
insert into Resource(id, url, permission) values (49, '/admin/product/create', 'admin');
insert into Resource(id, url, permission) values (100, '/admin/product/update', 'admin');

insert into Resource(id, url, permission) values (50, '/admin/category/listForJson', 'admin');

insert into Resource(id, url, permission) values (60, '/admin/plan/planList', 'admin');
insert into Resource(id, url, permission) values (61, '/admin/plan/retrievePlan', 'admin');

insert into Resource(id, url, permission) values (70, '/shopcart', 'user');

insert into Resource(id, url, permission) values (200, '/user/cart/modules/index', 'user');
insert into Resource(id, url, permission) values (201, '/user/cart/scripts/bootstrap', 'user');
insert into Resource(id, url, permission) values (202, '/user/cart/scripts/template', 'user');
insert into Resource(id, url, permission) values (203, '/user/cart/create', 'user');
insert into Resource(id, url, permission) values (204, '/user/cart/clear', 'user');
insert into Resource(id, url, permission) values (205, '/user/cart/add', 'user');
insert into Resource(id, url, permission) values (206, '/user/cart/update', 'user');
insert into Resource(id, url, permission) values (207, '/user/cart/remove', 'user');
insert into Resource(id, url, permission) values (208, '/user/cart/showPayMethods', 'user');
insert into Resource(id, url, permission) values (209, '/user/cart/checkout', 'user');

insert into Resource(id, url, permission) values (300, '/user/instance/modules/index', 'user');
insert into Resource(id, url, permission) values (301, '/user/instance/scripts/bootstrap', 'user');
insert into Resource(id, url, permission) values (302, '/user/instance/getPagerInstanceList', 'user');
insert into Resource(id, url, permission) values (303, '/user/instance/imcontrol', 'user');
insert into Resource(id, url, permission) values (304, '/user/instance/getInstance', 'user');

insert into Resource(id, url, permission) values (380, '/user/plan/planList', 'user');


INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (1,'name_id',NULL,'Category');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (2,'name_id',NULL,'Category');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (3,'name_id',NULL,'Category');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (4,'name_id',NULL,'Category');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (5,'name_id',NULL,'Category');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (6,'name_id',NULL,'Category');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (7,'name_id',NULL,'Category');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (8,'name_id',NULL,'Category');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (9,'name_id',NULL,'Category');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (10,'name_id',NULL,'OrderPeriod');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (11,'name_id',NULL,'OrderPeriod');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (12,'name_id',NULL,'OrderPeriod');

INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (1,'OS Image',1,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (2,'操作系统镜像',1,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (3,'Data Center',2,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (4,'数据中心',2,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (5,'Flavor',3,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (6,'硬件配置',3,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (7,'Plan',4,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (8,'付费套餐',4,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (9,'IP Address',5,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (10,'IP地址',5,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (11,'Storage',6,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (12,'云存储',6,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (13,'Usage',7,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (14,'用量',7,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (15,'Promotion',8,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (16,'促销',8,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (17,'Other 1',9,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (18,'其他1',9,2);

INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (19,'Pay AS You Go',10,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (20,'即付即用',10,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (21,'Month',11,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (22,'包月',11,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (23,'Year',12,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (24,'包年',12,2);

INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (1,1,1,1);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (2,1,1,2);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (3,1,1,3);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (4,1,1,4);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (5,1,1,5);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (6,1,1,6);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (7,1,1,7);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (8,1,1,8);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (9,1,1,9);

INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id) values(1, '2013-02-21 12:25:48', 0, 1, 11, 10);
INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id) values(2, '2013-02-21 12:25:48', 0, 1, 2, 11);
INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id) values(3, '2013-02-21 12:25:48', 0, 1, 1, 12);

