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
insert into Configuration(id, name, value) values(37, 'openstack.endpoint.volumesattachment', '/servers/{server}/os-volume_attachments');
insert into Configuration(id, name, value) values(38, 'openstack.endpoint.volumesattachmentDetail', '/servers/{server}/os-volume_attachments/{attachment}');

insert into Configuration(id, name, value) values(50, 'openstack.role.admin', 'faefa2a247154e50ba2d0489c37dd2c5');
insert into Configuration(id, name, value) values(51, 'openstack.role.reseller', 'd1769816ef74437a8cb41199fcf1055f');
insert into Configuration(id, name, value) values(52, 'openstack.role.member', '8321e47acbc64c3796af5b01ce969836');

-- configuration for 211.152.59.3
--insert into Configuration(id, name, value) values(50, 'openstack.role.admin', '5fa44d963d1c476f934407a979c3f591');
--insert into Configuration(id, name, value) values(51, 'openstack.role.reseller', '8289859bfa02405f85de97be60689e8e');
--insert into Configuration(id, name, value) values(52, 'openstack.role.member', '2de10f0627b34a11973eab0ff88e6c3c');

insert into Configuration(id, name, value) values(53, 'openstack.cache.expire', '2');

insert into Configuration(id, name, value) values(60, 'socket.ip', 'localhost');
insert into Configuration(id, name, value) values(61, 'socket.port', '11533');
insert into Configuration(id, name, value) values(62, 'socket.ceilmeter.timeout', '60');
insert into Configuration(id, name, value) values(63, 'socket.timestamp.tolerance', '3000');

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
insert into Dictionary(id, dict_key, code, language_id, value) values (13, 'order.status', '1', 1, 'New');
insert into Dictionary(id, dict_key, code, language_id, value) values (14, 'order.status', '1', 2, '新建');
insert into Dictionary(id, dict_key, code, language_id, value) values (15, 'order.status', '2', 1, 'Processing');
insert into Dictionary(id, dict_key, code, language_id, value) values (16, 'order.status', '2', 2, '处理中');
insert into Dictionary(id, dict_key, code, language_id, value) values (17, 'order.status', '3', 1, 'Ready');
insert into Dictionary(id, dict_key, code, language_id, value) values (18, 'order.status', '3', 2, '就绪');
insert into Dictionary(id, dict_key, code, language_id, value) values (19, 'order.status', '4', 1, 'Active');
insert into Dictionary(id, dict_key, code, language_id, value) values (20, 'order.status', '4', 2, '有效');
insert into Dictionary(id, dict_key, code, language_id, value) values (21, 'order.status', '5', 1, 'Finished');
insert into Dictionary(id, dict_key, code, language_id, value) values (22, 'order.status', '5', 2, '完成');
insert into Dictionary(id, dict_key, code, language_id, value) values (23, 'order.status', '6', 1, 'Cancelled');
insert into Dictionary(id, dict_key, code, language_id, value) values (24, 'order.status', '6', 2, '取消');
insert into Dictionary(id, dict_key, code, language_id, value) values (25, 'true.false', 'true', 1, 'Yes');
insert into Dictionary(id, dict_key, code, language_id, value) values (26, 'true.false', 'true', 2, '是');
insert into Dictionary(id, dict_key, code, language_id, value) values (27, 'true.false', 'false', 1, 'No');
insert into Dictionary(id, dict_key, code, language_id, value) values (28, 'true.false', 'false', 2, '否');

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

insert into Resource(id, url, permission) values (210, '/user/cart/modules/ip', 'user');
insert into Resource(id, url, permission) values (211, '/user/cart/modules/volume', 'user');


