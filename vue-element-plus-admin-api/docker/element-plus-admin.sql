/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : localhost:3306
 Source Schema         : element-plus-admin

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 29/12/2025 14:48:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `sort` int NOT NULL DEFAULT 0 COMMENT '字典排序',
  `label` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` bigint NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater` bigint NOT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '登入', '1', 'login_type', '2025-09-17 10:44:36', 1, '2025-09-17 10:44:36', 1, 0);
INSERT INTO `sys_dict_data` VALUES (2, 2, '登出', '2', 'login_type', '2025-09-17 10:44:55', 1, '2025-09-17 10:44:55', 1, 0);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '字典名称',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` bigint NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater` bigint NOT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '登录类型', 'login_type', '2025-09-17 10:43:54', 1, '2025-09-17 10:43:59', 1, 0);

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` int NOT NULL COMMENT '类型:1:登录,2:登出',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `user_agent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `user_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户IP',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录结果',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` bigint NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater` bigint NOT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '上级ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设置该路由在侧边栏和面包屑中展示的名字',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当设置 noredirect 的时候该路由在面包屑导航中不可被点击',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题',
  `hidden` tinyint NOT NULL DEFAULT 0 COMMENT '当设置 true 的时候该路由不会再侧边栏出现 如404，login等页面(默认 false)',
  `always_show` tinyint NOT NULL DEFAULT 0 COMMENT '当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式，\r\n                              只有一个时，会将那个子路由当做根路由显示在侧边栏，\r\n                              若你想不管路由下面的 children 声明的个数都显示你的根路由，\r\n                              你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，\r\n                              一直显示根路由(默认 false)',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设置该路由的图标',
  `no_cache` tinyint NOT NULL DEFAULT 0 COMMENT '如果设置为true，则不会被 <keep-alive> 缓存(默认 false)',
  `breadcrumb` tinyint NOT NULL DEFAULT 1 COMMENT '如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)',
  `affix` tinyint NOT NULL DEFAULT 0 COMMENT '如果设置为true，则会一直固定在tag项中(默认 false)',
  `no_tags_view` tinyint NOT NULL DEFAULT 0 COMMENT '如果设置为true，则不会出现在tag中(默认 false)',
  `active_menu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示高亮的路由路径',
  `sort` int NOT NULL DEFAULT 1 COMMENT '排序',
  `type` int NOT NULL DEFAULT 1 COMMENT '类型:1:菜单,2:按钮',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` bigint NOT NULL DEFAULT 1 COMMENT '创建人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater` bigint NOT NULL DEFAULT 1 COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, NULL, 'router.dashboard', '/dashboard', '#', '/dashboard/analysis', 'Dashboard', 0, 1, 'vi-ant-design:dashboard-filled', 0, 1, 0, 0, NULL, 1, 0, NULL, '2025-09-05 10:37:55', 1, '2025-09-16 14:21:50', 1, 0);
