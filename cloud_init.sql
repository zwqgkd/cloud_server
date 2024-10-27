/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80402
 Source Host           : localhost:3306
 Source Schema         : cloud_server

 Target Server Type    : MySQL
 Target Server Version : 80402
 File Encoding         : 65001

 Date: 27/10/2024 10:49:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_directory_index
-- ----------------------------
DROP TABLE IF EXISTS `tb_directory_index`;
CREATE TABLE `tb_directory_index`  (
  `directory_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `local_remote` int(0) NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`directory_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_file
-- ----------------------------
DROP TABLE IF EXISTS `tb_file`;
CREATE TABLE `tb_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `father_id` int(0) NULL DEFAULT NULL,
  `is_dir` int(0) NULL DEFAULT NULL,
  `local_remote` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_license
-- ----------------------------
DROP TABLE IF EXISTS `tb_license`;
CREATE TABLE `tb_license`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `license_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `procedure_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `license_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `exp` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_permission
-- ----------------------------
INSERT INTO `tb_permission` VALUES (1, '用户删除', '用户管理');
INSERT INTO `tb_permission` VALUES (2, '用户新增', '用户管理');
INSERT INTO `tb_permission` VALUES (3, '用户查询', '用户管理');
INSERT INTO `tb_permission` VALUES (4, '用户编辑', '用户管理');
INSERT INTO `tb_permission` VALUES (5, '重置密码', '用户管理');
INSERT INTO `tb_permission` VALUES (6, '算子新增', '算子管理');
INSERT INTO `tb_permission` VALUES (7, '算子查询', '算子管理');
INSERT INTO `tb_permission` VALUES (8, '算子删除', '算子管理');
INSERT INTO `tb_permission` VALUES (9, '算子更新', '算子管理');
INSERT INTO `tb_permission` VALUES (10, '算子审核', '算子管理');
INSERT INTO `tb_permission` VALUES (11, '算子详情', '算子管理');
INSERT INTO `tb_permission` VALUES (12, '方案新增', '方案管理');
INSERT INTO `tb_permission` VALUES (13, '方案查询', '方案管理');
INSERT INTO `tb_permission` VALUES (14, '方案删除', '方案管理');
INSERT INTO `tb_permission` VALUES (15, '方案更新', '方案管理');
INSERT INTO `tb_permission` VALUES (16, '方案下载', '方案管理');
INSERT INTO `tb_permission` VALUES (17, '方案审核', '方案管理');
INSERT INTO `tb_permission` VALUES (18, '方案详情', '方案管理');
INSERT INTO `tb_permission` VALUES (19, 'License基础信息管理', 'License管理');
INSERT INTO `tb_permission` VALUES (20, 'License新增', 'License管理');
INSERT INTO `tb_permission` VALUES (21, 'License查询', 'License管理');
INSERT INTO `tb_permission` VALUES (22, 'License编辑', 'License管理');
INSERT INTO `tb_permission` VALUES (23, '软件下载', '软件管理');
INSERT INTO `tb_permission` VALUES (24, '软件版本查询', '软件管理');
INSERT INTO `tb_permission` VALUES (25, '软件更新', '软件管理');

-- ----------------------------
-- Table structure for tb_procedure_index
-- ----------------------------
DROP TABLE IF EXISTS `tb_procedure_index`;
CREATE TABLE `tb_procedure_index`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `procedure_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `directory_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1873600515 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, '超级管理员', '张三', '2024-10-25 15:31:09', '2024-10-26 15:31:14');
INSERT INTO `tb_role` VALUES (2, '管理员', '张三', '2024-10-25 15:31:10', '2024-10-27 15:31:18');
INSERT INTO `tb_role` VALUES (3, '普通用户', '张三', '2024-10-25 15:31:12', '2024-10-27 15:31:21');

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NULL DEFAULT NULL,
  `permission_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_permission
-- ----------------------------
INSERT INTO `tb_role_permission` VALUES (1, 1, 1);
INSERT INTO `tb_role_permission` VALUES (2, 1, 2);
INSERT INTO `tb_role_permission` VALUES (3, 1, 3);
INSERT INTO `tb_role_permission` VALUES (4, 1, 4);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_id` int(0) NULL DEFAULT 3,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
