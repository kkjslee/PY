-- You can use this file to load seed data into the database using SQL statements
insert into language (id, name, language, country) values (1, 'English', 'en', 'US');
insert into language (id, name, language, country) values (2, '中文', 'zh', 'CN');

INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (1,'openstack.tenant.admin','1e95fe5ea4904b5e8ed75bfe69decc15');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (2,'openstack.tenant.demo','1e95fe5ea4904b5e8ed75bfe69decc15');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (3,'openstack.user.admin.name','admin');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (4,'openstack.user.admin.pass','Password9988!');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (5,'openstack.endpoint.tokens','http://192.168.1.19:5000/v2.0/tokens');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (6,'openstack.endpoint.tenants','/tenants');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (7,'openstack.endpoint.tenant','/tenants/{tenant}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (8,'openstack.endpoint.users','/users');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (9,'openstack.endpoint.user','/users/{user}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (10,'openstack.endpoint.role','/tenants/{tenant}/users/{user}/roles/OS-KSADM/{role}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (11,'openstack.endpoint.hosts','/os-hosts');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (12,'openstack.endpoint.host.describe','/os-hosts/{host}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (13,'openstack.endpoint.flavors','/flavors');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (14,'openstack.endpoint.flavors.detail','/flavors/detail');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (15,'openstack.endpoint.flavor','/flavors/{flavor}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (16,'openstack.endpoint.images','/images');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (17,'openstack.endpoint.images.detail','/images/detail');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (18,'openstack.endpoint.image','/images/{image}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (19,'openstack.endpoint.servers','/servers');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (20,'openstack.endpoint.servers.detail','/servers/detail');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (21,'openstack.endpoint.server','/servers/{server}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (22,'openstack.endpoint.server.action','/servers/{server}/action');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (23,'openstack.endpoint.networks','v2.0/networks');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (24,'openstack.endpoint.network','v2.0/networks/{network}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (25,'openstack.endpoint.subnets','v2.0/subnets');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (26,'openstack.endpoint.subnet','v2.0/subnets/{subnet}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (27,'openstack.endpoint.ports','v2.0/ports');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (28,'openstack.endpoint.port','v2.0/ports/{port}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (29,'openstack.endpoint.volumes','/volumes');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (30,'openstack.endpoint.volumes.detail','/volumes/detail');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (31,'openstack.endpoint.volume','/volumes/{volume}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (32,'openstack.endpoint.volumetypes','/types');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (33,'openstack.endpoint.volumetype','/types/{type}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (34,'openstack.endpoint.volumesnapshots','/snapshots');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (35,'openstack.endpoint.volumesnapshots.detail','/snapshots/detail');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (36,'openstack.endpoint.volumesnapshot','/snapshots/{snapshot}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (37,'openstack.endpoint.volumesattachment','/servers/{server}/os-volume_attachments');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (38,'openstack.endpoint.volumesattachmentDetail','/servers/{server}/os-volume_attachments/{attachment}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (39,'openstack.endpoint.routers','v2.0/routers');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (40,'openstack.endpoint.router.interface.add','v2.0/routers/{router}/add_router_interface');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (41,'openstack.endpoint.router.interface.remove','v2.0/routers/{router}/add_router_interface');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (42,'openstack.endpoint.floatingips','v2.0/floatingips');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (43,'openstack.endpoint.floatingip','v2.0/floatingips/{floatingip}');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (50,'openstack.role.admin','7ee752355d0544c4bf95bcec2472d484');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (51,'openstack.role.reseller','7ee752355d0544c4bf95bcec2472d484');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (52,'openstack.role.member','7ee752355d0544c4bf95bcec2472d484');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (53,'openstack.cache.expire','2');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (60,'socket.ip','192.168.1.122');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (61,'socket.port','11533');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (62,'socket.ceilmeter.timeout','60');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (63,'socket.timestamp.tolerance','3000');
INSERT INTO `configuration` (`id`,`name`,`value`) VALUES (69,'openstack.user.password','5f4dcc3b5aa765d61d8327deb882cf99');

insert into role(id, name) values (1, 'admin');
insert into role(id, name) values (2, 'user');
insert into role(id, name) values (3, 'agent');

insert into tenant(id, name, display_name, role_id, ageing) values (1, 'admin', 'admin', 1, 1);

insert into user(id, user_name, password, first_name, last_name, role_id, default_tenant_id, status, ageing) values (1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin', 'admin', 1, 1, 1, 1);

insert into tenant_user(user_id, tenant_id) values (1, 1);

insert into dictionary(id, dict_key, code, language_id, value) values (1, 'period.type', '1', 1, 'Year');
insert into dictionary(id, dict_key, code, language_id, value) values (2, 'period.type', '1', 2, '年');
insert into dictionary(id, dict_key, code, language_id, value) values (3, 'period.type', '2', 1, 'Month');
insert into dictionary(id, dict_key, code, language_id, value) values (4, 'period.type', '2', 2, '月');
insert into dictionary(id, dict_key, code, language_id, value) values (5, 'period.type', '4', 1, 'Week');
insert into dictionary(id, dict_key, code, language_id, value) values (6, 'period.type', '4', 2, '周');
insert into dictionary(id, dict_key, code, language_id, value) values (7, 'period.type', '5', 1, 'Day');
insert into dictionary(id, dict_key, code, language_id, value) values (8, 'period.type', '5', 2, '天');
insert into dictionary(id, dict_key, code, language_id, value) values (9, 'period.type', '11', 1, 'Hour');
insert into dictionary(id, dict_key, code, language_id, value) values (10, 'period.type', '11', 2, '小时');
insert into dictionary(id, dict_key, code, language_id, value) values (11, 'period.hour', '12', 1, 'Minute');
insert into dictionary(id, dict_key, code, language_id, value) values (12, 'period.hour', '12', 2, '分钟');
insert into dictionary(id, dict_key, code, language_id, value) values (13, 'order.status', '1', 1, 'New');
insert into dictionary(id, dict_key, code, language_id, value) values (14, 'order.status', '1', 2, '新建');
insert into dictionary(id, dict_key, code, language_id, value) values (15, 'order.status', '2', 1, 'Processing');
insert into dictionary(id, dict_key, code, language_id, value) values (16, 'order.status', '2', 2, '处理中');
insert into dictionary(id, dict_key, code, language_id, value) values (17, 'order.status', '3', 1, 'Ready');
insert into dictionary(id, dict_key, code, language_id, value) values (18, 'order.status', '3', 2, '就绪');
insert into dictionary(id, dict_key, code, language_id, value) values (19, 'order.status', '4', 1, 'Active');
insert into dictionary(id, dict_key, code, language_id, value) values (20, 'order.status', '4', 2, '有效');
insert into dictionary(id, dict_key, code, language_id, value) values (21, 'order.status', '5', 1, 'Finished');
insert into dictionary(id, dict_key, code, language_id, value) values (22, 'order.status', '5', 2, '完成');
insert into dictionary(id, dict_key, code, language_id, value) values (23, 'order.status', '6', 1, 'Cancelled');
insert into dictionary(id, dict_key, code, language_id, value) values (24, 'order.status', '6', 2, '取消');
insert into dictionary(id, dict_key, code, language_id, value) values (25, 'true.false', 'true', 1, 'Yes');
insert into dictionary(id, dict_key, code, language_id, value) values (26, 'true.false', 'true', 2, '是');
insert into dictionary(id, dict_key, code, language_id, value) values (27, 'true.false', 'false', 1, 'No');
insert into dictionary(id, dict_key, code, language_id, value) values (28, 'true.false', 'false', 2, '否');
insert into dictionary(id, dict_key, code, language_id, value) values (29, 'invoice.status', '2', 1, 'Unpaid');
insert into dictionary(id, dict_key, code, language_id, value) values (30, 'invoice.status', '2', 2, '未支付');
insert into dictionary(id, dict_key, code, language_id, value) values (31, 'invoice.status', '3', 1, 'Paid');
insert into dictionary(id, dict_key, code, language_id, value) values (32, 'invoice.status', '3', 2, '已支付');
insert into dictionary(id, dict_key, code, language_id, value) values (33, 'invoice.status', '4', 1, 'Overdue');
insert into dictionary(id, dict_key, code, language_id, value) values (34, 'invoice.status', '4', 2, '已过期');
insert into dictionary(id, dict_key, code, language_id, value) values (35, 'invoice.status', '5', 1, 'Deleted');
insert into dictionary(id, dict_key, code, language_id, value) values (36, 'invoice.status', '5', 2, '已删除');

insert into resource(id, url, permission) values (1, '/admin', 'admin');

insert into resource(id, url, permission) values (2, '/admin/instance/modules/index', 'admin');
insert into resource(id, url, permission) values (3, '/admin/instance/scripts/bootstrap', 'admin');
insert into resource(id, url, permission) values (4, '/admin/instance/scripts/template', 'admin');
insert into resource(id, url, permission) values (5, '/admin/instance/getPagerInstanceList', 'admin');
insert into resource(id, url, permission) values (6, '/admin/instance/imcontrol', 'admin');
insert into resource(id, url, permission) values (7, '/admin/instance/createInstance', 'admin');
insert into resource(id, url, permission) values (8, '/admin/instance/getInstance', 'admin');

insert into resource(id, url, permission) values (9, '/admin/image/getPagerImageList', 'admin');
insert into resource(id, url, permission) values (10, '/admin/image/imgList', 'admin');
insert into resource(id, url, permission) values (11, '/admin/image/createImage', 'admin');
insert into resource(id, url, permission) values (12, '/admin/image/retrieveImage', 'admin');

insert into resource(id, url, permission) values (13, '/admin/flavor/modules/index', 'admin');
insert into resource(id, url, permission) values (14, '/admin/flavor/scripts/bootstrap', 'admin');
insert into resource(id, url, permission) values (15, '/admin/flavor/scripts/template', 'admin');
insert into resource(id, url, permission) values (16, '/admin/flavor/getPagerFlavorList', 'admin');
insert into resource(id, url, permission) values (17, '/admin/flavor/createFlavor', 'admin');
insert into resource(id, url, permission) values (18, '/admin/flavor/removeFlavor', 'admin');
insert into resource(id, url, permission) values (19, '/admin/flavor/getFlavorDetails', 'admin');

insert into resource(id, url, permission) values (20, '/admin/scripts/navinit', 'admin');
insert into resource(id, url, permission) values (21, '/admin/scripts/bootstrap', 'admin');
insert into resource(id, url, permission) values (22, '/admin/scripts/template', 'admin');
insert into resource(id, url, permission) values (23, '/admin/modules/entry/index', 'admin');

insert into resource(id, url, permission) values (24, '/admin/image/modules/index', 'admin');
insert into resource(id, url, permission) values (25, '/admin/flavor/flavorList', 'admin');
insert into resource(id, url, permission) values (26, '/admin/image/scripts/bootstrap', 'admin');
insert into resource(id, url, permission) values (27, '/admin/image/scripts/template', 'admin');
insert into resource(id, url, permission) values (28, '/admin/image/getPagerAllImageList', 'admin');
insert into resource(id, url, permission) values (29, '/admin/flavor/getPagerAllFlavorList', 'admin');
insert into resource(id, url, permission) values (30, '/admin/flavor/nameCheck', 'admin');

insert into resource(id, url, permission) values (31, '/user', 'user');
insert into resource(id, url, permission) values (32, '/user/scripts/navinit', 'user');
insert into resource(id, url, permission) values (35, '/user/modules/entry/index', 'user');


insert into resource(id, url, permission) values (36, '/admin/category/modules/index', 'admin');
insert into resource(id, url, permission) values (37, '/admin/category/scripts/bootstrap', 'admin');
insert into resource(id, url, permission) values (38, '/admin/category/scripts/template', 'admin');
insert into resource(id, url, permission) values (39, '/admin/category/listForJsp', 'admin');
insert into resource(id, url, permission) values (40, '/admin/category/create', 'admin');
insert into resource(id, url, permission) values (41, '/admin/category/update', 'admin');
insert into resource(id, url, permission) values (42, '/admin/category/remove', 'admin');

insert into resource(id, url, permission) values (43, '/admin/product/modules/index', 'admin');
insert into resource(id, url, permission) values (44, '/admin/product/scripts/bootstrap', 'admin');
insert into resource(id, url, permission) values (45, '/admin/product/scripts/template', 'admin');
insert into resource(id, url, permission) values (46, '/admin/product/remove', 'admin');
insert into resource(id, url, permission) values (47, '/admin/product/editPrice', 'admin');
insert into resource(id, url, permission) values (48, '/admin/product/listForJsp', 'admin');
insert into resource(id, url, permission) values (49, '/admin/product/create', 'admin');
insert into resource(id, url, permission) values (100, '/admin/product/update', 'admin');

insert into resource(id, url, permission) values (50, '/admin/category/listForJson', 'admin');

insert into resource(id, url, permission) values (60, '/admin/plan/planList', 'admin');
insert into resource(id, url, permission) values (61, '/admin/plan/retrievePlan', 'admin');
insert into resource(id, url, permission) values (62, '/admin/modules/entry/edit', 'admin');
insert into resource(id, url, permission) values (63, '/admin/modules/entry/save', 'admin');

insert into resource(id, url, permission) values (70, '/shopcart', 'user');

insert into resource(id, url, permission) values (200, '/user/cart/modules/index', 'user');
insert into resource(id, url, permission) values (201, '/user/cart/scripts/bootstrap', 'user');
insert into resource(id, url, permission) values (202, '/user/cart/scripts/template', 'user');
insert into resource(id, url, permission) values (203, '/user/cart/create', 'user');
insert into resource(id, url, permission) values (204, '/user/cart/clear', 'user');
insert into resource(id, url, permission) values (205, '/user/cart/add', 'user');
insert into resource(id, url, permission) values (206, '/user/cart/update', 'user');
insert into resource(id, url, permission) values (207, '/user/cart/remove', 'user');
insert into resource(id, url, permission) values (208, '/user/cart/showPayMethods', 'user');
insert into resource(id, url, permission) values (209, '/user/cart/checkout', 'user');
insert into resource(id, url, permission) values (210, '/user/cart/modules/ip', 'user');
insert into resource(id, url, permission) values (211, '/user/cart/modules/volume', 'user');
insert into resource(id, url, permission) values (212, '/user/cart/showOrderDetails', 'user');
insert into resource(id, url, permission) values (213, '/user/cart/showPayMethodNoBtn', 'user');



insert into resource(id, url, permission) values (300, '/user/instance/modules/index', 'user');
insert into resource(id, url, permission) values (301, '/user/instance/scripts/bootstrap', 'user');
insert into resource(id, url, permission) values (302, '/user/instance/getPagerInstanceList', 'user');
insert into resource(id, url, permission) values (303, '/user/instance/imcontrol', 'user');
insert into resource(id, url, permission) values (304, '/user/instance/getInstance', 'user');
insert into resource(id, url, permission) values (305, '/user/instance/scripts/template', 'user');
insert into resource(id, url, permission) values (306, '/user/instance/showInstanceDetails', 'user');
insert into resource(id, url, permission) values (307, '/user/instance/getInstancesWidthStatus', 'user');
insert into resource(id, url, permission) values (308, '/user/instance/getPagerInstanceStatusList', 'user');
insert into resource(id, url, permission) values (309, '/user/instance/modules/instanceStatus', 'user');
insert into resource(id, url, permission) values (310, '/user/instance/updateInstanceWithName', 'user');


insert into resource(id, url, permission) values (350, '/user/cinder/modules/index', 'user');
insert into resource(id, url, permission) values (351, '/user/cinder/getPagerVolumeTypeList', 'user');
insert into resource(id, url, permission) values (352, '/user/cinder/getPagerVolumeList', 'user');
insert into resource(id, url, permission) values (353, '/user/cinder/volumecontrol', 'user');
insert into resource(id, url, permission) values (354, '/user/cinder/getVolumeDetail', 'user');
insert into resource(id, url, permission) values (355, '/user/cinder/modules/template', 'user');

insert into resource(id, url, permission) values (360, '/user/network/modules/index', 'user');
insert into resource(id, url, permission) values (361, '/user/network/getPagerIPList', 'user');
insert into resource(id, url, permission) values (362, '/user/network/ipcontrol', 'user');
insert into resource(id, url, permission) values (363, '/user/network/getIPDetail', 'user');
insert into resource(id, url, permission) values (364, '/user/network/getIPDetail', 'user');
insert into resource(id, url, permission) values (365, '/user/network/ip/index', 'user');
insert into resource(id, url, permission) values (366, '/user/network/modules/template', 'user');

insert into resource(id, url, permission) values (380, '/user/plan/planList', 'user');

insert into resource(id, url, permission) values (400, '/admin/quantum/modules/index', 'admin');
insert into resource(id, url, permission) values (401, '/admin/quantum/getPagerNetworkList', 'admin');
insert into resource(id, url, permission) values (402, '/admin/quantum/createNetwork', 'admin');
insert into resource(id, url, permission) values (403, '/admin/quantum/createSubnet', 'admin');
insert into resource(id, url, permission) values (404, '/admin/quantum/showCreateNetworkForm', 'admin');
insert into resource(id, url, permission) values (405, '/admin/quantum/showCreateSubnetForm', 'admin');
insert into resource(id, url, permission) values (406, '/admin/quantum/showEditNetworkForm', 'admin');
insert into resource(id, url, permission) values (407, '/admin/quantum/editNetwork', 'admin');
insert into resource(id, url, permission) values (408, '/admin/quantum/removeNetwork', 'admin');
insert into resource(id, url, permission) values (409, '/admin/quantum/getNetworkDetails', 'admin');
insert into resource(id, url, permission) values (410, '/admin/quantum/getPagerSubnetList', 'admin');
insert into resource(id, url, permission) values (411, '/admin/quantum/getPagerPortList', 'admin');
insert into resource(id, url, permission) values (412, '/admin/quantum/createPort', 'admin');
insert into resource(id, url, permission) values (413, '/admin/quantum/editPort', 'admin');
insert into resource(id, url, permission) values (414, '/admin/quantum/showEditSubnetForm', 'admin');
insert into resource(id, url, permission) values (415, '/admin/quantum/showEditPortForm', 'admin');
insert into resource(id, url, permission) values (416, '/admin/quantum/showCreatePortForm', 'admin');
insert into resource(id, url, permission) values (417, '/admin/quantum/editSubnet', 'admin');
insert into resource(id, url, permission) values (418, '/admin/quantum/removePort', 'admin');
insert into resource(id, url, permission) values (419, '/admin/quantum/removeSubnet', 'admin');
insert into resource(id, url, permission) values (420, '/admin/quantum/showSubnetDetail', 'admin');
insert into resource(id, url, permission) values (421, '/admin/quantum/showPortDetail', 'admin');

insert into resource(id, url, permission) values (450, '/admin/cinder/modules/index', 'admin');
insert into resource(id, url, permission) values (451, '/admin/cinder/getPagerVolumeTypeList', 'admin');

insert into resource(id, url, permission) values (500, '/user/order/getPagerOrderList', 'user');
insert into resource(id, url, permission) values (501, '/user/order/modules/index', 'user');
insert into resource(id, url, permission) values (502, '/user/order/showOrderDetails', 'user');

insert into resource(id, url, permission) values (600, '/admin/order/getPagerOrderList', 'admin');
insert into resource(id, url, permission) values (601, '/admin/order/modules/index', 'admin');

insert into resource(id, url, permission) values (700, '/user/showedit', 'user');
insert into resource(id, url, permission) values (701, '/user/edit', 'user');
insert into resource(id, url, permission) values (702, '/user/changePassword', 'user');


insert into resource(id, url, permission) values (800, '/user/invoice/modules/index', 'user');
insert into resource(id, url, permission) values (801, '/user/invoice/getPagerInvoiceList', 'user');

insert into resource(id, url, permission) values (900, '/user/pay/showPay', 'user');
insert into resource(id, url, permission) values (901, '/user/pay/topup', 'user');
insert into resource(id, url, permission) values (902, '/user/pay/accountpay', 'user');
insert into resource(id, url, permission) values (903, '/user/pay/alipay', 'user');



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
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (29,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (30,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (31,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (32,'name_id',NULL,'ItemSpecification');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (33,'name_id',NULL,'DataCenter');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (34,'name_id',NULL,'DataCenter');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (40,'type',NULL,'PaymentMethod');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (41,'type',NULL,'PaymentMethod');
INSERT INTO `i18n_link` (`id`,`column_name`,`create_time`,`table_name`) VALUES (42,'type',NULL,'PaymentMethod');

INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (1,'OS Image',1,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (2,'操作系统镜像',1,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (3,'Data Center',2,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (4,'数据中心',2,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (5,'Flavor',3,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (6,'硬件配置',3,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (7,'Plan',4,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (8,'付费套餐',4,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (9,'IP Address',5,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (10,'IP地址',5,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (11,'Storage',6,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (12,'云存储',6,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (13,'Usage',7,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (14,'用量',7,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (15,'Promotion',8,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (16,'促销',8,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (17,'Other 1',9,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (18,'其他1',9,2);

INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (19,'Pay AS You Go',10,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (20,'即付即用',10,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (21,'Day',11,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (22,'包天',11,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (23,'Month',12,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (24,'包月',12,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (25,'Year',13,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (26,'包年',13,2);

INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (27,'Cirros for Demo',14,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (28,'测试用Cirros',14,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (29,'Data Center A',15,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (30,'数据中心A',15,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (31,'Data Center B',16,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (32,'数据中心B',16,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (33,'Flavor Tiny',17,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (34,'迷你型配置',17,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (35,'Flavor Small',18,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (36,'经济型配置',18,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (37,'Flavor Medium',19,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (38,'主流型配置',19,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (39,'Flavor Large',20,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (40,'商务型配置',20,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (41,'Flavor Ultra',21,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (42,'豪华型配置',21,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (43,'Pay As You Go',22,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (44,'即付即用',22,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (45,'Monthly',23,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (46,'按月付费',23,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (47,'Yearly',24,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (48,'按年付费',24,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (49,'Web Address',25,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (50,'网站IP',25,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (51,'Not Web Address',26,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (52,'非网站IP',26,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (53,'Volume 5GB',27,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (54,'5G磁盘',27,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (55,'Volume 10GB',28,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (56,'10G磁盘',28,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (57,'CPU Usage',29,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (58,'CPU用量',29,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (59,'Memory Usage',30,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (60,'内存用量',30,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (61,'Disk Usage',31,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (62,'磁盘用量',31,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (63,'Network Usage',32,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (64,'网络用量',32,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (65,'Data Center A',33,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (66,'数据中心A',33,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (67,'Data Center B',34,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (68,'数据中心B',34,2);

INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (70,'alipay',40,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (71,'支付宝',40,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (72,'Account payment',41,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (73,'账户',41,2);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (74,'offline payment',42,1);
INSERT INTO `i18n` (`id`,`content`,`i18n_link_id`,`language_id`) VALUES (75,'线下',42,2);

INSERT INTO `category` (`id`,`enable`,`system`,`name_id`) VALUES (1,1,1,1);
INSERT INTO `category` (`id`,`enable`,`system`,`name_id`) VALUES (2,1,1,2);
INSERT INTO `category` (`id`,`enable`,`system`,`name_id`) VALUES (3,1,1,3);
INSERT INTO `category` (`id`,`enable`,`system`,`name_id`) VALUES (4,1,1,4);
INSERT INTO `category` (`id`,`enable`,`system`,`name_id`) VALUES (5,1,1,5);
INSERT INTO `category` (`id`,`enable`,`system`,`name_id`) VALUES (6,1,1,6);
INSERT INTO `category` (`id`,`enable`,`system`,`name_id`) VALUES (7,1,1,7);
INSERT INTO `category` (`id`,`enable`,`system`,`name_id`) VALUES (8,1,1,8);
INSERT INTO `category` (`id`,`enable`,`system`,`name_id`) VALUES (9,1,1,9);

INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id, pay_as_you_go) values(1, '2013-02-21 12:25:48', 0, 1, 11, 10, true);
INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id, pay_as_you_go) values(2, '2013-02-21 12:25:48', 0, 1, 5, 11, false);
INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id, pay_as_you_go) values(3, '2013-02-21 12:25:48', 0, 1, 2, 12, false);
INSERT INTO order_period(id, create_time, deleted, period_qutity, period_type, name_id, pay_as_you_go) values(4, '2013-02-21 12:25:48', 0, 1, 1, 13, false);

INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (1,1,'2013-02-25 11:06:14',0,14,2,'1','2013-02-25 11:06:14',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (2,1,'2013-02-25 11:06:42',0,15,7,'1','2013-02-25 11:06:42',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (3,0,'2013-02-25 11:07:01',0,16,7,'2','2013-02-25 11:07:01',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (4,1,'2013-02-25 11:07:33',1,17,1,'1','2013-02-25 11:07:33',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (5,1,'2013-02-25 11:07:57',5,18,1,'2','2013-02-25 11:07:57',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (6,1,'2013-02-25 11:08:22',10,19,1,'3','2013-02-25 11:08:22',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (7,1,'2013-02-25 11:08:48',20,20,1,'4','2013-02-25 11:08:48',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (8,1,'2013-02-25 11:09:10',50,21,1,'5','2013-02-25 11:09:10',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (9,1,'2013-02-25 11:09:36',0,22,6,'1','2013-02-25 11:09:36',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (10,1,'2013-02-25 11:10:08',1,23,6,'3','2013-02-25 11:10:08',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (11,1,'2013-02-25 11:10:29',10,24,6,'4','2013-02-25 11:10:29',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (12,1,'2013-02-25 11:10:55',50,25,4,'1','2013-02-25 11:10:55',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (13,1,'2013-02-25 11:10:55',50,26,4,'2','2013-02-25 11:10:55',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (14,1,'2013-02-25 11:11:19',5,27,3,'1','2013-02-25 11:11:19',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (15,1,'2013-02-25 11:11:44',10,28,3,'2','2013-02-25 11:11:44',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (16,1,'2013-02-25 11:12:06',0.01,29,5,NULL,'2013-02-25 11:12:06',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (17,1,'2013-02-25 11:12:06',0.01,30,5,NULL,'2013-02-25 11:12:06',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (18,1,'2013-02-25 11:12:06',0.01,31,5,NULL,'2013-02-25 11:12:06',NULL);
INSERT INTO `item_specification` (`id`,`available`,`created`,`default_price`,`name_id`,`os_type`,`ref_id`,`updated`,`profile_id`) VALUES (19,1,'2013-02-25 11:12:06',0.01,32,5,NULL,'2013-02-25 11:12:06',NULL);

INSERT INTO `profile` (`id`,`cpu_id`,`disk_id`,`memory_id`,`network_id`) VALUES (1,16,17,18,19);

INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (1,'2013-02-25 11:06:14','2013-02-25 11:06:14',0,1);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (2,'2013-02-25 11:06:42','2013-02-25 11:06:42',0,2);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (3,'2013-02-25 11:07:01','2013-02-25 11:07:01',0,3);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (4,'2013-02-25 11:07:33','2013-02-25 11:07:33',1,4);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (5,'2013-02-25 11:07:57','2013-02-25 11:07:57',5,5);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (6,'2013-02-25 11:08:22','2013-02-25 11:08:22',10,6);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (7,'2013-02-25 11:08:48','2013-02-25 11:08:48',20,7);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (8,'2013-02-25 11:09:10','2013-02-25 11:09:10',50,8);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (9,'2013-02-25 11:09:36','2013-02-25 11:09:36',0,9);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (10,'2013-02-25 11:10:08','2013-02-25 11:10:08',1,10);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (11,'2013-02-25 11:10:29','2013-02-25 11:10:29',10,11);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (12,'2013-02-25 11:10:55','2013-02-25 11:10:55',50,12);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (13,'2013-02-25 11:11:19','2013-02-25 11:11:19',50,13);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (14,'2013-02-25 11:11:19','2013-02-25 11:11:19',5,14);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (15,'2013-02-25 11:11:44','2013-02-25 11:11:44',10,15);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (16,'2013-02-25 11:12:06','2013-02-25 11:12:06',0.01,16);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (17,'2013-02-25 11:12:06','2013-02-25 11:12:06',0.02,17);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (18,'2013-02-25 11:12:06','2013-02-25 11:12:06',0.03,18);
INSERT INTO `price` (`id`,`activated`,`created`,`value`,`item_id`) VALUES (19,'2013-02-25 11:12:06','2013-02-25 11:12:06',0.05,19);

INSERT INTO `data_center` (`id`,`name_id`,`external_network`) VALUES (1,33,'e1070932-b912-4680-8f3c-df8eb1110bdb');
INSERT INTO `data_center` (`id`,`name_id`,`external_network`) VALUES (2,34,'e1070932-b912-4680-8f3c-df8eb1110bdb');

INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (1,'1','1',1);
INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (2,'2','2',1);
INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (3,'3','3',1);
INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (4,'4','4',1);
INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (5,'5','5',1);
INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (6,'1','1',2);
INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (7,'2','2',2);
INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (8,'3','3',2);
INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (9,'4','4',2);
INSERT INTO `flavor` (`id`,`uuid`,`ref_id`,`data_center_id`) VALUES (10,'5','5',2);

INSERT INTO `image` (`id`,`uuid`,`ref_id`,`data_center_id`,`family`) VALUES (1,'1','0904cd16-ba21-4eea-afe0-c48ba91f4200',1,'other');
INSERT INTO `image` (`id`,`uuid`,`ref_id`,`data_center_id`,`family`) VALUES (2,'1','0904cd16-ba21-4eea-afe0-c48ba91f4200',2,'other');

INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (1,'6da6ace8-72a5-44bc-95eb-ffdd352df812','1',1);
INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (2,'0232a395-350c-4321-945c-29745e7f95e0','2',1);
INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (3,'cedb4a86-8790-477e-96d7-0ecc5fc239c9','3',1);
INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (4,'ece77af2-67b6-4907-9c3e-600d0d759ea7','4',1);
INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (5,'a45a1137-9672-4a2d-81cb-b3d105cd48c1','5',1);
INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (6,'6da6ace8-72a5-44bc-95eb-ffdd352df812','1',2);
INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (7,'0232a395-350c-4321-945c-29745e7f95e0','2',2);
INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (8,'cedb4a86-8790-477e-96d7-0ecc5fc239c9','3',2);
INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (9,'ece77af2-67b6-4907-9c3e-600d0d759ea7','4',2);
INSERT INTO `volume_type` (`id`,`ref_id`,`uuid`,`data_center_id`) VALUES (10,'a45a1137-9672-4a2d-81cb-b3d105cd48c1','5',2);

INSERT INTO `network_type` (`id`,`web`,`data_center_id`,`uuid`) VALUES (1,0,1,'1');
INSERT INTO `network_type` (`id`,`web`,`data_center_id`,`uuid`) VALUES (2,1,1,'2');
INSERT INTO `network_type` (`id`,`web`,`data_center_id`,`uuid`) VALUES (3,0,2,'1');
INSERT INTO `network_type` (`id`,`web`,`data_center_id`,`uuid`) VALUES (4,1,2,'2');

insert into payment_method(id, payment_type, catalog, icon, text, endpoint) values (1, 1, 3, null, 40, '{host}/user/pay/alipay');
insert into payment_method(id, payment_type, catalog, icon, text, endpoint) values (2, 504, 2, null, 41, '{host}/user/pay/accountpay');
insert into payment_method(id, payment_type, catalog, icon, text, endpoint) values (3, 50, 3, null, 42, '');

insert into payment_method_property(id, method_id, type, name, value) values (1, 1, 2, 'service', 'create_direct_pay_by_user');
insert into payment_method_property(id, method_id, type, name, value) values (2, 1, 2, 'partner', '');
insert into payment_method_property(id, method_id, type, name, value) values (3, 1, 2, '_input_charset', 'utf-8');
insert into payment_method_property(id, method_id, type, name, value) values (4, 1, 2, 'sign_type', 'MD5');
insert into payment_method_property(id, method_id, type, name, value) values (5, 1, 3, 'sign', 'com.inforstack.openstack.payment.method.prop.builder.AlipaySignParamBuilder.build');
insert into payment_method_property(id, method_id, type, name, value) values (6, 1, 2, 'return_url', '{host}/user/pay/alipay');
insert into payment_method_property(id, method_id, type, name, value) values (7, 1, 2, 'out_trade_no', '{payment.sequence}');
insert into payment_method_property(id, method_id, type, name, value) values (8, 1, 2, 'subject', '{payment.subject}');
insert into payment_method_property(id, method_id, type, name, value) values (9, 1, 2, 'payment_type', '1');
insert into payment_method_property(id, method_id, type, name, value) values (10, 1, 2, 'seller_id', '');
insert into payment_method_property(id, method_id, type, name, value) values (11, 1, 2, 'total_fee', '{price}');
insert into payment_method_property(id, method_id, type, name, value) values (12, 1, 2, 'trade_id', '{payment.id}');
insert into payment_method_property(id, method_id, type, name, value) values (13, 2, 2, 'trade_id', '{payment.id}');

insert into mail_configuration(id, protocal, host, port, username, password) values (1, 'smtp', 'smtp.163.com', '25', 'test99cloud@163.com', '455740054e5c');

insert into mail(id, code, sender) values (1, 'validateUser', 1);
insert into mail(id, code, sender) values (2, 'resetPassword', 1);
insert into mail(id, code, sender) values (3, 'resetEmail', 1);
insert into mail(id, code, sender) values (4, 'resetEmailVerify', 1);

insert into mail_template(id, language_id, type, title, body, mail_id) values(1, 2, 1, '激活', '{url}', 1);
insert into mail_template(id, language_id, type, title, body, mail_id) values(2, 2, 2, '重置密码', '{url}', 2);
insert into mail_template(id, language_id, type, title, body, mail_id) values(3, 2, 2, '修改邮箱', '{url}', 3);
insert into mail_template(id, language_id, type, title, body, mail_id) values(4, 2, 2, '修改邮箱验证码', '{url}', 4);