INSERT INTO `sys_menu` VALUES (2, 1, 'router.analysis', 'analysis', 'views/Dashboard/Analysis', NULL, 'Analysis', 0, 0, NULL, 1, 1, 1, 0, NULL, 2, 1, NULL, '2025-09-05 10:37:55', 1, '2025-09-11 10:37:12', 1, 0);
INSERT INTO `sys_menu` VALUES (3, 1, 'router.workplace', 'workplace', 'views/Dashboard/Workplace', NULL, 'Workplace', 0, 0, NULL, 1, 1, 1, 0, NULL, 2, 1, NULL, '2025-09-05 10:37:55', 1, '2025-09-11 10:37:12', 1, 0);
INSERT INTO `sys_menu` VALUES (4, NULL, 'router.system', '/system', '#', '/system/user', 'System', 0, 1, 'vi-ant-design:lock-filled', 0, 1, 0, 0, NULL, 2, 0, NULL, '2025-09-05 10:37:55', 1, '2025-09-17 13:02:21', 1, 0);
INSERT INTO `sys_menu` VALUES (5, 4, 'router.user', 'user', 'views/System/User/User', NULL, 'User', 0, 0, NULL, 0, 1, 0, 0, NULL, 1, 1, NULL, '2025-09-05 10:37:55', 1, '2025-09-17 13:02:38', 1, 0);
INSERT INTO `sys_menu` VALUES (6, 4, 'router.menu', 'menu', 'views/System/Menu/Menu', NULL, 'Menu', 0, 0, NULL, 0, 1, 0, 0, NULL, 3, 1, NULL, '2025-09-05 10:37:55', 1, '2025-09-17 13:02:38', 1, 0);
INSERT INTO `sys_menu` VALUES (7, 4, 'router.role', 'role', 'views/System/Role/Role', NULL, 'Role', 0, 0, NULL, 0, 1, 0, 0, NULL, 2, 1, NULL, '2025-09-05 10:37:55', 1, '2025-09-17 13:02:38', 1, 0);
INSERT INTO `sys_menu` VALUES (8, 4, 'router.log', 'log', 'views/System/Log/Log', NULL, 'Log', 0, 0, NULL, 0, 1, 0, 0, NULL, 4, 1, NULL, '2025-09-17 11:20:26', 1, '2025-09-17 13:02:38', 1, 0);
INSERT INTO `sys_menu` VALUES (9, 5, 'router.create', NULL, '##', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 2, 2, 'system:user:create', '2025-09-16 14:07:54', 1, '2025-09-17 11:29:15', 1, 0);
INSERT INTO `sys_menu` VALUES (10, 5, 'router.query', NULL, '##', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 1, 2, 'system:user:query', '2025-09-16 14:09:15', 1, '2025-09-17 11:29:15', 1, 0);
INSERT INTO `sys_menu` VALUES (11, 5, 'router.update', NULL, '##', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 3, 2, 'system:user:update', '2025-09-16 14:15:29', 1, '2025-09-17 11:29:15', 1, 0);
INSERT INTO `sys_menu` VALUES (12, 5, 'router.delete', NULL, '##', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 4, 2, 'system:user:delete', '2025-09-16 14:15:43', 1, '2025-09-17 11:29:15', 1, 0);
INSERT INTO `sys_menu` VALUES (13, 7, 'router.query', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 1, 2, 'system:role:query', '2025-09-16 14:36:12', 1, '2025-09-17 11:29:32', 1, 0);
INSERT INTO `sys_menu` VALUES (14, 7, 'router.create', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 2, 2, 'system:role:create', '2025-09-16 14:36:33', 1, '2025-09-17 11:29:32', 1, 0);
INSERT INTO `sys_menu` VALUES (15, 7, 'router.update', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 3, 2, 'system:role:update', '2025-09-16 14:36:52', 1, '2025-09-17 11:29:32', 1, 0);
INSERT INTO `sys_menu` VALUES (16, 7, 'router.delete', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 4, 2, 'system:role:delet', '2025-09-16 14:37:14', 1, '2025-09-17 11:29:32', 1, 0);
INSERT INTO `sys_menu` VALUES (17, 6, 'router.query', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 1, 2, 'system:menu:query', '2025-09-16 14:37:36', 1, '2025-09-17 11:29:32', 1, 0);
INSERT INTO `sys_menu` VALUES (18, 6, 'router.create', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 2, 2, 'system:menu:create', '2025-09-16 14:37:55', 1, '2025-09-17 11:29:32', 1, 0);
INSERT INTO `sys_menu` VALUES (19, 6, 'router.update', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 3, 2, 'system:menu:update', '2025-09-16 14:38:11', 1, '2025-09-17 11:29:32', 1, 0);
INSERT INTO `sys_menu` VALUES (20, 6, 'router.delete', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 4, 2, 'system:menu:delete', '2025-09-16 14:38:25', 1, '2025-09-17 11:29:32', 1, 0);
INSERT INTO `sys_menu` VALUES (21, 8, 'router.query', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 1, 2, 'system:log:query', '2025-09-17 11:20:45', 1, '2025-09-17 11:29:32', 1, 0);
INSERT INTO `sys_menu` VALUES (22, 5, 'router.password', NULL, '#', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, NULL, 5, 2, 'system:user:password', '2025-09-17 14:39:20', 1, '2025-09-17 14:39:20', 1, 0);

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户编号',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作类型',
  `sub_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作名',
  `biz_id` bigint NOT NULL COMMENT '操作数据模块编号',
  `action` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作内容',
  `success` tinyint(1) NOT NULL DEFAULT 1 COMMENT '操作结果',
  `extra` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求地址',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器 UA',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operate_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `menu_ids` json NULL COMMENT '菜单ID集合',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` bigint NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `updater` bigint NOT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '添加一个备注', '[1, 2, 3, 4, 5, 10, 9, 11, 12, 22, 7, 13, 14, 15, 16, 6, 17, 18, 19, 20, 8, 21]', '2023-10-11 11:16:10', 1, '2025-09-18 10:33:38', 1, 0);