insert into Resource(id, url, permission) values (300, '/user/instance/modules/index', 'user');
insert into Resource(id, url, permission) values (301, '/user/instance/scripts/bootstrap', 'user');
insert into Resource(id, url, permission) values (302, '/user/instance/getPagerInstanceList', 'user');
insert into Resource(id, url, permission) values (303, '/user/instance/imcontrol', 'user');
insert into Resource(id, url, permission) values (304, '/user/instance/getInstance', 'user');
insert into Resource(id, url, permission) values (305, '/user/instance/scripts/template', 'user');
insert into Resource(id, url, permission) values (306, '/user/instance/showInstanceDetails', 'user');

insert into Resource(id, url, permission) values (350, '/user/cinder/modules/index', 'user');
insert into Resource(id, url, permission) values (351, '/user/cinder/getPagerVolumeTypeList', 'user');
insert into Resource(id, url, permission) values (352, '/user/cinder/getPagerVolumeList', 'user');



insert into Resource(id, url, permission) values (380, '/user/plan/planList', 'user');


insert into Resource(id, url, permission) values (400, '/admin/quantum/modules/index', 'admin');
insert into Resource(id, url, permission) values (401, '/admin/quantum/getPagerNetworkList', 'admin');
insert into Resource(id, url, permission) values (402, '/admin/quantum/createNetwork', 'admin');
insert into Resource(id, url, permission) values (403, '/admin/quantum/createSubnet', 'admin');
insert into Resource(id, url, permission) values (404, '/admin/quantum/showCreateNetworkForm', 'admin');
insert into Resource(id, url, permission) values (405, '/admin/quantum/showCreateSubnetForm', 'admin');
insert into Resource(id, url, permission) values (406, '/admin/quantum/showEditNetworkForm', 'admin');
insert into Resource(id, url, permission) values (407, '/admin/quantum/editNetwork', 'admin');
insert into Resource(id, url, permission) values (408, '/admin/quantum/removeNetwork', 'admin');
insert into Resource(id, url, permission) values (409, '/admin/quantum/getNetworkDetails', 'admin');
insert into Resource(id, url, permission) values (410, '/admin/quantum/getPagerSubnetList', 'admin');
insert into Resource(id, url, permission) values (411, '/admin/quantum/getPagerPortList', 'admin');
insert into Resource(id, url, permission) values (412, '/admin/quantum/createPort', 'admin');
insert into Resource(id, url, permission) values (413, '/admin/quantum/editPort', 'admin');
insert into Resource(id, url, permission) values (414, '/admin/quantum/showEditSubnetForm', 'admin');
insert into Resource(id, url, permission) values (415, '/admin/quantum/showEditPortForm', 'admin');
insert into Resource(id, url, permission) values (416, '/admin/quantum/showCreatePortForm', 'admin');
insert into Resource(id, url, permission) values (417, '/admin/quantum/editSubnet', 'admin');
insert into Resource(id, url, permission) values (418, '/admin/quantum/removePort', 'admin');
insert into Resource(id, url, permission) values (419, '/admin/quantum/removeSubnet', 'admin');
insert into Resource(id, url, permission) values (420, '/admin/quantum/showSubnetDetail', 'admin');
insert into Resource(id, url, permission) values (421, '/admin/quantum/showPortDetail', 'admin');

insert into Resource(id, url, permission) values (450, '/admin/cinder/modules/index', 'admin');
insert into Resource(id, url, permission) values (451, '/admin/cinder/getPagerVolumeTypeList', 'admin');

insert into Resource(id, url, permission) values (500, '/user/order/getPagerOrderList', 'user');
insert into Resource(id, url, permission) values (501, '/user/order/modules/index', 'user');

