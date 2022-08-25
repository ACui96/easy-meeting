/*
 Navicat Premium Data Transfer

 Source Server         : 中银金科
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 172.30.66.253:3306
 Source Schema         : sled_dev

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 25/08/2022 10:42:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_module_menu
-- ----------------------------
DROP TABLE IF EXISTS `b_module_menu`;
CREATE TABLE `b_module_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mod_id` varchar(6) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜单编号',
  `mod_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '子菜单路由',
  `mod_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜单中文名称',
  `mod_icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜单图标',
  `mod_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜单地址',
  `mod_component` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜单元素',
  `mod_pid` varchar(6) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '父菜单编号',
  `is_child` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '是否是叶子节点，1-是，2-不是',
  `sort_id` int(11) NULL DEFAULT 0 COMMENT '菜单排序编号',
  `mod_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜单状态',
  `rem` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '备注说明',
  `mod_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `menu_idx`(`mod_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '系统菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_module_menu
-- ----------------------------
INSERT INTO `b_module_menu` VALUES (1, '-1', 'root', '', '', '', '', '', '2', 0, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (2, '01', 'deposit', '押金管理', 'dict', '/deposit', 'Layout', '-1', '2', 1, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (3, '02', 'System', '系统管理', 'system', '/System', 'Layout', '-1', '2', 3, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (4, '0201', 'User', '用户管理', '', 'user', 'system/user/index', '02', '1', 1, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (5, '0202', 'Role', '角色管理', '', 'role', 'system/role/index', '02', '1', 2, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (6, '0101', 'PrivateInquiry', '对私押金查询', '', 'privateInquiry', 'deposit/privateInquiry', '01', '1', 1, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (7, '0102', 'CompanyInquiry', '对公押金查询', '', 'companyInquiry', 'deposit/companyInquiry', '01', '1', 2, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (8, '0104', 'CompanyOrder', '对公押金打标', '', 'companyOrder', 'deposit/companyOrder', '01', '1', 4, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (9, '0203', 'paramConfig', '参数管理', '', 'paramConfig', 'system/paramConfig/index', '02', '1', 3, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (10, '04', 'Reconcile', '对账管理', 'list', '/reconcile', 'Layout', '-1', '2', 4, '0', '', '2022-06-23 16:07:49');
INSERT INTO `b_module_menu` VALUES (11, '0401', 'Liation', '手动对账', '', 'liation', 'reconcile/liation/index', '04', '1', 0, '0', '', '2022-06-23 16:07:49');

-- ----------------------------
-- Table structure for b_role_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `b_role_menu_rel`;
CREATE TABLE `b_role_menu_rel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '角色编号',
  `mod_id` varchar(6) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜单编号',
  `mod_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `mod_idx`(`role_id`, `mod_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of b_role_menu_rel
-- ----------------------------
INSERT INTO `b_role_menu_rel` VALUES (1, '1', '01', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (2, '1', '0101', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (3, '1', '0102', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (4, '1', '0104', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (5, '1', '02', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (6, '1', '0201', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (7, '1', '0202', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (8, '1', '03', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (9, '1', '0301', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (10, '2', '01', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (11, '2', '0101', '2022-06-23 16:08:37');
INSERT INTO `b_role_menu_rel` VALUES (12, '2', '0102', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (13, '2', '0104', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (14, '2', '02', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (15, '2', '0201', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (16, '2', '03', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (17, '2', '0301', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (18, '3', '01', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (19, '3', '0101', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (20, '3', '0102', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (21, '3', '0104', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (22, '3', '03', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (23, '3', '0301', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (24, '1', '0203', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (25, '1', '04', '2022-06-23 16:08:38');
INSERT INTO `b_role_menu_rel` VALUES (26, '1', '0401', '2022-06-23 16:08:38');

-- ----------------------------
-- Table structure for b_role_user_rel
-- ----------------------------
DROP TABLE IF EXISTS `b_role_user_rel`;
CREATE TABLE `b_role_user_rel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '角色编号',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '用户ID',
  `mod_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_idx`(`role_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色人员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of b_role_user_rel
-- ----------------------------
INSERT INTO `b_role_user_rel` VALUES (1, '1', '18071026689', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (2, '1', '18872530056', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (3, '2', '1638778282429', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (4, '2', '1638778316757', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (5, '2', '1638778356504', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (6, '2', '1638778390015', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (7, '2', '1642141631454', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (8, '3', '1638778606113', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (9, '3', '1638778625443', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (10, '3', '1638778652943', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (11, '3', '1638778672731', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (12, '3', '1638778715801', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (13, '3', '1638778730690', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (14, '3', '1638778749086', '2022-06-23 16:08:46');
INSERT INTO `b_role_user_rel` VALUES (15, '3', '1638778772624', '2022-06-23 16:08:47');
INSERT INTO `b_role_user_rel` VALUES (16, '3', '1638778797304', '2022-06-23 16:08:47');
INSERT INTO `b_role_user_rel` VALUES (17, '3', '1638778845513', '2022-06-23 16:08:47');
INSERT INTO `b_role_user_rel` VALUES (18, '3', '1638778866763', '2022-06-23 16:08:47');
INSERT INTO `b_role_user_rel` VALUES (19, '3', '1638778898804', '2022-06-23 16:08:47');
INSERT INTO `b_role_user_rel` VALUES (20, '3', '1638778921712', '2022-06-23 16:08:47');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_dept_id`(`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1552553108568068098, '超级管理员', '', NULL, 1067246875800000001, '2022-07-28 15:14:41', 1067246875800000001, '2022-07-28 15:14:41');
INSERT INTO `sys_role` VALUES (1552576291375427586, '普通用户', '', NULL, 1067246875800000001, '2022-07-28 16:46:48', 1067246875800000001, '2022-07-29 14:04:45');

-- ----------------------------
-- Table structure for t_attendees
-- ----------------------------
DROP TABLE IF EXISTS `t_attendees`;
CREATE TABLE `t_attendees`  (
  `a_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `a_uid` bigint(20) NOT NULL COMMENT '外键 员工id',
  `a_cid` bigint(20) NOT NULL COMMENT '外键 会议id',
  `a_msg_status` int(1) NULL DEFAULT 1 COMMENT '1未发送 2 已发送 ',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `a_ustatus` int(1) NULL DEFAULT 1 COMMENT '0 被删除 1 正常',
  PRIMARY KEY (`a_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 128 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_attendees
-- ----------------------------
INSERT INTO `t_attendees` VALUES (1, 7, 13, 1, '2022-07-15 10:38:28', '2022-07-15 10:38:31', 1);
INSERT INTO `t_attendees` VALUES (2, 8, 13, 1, '2022-07-15 10:40:48', '2022-07-15 10:40:51', 1);
INSERT INTO `t_attendees` VALUES (3, 11, 13, 1, '2022-07-15 10:40:48', '2022-07-15 10:40:51', 1);
INSERT INTO `t_attendees` VALUES (4, 10, 14, 1, '2022-07-15 10:40:48', '2022-07-26 11:27:30', 1);
INSERT INTO `t_attendees` VALUES (5, 11, 14, 1, '2022-07-15 10:40:48', '2022-07-15 10:40:51', 1);
INSERT INTO `t_attendees` VALUES (6, 1, 54, 1, '2022-07-21 15:13:06', '2022-07-21 15:13:06', 1);
INSERT INTO `t_attendees` VALUES (7, 1, 54, 1, '2022-07-21 15:13:06', '2022-07-21 15:13:06', 1);
INSERT INTO `t_attendees` VALUES (8, 1, 55, 1, '2022-07-21 15:17:23', '2022-07-21 15:17:23', 1);
INSERT INTO `t_attendees` VALUES (9, 1, 55, 2, '2022-07-21 15:17:23', '2022-07-21 15:17:23', 1);
INSERT INTO `t_attendees` VALUES (10, 1, 55, 1, '2022-07-21 15:17:23', '2022-07-21 15:17:23', 1);
INSERT INTO `t_attendees` VALUES (11, 1, 55, 1, '2022-07-21 15:17:23', '2022-07-21 15:17:23', 1);
INSERT INTO `t_attendees` VALUES (12, 1, 56, 1, '2022-07-21 15:18:36', '2022-07-21 15:18:36', 1);
INSERT INTO `t_attendees` VALUES (13, 1, 56, 1, '2022-07-21 15:18:36', '2022-07-21 15:18:36', 1);
INSERT INTO `t_attendees` VALUES (14, 1, 56, 1, '2022-07-21 15:18:36', '2022-07-21 15:18:36', 1);
INSERT INTO `t_attendees` VALUES (15, 1, 56, 1, '2022-07-21 15:18:36', '2022-07-21 15:18:36', 1);
INSERT INTO `t_attendees` VALUES (16, 1, 57, 1, '2022-07-21 15:19:46', '2022-07-21 15:19:46', 1);
INSERT INTO `t_attendees` VALUES (17, 2, 57, 1, '2022-07-21 15:19:46', '2022-07-21 15:19:46', 1);
INSERT INTO `t_attendees` VALUES (18, 3, 57, 1, '2022-07-21 15:19:46', '2022-07-21 15:19:46', 1);
INSERT INTO `t_attendees` VALUES (19, 4, 57, 1, '2022-07-21 15:19:46', '2022-07-21 15:19:46', 1);
INSERT INTO `t_attendees` VALUES (20, 1, 58, 1, '2022-07-21 15:22:54', '2022-07-21 15:22:54', 1);
INSERT INTO `t_attendees` VALUES (21, 3, 58, 1, '2022-07-21 15:22:54', '2022-07-21 15:22:54', 1);
INSERT INTO `t_attendees` VALUES (22, 5, 58, 1, '2022-07-21 15:22:54', '2022-07-21 15:22:54', 1);
INSERT INTO `t_attendees` VALUES (23, 12, 58, 1, '2022-07-21 15:22:54', '2022-07-26 11:24:47', 1);
INSERT INTO `t_attendees` VALUES (24, 1, 60, 1, '2022-07-21 16:55:41', '2022-07-21 16:55:41', 1);
INSERT INTO `t_attendees` VALUES (25, 3, 60, 1, '2022-07-21 16:55:41', '2022-07-21 16:55:41', 1);
INSERT INTO `t_attendees` VALUES (26, 5, 60, 1, '2022-07-21 16:55:41', '2022-07-21 16:55:41', 1);
INSERT INTO `t_attendees` VALUES (27, 12, 60, 1, '2022-07-21 16:55:41', '2022-07-26 11:24:47', 1);
INSERT INTO `t_attendees` VALUES (28, 1, 61, 1, '2022-07-21 17:04:50', '2022-07-21 17:04:50', 1);
INSERT INTO `t_attendees` VALUES (29, 2, 61, 1, '2022-07-21 17:04:50', '2022-07-21 17:04:50', 1);
INSERT INTO `t_attendees` VALUES (30, 3, 61, 1, '2022-07-21 17:04:50', '2022-07-21 17:04:50', 1);
INSERT INTO `t_attendees` VALUES (31, 4, 61, 1, '2022-07-21 17:04:50', '2022-07-21 17:04:50', 1);
INSERT INTO `t_attendees` VALUES (32, 9, 86, 1, '2022-07-27 16:16:02', '2022-07-27 16:16:02', 1);
INSERT INTO `t_attendees` VALUES (33, 1, 87, 1, '2022-07-27 16:21:06', '2022-07-27 16:21:06', 1);
INSERT INTO `t_attendees` VALUES (34, 1, 88, 1, '2022-07-27 16:32:26', '2022-07-27 16:32:26', 1);
INSERT INTO `t_attendees` VALUES (35, 1, 89, 1, '2022-07-27 16:37:30', '2022-07-27 16:37:30', 1);
INSERT INTO `t_attendees` VALUES (36, 28, 10087, 1, '2022-07-28 10:04:13', '2022-07-28 10:04:13', 1);
INSERT INTO `t_attendees` VALUES (37, 5, 10087, 1, '2022-07-28 10:04:13', '2022-07-28 10:04:13', 1);
INSERT INTO `t_attendees` VALUES (38, 31, 10088, 1, '2022-07-28 10:16:46', '2022-07-28 10:16:46', 1);
INSERT INTO `t_attendees` VALUES (39, 24, 10088, 1, '2022-07-28 10:16:46', '2022-07-28 10:16:46', 1);
INSERT INTO `t_attendees` VALUES (40, 29, 10088, 1, '2022-07-28 10:16:46', '2022-07-28 10:16:46', 1);
INSERT INTO `t_attendees` VALUES (41, 21, 10089, 1, '2022-07-29 09:28:25', '2022-07-29 09:28:25', 1);
INSERT INTO `t_attendees` VALUES (42, 22, 10089, 1, '2022-07-29 09:28:25', '2022-07-29 09:28:25', 1);
INSERT INTO `t_attendees` VALUES (43, 1, 10090, 1, '2022-08-05 11:11:03', '2022-08-05 11:11:03', 1);
INSERT INTO `t_attendees` VALUES (44, 5, 10091, 1, '2022-08-05 11:21:29', '2022-08-05 11:21:29', 1);
INSERT INTO `t_attendees` VALUES (45, 1, 10091, 1, '2022-08-05 11:21:29', '2022-08-05 11:21:29', 1);
INSERT INTO `t_attendees` VALUES (46, 16, 10092, 1, '2022-08-05 15:26:52', '2022-08-05 15:26:52', 1);
INSERT INTO `t_attendees` VALUES (47, 42, 10092, 1, '2022-08-05 15:26:52', '2022-08-05 15:26:52', 1);
INSERT INTO `t_attendees` VALUES (48, 45, 10092, 1, '2022-08-05 15:26:52', '2022-08-05 15:26:52', 1);
INSERT INTO `t_attendees` VALUES (49, 48, 10093, 1, '2022-08-05 15:58:59', '2022-08-05 15:58:59', 1);
INSERT INTO `t_attendees` VALUES (50, 5, 10093, 1, '2022-08-05 15:58:59', '2022-08-05 15:58:59', 1);
INSERT INTO `t_attendees` VALUES (51, 30, 10093, 1, '2022-08-05 15:58:59', '2022-08-05 15:58:59', 1);
INSERT INTO `t_attendees` VALUES (52, 9, 10093, 1, '2022-08-05 15:58:59', '2022-08-05 15:58:59', 1);
INSERT INTO `t_attendees` VALUES (53, 3, 10093, 1, '2022-08-05 15:58:59', '2022-08-05 15:58:59', 1);
INSERT INTO `t_attendees` VALUES (54, 49, 10093, 1, '2022-08-05 15:58:59', '2022-08-05 15:58:59', 1);
INSERT INTO `t_attendees` VALUES (55, 11, 10096, 1, '2022-08-05 16:13:50', '2022-08-05 16:13:50', 1);
INSERT INTO `t_attendees` VALUES (56, 45, 10096, 1, '2022-08-05 16:13:50', '2022-08-05 16:13:50', 1);
INSERT INTO `t_attendees` VALUES (57, 27, 10097, 1, '2022-08-05 16:18:17', '2022-08-05 16:18:17', 1);
INSERT INTO `t_attendees` VALUES (58, 48, 10098, 1, '2022-08-05 16:22:16', '2022-08-05 16:22:16', 1);
INSERT INTO `t_attendees` VALUES (59, 31, 10099, 1, '2022-08-05 16:34:22', '2022-08-05 16:34:22', 1);
INSERT INTO `t_attendees` VALUES (60, 1, 10099, 1, '2022-08-05 16:34:22', '2022-08-05 16:34:22', 1);
INSERT INTO `t_attendees` VALUES (61, 23, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (62, 1, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (63, 31, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (64, 27, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (65, 47, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (66, 21, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (67, 24, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (68, 43, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (69, 20, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (70, 25, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (71, 44, 10100, 1, '2022-08-05 17:02:22', '2022-08-05 17:02:22', 1);
INSERT INTO `t_attendees` VALUES (72, 50, 10101, 1, '2022-08-08 10:02:16', '2022-08-08 10:02:16', 1);
INSERT INTO `t_attendees` VALUES (73, 50, 10102, 2, '2022-08-08 14:08:12', '2022-08-08 14:08:12', 1);
INSERT INTO `t_attendees` VALUES (74, 48, 10102, 2, '2022-08-08 14:08:12', '2022-08-08 14:08:12', 1);
INSERT INTO `t_attendees` VALUES (75, 28, 10104, 1, '2022-08-08 15:34:23', '2022-08-08 15:34:23', 1);
INSERT INTO `t_attendees` VALUES (76, 23, 10105, 1, '2022-08-08 15:36:24', '2022-08-08 15:36:24', 1);
INSERT INTO `t_attendees` VALUES (77, 48, 10107, 1, '2022-08-08 16:38:40', '2022-08-08 16:38:40', 1);
INSERT INTO `t_attendees` VALUES (78, 56, 10109, 1, '2022-08-09 10:35:42', '2022-08-09 10:35:42', 1);
INSERT INTO `t_attendees` VALUES (79, 1, 10110, 1, '2022-08-09 10:36:19', '2022-08-09 10:36:19', 1);
INSERT INTO `t_attendees` VALUES (80, 55, 10110, 1, '2022-08-09 10:36:19', '2022-08-09 10:36:19', 1);
INSERT INTO `t_attendees` VALUES (81, 49, 10110, 1, '2022-08-09 10:36:19', '2022-08-09 10:36:19', 1);
INSERT INTO `t_attendees` VALUES (82, 1, 10111, 1, '2022-08-09 10:42:10', '2022-08-09 10:42:10', 1);
INSERT INTO `t_attendees` VALUES (83, 55, 10111, 1, '2022-08-09 10:42:10', '2022-08-09 10:42:10', 1);
INSERT INTO `t_attendees` VALUES (84, 49, 10111, 1, '2022-08-09 10:42:10', '2022-08-09 10:42:10', 1);
INSERT INTO `t_attendees` VALUES (85, 48, 10112, 1, '2022-08-09 10:45:11', '2022-08-09 10:45:11', 1);
INSERT INTO `t_attendees` VALUES (86, 53, 10113, 1, '2022-08-09 11:04:15', '2022-08-09 11:04:15', 1);
INSERT INTO `t_attendees` VALUES (87, 1, 10114, 1, '2022-08-09 15:10:11', '2022-08-09 15:10:11', 1);
INSERT INTO `t_attendees` VALUES (88, 1, 10115, 1, '2022-08-09 15:10:56', '2022-08-09 15:10:56', 1);
INSERT INTO `t_attendees` VALUES (89, 1, 10116, 1, '2022-08-09 15:11:15', '2022-08-09 15:11:15', 1);
INSERT INTO `t_attendees` VALUES (90, 56, 10117, 1, '2022-08-09 16:29:37', '2022-08-09 16:29:37', 1);
INSERT INTO `t_attendees` VALUES (91, 50, 10118, 1, '2022-08-09 16:32:53', '2022-08-09 16:32:53', 1);
INSERT INTO `t_attendees` VALUES (92, 53, 10119, 1, '2022-08-10 14:09:53', '2022-08-10 14:09:53', 1);
INSERT INTO `t_attendees` VALUES (93, 48, 10121, 1, '2022-08-10 14:34:50', '2022-08-10 14:34:50', 1);
INSERT INTO `t_attendees` VALUES (94, 53, 10122, 1, '2022-08-10 14:36:44', '2022-08-10 14:36:44', 1);
INSERT INTO `t_attendees` VALUES (95, 48, 10123, 1, '2022-08-10 14:37:15', '2022-08-10 14:37:15', 1);
INSERT INTO `t_attendees` VALUES (96, 1, 10123, 1, '2022-08-10 14:37:15', '2022-08-10 14:37:15', 1);
INSERT INTO `t_attendees` VALUES (97, 55, 10123, 1, '2022-08-10 14:37:15', '2022-08-10 14:37:15', 1);
INSERT INTO `t_attendees` VALUES (98, 49, 10123, 1, '2022-08-10 14:37:15', '2022-08-10 14:37:15', 1);
INSERT INTO `t_attendees` VALUES (99, 1, 10124, 1, '2022-08-10 14:38:24', '2022-08-10 14:38:24', 1);
INSERT INTO `t_attendees` VALUES (100, 55, 10124, 1, '2022-08-10 14:38:24', '2022-08-10 14:38:24', 1);
INSERT INTO `t_attendees` VALUES (101, 49, 10124, 1, '2022-08-10 14:38:24', '2022-08-10 14:38:24', 1);
INSERT INTO `t_attendees` VALUES (102, 48, 10125, 1, '2022-08-10 14:40:05', '2022-08-10 14:40:05', 1);
INSERT INTO `t_attendees` VALUES (103, 48, 10126, 1, '2022-08-10 14:40:37', '2022-08-10 14:40:37', 1);
INSERT INTO `t_attendees` VALUES (104, 53, 10127, 1, '2022-08-10 14:45:23', '2022-08-10 14:45:23', 1);
INSERT INTO `t_attendees` VALUES (105, 53, 10128, 1, '2022-08-10 14:45:26', '2022-08-10 14:45:26', 1);
INSERT INTO `t_attendees` VALUES (106, 53, 10129, 1, '2022-08-10 14:48:01', '2022-08-10 14:48:01', 1);
INSERT INTO `t_attendees` VALUES (107, 53, 10130, 1, '2022-08-10 14:48:01', '2022-08-10 14:48:01', 1);
INSERT INTO `t_attendees` VALUES (108, 9, 10131, 1, '2022-08-10 14:52:19', '2022-08-10 14:52:19', 1);
INSERT INTO `t_attendees` VALUES (109, 46, 10131, 1, '2022-08-10 14:52:19', '2022-08-10 14:52:19', 1);
INSERT INTO `t_attendees` VALUES (110, 48, 10134, 1, '2022-08-10 14:58:31', '2022-08-10 14:58:31', 1);
INSERT INTO `t_attendees` VALUES (111, 52, 10134, 1, '2022-08-10 14:58:31', '2022-08-10 14:58:31', 1);
INSERT INTO `t_attendees` VALUES (112, 48, 10140, 1, '2022-08-12 08:36:44', '2022-08-12 08:36:44', 1);
INSERT INTO `t_attendees` VALUES (113, 52, 10140, 1, '2022-08-12 08:36:44', '2022-08-12 08:36:44', 1);
INSERT INTO `t_attendees` VALUES (114, 53, 10141, 1, '2022-08-12 08:50:01', '2022-08-12 08:50:01', 1);
INSERT INTO `t_attendees` VALUES (115, 48, 10142, 1, '2022-08-12 08:53:50', '2022-08-12 08:53:50', 1);
INSERT INTO `t_attendees` VALUES (116, 52, 10142, 1, '2022-08-12 08:53:50', '2022-08-12 08:53:50', 1);
INSERT INTO `t_attendees` VALUES (117, 48, 10143, 1, '2022-08-12 08:54:11', '2022-08-12 08:54:11', 1);
INSERT INTO `t_attendees` VALUES (118, 52, 10143, 1, '2022-08-12 08:54:11', '2022-08-12 08:54:11', 1);
INSERT INTO `t_attendees` VALUES (119, 60, 10143, 1, '2022-08-12 08:54:11', '2022-08-12 08:54:11', 1);
INSERT INTO `t_attendees` VALUES (120, 48, 10144, 1, '2022-08-12 08:54:49', '2022-08-12 08:54:49', 1);
INSERT INTO `t_attendees` VALUES (121, 52, 10144, 1, '2022-08-12 08:54:49', '2022-08-12 08:54:49', 1);
INSERT INTO `t_attendees` VALUES (122, 60, 10144, 1, '2022-08-12 08:54:49', '2022-08-12 08:54:49', 1);
INSERT INTO `t_attendees` VALUES (123, 52, 10145, 1, '2022-08-12 08:55:32', '2022-08-12 08:55:32', 1);
INSERT INTO `t_attendees` VALUES (124, 60, 10145, 1, '2022-08-12 08:55:32', '2022-08-12 08:55:32', 1);
INSERT INTO `t_attendees` VALUES (125, 52, 10147, 1, '2022-08-12 14:10:38', '2022-08-12 14:10:38', 1);
INSERT INTO `t_attendees` VALUES (126, 49, 10148, 1, '2022-08-12 14:13:49', '2022-08-12 14:13:49', 1);
INSERT INTO `t_attendees` VALUES (127, 60, 10152, 1, '2022-08-18 14:46:29', '2022-08-18 14:46:29', 0);

-- ----------------------------
-- Table structure for t_board_room
-- ----------------------------
DROP TABLE IF EXISTS `t_board_room`;
CREATE TABLE `t_board_room`  (
  `room_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会议室ID',
  `b_type` int(1) NOT NULL DEFAULT 0 COMMENT '012',
  `b_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会议室名称',
  `b_state` int(1) NULL DEFAULT 1 COMMENT '设备状态0:不正常 1：正常',
  `b_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房间信息',
  `capacities` int(20) NULL DEFAULT NULL COMMENT '会议室容纳人数',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `b_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '地点未填写，请补充' COMMENT '会议室地点',
  PRIMARY KEY (`room_id`) USING BTREE,
  INDEX `room_id`(`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_board_room
-- ----------------------------
INSERT INTO `t_board_room` VALUES (1, 2, '666会议室', 1, '10', 5, '2022-04-06 17:21:26', '2022-07-15 16:39:54', '2604');
INSERT INTO `t_board_room` VALUES (2, 2, '2', 1, '2', 30, NULL, '2022-08-08 15:29:55', '2604');
INSERT INTO `t_board_room` VALUES (3, 1, '301', 1, '投影仪、话筒', 23, '2022-07-14 16:48:39', '2022-07-14 16:48:39', '2604');
INSERT INTO `t_board_room` VALUES (5, 0, 'room111', 1, 'room111room111room111', 10, '2022-07-15 14:36:24', '2022-07-15 14:36:24', '2604');
INSERT INTO `t_board_room` VALUES (6, 1, '504', 1, '话筒', 20, '2022-07-15 14:43:27', '2022-07-15 14:43:27', '2604');
INSERT INTO `t_board_room` VALUES (7, 1, '522', 1, '电视', 22, '2022-07-15 14:44:23', '2022-07-15 14:44:23', '2604');
INSERT INTO `t_board_room` VALUES (8, 1, '622', 1, '投影仪', 500, '2022-07-15 15:01:59', '2022-07-15 15:01:59', '2604');
INSERT INTO `t_board_room` VALUES (9, 2, '9号会议室', 1, '10', 10, '2022-07-15 16:39:28', '2022-07-15 16:39:28', '2604');
INSERT INTO `t_board_room` VALUES (16, 1, 'test_del_msg', 1, '26楼', 10, '2022-07-20 14:24:19', '2022-07-20 14:24:19', '2604');
INSERT INTO `t_board_room` VALUES (17, 0, 'lll', 1, '666', 50, '2022-07-22 23:05:19', '2022-07-22 23:05:19', '2604');
INSERT INTO `t_board_room` VALUES (18, 0, '724', 1, '724', 30, '2022-07-23 08:58:06', '2022-07-23 08:58:06', '2604');
INSERT INTO `t_board_room` VALUES (21, 0, 'testlocation', 1, 'string', 10, '2022-07-27 16:51:06', '2022-07-27 16:55:54', 'testUpdateLocation');
INSERT INTO `t_board_room` VALUES (22, 0, '111', 1, '二楼102', 50, '2022-07-27 17:09:09', '2022-08-12 08:39:09', '3楼');
INSERT INTO `t_board_room` VALUES (23, 0, 'test1', 1, 'test2', 10, '2022-07-28 08:56:43', '2022-07-28 08:56:43', 'test');
INSERT INTO `t_board_room` VALUES (24, 0, 'test2', 1, 'test3', 10, '2022-07-28 08:56:57', '2022-07-28 08:56:57', 'test1');
INSERT INTO `t_board_room` VALUES (25, 0, 'test2111', 1, 'test3', 10, '2022-07-28 08:59:42', '2022-07-28 08:59:42', 'test1111');
INSERT INTO `t_board_room` VALUES (26, 0, '专用会议室', 1, '专用会议室', 12, '2022-07-28 09:04:58', '2022-08-08 16:48:20', '1楼');
INSERT INTO `t_board_room` VALUES (27, 0, '木鱼', 1, '木鱼', 11, '2022-07-28 09:23:18', '2022-07-28 09:23:18', '二楼');

-- ----------------------------
-- Table structure for t_conference_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_conference_rel`;
CREATE TABLE `t_conference_rel`  (
  `con_id` bigint(50) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '会议ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `u_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预订人邮箱',
  `room_id` bigint(20) NOT NULL COMMENT '会议室ID',
  `b_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预订房间 ',
  `c_begin_time` time(0) NOT NULL COMMENT '开始时间HH:mm:ss',
  `c_end_time` time(0) NOT NULL COMMENT '结束时间HH:mm:ss',
  `c_date` date NULL DEFAULT NULL COMMENT '会议日期yyyy-MM-dd',
  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议主题',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '会议审批状态： 1-未审批 2-通过审批 3-拒绝 4-已取消',
  `u_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户姓名',
  PRIMARY KEY (`con_id`) USING BTREE,
  INDEX `userID`(`user_id`) USING BTREE,
  INDEX `roomID`(`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10153 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_conference_rel
-- ----------------------------
INSERT INTO `t_conference_rel` VALUES (1, 23, 'yu-zhou', 1, '101', '09:00:00', '10:00:00', '2022-04-11', 'xxx', '2022-07-14 14:25:08', '2022-04-10 18:55:39', 2, '崔崔1');
INSERT INTO `t_conference_rel` VALUES (3, 23, 'yu-zhou', 1, '101', '09:00:00', '10:00:00', '2022-04-19', 'xxx', '2022-07-14 15:05:35', '2022-04-10 18:55:52', 2, '崔崔1');
INSERT INTO `t_conference_rel` VALUES (4, 23, 'yu-zhou', 1, '101', '09:00:00', '10:00:00', '2022-04-26', 'xxx', '2022-04-10 18:55:52', '2022-04-10 18:55:52', 2, '崔崔1');
INSERT INTO `t_conference_rel` VALUES (5, 1, 'yu-zhou', 1, '101', '09:00:00', '10:00:00', '2022-05-03', 'xxx', '2022-04-10 18:55:52', '2022-04-10 18:55:52', 2, '崔崔1');
INSERT INTO `t_conference_rel` VALUES (6, 1, 'yu-zhou', 1, '101', '09:00:00', '10:00:00', '2022-09-23', 'xxx', '2022-08-08 15:20:55', '2022-04-10 18:55:52', 4, '崔崔2');
INSERT INTO `t_conference_rel` VALUES (7, 1, 'yu-zhou-neu@neusoft.com', 1, '101', '10:00:00', '11:00:00', '2022-05-03', '学习如何做预约开会系统', '2022-04-11 10:29:32', '2022-04-11 10:29:32', 2, '崔崔2');
INSERT INTO `t_conference_rel` VALUES (8, 1, 'yu-zhou-neu@neusoft.com', 1, '101', '11:00:00', '12:00:00', '2022-09-18', '学习如何做预约开会系统', '2022-07-14 15:01:06', '2022-04-11 10:32:22', 3, '崔崔2');
INSERT INTO `t_conference_rel` VALUES (9, 1, 'yu-zhou-neu@neusoft.com', 1, '101', '12:00:00', '13:00:00', '2022-10-21', '学习如何做预约开会系统', '2022-07-15 08:55:20', '2022-04-11 10:34:22', 4, '崔崔2');
INSERT INTO `t_conference_rel` VALUES (10, 1, 'yu-zhou-neu@neusoft.com', 1, '101', '13:00:00', '14:00:00', '2022-05-03', '学习如何做预约开会系统', '2022-07-15 08:55:38', '2022-04-11 10:38:17', 2, '崔崔2');
INSERT INTO `t_conference_rel` VALUES (11, 1, 'yu-zhou-neu@neusoft.com', 1, '101', '14:00:00', '15:00:00', '2022-05-03', '学习如何做预约开会系统', '2022-04-11 10:52:50', '2022-04-11 10:52:50', 2, '崔崔2');
INSERT INTO `t_conference_rel` VALUES (12, 1, 'yu-zhou-neu@neusoft.com', 1, '101', '14:00:00', '15:00:00', '2022-05-04', '学习如何做预约开会系统', '2022-04-11 10:55:07', '2022-04-11 10:55:07', 2, '崔崔2');
INSERT INTO `t_conference_rel` VALUES (13, 7, 'xinfeng.cui@foxmail.com', 1, '101', '09:00:00', '11:00:00', '2022-07-06', '发年终奖', '2022-07-15 16:00:15', '2022-07-05 15:53:47', 2, 'L7');
INSERT INTO `t_conference_rel` VALUES (14, 1, 'xinfeng.cui1@foxmail.com', 1, '101', '10:00:00', '11:00:00', '2022-07-07', 'dfdf', '2022-07-06 09:23:12', '2022-07-06 09:23:12', 2, '崔崔1');
INSERT INTO `t_conference_rel` VALUES (15, 3, '17603209617@163.com', 1, '101', '12:00:00', '20:00:00', '2022-07-21', 'gggg', '2022-07-06 09:29:20', '2022-07-06 09:29:20', 2, 'L3');
INSERT INTO `t_conference_rel` VALUES (16, 5, 'jamespai0817@foxmail.com', 1, '101', '09:30:00', '10:00:00', '2022-07-13', '34', '2022-07-06 09:52:43', '2022-07-06 09:52:43', 2, 'L5');
INSERT INTO `t_conference_rel` VALUES (17, 1, 'xinfeng.cui1@foxmail.com', 1, '101', '20:00:00', '20:30:00', '2022-07-07', '嗡嗡嗡', '2022-07-15 09:00:00', '2022-07-06 14:37:27', 2, '崔崔2');
INSERT INTO `t_conference_rel` VALUES (18, 1, 'xinfeng.cui1@foxmail.com', 1, '101', '20:00:00', '20:30:00', '2022-07-14', '嗡嗡嗡', '2022-07-06 14:37:27', '2022-07-06 14:37:27', 2, '崔崔2');
INSERT INTO `t_conference_rel` VALUES (19, 1, 'xinfeng.cui1@foxmail.com', 1, '101', '20:00:00', '20:30:00', '2022-07-21', '嗡嗡嗡', '2022-07-06 14:37:27', '2022-07-06 14:37:27', 2, '崔崔1');
INSERT INTO `t_conference_rel` VALUES (20, 1, 'xinfeng.cui1@foxmail.com', 1, '101', '20:00:00', '20:45:00', '2022-07-28', '嗡嗡嗡', '2022-07-25 14:47:06', '2022-07-06 14:37:27', 2, '崔崔1');
INSERT INTO `t_conference_rel` VALUES (21, 3, '17603209617@163.com', 1, '101', '20:30:00', '21:00:00', '2022-07-07', 'ddd', '2022-07-06 15:29:02', '2022-07-06 15:29:02', 2, 'L3');
INSERT INTO `t_conference_rel` VALUES (22, 10, '1473444277@qq.com', 1, '101', '09:30:00', '10:30:00', '2022-07-15', 'www', '2022-07-13 16:07:20', '2022-07-13 16:07:20', 2, 'L10');
INSERT INTO `t_conference_rel` VALUES (23, 9, 'zehaotan@foxmail.com', 1, '101', '12:30:00', '15:00:00', '2022-07-14', 'test', '2022-07-13 16:31:33', '2022-07-13 16:31:33', 2, 'L9');
INSERT INTO `t_conference_rel` VALUES (24, 14, '1473444277@qq.com', 1, '101', '09:00:00', '09:30:00', '2022-07-18', '肯德基疯狂星期四', '2022-07-14 15:56:05', '2022-07-14 15:56:05', 2, 'L14');
INSERT INTO `t_conference_rel` VALUES (30, 9, 'zehaotan@foxmail.com', 2, '101', '12:30:00', '15:00:00', '2022-07-14', 'test', '2022-07-13 16:31:33', '2022-07-13 16:31:33', 2, 'L9');
INSERT INTO `t_conference_rel` VALUES (31, 9, 'zehaotan@foxmail.com', 2, '101', '12:30:00', '15:00:00', '2022-07-14', 'test', '2022-07-13 16:31:33', '2022-07-13 16:31:33', 2, 'L9');
INSERT INTO `t_conference_rel` VALUES (32, 13, 'z', 6, '6', '08:44:32', '11:44:34', '2022-07-17', 'w', '2022-07-26 10:28:45', '2022-07-26 10:28:49', 2, 'L13');
INSERT INTO `t_conference_rel` VALUES (33, 13, '232', 3, '3', '08:45:29', '11:45:35', '2022-07-18', '2eeqeq', '2022-07-26 10:28:51', '2022-07-26 10:28:54', 2, 'L13');
INSERT INTO `t_conference_rel` VALUES (34, 12, 'we', 6, '6', '08:46:22', '09:46:25', '2022-07-18', '13wwew', '2022-07-26 10:28:58', '2022-07-26 10:29:00', 2, 'L12');
INSERT INTO `t_conference_rel` VALUES (35, 13, 'AK', 3, '3', '08:47:07', '08:47:10', '2022-07-16', 'wewew', '2022-07-26 10:29:03', '2022-07-26 10:29:05', 2, 'L13');
INSERT INTO `t_conference_rel` VALUES (36, 13, 'wewe', 3, '3', '08:47:45', '09:47:51', '2022-07-14', '13', '2022-07-26 10:29:08', '2022-07-26 10:29:11', 2, 'L13');
INSERT INTO `t_conference_rel` VALUES (37, 12, 'qwqwq', 4, '4', '08:48:26', '08:48:28', '2022-07-15', '2131212', '2022-07-26 10:29:13', '2022-07-26 10:29:17', 2, 'L12');
INSERT INTO `t_conference_rel` VALUES (38, 25, '1473444277@qq.com', 1, '666会议室', '06:30:00', '09:45:00', '2022-08-19', '早八开会', '2022-07-20 10:33:31', '2022-07-20 10:33:31', 2, '');
INSERT INTO `t_conference_rel` VALUES (39, 14, '1473444277@qq.com', 1, '666会议室', '09:30:00', '12:30:00', '2022-12-01', '开会', '2022-07-20 10:50:00', '2022-07-20 10:50:00', 0, '');
INSERT INTO `t_conference_rel` VALUES (46, 14, '1473444277@qq.com', 1, '666会议室', '08:30:00', '06:30:00', '2022-08-20', '开会', '2022-07-20 13:54:30', '2022-07-20 13:54:30', 0, '');
INSERT INTO `t_conference_rel` VALUES (47, 14, '1473444277@qq.com', 1, '666会议室', '14:30:00', '16:30:00', '2022-12-01', '开会', '2022-07-20 14:06:44', '2022-07-20 14:06:44', 0, '');
INSERT INTO `t_conference_rel` VALUES (48, 14, '1473444277@qq.com', 2, '2', '09:00:00', '10:00:00', '2022-08-19', 'xxx', '2022-07-20 14:11:33', '2022-07-20 14:11:33', 2, '');
INSERT INTO `t_conference_rel` VALUES (82, 3, '3', 1, '666会议室', '10:00:00', '14:15:00', '2022-08-19', '123', '2022-08-12 10:59:17', '2022-07-25 09:16:45', 2, '崔崔1');
INSERT INTO `t_conference_rel` VALUES (83, 3, '3', 3, '301', '10:00:00', '12:00:00', '2022-08-19', '1234', '2022-08-12 08:51:00', '2022-07-25 09:16:50', 3, '崔崔1');
INSERT INTO `t_conference_rel` VALUES (84, 16, '2222@qq.com', 2, '2', '09:00:00', '10:00:00', '2022-07-30', '拒绝', '2022-07-26 10:46:33', '2022-07-26 10:46:33', 0, '');
INSERT INTO `t_conference_rel` VALUES (85, 16, '2222@qq.com', 2, '2', '09:00:00', '11:30:00', '2022-07-28', '测试', '2022-07-27 10:18:35', '2022-07-27 10:18:35', 0, '');
INSERT INTO `t_conference_rel` VALUES (86, 1, '123@123.com', 1, '666会议室', '10:00:00', '11:00:00', '2022-07-23', '111', '2022-08-10 09:41:50', '2022-07-27 16:16:02', 2, 'tzh');
INSERT INTO `t_conference_rel` VALUES (87, 1, '123@123.com', 1, '666会议室', '09:30:00', '10:00:00', '2022-12-12', '状态', '2022-08-08 10:56:47', '2022-07-27 16:21:06', 4, 'tzh');
INSERT INTO `t_conference_rel` VALUES (88, 1, '123@123.com', 1, '666会议室', '08:30:00', '09:00:00', '2022-12-13', '状态', '2022-08-08 10:58:25', '2022-07-27 16:32:26', 4, 'tzh');
INSERT INTO `t_conference_rel` VALUES (89, 1, '123@123.com', 1, '666会议室', '08:30:00', '09:00:00', '2022-12-13', '状态', '2022-08-08 15:15:40', '2022-07-27 16:37:30', 4, 'tzh');
INSERT INTO `t_conference_rel` VALUES (10086, 1, '123@123.com', 1, '666会议室', '10:30:00', '11:00:00', '2022-12-12', '状态', '2022-07-27 16:21:06', '2022-07-27 16:21:06', 2, 'tzh');
INSERT INTO `t_conference_rel` VALUES (10087, 30, '23etre', 1, '666会议室', '09:00:00', '09:30:00', '2022-07-28', 'xxx', '2022-08-10 14:54:55', '2022-07-28 10:04:13', 2, 'wqrwe');
INSERT INTO `t_conference_rel` VALUES (10088, 30, '23etre', 3, '301', '09:00:00', '10:30:00', '2022-07-28', 'meis', '2022-08-12 08:45:47', '2022-07-28 10:16:46', 2, 'wqrwe');
INSERT INTO `t_conference_rel` VALUES (10089, 30, '23etre', 1, '666会议室', '09:00:00', '10:00:00', '2022-07-29', '2342日如发热', '2022-08-12 09:16:58', '2022-07-29 09:28:25', 2, 'wqrwe');
INSERT INTO `t_conference_rel` VALUES (10090, 44, 'zehaotan@foxmail.com', 2, '2', '10:00:00', '12:15:00', '2022-08-09', '8.5', '2022-08-12 09:54:33', '2022-08-05 11:11:03', 3, '谭泽浩');
INSERT INTO `t_conference_rel` VALUES (10091, 23, '1473@qq.com', 9, '9号会议室', '10:00:00', '11:00:00', '2022-08-05', 'bj', '2022-08-05 16:28:03', '2022-08-05 11:21:29', 3, '罗');
INSERT INTO `t_conference_rel` VALUES (10092, 49, 'xinfeng.cui@foxmail.com', 1, '666会议室', '09:00:00', '12:00:00', '2022-08-05', '测试测试测试测试测试测试测试测试', '2022-08-05 16:34:04', '2022-08-05 15:26:52', 3, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10093, 50, '123@123.com', 2, '2', '09:30:00', '10:30:00', '2022-08-05', 'dhdidf', '2022-08-12 08:46:40', '2022-08-05 15:58:59', 2, '徐哲');
INSERT INTO `t_conference_rel` VALUES (10094, 45, 'asdf@qq.com', 5, 'room111', '10:30:00', '12:00:00', '2022-08-05', '你好', '2022-08-05 16:10:37', '2022-08-05 16:06:35', 2, '昌');
INSERT INTO `t_conference_rel` VALUES (10095, 23, '1473@qq.com', 1, '666会议室', '11:00:00', '12:00:00', '2022-08-05', '122121', '2022-08-12 09:17:50', '2022-08-05 16:07:26', 2, '罗');
INSERT INTO `t_conference_rel` VALUES (10096, 45, 'asdf@qq.com', 1, '666会议室', '09:00:00', '14:00:00', '2022-08-06', '你好', '2022-08-05 16:22:43', '2022-08-05 16:13:50', 3, '昌');
INSERT INTO `t_conference_rel` VALUES (10097, 45, 'asdf@qq.com', 5, 'room111', '09:00:00', '10:00:00', '2022-08-05', '你好', '2022-08-12 09:18:05', '2022-08-05 16:18:17', 3, '昌');
INSERT INTO `t_conference_rel` VALUES (10098, 45, 'asdf@qq.com', 2, '2', '09:00:00', '11:00:00', '2022-08-06', '你好', '2022-08-05 16:22:41', '2022-08-05 16:22:16', 3, '昌');
INSERT INTO `t_conference_rel` VALUES (10099, 50, '123@123.com', 6, '504', '19:30:00', '21:00:00', '2022-08-05', '8789', '2022-08-12 09:18:27', '2022-08-05 16:34:22', 3, '徐哲');
INSERT INTO `t_conference_rel` VALUES (10100, 50, '123@123.com', 1, '666会议室', '18:30:00', '19:00:00', '2022-08-05', '47323', '2022-08-12 09:54:33', '2022-08-05 17:02:22', 3, '徐哲');
INSERT INTO `t_conference_rel` VALUES (10101, 50, '123@123.com', 2, '2', '10:00:00', '12:30:00', '2022-12-16', '3424', '2022-08-08 10:02:34', '2022-08-08 10:02:16', 2, '徐哲');
INSERT INTO `t_conference_rel` VALUES (10102, 50, '123@123.com', 2, '2', '09:30:00', '10:30:00', '2022-08-26', '431', '2022-08-08 15:13:14', '2022-08-08 14:08:12', 2, '徐哲');
INSERT INTO `t_conference_rel` VALUES (10103, 23, '1473@qq.com', 1, '666会议室', '17:30:00', '19:15:00', '2022-08-08', '121211', '2022-08-10 17:20:25', '2022-08-08 15:34:15', 2, '罗');
INSERT INTO `t_conference_rel` VALUES (10104, 5, '5', 9, '9号会议室', '15:30:00', '19:00:00', '2022-08-08', '2142345', '2022-08-12 09:54:33', '2022-08-08 15:34:23', 3, 'L5');
INSERT INTO `t_conference_rel` VALUES (10105, 23, '1473@qq.com', 1, '666会议室', '15:30:00', '16:00:00', '2022-08-08', '开会咯', '2022-08-12 09:54:33', '2022-08-08 15:36:24', 3, '罗');
INSERT INTO `t_conference_rel` VALUES (10106, 49, 'xinfeng.cui@foxmail.com', 1, '666会议室', '20:00:00', '21:00:00', '2022-08-08', '表彰大会(错误数据！）', '2022-08-08 15:45:35', '2022-08-08 15:45:13', 3, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10108, 49, 'xinfeng.cui@foxmail.com', 1, '666会议室', '12:00:00', '20:00:00', '2022-08-09', '111', '2022-08-09 10:10:58', '2022-08-09 10:09:36', 4, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10109, 30, '243vfr7@qq.com', 6, '504', '18:30:00', '19:30:00', '2022-08-09', '67453664', '2022-08-12 09:54:33', '2022-08-09 10:35:42', 3, 'jefwver23534363345');
INSERT INTO `t_conference_rel` VALUES (10110, 30, '243vfr7@qq.com', 16, 'test_del_msg', '12:00:00', '12:30:00', '2022-08-09', '5563', '2022-08-12 09:54:33', '2022-08-09 10:36:19', 3, 'jefwver23534363345');
INSERT INTO `t_conference_rel` VALUES (10111, 30, '243vfr7@qq.com', 7, '522', '11:30:00', '12:00:00', '2022-08-09', '1234', '2022-08-12 09:54:34', '2022-08-09 10:42:10', 3, 'jefwver23534363345');
INSERT INTO `t_conference_rel` VALUES (10112, 30, '243vfr7@qq.com', 17, 'lll', '12:00:00', '12:30:00', '2022-08-09', '12355', '2022-08-12 09:54:34', '2022-08-09 10:45:11', 3, 'jefwver23534363345');
INSERT INTO `t_conference_rel` VALUES (10113, 23, '1473@qq.com', 1, '666会议室', '11:00:00', '11:30:00', '2022-08-09', '666', '2022-08-12 09:54:34', '2022-08-09 11:04:15', 3, '罗');
INSERT INTO `t_conference_rel` VALUES (10114, 1, '234245@qq.com', 1, '666会议室', '15:30:00', '16:00:00', '2022-08-09', '开会', '2022-08-12 09:54:34', '2022-08-09 15:10:11', 3, 'wrwe');
INSERT INTO `t_conference_rel` VALUES (10115, 1, '234245@qq.com', 1, '666会议室', '15:30:00', '16:00:00', '2022-08-09', '开会', '2022-08-09 16:38:43', '2022-08-09 15:10:56', 4, 'wrwe');
INSERT INTO `t_conference_rel` VALUES (10116, 1, '234245@qq.com', 1, '666会议室', '15:30:00', '16:30:00', '2022-08-09', '开会', '2022-08-09 16:16:07', '2022-08-09 15:11:15', 4, 'wrwe');
INSERT INTO `t_conference_rel` VALUES (10117, 49, 'xinfeng.cui@foxmail.com', 1, '666会议室', '09:00:00', '10:30:00', '2022-08-10', '123', '2022-08-12 09:54:34', '2022-08-09 16:29:37', 3, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10118, 49, 'xinfeng.cui@foxmail.com', 9, '9号会议室', '09:00:00', '10:00:00', '2022-08-10', 'testtest111', '2022-08-12 09:54:34', '2022-08-09 16:32:53', 3, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10119, 58, '1473444@qq.com', 2, '2', '17:00:00', '18:00:00', '2022-08-10', 'kkkk', '2022-08-12 09:54:34', '2022-08-10 14:09:53', 3, '罗一');
INSERT INTO `t_conference_rel` VALUES (10120, 23, '1473@qq.com', 1, '666会议室', '20:00:00', '20:30:00', '2022-08-10', '开会', '2022-08-10 14:09:59', '2022-08-10 14:09:59', 3, '罗');
INSERT INTO `t_conference_rel` VALUES (10121, 30, '243vfr7@qq.com', 8, '622', '09:30:00', '17:30:00', '2022-08-10', '如图一', '2022-08-12 09:54:34', '2022-08-10 14:34:50', 3, 'jefwver23534363345');
INSERT INTO `t_conference_rel` VALUES (10122, 30, '243vfr7@qq.com', 6, '504', '19:30:00', '20:00:00', '2022-08-10', '2354435', '2022-08-12 09:54:35', '2022-08-10 14:36:44', 3, 'jefwver23534363345');
INSERT INTO `t_conference_rel` VALUES (10123, 41, 'qq@sina.com', 1, '666会议室', '09:00:00', '09:30:00', '2022-08-18', '', '2022-08-10 14:39:24', '2022-08-10 14:37:15', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10124, 41, 'qq@sina.com', 1, '666会议室', '10:30:00', '11:00:00', '2022-08-18', '', '2022-08-10 14:39:25', '2022-08-10 14:38:24', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10125, 41, 'qq@sina.com', 1, '666会议室', '09:00:00', '09:30:00', '2022-08-20', '', '2022-08-10 14:42:30', '2022-08-10 14:40:05', 3, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10126, 41, 'qq@sina.com', 1, '666会议室', '09:00:00', '09:30:00', '2022-08-21', '', '2022-08-10 14:42:31', '2022-08-10 14:40:37', 3, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10127, 50, '123@00.com', 2, '2', '15:00:00', '15:30:00', '2022-08-10', '', '2022-08-10 14:47:17', '2022-08-10 14:45:23', 4, '徐哲');
INSERT INTO `t_conference_rel` VALUES (10128, 23, '1473@qq.com', 2, '2', '17:00:00', '17:30:00', '2022-08-10', '', '2022-08-12 09:54:35', '2022-08-10 14:45:26', 3, '罗');
INSERT INTO `t_conference_rel` VALUES (10129, 50, '123@00.com', 5, 'room111', '19:00:00', '20:30:00', '2022-08-10', '', '2022-08-12 09:54:35', '2022-08-10 14:48:01', 3, '徐哲');
INSERT INTO `t_conference_rel` VALUES (10130, 23, '1473@qq.com', 3, '301', '20:00:00', '20:30:00', '2022-08-10', '', '2022-08-10 14:48:01', '2022-08-10 14:48:01', 3, '罗');
INSERT INTO `t_conference_rel` VALUES (10131, 5, '5', 2, '2', '19:00:00', '19:30:00', '2022-08-10', '23534', '2022-08-12 09:54:35', '2022-08-10 14:52:19', 3, 'L5');
INSERT INTO `t_conference_rel` VALUES (10132, 41, 'qq@sina.com', 1, '666会议室', '09:00:00', '09:30:00', '2022-08-13', 'test confilct', '2022-08-10 15:07:38', '2022-08-10 14:57:48', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10133, 49, 'xinfeng.cui@foxmail.com', 1, '666会议室', '09:00:00', '09:30:00', '2022-08-13', 'test confilct 2', '2022-08-10 15:07:40', '2022-08-10 14:58:16', 2, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10134, 41, 'qq@sina.com', 2, '2', '09:00:00', '09:30:00', '2022-08-12', '', '2022-08-12 08:49:46', '2022-08-10 14:58:31', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10135, 41, 'qq@sina.com', 2, '2', '10:00:00', '11:00:00', '2022-08-12', '22', '2022-08-10 14:58:44', '2022-08-10 14:58:44', 3, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10136, 49, 'xinfeng.cui@foxmail.com', 1, '666会议室', '09:00:00', '09:30:00', '2022-08-25', 'test confilct1', '2022-08-10 20:27:11', '2022-08-10 16:38:34', 3, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10137, 41, 'qq@sina.com', 1, '666会议室', '09:00:00', '09:30:00', '2022-08-25', 'test confilct2', '2022-08-10 20:23:53', '2022-08-10 16:38:54', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10138, 41, 'qq@sina.com', 1, '666会议室', '09:00:00', '09:30:00', '2022-08-26', '111', '2022-08-11 09:15:58', '2022-08-10 16:57:26', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10139, 49, 'xinfeng.cui@foxmail.com', 1, '666会议室', '09:00:00', '09:30:00', '2022-08-26', '222', '2022-08-11 09:15:58', '2022-08-10 16:58:14', 3, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10140, 5, '5', 3, '301', '15:30:00', '17:00:00', '2022-08-12', '23543645', '2022-08-12 08:58:06', '2022-08-12 08:36:44', 2, 'L5');
INSERT INTO `t_conference_rel` VALUES (10141, 44, 'zehaotan@foxmail.com', 5, 'room111', '10:00:00', '11:30:00', '2022-08-12', 'TTTTTest', '2022-08-12 08:50:01', '2022-08-12 08:50:01', 3, '沢沢');
INSERT INTO `t_conference_rel` VALUES (10142, 52, '112233@qq.com', 3, '301', '09:00:00', '09:30:00', '2022-08-26', '', '2022-08-12 11:27:37', '2022-08-12 08:53:50', 3, '昌武洋');
INSERT INTO `t_conference_rel` VALUES (10143, 41, 'qq@sina.com', 3, '301', '09:00:00', '09:30:00', '2022-08-12', '', '2022-08-12 08:54:11', '2022-08-12 08:54:11', 3, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10144, 41, 'qq@sina.com', 3, '301', '09:00:00', '09:30:00', '2022-08-26', '', '2022-08-12 14:07:38', '2022-08-12 08:54:49', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10145, 60, 'tzh@sh.com', 7, '522', '11:00:00', '20:00:00', '2022-08-23', 'hjhj', '2022-08-12 10:59:24', '2022-08-12 08:55:32', 3, '沢22');
INSERT INTO `t_conference_rel` VALUES (10146, 49, 'xinfeng.cui@foxmail.com', 1, '666会议室', '17:30:00', '18:00:00', '2022-08-12', 'test', '2022-08-12 11:30:52', '2022-08-12 11:30:52', 3, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10147, 41, 'qq@sina.com', 17, 'lll', '20:00:00', '20:30:00', '2022-08-12', 'test', '2022-08-12 14:19:33', '2022-08-12 14:10:38', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10148, 49, 'xinfeng.cui@foxmail.com', 1, '666会议室', '19:00:00', '20:00:00', '2022-08-13', 'test', '2022-08-12 14:34:45', '2022-08-12 14:13:49', 2, '崔欣锋');
INSERT INTO `t_conference_rel` VALUES (10149, 41, 'qq@sina.com', 2, '2', '09:00:00', '09:30:00', '2022-08-18', '', '2022-08-12 14:26:10', '2022-08-12 14:23:39', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10150, 41, 'qq@sina.com', 5, 'room111', '09:30:00', '11:00:00', '2022-08-18', '', '2022-08-12 14:26:04', '2022-08-12 14:25:50', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10151, 41, 'qq@sina.com', 5, 'room111', '11:00:00', '12:30:00', '2022-08-25', '', '2022-08-12 14:26:48', '2022-08-12 14:26:44', 2, '黑曼巴');
INSERT INTO `t_conference_rel` VALUES (10152, 44, 'zehaotan@foxmail.com', 5, 'room111', '11:30:00', '19:45:00', '2022-08-22', 'ttest', '2022-08-18 15:08:38', '2022-08-18 14:46:29', 3, '沢沢');

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept`  (
  `dept_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `d_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `d_manager_id` int(255) NULL DEFAULT NULL COMMENT '部门主管编号',
  `d_parent_id` int(255) NOT NULL COMMENT '上级部门编号',
  `d_level` int(255) NULL DEFAULT NULL COMMENT '部门层级 0 为最高部门',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES (1, 'xx公司', 1, 0, 0, NULL, NULL);
INSERT INTO `t_dept` VALUES (2, '测试部门', 45, 1, 1, NULL, NULL);
INSERT INTO `t_dept` VALUES (3, '财务部门', 47, 1, 1, NULL, NULL);
INSERT INTO `t_dept` VALUES (4, '研发部门', 9, 1, 1, NULL, NULL);
INSERT INTO `t_dept` VALUES (5, '人事部门', 46, 1, 1, NULL, NULL);
INSERT INTO `t_dept` VALUES (6, '维护部门', 48, 1, 1, NULL, NULL);
INSERT INTO `t_dept` VALUES (7, '运营部门', 49, 1, 1, NULL, NULL);
INSERT INTO `t_dept` VALUES (8, '市场部门', 50, 1, 1, NULL, NULL);
INSERT INTO `t_dept` VALUES (9, '企划部门', 21, 1, 1, NULL, NULL);
INSERT INTO `t_dept` VALUES (19, '测试部一组', 5, 2, 2, NULL, NULL);
INSERT INTO `t_dept` VALUES (20, '测试部二组', 6, 2, 2, NULL, NULL);
INSERT INTO `t_dept` VALUES (21, '财务部一组', 7, 3, 2, NULL, NULL);
INSERT INTO `t_dept` VALUES (22, '财务部二组', 8, 3, 2, NULL, NULL);
INSERT INTO `t_dept` VALUES (23, '研发部一组', 9, 4, 2, NULL, NULL);
INSERT INTO `t_dept` VALUES (24, '研发部二组', 10, 4, 2, NULL, NULL);
INSERT INTO `t_dept` VALUES (25, '人事部一组', 11, 5, 2, NULL, NULL);
INSERT INTO `t_dept` VALUES (26, '人事部二组', 12, 5, 2, NULL, NULL);
INSERT INTO `t_dept` VALUES (27, '人事部三组', 13, 5, 2, NULL, NULL);
INSERT INTO `t_dept` VALUES (28, '维护部一组', 14, 6, 2, NULL, NULL);

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
  `m_id` int(100) NOT NULL COMMENT '模板id',
  `m_tencent_tmp_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '腾讯云模板',
  `m_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `m_rel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板内容\r\n',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`m_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_message
-- ----------------------------
INSERT INTO `t_message` VALUES (1, '1467996', '预约回复模板（未通过/已通过）', '会议室预约申请{1}，会议室名称：{2},预约时间：{3}，详情请前往个人中心查看，祝您工作愉快。', '2022-07-13 09:13:46', '2022-07-13 09:13:48');
INSERT INTO `t_message` VALUES (2, '1470256', '预约结果通知-多参数', '	\r\n会议室预约申请{1}，会议室名称：{2},预约时间：{3}，{4}-{5}，祝您工作愉快。', '2022-07-13 09:15:38', '2022-07-13 09:15:35');
INSERT INTO `t_message` VALUES (3, '1471039', '验证码-通用', '{1}为您的验证码，请于{2}分钟内填写，如非本人操作，请忽略本短信。', '2022-07-13 09:15:32', '2022-07-13 09:15:29');
INSERT INTO `t_message` VALUES (4, '1471528', '	\r\n申请未通过-通知模板', '尊敬的{1}您好，会议室预约申请{2}，会议室名称：{3},预约时间：{4}，{5}-{6}，请重新申请。', '2022-07-13 09:16:08', '2022-07-13 09:16:05');
INSERT INTO `t_message` VALUES (5, '1471530', '群发-通知会议详情', '会议通知：尊敬的{1}您好，请于{2}{3}-{4}准时到{5}参加{6}会议，非本人请忽略此短信。', '2022-07-13 09:16:29', '2022-07-13 09:16:26');

-- ----------------------------
-- Table structure for t_repair
-- ----------------------------
DROP TABLE IF EXISTS `t_repair`;
CREATE TABLE `t_repair`  (
  `repair_id` bigint(20) NOT NULL COMMENT '维修id',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `u_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预订人邮箱',
  `room_id` bigint(20) NOT NULL COMMENT '会议室ID',
  `b_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预订房间 ',
  `repair_message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报修信息',
  `repair_state` int(1) NULL DEFAULT NULL COMMENT '0：完成   1：未完成',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`repair_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_repair
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `u_mail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `u_pwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `u_phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `u_verify` int(11) NULL DEFAULT NULL COMMENT '0普通用户，1管理员',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `u_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `u_dept_id` int(255) NULL DEFAULT NULL COMMENT '用户部门编号',
  `u_status` int(1) NOT NULL DEFAULT 1 COMMENT '用户状态 0被删除 1 正常',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '234245@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '12314324', 0, '2022-07-12 10:08:54', '2022-08-10 14:59:10', 'wrwe', 20, 1);
INSERT INTO `t_user` VALUES (3, '3', 'e10adc3949ba59abbe56e057f20f883e', '13327011921', 0, NULL, NULL, '崔崔1', 1, 1);
INSERT INTO `t_user` VALUES (5, '5', 'e10adc3949ba59abbe56e057f20f883e', '18627011921', 1, NULL, '2022-08-08 16:33:39', 'L5', 1, 1);
INSERT INTO `t_user` VALUES (9, '9', '9', '9', 0, NULL, '2022-08-08 16:35:01', 'L9', 21, 1);
INSERT INTO `t_user` VALUES (10, '10', '10', '10', 0, NULL, '2022-07-26 11:27:30', 'L10', 21, 0);
INSERT INTO `t_user` VALUES (11, '1243254', '61f90097e3234cf91d2c5206f666a5ca', '21432543', 0, '2022-07-13 16:33:35', '2022-08-08 16:48:54', '的hi封闭今晚v呢色弱', 1, 1);
INSERT INTO `t_user` VALUES (13, 'djek@jk.com', '0288e552bd01d2d2f868b299773daabd', '18742020998', 0, '2022-07-14 14:02:25', '2022-07-14 14:02:25', 'L13', 1, 0);
INSERT INTO `t_user` VALUES (16, '234245@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '12314324', 1, '2022-07-21 17:20:31', '2022-08-08 15:27:06', 'wrwe', 1, 0);
INSERT INTO `t_user` VALUES (18, '234245@qq.com', '1c104b9c0accfca52ef21728eaf01453', '12314324', 1, '2022-07-22 14:42:27', '2022-08-09 08:55:17', 'wrwe', 1, 1);
INSERT INTO `t_user` VALUES (19, '23424521@qq.com', '1c104b9c0accfca52ef21728eaf01453', '18627121565', 0, '2022-07-22 14:43:26', '2022-08-08 16:22:48', 'uiwehrjvn', 1, 1);
INSERT INTO `t_user` VALUES (20, '2342qw21@qq.com', '1c104b9c0accfca52ef21728eaf01453', '18627121545', 0, '2022-07-22 14:43:42', '2022-08-10 14:59:32', 'uiwehrjvn214', 1, 1);
INSERT INTO `t_user` VALUES (21, '123@qq.com', '748c5881461367ab9eed5af82966a480', '13425097845', 0, '2022-07-22 15:40:44', '2022-08-08 15:27:19', 'diwefiuwe2343', 23, 1);
INSERT INTO `t_user` VALUES (22, '124@gmial.com', 'e10adc3949ba59abbe56e057f20f883e', '17603209617', 0, '2022-07-22 16:10:51', '2022-08-05 14:19:44', 'Chandler', 24, 0);
INSERT INTO `t_user` VALUES (23, '1473@qq.com', '25d55ad283aa400af464c76d713c07ad', '15870625618', 1, '2022-07-22 16:11:55', '2022-08-08 16:44:25', '罗', 1, 1);
INSERT INTO `t_user` VALUES (24, '123@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '13425097845', 0, NULL, '2022-08-08 15:27:29', 'diwefiuwe2343', 1, 1);
INSERT INTO `t_user` VALUES (25, '111', '698d51a19d8a121ce581499d7b701668', '111', 1, '2022-07-22 23:06:31', '2022-08-12 08:40:13', '123453845', 1, 1);
INSERT INTO `t_user` VALUES (27, '4556', 'cd8ab09058a1378bafaf95ec630ba9d5', '123432454', 0, '2022-07-23 09:08:01', '2022-08-05 15:08:19', '12332453@jiubugai', 1, 1);
INSERT INTO `t_user` VALUES (28, '123321', '4297f44b13955235245b2497399d7a93', '34738294343', 0, '2022-07-25 16:45:35', '2022-08-09 08:56:11', '1234', 22, 0);
INSERT INTO `t_user` VALUES (30, '243vfr7@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '15084181070', 0, '2022-07-26 15:38:18', '2022-08-09 10:09:31', 'jefwver23534363345', 27, 1);
INSERT INTO `t_user` VALUES (31, '34565', 'e10adc3949ba59abbe56e057f20f883e', '5464875', 0, '2022-07-26 15:39:50', '2022-08-05 15:19:10', '', 1, 1);
INSERT INTO `t_user` VALUES (41, 'qq@sina.com', 'e10adc3949ba59abbe56e057f20f883e', '17603209618', 1, '2022-07-26 17:28:40', '2022-08-08 16:32:58', '黑曼巴', 1, 1);
INSERT INTO `t_user` VALUES (42, '7656465', 'e10adc3949ba59abbe56e057f20f883e', '674567', 0, '2022-07-27 15:21:14', '2022-08-09 08:57:46', '', 21, 0);
INSERT INTO `t_user` VALUES (43, '12345', '81dc9bdb52d04dc20036dbd8313ed055', '12345', 0, '2022-07-28 09:00:11', '2022-08-05 15:11:41', 'nihaom', 1, 1);
INSERT INTO `t_user` VALUES (44, 'zehaotan@foxmail.com', '88318bb81374b63626c585e99e847403', '18717188578', 1, '2022-08-05 11:09:13', '2022-08-12 08:46:06', '沢沢', 23, 1);
INSERT INTO `t_user` VALUES (45, 'asdf@qq.com', 'ece35e1848280a119e887286bb5cc1e0', '18251952019', 0, '2022-08-05 11:21:04', '2022-08-05 11:21:04', '昌', 1, 1);
INSERT INTO `t_user` VALUES (46, '234245@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '12314324', 0, '2022-08-05 14:45:02', '2022-08-08 15:20:57', 'wrwe', 21, 1);
INSERT INTO `t_user` VALUES (47, '7684456', '0288e552bd01d2d2f868b299773daabd', '8795674', 1, '2022-08-05 14:56:07', '2022-08-08 16:33:40', '123@123.com', 1, 1);
INSERT INTO `t_user` VALUES (48, '1233', '5da3b93bdb1c0d9a58cdde0c7a8db338', '15382205602', 0, '2022-08-05 14:58:03', '2022-08-05 14:58:03', '321', 19, 1);
INSERT INTO `t_user` VALUES (49, 'xinfeng.cui@foxmail.com', 'e10adc3949ba59abbe56e057f20f883e', '17603209617', 0, '2022-08-05 15:24:41', '2022-08-08 16:37:45', '崔欣锋', 20, 1);
INSERT INTO `t_user` VALUES (50, '123@00.com', 'b45cffe084dd3d20d928bee85e7b0f21', '13252800136', 1, '2022-08-05 15:58:13', '2022-08-09 09:58:17', '徐哲', 9, 1);
INSERT INTO `t_user` VALUES (52, '112233@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '17763710724', 0, '2022-08-08 16:10:42', '2022-08-10 14:43:32', '昌武洋', 19, 1);
INSERT INTO `t_user` VALUES (53, '1234@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '18627011922', 0, '2022-08-09 09:00:12', '2022-08-09 09:00:12', 'pjames', 24, 1);
INSERT INTO `t_user` VALUES (54, '123117@foxmail.com', 'e10adc3949ba59abbe56e057f20f883e', '17764021821', 0, '2022-08-09 09:02:34', '2022-08-09 09:02:34', 'youngMoney', 27, 1);
INSERT INTO `t_user` VALUES (55, 'jackLiu@pku.edu.cn', 'e10adc3949ba59abbe56e057f20f883e', '18955463427', 1, '2022-08-09 09:03:59', '2022-08-09 09:03:59', 'yesy', 20, 1);
INSERT INTO `t_user` VALUES (56, '179@163.com', 'e10adc3949ba59abbe56e057f20f883e', '15432671897', 0, '2022-08-09 09:05:12', '2022-08-10 14:59:42', 'test1', 22, 1);
INSERT INTO `t_user` VALUES (57, '123@1q.com', 'e10adc3949ba59abbe56e057f20f883e', '18627002779', 0, '2022-08-09 09:06:08', '2022-08-09 09:06:51', 'qiunil', 19, 0);
INSERT INTO `t_user` VALUES (58, '1473444@qq.com', '25d55ad283aa400af464c76d713c07ad', '17391902854', 0, '2022-08-10 14:06:34', '2022-08-10 14:06:34', '罗一', 23, 1);
INSERT INTO `t_user` VALUES (59, '123@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '17873550951', 1, '2022-08-12 08:44:59', '2022-08-12 08:44:59', 'liccc', 25, 1);
INSERT INTO `t_user` VALUES (60, 'tzh@sh.com', '88318bb81374b63626c585e99e847403', '18717188579', 0, '2022-08-12 08:53:58', '2022-08-12 08:53:58', '沢22', 19, 1);

SET FOREIGN_KEY_CHECKS = 1;