INSERT INTO `sys_role` VALUES (2, '管理员', '角色', '[1, 2, 3, 5, 10, 9, 11, 12, 22, 7, 13, 14, 15, 16, 6, 17, 18, 19, 20]', '2023-10-12 13:27:50', 1, '2025-09-17 15:52:22', 1, 0);
INSERT INTO `sys_role` VALUES (3, '测试', NULL, '[10, 5, 4]', '2025-09-17 15:53:21', 1, '2025-09-18 13:22:18', 1, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif' COMMENT '头像',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `role_ids` json NULL COMMENT '角色ID集合',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` bigint NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater` bigint NOT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_01`(`account` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'wpC0p2jNDaYWhhxZPyB4/POPz4+lz8n1fv6UrPDXanF/fCYoY5pewQJosQcV/ZQJFqXRmd+Xn7jvFRxYCOYMtvgxIVL3yn5BklnVUifxSjy+MOMEVxp/L8sGKSACr6uYljPUL1jtWAJZnA0y83yRkxSBBr4+PDbnJWM2N9zKD64=', '管理员', 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif', '12332131', NULL, NULL, '[1]', 1, '2023-10-11 11:17:57', 0, '2025-09-17 10:30:41', 1, 0);
INSERT INTO `sys_user` VALUES (2, 'moran', 'larxj9nkkTvTBzX4Ky804BMYsxDPQMFJToYdCrbnIPcUAxRtH14FwbtqOe+Q9HeeoPXdR+b19SxjKBnJ9z7UTtmg1oe8yn76IPZdWNMsBJXColp2n+K5EYVC0xpihQGYjzY658e75XzBgIbGSDtz5oW0O4IXSSmYuzxoc/mSO1U=', '默然', 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif', '18746176990', '1280520512@qq.com', NULL, '[1]', 1, '2024-03-25 15:51:14', 0, '2025-09-01 16:41:45', 0, 0);
INSERT INTO `sys_user` VALUES (5, 'test', 'LH8tkaY2upOorMYC+wUdERf61H1a3p01gI5ZEC1V6Y1VaJ0pZb72AYGKKwX3e/z1ghCZeVXjwpD9ypwr+J6IOR72Tmc7yrPInKZBZzw2o5VSsNN+1Mo7u/VZTHSzXssAO13+Ijac3CzLbff9VAoTo1YCfC5GJ6OtlU5lWAwfBLM=', '测试001', 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif', '111111', '123@123.com', '123', '[3]', 1, '2025-09-17 17:29:15', 1, '2025-09-18 13:21:34', 1, 1);
INSERT INTO `sys_user` VALUES (6, 'test002', '4QHqtoCogLiYPve6U+CgdCmuMuJhlD33MGaBgcpNMOiGwwSM9DS7Fpb2nR7v858wiqbhhYT7JyKXdKrT5oamAPxrCX/Qyv5fqK48fl1WX8E0f43tXmJ8XNzq19uD2mRquaWcYMBdf2//xKuPximTVrUxh541BaPHI3XgONIfhNQ=', '测试002', 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif', '123123', '123', '123', '[3]', 1, '2025-09-18 13:22:03', 1, '2025-09-18 13:22:10', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