insert into Resource(id, url, permission) values (600, '/admin/order/getPagerOrderList', 'admin');
insert into Resource(id, url, permission) values (601, '/admin/order/modules/index', 'admin');

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
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (13,'name_id',NULL,'OrderPeriod');

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
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (21,'Day',11,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (22,'包天',11,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (23,'Month',12,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (24,'包月',12,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (25,'Year',13,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (26,'包年',13,2);

INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (1,1,1,1);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (2,1,1,2);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (3,1,1,3);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (4,1,1,4);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (5,1,1,5);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (6,1,1,6);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (7,1,1,7);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (8,1,1,8);
INSERT INTO `Category` (`id`,`enable`,`system`,`name_id`) VALUES (9,1,1,9);

INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id, pay_as_you_go) values(1, '2013-02-21 12:25:48', 0, 1, 11, 10, true);
INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id, pay_as_you_go) values(2, '2013-02-21 12:25:48', 0, 1, 5, 11, false);
INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id, pay_as_you_go) values(3, '2013-02-21 12:25:48', 0, 1, 2, 12, false);
INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id, pay_as_you_go) values(4, '2013-02-21 12:25:48', 0, 1, 1, 13, false);

INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (14,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (15,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (16,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (17,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (18,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (19,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (20,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (21,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (22,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (23,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (24,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (25,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (26,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (27,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (28,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (29,'name_id',NULL,'DataCenter');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (30,'name_id',NULL,'DataCenter');

INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (27,'Cirros for Demo',14,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (28,'测试用Cirros',14,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (29,'Data Center A',15,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (30,'数据中心A',15,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (31,'Data Center B',16,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (32,'数据中心B',16,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (33,'Flavor Tiny',17,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (34,'迷你型配置',17,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (35,'Flavor Small',18,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (36,'经济型配置',18,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (37,'Flavor Medium',19,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (38,'主流型配置',19,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (39,'Flavor Large',20,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (40,'商务型配置',20,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (41,'Flavor Ultra',21,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (42,'豪华型配置',21,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (43,'Pay As You Go',22,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (44,'即付即用',22,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (45,'Monthly',23,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (46,'按月付费',23,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (47,'Yearly',24,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (48,'按年付费',24,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (49,'IP Address',25,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (50,'IP地址',25,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (51,'Volume 5GB',26,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (52,'5G磁盘',26,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (53,'Volume 10GB',27,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (54,'10G磁盘',27,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (55,'CPUUsage',28,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (56,'CPU用量',28,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (57,'Data Center A',29,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (68,'数据中心A',29,2);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (59,'Data Center B',30,1);
INSERT INTO `I18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (60,'数据中心B',30,2);

INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (1,1,'2013-02-25 11:06:14',0,14,2,'1','2013-02-25 11:06:14',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (2,1,'2013-02-25 11:06:42',0,15,7,'1','2013-02-25 11:06:42',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (3,1,'2013-02-25 11:07:01',0,16,7,'2','2013-02-25 11:07:01',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (4,1,'2013-02-25 11:07:33',1,17,1,'1','2013-02-25 11:07:33',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (5,1,'2013-02-25 11:07:57',5,18,1,'2','2013-02-25 11:07:57',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (6,1,'2013-02-25 11:08:22',10,19,1,'3','2013-02-25 11:08:22',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (7,1,'2013-02-25 11:08:48',20,20,1,'4','2013-02-25 11:08:48',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (8,1,'2013-02-25 11:09:10',50,21,1,'5','2013-02-25 11:09:10',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (9,1,'2013-02-25 11:09:36',0,22,6,'1','2013-02-25 11:09:36',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (10,1,'2013-02-25 11:10:08',1,23,6,'3','2013-02-25 11:10:08',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (11,1,'2013-02-25 11:10:29',10,24,6,'4','2013-02-25 11:10:29',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (12,1,'2013-02-25 11:10:55',50,25,4,NULL,'2013-02-25 11:10:55',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (13,1,'2013-02-25 11:11:19',5,26,3,'1','2013-02-25 11:11:19',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (14,1,'2013-02-25 11:11:44',10,27,3,'2','2013-02-25 11:11:44',NULL);
INSERT INTO `ItemSpecification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (15,1,'2013-02-25 11:12:06',0.01,28,5,NULL,'2013-02-25 11:12:06',NULL);

INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (1,'2013-02-25 11:06:14','2013-02-25 11:06:14',0,1);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (2,'2013-02-25 11:06:42','2013-02-25 11:06:42',0,2);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (3,'2013-02-25 11:07:01','2013-02-25 11:07:01',0,3);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (4,'2013-02-25 11:07:33','2013-02-25 11:07:33',1,4);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (5,'2013-02-25 11:07:57','2013-02-25 11:07:57',5,5);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (6,'2013-02-25 11:08:22','2013-02-25 11:08:22',10,6);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (7,'2013-02-25 11:08:48','2013-02-25 11:08:48',20,7);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (8,'2013-02-25 11:09:10','2013-02-25 11:09:10',50,8);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (9,'2013-02-25 11:09:36','2013-02-25 11:09:36',0,9);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (10,'2013-02-25 11:10:08','2013-02-25 11:10:08',1,10);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (11,'2013-02-25 11:10:29','2013-02-25 11:10:29',10,11);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (12,'2013-02-25 11:10:55','2013-02-25 11:10:55',50,12);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (13,'2013-02-25 11:11:19','2013-02-25 11:11:19',5,13);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (14,'2013-02-25 11:11:44','2013-02-25 11:11:44',10,14);
INSERT INTO `Price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (15,'2013-02-25 11:12:06','2013-02-25 11:12:06',0.01,15);

INSERT INTO `DataCenter` (`id`,`name_id`) VALUES (1,29);
INSERT INTO `DataCenter` (`id`,`name_id`) VALUES (2,30);

INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (1,'1','1',1);
INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (2,'2','2',1);
INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (3,'3','3',1);
INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (4,'4','4',1);
INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (5,'5','5',1);
INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (6,'1','1',2);
INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (7,'2','2',2);
INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (8,'3','3',2);
INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (9,'4','4',2);
INSERT INTO `Flavor` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (10,'5','5',2);


INSERT INTO `Image` (`id`,`uuid`,`refId`,`data_center_id`,`family`) VALUES (1,'1','7429ca38-5a99-438c-a94d-1dddfcb41414',1,'other');
INSERT INTO `Image` (`id`,`uuid`,`refId`,`data_center_id`,`family`) VALUES (2,'1','7429ca38-5a99-438c-a94d-1dddfcb41414',2,'other');

INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (1,'1','716f6bc2-827c-440a-85ad-cfc5f4d42708',1);
INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (2,'2','6030b0fb-506c-4f80-942f-65388461318b',1);
INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (3,'3','b6e4203e-6534-41e4-a9c0-c3bf462a287d',1);
INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (4,'4','299f1cf5-3299-43b1-9df9-d4151290b459',1);
INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (5,'5','d96a771e-1dab-42eb-a9af-faefadbf50cb',1);
INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (6,'1','716f6bc2-827c-440a-85ad-cfc5f4d42708',2);
INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (7,'2','6030b0fb-506c-4f80-942f-65388461318b',2);
INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (8,'3','b6e4203e-6534-41e4-a9c0-c3bf462a287d',2);
INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (9,'4','299f1cf5-3299-43b1-9df9-d4151290b459',2);
INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (10,'5','d96a771e-1dab-42eb-a9af-faefadbf50cb',2);

-- configuration for 211.152.59.3
--INSERT INTO `Image` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (1,'1','b14a2317-22b7-45a3-b064-a6c8c4ae5b14',1);
--INSERT INTO `Image` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (2,'1','b14a2317-22b7-45a3-b064-a6c8c4ae5b14',2);

--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (1,'1','1',1);
--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (2,'2','2',1);
--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (3,'3','3',1);
--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (4,'4','4',1);
--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (5,'5','5',1);
--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (6,'1','1',2);
--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (7,'2','2',2);
--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (8,'3','3',3);
--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (9,'4','4',4);
--INSERT INTO `VolumeType` (`id`,`uuid`,`refId`,`data_center_id`) VALUES (10,'5','5',5);
