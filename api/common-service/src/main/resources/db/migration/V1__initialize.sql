-- 初始租户
CREATE TABLE IF NOT EXISTS `tbl_common_service_client` (
                 `id` VARCHAR(255) NOT NULL,
                 `address` VARCHAR(255) NULL DEFAULT NULL,
                 `audit_time` DATETIME(6) NULL DEFAULT NULL,
                 `phone` VARCHAR(255) NULL DEFAULT NULL,
                 `create_time` DATETIME(6) NOT NULL,
                 `email` VARCHAR(255) NULL DEFAULT NULL,
                 `logo` VARCHAR(255) NULL DEFAULT NULL,
                 `name` VARCHAR(255) NULL DEFAULT NULL,
                 `remark` VARCHAR(255) NULL DEFAULT NULL,
                 `systemic` BIT(1) NULL DEFAULT NULL,
                 `usable` BIT(1) NULL DEFAULT NULL,
                 `username` VARCHAR(255) NULL DEFAULT NULL,
                 `approved` BIT(1) NOT NULL,
                 PRIMARY KEY (`id`)
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;
REPLACE INTO `tbl_common_service_client` (`id`, `address`, `audit_time`, `phone`, `create_time`, `email`, `logo`, `name`, `remark`, `systemic`, `usable`, `username`, `approved`) VALUES
('T2Admin', '四川省成都市', NULL, '15520449931', '2020-12-29 14:21:55.183332', 'it_tangzhang@163.com', 'https://my-admin-v2.oss-cn-chengdu.aliyuncs.com/tzcc_ren/picture/10000/logo.jpg_custom', 'T2Admin System', '哇...', b'0', b'1', 'tangzhangss', b'1');

-- 初始用户
CREATE TABLE IF NOT EXISTS `tbl_common_service_user` (
           `id` BIGINT(20) NOT NULL,
           `client_id` VARCHAR(50) NOT NULL,
           `create_time` DATETIME(6) NOT NULL,
           `creator_id` BIGINT(20) NOT NULL,
           `remark` VARCHAR(255) NULL DEFAULT NULL,
           `systemic` BIT(1) NULL DEFAULT NULL,
           `update_time` DATETIME(6) NULL DEFAULT NULL,
           `usable` BIT(1) NOT NULL,
           `avatars` VARCHAR(255) NULL DEFAULT NULL,
           `phone` VARCHAR(255) NULL DEFAULT NULL,
           `email` VARCHAR(255) NULL DEFAULT NULL,
           `name` VARCHAR(255) NULL DEFAULT NULL,
           `password` VARCHAR(255) NULL DEFAULT NULL,
           `role_ids` VARCHAR(255) NULL DEFAULT NULL,
           `username` VARCHAR(255) NULL DEFAULT NULL,
           `creator_name` VARCHAR(255) NOT NULL,
           PRIMARY KEY (`id`)
)
    COLLATE='utf8mb4_general_ci'
    ENGINE=InnoDB
;
INSERT IGNORE INTO `tbl_common_service_user` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `avatars`, `phone`, `email`, `name`, `password`, `role_ids`, `username`, `creator_name`) VALUES
(10000, 'T2Admin', '2020-12-13 19:40:47.000000', 10000, '企业管理员账号', b'1', '2021-01-16 19:53:36.678860', b'1', 'https://my-admin-v2.oss-cn-chengdu.aliyuncs.com/tzcc_ren/picture/10000/logo.jpg_custom', '15520449931', 'it_tangzhang@163.com', 'T2Admin', 'd96411f9ee2c4f1d1d980553e2c9f99c', '', 'T2Admin', '');
-- 初始菜单
CREATE TABLE IF NOT EXISTS `tbl_common_service_domain` (
                                             `id` BIGINT(20) NOT NULL,
                                             `client_id` VARCHAR(255) NOT NULL,
                                             `create_time` DATETIME(6) NOT NULL,
                                             `creator_id` BIGINT(20) NOT NULL,
                                             `remark` VARCHAR(255) NULL DEFAULT NULL,
                                             `systemic` BIT(1) NOT NULL,
                                             `update_time` DATETIME(6) NULL DEFAULT NULL,
                                             `usable` BIT(1) NOT NULL,
                                             `dev_address` VARCHAR(255) NULL DEFAULT NULL,
                                             `pro_address` VARCHAR(255) NULL DEFAULT NULL,
                                             `name` VARCHAR(255) NULL DEFAULT NULL,
                                             `creator_name` VARCHAR(255) NOT NULL,
                                             PRIMARY KEY (`id`)
)
    COLLATE='utf8mb4_general_ci'
    ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `tbl_common_service_menu` (
                                           `id` BIGINT(20) NOT NULL,
                                           `client_id` VARCHAR(50) NOT NULL,
                                           `create_time` DATETIME(6) NOT NULL,
                                           `creator_id` BIGINT(20) NOT NULL,
                                           `remark` VARCHAR(255) NULL DEFAULT NULL,
                                           `systemic` BIT(1) NULL DEFAULT NULL,
                                           `update_time` DATETIME(6) NULL DEFAULT NULL,
                                           `usable` BIT(1) NOT NULL,
                                           `active_menu` VARCHAR(255) NULL DEFAULT NULL,
                                           `affix` BIT(1) NULL DEFAULT NULL,
                                           `always_show` BIT(1) NULL DEFAULT NULL,
                                           `breadcrumb` BIT(1) NULL DEFAULT NULL,
                                           `hidden` BIT(1) NULL DEFAULT NULL,
                                           `icon` VARCHAR(255) NULL DEFAULT NULL,
                                           `no_cache` BIT(1) NULL DEFAULT NULL,
                                           `parent_id` BIGINT(20) NULL DEFAULT NULL,
                                           `path` VARCHAR(255) NULL DEFAULT NULL,
                                           `redirect` VARCHAR(255) NULL DEFAULT NULL,
                                           `title` VARCHAR(255) NULL DEFAULT NULL,
                                           `url` VARCHAR(255) NULL DEFAULT NULL,
                                           `name` VARCHAR(255) NULL DEFAULT NULL,
                                           `order_no` INT(11) NOT NULL,
                                           `client_ids` VARCHAR(255) NULL DEFAULT NULL,
                                           `domain_id` BIGINT(20) NULL DEFAULT NULL,
                                           `creator_name` VARCHAR(255) NOT NULL,
                                           PRIMARY KEY (`id`),
                                           INDEX `FKqyrihvqku7rbkr5n4vabirlpc` (`parent_id`),
                                           INDEX `FKppf5ygd14tw2n5drgbs7j5vc6` (`domain_id`),
                                           CONSTRAINT `FKppf5ygd14tw2n5drgbs7j5vc6` FOREIGN KEY (`domain_id`) REFERENCES `tbl_common_service_domain` (`id`),
                                           CONSTRAINT `FKqyrihvqku7rbkr5n4vabirlpc` FOREIGN KEY (`parent_id`) REFERENCES `tbl_common_service_menu` (`id`)
)
    COLLATE='utf8mb4_general_ci'
    ENGINE=InnoDB
