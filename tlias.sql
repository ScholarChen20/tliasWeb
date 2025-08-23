/*
 Navicat Premium Dump SQL

 Source Server         : JavaWeb
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : tlias

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 11/05/2025 19:01:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE tlias
-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '班级名称',
  `room` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '班级教室',
  `begin_date` date NOT NULL COMMENT '开课时间',
  `end_date` date NOT NULL COMMENT '结课时间',
  `master_id` int UNSIGNED NULL DEFAULT NULL COMMENT '班主任ID, 关联员工表ID',
  `subject` tinyint UNSIGNED NOT NULL COMMENT '学科, 1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6: 嵌入式',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '班级表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES (1, '计科1班', '212', '2024-04-30', '2024-06-29', 10, 1, '2024-06-01 17:08:23', '2024-06-01 17:39:58');
INSERT INTO `clazz` VALUES (2, '计专1班', '210', '2024-07-10', '2024-01-20', 3, 2, '2024-06-01 17:45:12', '2024-06-01 17:45:12');
INSERT INTO `clazz` VALUES (3, '软工1班', '108', '2024-06-15', '2024-12-25', 6, 3, '2024-06-01 17:45:40', '2025-05-10 21:03:12');
INSERT INTO `clazz` VALUES (4, '人智1班', '105', '2024-07-20', '2024-02-20', 20, 4, '2024-06-01 17:46:10', '2025-05-10 21:03:18');
INSERT INTO `clazz` VALUES (5, '大数据1班', '209', '2024-08-01', '2024-02-15', 8, 3, '2024-06-01 17:51:21', '2025-05-10 15:53:16');
INSERT INTO `clazz` VALUES (6, '网工1班', '325', '2024-11-20', '2024-05-10', 36, 5, '2024-11-15 11:35:46', '2025-05-10 21:03:25');
INSERT INTO `clazz` VALUES (7, '物网1班', '305', '2021-05-31', '2026-05-07', 15, 6, '2025-05-10 15:54:41', '2025-05-10 15:54:41');
INSERT INTO `clazz` VALUES (8, '计科2班', 'L1-303', '2025-05-08', '2025-12-31', 44, 5, '2025-05-10 21:03:02', '2025-05-10 21:03:34');

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES (1, '学工部', '2025-05-02 23:11:17', '2025-05-03 23:20:22');
INSERT INTO `dept` VALUES (2, '教研部', '2025-05-02 23:11:17', '2025-05-20 23:11:20');
INSERT INTO `dept` VALUES (3, '咨询部', '2025-05-02 23:11:17', '2025-05-20 23:11:20');
INSERT INTO `dept` VALUES (5, '人事部', '2025-05-02 23:11:17', '2025-05-20 23:11:20');
INSERT INTO `dept` VALUES (6, '行政部', '2025-05-02 23:11:17', '2025-05-20 23:11:20');
INSERT INTO `dept` VALUES (7, '信息中心', '2025-05-03 14:43:09', '2025-05-03 13:44:17');
INSERT INTO `dept` VALUES (10, '体育部', '2025-05-03 13:57:24', '2025-05-03 14:16:54');
INSERT INTO `dept` VALUES (11, '后勤部', '2025-05-03 14:18:47', '2025-05-03 14:18:47');
INSERT INTO `dept` VALUES (12, '图书馆', '2025-05-10 21:02:07', '2025-05-10 21:02:07');

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '123456',
  `name` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phone` char(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `gender` tinyint NULL DEFAULT NULL,
  `job` tinyint NULL DEFAULT NULL,
  `salary` decimal(10, 2) NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `entry_date` date NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `dept_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES (1, 'shinaian', '123456', '施耐庵', '13309090001', 1, 4, 15000.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2000-01-01', '2023-10-20 16:35:33', '2023-11-16 16:11:26', 2);
INSERT INTO `emp` VALUES (2, 'songjiang', '123456', '宋江', '13309090002', 1, 2, 8600.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2015-01-01', '2023-10-20 16:35:33', '2023-10-20 16:35:37', 2);
INSERT INTO `emp` VALUES (3, 'lujunyi', '123456', '卢俊义', '13309090003', 1, 2, 8900.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2008-05-01', '2023-10-20 16:35:33', '2023-10-20 16:35:39', 2);
INSERT INTO `emp` VALUES (4, 'wuyong', '123456', '吴用', '13309090004', 1, 2, 9200.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2007-01-01', '2023-10-20 16:35:33', '2023-10-20 16:35:41', 2);
INSERT INTO `emp` VALUES (5, 'gongsunsheng', '123456', '公孙胜', '13309090005', 1, 2, 9500.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2012-12-05', '2023-10-20 16:35:33', '2023-10-20 16:35:43', 2);
INSERT INTO `emp` VALUES (6, 'huosanniang', '123456', '扈三娘', '13309090006', 2, 3, 6500.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2013-09-05', '2023-10-20 16:35:33', '2023-10-20 16:35:45', 1);
INSERT INTO `emp` VALUES (7, 'chaijin', '123456', '柴进', '13309090007', 1, 1, 4700.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2005-08-01', '2023-10-20 16:35:33', '2023-10-20 16:35:47', 1);
INSERT INTO `emp` VALUES (8, 'likui', '123456', '李逵', '13309090008', 1, 1, 4800.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2014-11-09', '2023-10-20 16:35:33', '2023-10-20 16:35:49', 1);
INSERT INTO `emp` VALUES (9, 'wusong', '123456', '武松', '13309090009', 1, 1, 4900.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2011-03-11', '2023-10-20 16:35:33', '2023-10-20 16:35:51', 1);
INSERT INTO `emp` VALUES (10, 'linchong', '123456', '林冲', '13309090010', 1, 1, 5000.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2013-09-05', '2023-10-20 16:35:33', '2023-10-20 16:35:53', 1);
INSERT INTO `emp` VALUES (11, 'huyanzhuo', '123456', '呼延灼', '13309090011', 1, 2, 9700.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2007-02-01', '2023-10-20 16:35:33', '2023-10-20 16:35:55', 2);
INSERT INTO `emp` VALUES (12, 'xiaoliguang', '123456', '小李广', '13309090012', 1, 2, 10000.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2008-08-18', '2023-10-20 16:35:33', '2023-10-20 16:35:57', 2);
INSERT INTO `emp` VALUES (13, 'yangzhi', '123456', '杨志', '13309090013', 1, 1, 5300.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2012-11-01', '2023-10-20 16:35:33', '2023-10-20 16:35:59', 1);
INSERT INTO `emp` VALUES (14, 'shijin', '123456', '史进', '13309090014', 1, 2, 10600.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2002-08-01', '2023-10-20 16:35:33', '2023-10-20 16:36:01', 2);
INSERT INTO `emp` VALUES (15, 'sunerniang', '123456', '孙二娘', '13309090015', 2, 2, 10900.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2011-05-01', '2023-10-20 16:35:33', '2023-10-20 16:36:03', 2);
INSERT INTO `emp` VALUES (16, 'luzhishen', '123456', '鲁智深', '13309090016', 1, 2, 9600.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2010-01-01', '2023-10-20 16:35:33', '2023-10-20 16:36:05', 2);
INSERT INTO `emp` VALUES (17, 'liying', '12345678', '李应', '13309090017', 1, 1, 5800.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2015-03-21', '2023-10-20 16:35:33', '2023-10-20 16:36:07', 1);
INSERT INTO `emp` VALUES (18, 'shiqian', '123456', '时迁', '13309090018', 1, 2, 10200.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2015-01-01', '2023-10-20 16:35:33', '2023-10-20 16:36:09', 2);
INSERT INTO `emp` VALUES (19, 'gudasao', '123456', '顾大嫂', '13309090019', 2, 2, 10500.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2008-01-01', '2023-10-20 16:35:33', '2023-10-20 16:36:11', 2);
INSERT INTO `emp` VALUES (20, 'ruanxiaoer', '123456', '阮小二', '13309090020', 1, 2, 10800.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2018-01-01', '2023-10-20 16:35:33', '2023-10-20 16:36:13', 2);
INSERT INTO `emp` VALUES (21, 'ruanxiaowu', '123456', '阮小五', '13309090021', 1, 5, 5200.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2015-01-01', '2023-10-20 16:35:33', '2023-10-20 16:36:15', 3);
INSERT INTO `emp` VALUES (22, 'ruanxiaoqi', '123456', '阮小七', '13309090022', 1, 5, 5500.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2016-01-01', '2023-10-20 16:35:33', '2023-10-20 16:36:17', 3);
INSERT INTO `emp` VALUES (23, 'ruanji', '123456', '阮籍', '13309090023', 1, 5, 5800.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2012-01-01', '2023-10-20 16:35:33', '2023-10-20 16:36:19', 3);
INSERT INTO `emp` VALUES (24, 'tongwei', '123456', '童威', '13309090024', 1, 5, 5000.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2006-01-01', '2023-10-20 16:35:33', '2023-10-20 16:36:21', 3);
INSERT INTO `emp` VALUES (25, 'tongmeng', '123456', '童猛', '13309090025', 1, 5, 4800.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2002-01-01', '2023-10-20 16:35:33', '2023-10-20 16:36:23', 3);
INSERT INTO `emp` VALUES (26, 'yanshun', '123456', '燕顺', '13309090026', 1, 5, 5400.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2011-01-01', '2023-10-20 16:35:33', '2023-11-08 22:12:46', 3);
INSERT INTO `emp` VALUES (27, 'lijun', '123456', '李俊', '13309090027', 1, 4, 6600.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2004-01-01', '2023-10-20 16:35:33', '2025-05-10 17:33:30', 2);
INSERT INTO `emp` VALUES (28, 'lizhong', '123456', '李忠', '13309090028', 1, 5, 5000.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2007-01-01', '2023-10-20 16:35:33', '2023-11-17 16:34:22', 3);
INSERT INTO `emp` VALUES (30, 'liyun', '123456', '李云', '13309090030', 1, NULL, NULL, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2020-03-01', '2023-10-20 16:35:33', '2023-10-20 16:36:31', NULL);
INSERT INTO `emp` VALUES (36, 'linghuchong', '123456', '令狐冲', '18809091212', 1, 2, 6800.00, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg', '2023-10-19', '2023-10-20 20:44:54', '2023-11-09 09:41:04', 2);
INSERT INTO `emp` VALUES (37, 'zzzzz', '123456', '张志洲', '13300001111', 1, 2, 7600.00, '', '2025-03-03', '2025-05-03 14:58:37', '2025-05-03 14:58:41', 1);
INSERT INTO `emp` VALUES (38, 'ccccc', '123456', '陈晨臣', '13300001111', 1, 2, 7600.00, '', '2025-03-03', '2025-05-03 14:58:37', '2025-05-03 14:58:41', 2);
INSERT INTO `emp` VALUES (39, 'yyyyy', '123456', '杨钰莹', '13300001111', 1, 2, 7600.00, '', '2025-03-03', '2025-05-03 14:58:37', '2025-05-10 17:33:44', 2);
INSERT INTO `emp` VALUES (40, 'lianghaiyan', '123456', '梁海燕', '13300001111', 1, 4, 8500.00, 'https://loremflickr.com/400/400?lock=6992607584580566', '2024-11-11', '2025-05-04 00:24:10', '2025-05-04 00:24:10', 1);
INSERT INTO `emp` VALUES (41, 'miehaorang', '123456', '梅浩然', '13011112222', 2, 2, 7800.00, 'https://loremflickr.com/400/400?lock=6771685015710480', '2025-04-09', '2025-05-04 00:35:00', '2025-05-10 17:33:02', 2);
INSERT INTO `emp` VALUES (44, 'linpingzhi', '123456', '林平之', '13300001111', 1, 3, 8000.00, 'https://loremflickr.com/400/400?lock=6992607584580566', '2024-11-11', '2025-05-04 00:36:10', '2025-05-04 00:36:10', 2);

-- ----------------------------
-- Table structure for emp_expr
-- ----------------------------
DROP TABLE IF EXISTS `emp_expr`;
CREATE TABLE `emp_expr`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `emp_id` int NOT NULL,
  `begin` date NULL DEFAULT NULL,
  `end` date NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `job` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of emp_expr
-- ----------------------------
INSERT INTO `emp_expr` VALUES (1, 44, '1984-12-07', '1950-09-14', 'in repr', 'aliquip commodo ad dolore aut');
INSERT INTO `emp_expr` VALUES (2, 44, '1954-04-19', '1986-06-08', 'Lorem non', 'cillum');
INSERT INTO `emp_expr` VALUES (11, 41, '1969-09-11', '1970-02-27', 'ex aliqua reprehe', 'aliqua');
INSERT INTO `emp_expr` VALUES (12, 41, '1949-06-27', '1979-08-11', 'adipisicing Ut sit e', 'consectetur ');

-- ----------------------------
-- Table structure for emp_log
-- ----------------------------
DROP TABLE IF EXISTS `emp_log`;
CREATE TABLE `emp_log`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of emp_log
-- ----------------------------

-- ----------------------------
-- Table structure for operate_log
-- ----------------------------
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_emp_id` int UNSIGNED NULL DEFAULT NULL COMMENT '操作人ID',
  `operate_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作的类名',
  `method_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作的方法名',
  `method_params` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法参数',
  `return_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返回值, 存储json格式',
  `cost_time` int NULL DEFAULT NULL COMMENT '方法执行耗时, 单位:ms',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operate_log
-- ----------------------------
INSERT INTO `operate_log` VALUES (1, 2, '2025-05-10 15:53:16', 'com.itheima.controller.ClazzController', 'update', '[Clazz(id=5, name=大数据科学与大数据技术1班, room=209, beginDate=2024-08-01, endDate=2024-02-15, masterId=8, subject=3, createTime=2024-06-01T17:51:21, updateTime=2025-05-10T15:53:15.813758400, masterName=null, status=null)]', 'Result(code=1, msg=success, data=null)', 52);
INSERT INTO `operate_log` VALUES (2, 2, '2025-05-10 15:54:41', 'com.itheima.controller.ClazzController', 'save', '[Clazz(id=null, name=物联网工程1班, room=305, beginDate=2021-05-31, endDate=2026-05-07, masterId=15, subject=6, createTime=2025-05-10T15:54:41.015172700, updateTime=2025-05-10T15:54:41.015172700, masterName=null, status=null)]', 'Result(code=1, msg=success, data=null)', 3);
INSERT INTO `operate_log` VALUES (3, 2, '2025-05-10 21:03:02', 'com.itheima.controller.ClazzController', 'save', '[Clazz(id=null, name=计科2班, room=L1-303, beginDate=2025-05-08, endDate=2025-12-31, masterId=44, subject=1, createTime=2025-05-10T21:03:01.648396200, updateTime=2025-05-10T21:03:01.648396200, masterName=null, status=null)]', 'Result(code=1, msg=success, data=null)', 3);
INSERT INTO `operate_log` VALUES (4, 2, '2025-05-10 21:03:12', 'com.itheima.controller.ClazzController', 'update', '[Clazz(id=3, name=软工1班, room=108, beginDate=2024-06-15, endDate=2024-12-25, masterId=6, subject=3, createTime=2024-06-01T17:45:40, updateTime=2025-05-10T21:03:11.695459700, masterName=null, status=null)]', 'Result(code=1, msg=success, data=null)', 3);
INSERT INTO `operate_log` VALUES (5, 2, '2025-05-10 21:03:18', 'com.itheima.controller.ClazzController', 'update', '[Clazz(id=4, name=人智1班, room=105, beginDate=2024-07-20, endDate=2024-02-20, masterId=20, subject=4, createTime=2024-06-01T17:46:10, updateTime=2025-05-10T21:03:18.312988600, masterName=null, status=null)]', 'Result(code=1, msg=success, data=null)', 5);
INSERT INTO `operate_log` VALUES (6, 2, '2025-05-10 21:03:25', 'com.itheima.controller.ClazzController', 'update', '[Clazz(id=6, name=网工1班, room=325, beginDate=2024-11-20, endDate=2024-05-10, masterId=36, subject=5, createTime=2024-11-15T11:35:46, updateTime=2025-05-10T21:03:25.184016800, masterName=null, status=null)]', 'Result(code=1, msg=success, data=null)', 4);
INSERT INTO `operate_log` VALUES (7, 2, '2025-05-10 21:03:34', 'com.itheima.controller.ClazzController', 'update', '[Clazz(id=8, name=计科2班, room=L1-303, beginDate=2025-05-08, endDate=2025-12-31, masterId=44, subject=5, createTime=2025-05-10T21:03:02, updateTime=2025-05-10T21:03:33.886496200, masterName=null, status=null)]', 'Result(code=1, msg=success, data=null)', 4);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `name` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '姓名',
  `no` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学号',
  `gender` tinyint UNSIGNED NOT NULL COMMENT '性别, 1: 男, 2: 女',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '手机号',
  `id_card` char(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '身份证号',
  `is_college` tinyint UNSIGNED NOT NULL COMMENT '是否来自于院校, 1:是, 0:否',
  `address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `degree` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '最高学历, 1:初中, 2:高中, 3:大专, 4:本科, 5:硕士, 6:博士',
  `graduation_date` date NULL DEFAULT NULL COMMENT '毕业时间',
  `clazz_id` int UNSIGNED NOT NULL COMMENT '班级ID, 关联班级表ID',
  `violation_count` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '违纪次数',
  `violation_score` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '违纪扣分',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `no`(`no` ASC) USING BTREE,
  UNIQUE INDEX `phone`(`phone` ASC) USING BTREE,
  UNIQUE INDEX `id_card`(`id_card` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '学员表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '段誉', '2022000001', 1, '18800000001', '110120000300200001', 1, '北京市昌平区建材城西路1号', 1, '2021-07-01', 2, 0, 0, '2024-11-14 21:22:19', '2024-11-15 16:20:59');
INSERT INTO `student` VALUES (2, '萧峰', '2022000002', 1, '18800210003', '110120000300200002', 1, '北京市昌平区建材城西路2号', 2, '2022-07-01', 1, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (3, '虚竹', '2022000003', 1, '18800013001', '110120000300200003', 1, '北京市昌平区建材城西路3号', 2, '2024-07-01', 1, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (4, '萧远山', '2022000004', 1, '18800003211', '110120000300200004', 0, '北京市昌平区建材城西路4号', 5, '2024-07-01', 1, 2, 15, '2024-11-14 21:22:19', '2025-05-10 18:44:14');
INSERT INTO `student` VALUES (5, '阿朱', '2022000005', 2, '18800160002', '110120000300200005', 1, '北京市昌平区建材城西路5号', 4, '2020-07-01', 1, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (6, '阿紫', '2022000006', 2, '18800000034', '110120000300200006', 1, '北京市昌平区建材城西路6号', 4, '2021-07-01', 2, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (7, '游坦之', '2022000007', 1, '18800000067', '110120000300200007', 1, '北京市昌平区建材城西路7号', 4, '2022-07-01', 2, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (8, '康敏', '2022000008', 2, '18800000077', '110120000300200008', 1, '北京市昌平区建材城西路8号', 5, '2024-07-01', 2, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (9, '徐长老', '2022000009', 1, '18800000341', '110120000300200009', 1, '北京市昌平区建材城西路9号', 3, '2024-07-01', 2, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (10, '云中鹤', '2022000010', 1, '18800006571', '110120000300200010', 1, '北京市昌平区建材城西路10号', 2, '2020-07-01', 2, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (11, '钟万仇', '2022000011', 1, '18800000391', '110120000300200011', 1, '北京市昌平区建材城西路11号', 4, '2021-07-01', 1, 0, 0, '2024-11-14 21:22:19', '2024-11-15 16:21:24');
INSERT INTO `student` VALUES (12, '崔百泉', '2022000012', 1, '18800000781', '110120000300200018', 1, '北京市昌平区建材城西路12号', 4, '2022-07-05', 3, 6, 17, '2024-11-14 21:22:19', '2025-05-10 16:53:53');
INSERT INTO `student` VALUES (13, '耶律洪基', '2022000013', 1, '18800008901', '110120000300200013', 1, '北京市昌平区建材城西路13号', 4, '2024-07-01', 2, 0, 0, '2024-11-14 21:22:19', '2024-11-15 16:21:21');
INSERT INTO `student` VALUES (14, '天山童姥', '2022000014', 2, '18800009201', '110120000300200014', 1, '北京市昌平区建材城西路14号', 4, '2024-07-01', 1, 0, 0, '2024-11-14 21:22:19', '2024-11-15 16:21:17');
INSERT INTO `student` VALUES (15, '刘竹庄', '2022000015', 1, '18800009401', '110120000300200015', 1, '北京市昌平区建材城西路15号', 3, '2020-07-01', 4, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (16, '李春来', '2022000016', 1, '18800008501', '110120000300200016', 1, '北京市昌平区建材城西路16号', 4, '2021-07-01', 4, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (17, '王语嫣', '2022000017', 2, '18800007601', '110120000300200017', 1, '北京市昌平区建材城西路17号', 2, '2022-07-01', 4, 0, 0, '2024-11-14 21:22:19', '2024-11-14 21:22:19');
INSERT INTO `student` VALUES (18, '郑成功', '2024001101', 1, '13309092345', '110110110110110110', 0, '北京市昌平区回龙观街道88号', 5, '2021-07-01', 3, 1, 10, '2024-11-15 16:26:18', '2025-05-10 18:44:05');
INSERT INTO `student` VALUES (19, '华盛顿', '2024000001', 1, '13011113333', '350823200101016710', 1, '福建省龙岩市', 5, '2025-05-27', 7, 0, 0, '2025-05-10 20:45:37', '2025-05-10 20:45:37');
INSERT INTO `student` VALUES (20, '陈昌海', '2024000002', 1, '13099991111', '355011200209090011', 1, '福建省福州市', 4, '2025-05-31', 6, 0, 0, '2025-05-10 20:49:51', '2025-05-10 20:49:51');
INSERT INTO `student` VALUES (21, '甲润六', '2024000003', 1, '18011112222', '30110019990909781X', 1, '广东省广州市', 3, '2025-05-27', 5, 0, 0, '2025-05-10 20:51:19', '2025-05-10 20:51:19');
INSERT INTO `student` VALUES (22, '刘海燕', '2024000004', 2, '13011154747', '280523200003036444', 0, '浙江省杭州市', 2, '2025-05-16', 5, 0, 0, '2025-05-10 20:52:40', '2025-05-10 20:52:40');

SET FOREIGN_KEY_CHECKS = 1;