;

INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (24441845624834, 'T2Admin', '2021-01-07 10:54:50.487665', 10000, '', b'1', '2021-11-19 13:37:18.344555', b'1', '', b'0', b'1', b'1', b'0', 'menu-set', b'0', NULL, '/system', 'noRedirect', '系统设置', '', 'System', 1, '', NULL, '');
INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (31401559666688, 'T2Admin', '2021-01-26 15:43:23.317097', 10000, '', b'1', '2021-11-19 13:41:30.693510', b'1', '', b'0', b'1', b'1', b'0', 'base-data', b'0', NULL, '/data', 'noRedirect', '基础数据', '', 'Data', 100, '', NULL, '');
INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (24441845624835, 'T2Admin', '2021-01-07 10:59:56.651307', 10000, '', b'1', '2021-02-06 22:51:04.677586', b'1', '', b'0', b'1', b'1', b'0', 'icon_role', b'0', 24441845624834, 'role', 'noRedirect', '角色信息', '/system/role', 'Role', 16, '', NULL, '');
INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (24441845624836, 'T2Admin', '2021-01-07 11:02:21.135843', 10000, '', b'1', '2021-11-19 10:44:20.930173', b'1', '', b'0', b'1', b'1', b'0', 'user_hollow', b'0', 24441845624834, 'user', 'noRedirect', '用户信息', '/system/user', 'User', 14, '', NULL, '');
INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (24441845624837, 'T2Admin', '2021-01-07 11:03:06.248182', 10000, '', b'0', '2021-11-19 10:59:01.855764', b'1', '', b'0', b'1', b'1', b'0', 'menu', b'0', 24441845624834, 'menu', 'noRedirect', '左侧菜单', '/system/menu', 'Menu', 10, '', NULL, '');
INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (26024251785216, 'T2Admin', '2021-01-11 19:37:40.419639', 10000, '', b'0', '2021-02-04 11:09:00.220413', b'1', '', b'0', b'1', b'1', b'0', 'icon_client', b'0', 24441845624834, 'client', 'noRedirect', '客户列表', '/system/client', 'Client', 12, '', NULL, '');
INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (31403912675328, 'T2Admin', '2021-01-26 15:49:31.221966', 10000, '', b'1', '2021-11-19 13:42:56.214055', b'1', '', b'0', b'1', b'1', b'0', 'memorandum', b'0', 31401559666688, 'memorandum', 'noRedirect', '备忘录', '/data/memorandum', 'Memorandum', 110, '', NULL, '');
INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (34240742805504, 'T2Admin', '2021-02-03 11:50:53.048657', 10000, '', b'0', '2021-11-19 13:44:16.459102', b'1', '', b'0', b'1', b'1', b'0', 'album', b'0', 31401559666688, 'album', 'noRedirect', '相册', '/data/album', 'Album', 120, '861136242', NULL, '');
INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (53924352241664, 'T2Admin', '2021-03-29 19:36:02.551550', 10000, '', b'0', '2021-03-29 19:44:12.167315', b'1', '', b'0', b'1', b'1', b'0', 'domain', b'0', 24441845624834, 'domain', 'noRedirect', '域名定义', '/system/domain', 'Domain', 18, '', NULL, '');
INSERT IGNORE INTO `tbl_common_service_menu` (`id`, `client_id`, `create_time`, `creator_id`, `remark`, `systemic`, `update_time`, `usable`, `active_menu`, `affix`, `always_show`, `breadcrumb`, `hidden`, `icon`, `no_cache`, `parent_id`, `path`, `redirect`, `title`, `url`, `name`, `order_no`, `client_ids`, `domain_id`, `creator_name`) VALUES (186129680711681, 'T2Admin', '2022-03-29 15:34:22.590477', 10000, '', b'1', '2022-03-29 15:37:21.847316', b'1', '', b'0', b'1', b'1', b'0', 'dict', b'0', 24441845624834, 'dict', 'noRedirect', '系统字典', '/system/dict', 'Dict', 20, '', NULL, 'T2Admin');
;
