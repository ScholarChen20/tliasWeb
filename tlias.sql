-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tlias
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clazz`
--

DROP TABLE IF EXISTS `clazz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clazz` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '班级名称',
  `room` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '班级教室',
  `begin_date` date NOT NULL COMMENT '开课时间',
  `end_date` date NOT NULL COMMENT '结课时间',
  `master_id` int unsigned DEFAULT NULL COMMENT '班主任ID, 关联员工表ID',
  `subject` tinyint unsigned NOT NULL COMMENT '学科, 1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6: 嵌入式',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='班级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clazz`
--

LOCK TABLES `clazz` WRITE;
/*!40000 ALTER TABLE `clazz` DISABLE KEYS */;
INSERT INTO `clazz` VALUES (1,'计科1班','212','2024-04-30','2024-06-29',10,1,'2024-06-01 17:08:23','2024-06-01 17:39:58'),(2,'计专1班','210','2024-07-10','2024-01-20',3,2,'2024-06-01 17:45:12','2024-06-01 17:45:12'),(3,'软工1班','108','2024-06-15','2024-12-25',6,3,'2024-06-01 17:45:40','2025-05-10 21:03:12'),(4,'人智1班','105','2024-07-20','2024-02-20',20,4,'2024-06-01 17:46:10','2025-05-10 21:03:18'),(5,'大数据1班','209','2024-08-01','2024-02-15',8,3,'2024-06-01 17:51:21','2025-05-10 15:53:16'),(6,'网工1班','325','2024-11-20','2024-05-10',36,5,'2024-11-15 11:35:46','2025-05-10 21:03:25'),(7,'物网1班','305','2021-05-31','2026-05-07',15,6,'2025-05-10 15:54:41','2025-05-10 15:54:41'),(8,'计科2班','L1-303','2025-05-08','2025-12-31',44,5,'2025-05-10 21:03:02','2025-05-10 21:03:34');
/*!40000 ALTER TABLE `clazz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dept` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` VALUES (1,'学工部','2025-05-02 23:11:17','2025-05-03 23:20:22'),(2,'教研部','2025-05-02 23:11:17','2025-05-20 23:11:20'),(3,'咨询部','2025-05-02 23:11:17','2025-05-20 23:11:20'),(5,'人事部','2025-05-02 23:11:17','2025-05-20 23:11:20'),(6,'行政部','2025-05-02 23:11:17','2025-05-20 23:11:20'),(7,'信息中心','2025-05-03 14:43:09','2025-05-03 13:44:17'),(10,'体育部','2025-05-03 13:57:24','2025-05-03 14:16:54'),(11,'后勤部','2025-05-03 14:18:47','2025-05-03 14:18:47'),(12,'图书馆','2025-05-10 21:02:07','2025-05-10 21:02:07');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `dept` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '科室',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '职称',
  `gender` varchar(3) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '性别：0女，1男',
  `hospital` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属医院',
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '介绍',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否可预约',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'李显子','心内科','主任医师','0','深圳大学总医院','教授、主任医师、博士生导师。擅长治疗心血管病',1,'13566664444','https://www.keaitupian.cn/cjpic/frombd/2/253/1985490445/2234330262.jpg','2025-08-15 10:46:46','2025-08-24 13:21:19'),(2,'陈曦','心内科','主治医师','0','深圳大学总医院','教授、主任医师、博士生导师。擅长治疗心血管病。',1,'13566664444','https://img95.699pic.com/element/40106/9618.png_300.png','2025-08-17 10:46:53','2025-08-24 10:46:29'),(3,'冠鲽','皮肤科','副主治医师','0','深圳大学总医院','教授、副主治、博士生导师。',1,'18164441389','https://tse4-mm.cn.bing.net/th/id/OIP-C.8F-vXAF42CSiBaMvax6IPgAAAA?cb=iwc2&rs=1&pid=ImgDetMain','2025-08-21 10:47:00','2025-08-24 10:46:29'),(4,'王菲','皮肤科','主治医师','0','深圳大学总医院','教授、主治、博士生导师。',1,'18966453325','https://tse4-mm.cn.bing.net/th/id/OIP-C.UZJ39CfXAPCBm63euHZSJAAAAA?cb=iwc2&rs=1&pid=ImgDetMain','2025-08-20 10:47:12','2025-08-24 10:46:29'),(5,'郑姐','中医科','主任医师','0','深圳大学总医院','科主任，主任中医师，副教授，从事中医临床工作近20年。',1,'17800012356','https://img95.699pic.com/element/40122/7036.png_300.png','2025-08-19 10:47:17','2025-08-24 10:46:29'),(6,'妍妍','心内科','住院医师','1','北京大学深圳医院','住院医师，硕士毕业于南方医学大学，主修心内科，擅长治疗高血压、心脏病等疾病',1,'13566664578','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/7ed4c9ed-b64d-436e-ac51-e34bec6699ed.png','2025-08-18 10:47:26','2025-08-24 14:42:25'),(7,'菲菲','中医科','主任医师','0','北京大学深圳医院','主任医师，博士毕业于北京大学医学院，主修中医内科，擅长内科治疗',0,'13566457896','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/9d6d1853-2cde-46cd-9a52-13d26e4e99b3.png','2025-08-24 13:26:50','2025-08-24 13:27:35');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp`
--

DROP TABLE IF EXISTS `emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '123456',
  `name` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phone` char(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `gender` tinyint DEFAULT NULL,
  `job` tinyint DEFAULT NULL,
  `salary` decimal(10,2) DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `entry_date` date DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `dept_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp`
--

LOCK TABLES `emp` WRITE;
/*!40000 ALTER TABLE `emp` DISABLE KEYS */;
INSERT INTO `emp` VALUES (1,'shinaian','123456','施耐庵','13309090001',1,4,15000.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2000-01-01','2023-10-20 16:35:33','2023-11-16 16:11:26',2),(2,'songjiang','123456','宋江','13309090002',1,2,8600.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01','2023-10-20 16:35:33','2023-10-20 16:35:37',2),(3,'lujunyi','123456','卢俊义','13309090003',1,2,8900.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-05-01','2023-10-20 16:35:33','2023-10-20 16:35:39',2),(4,'wuyong','123456','吴用','13309090004',1,2,9200.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-01-01','2023-10-20 16:35:33','2023-10-20 16:35:41',2),(5,'gongsunsheng','123456','公孙胜','13309090005',1,2,9500.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-12-05','2023-10-20 16:35:33','2023-10-20 16:35:43',2),(6,'huosanniang','123456','扈三娘','13309090006',2,3,6500.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2013-09-05','2023-10-20 16:35:33','2023-10-20 16:35:45',1),(7,'chaijin','123456','柴进','13309090007',1,1,4700.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2005-08-01','2023-10-20 16:35:33','2023-10-20 16:35:47',1),(8,'likui','123456','李逵','13309090008',1,1,4800.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2014-11-09','2023-10-20 16:35:33','2023-10-20 16:35:49',1),(9,'wusong','123456','武松','13309090009',1,1,4900.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-03-11','2023-10-20 16:35:33','2023-10-20 16:35:51',1),(10,'linchong','123456','林冲','13309090010',1,1,5000.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2013-09-05','2023-10-20 16:35:33','2023-10-20 16:35:53',1),(11,'huyanzhuo','123456','呼延灼','13309090011',1,2,9700.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-02-01','2023-10-20 16:35:33','2023-10-20 16:35:55',2),(12,'xiaoliguang','123456','小李广','13309090012',1,2,10000.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-08-18','2023-10-20 16:35:33','2023-10-20 16:35:57',2),(13,'yangzhi','123456','杨志','13309090013',1,1,5300.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-11-01','2023-10-20 16:35:33','2023-10-20 16:35:59',1),(14,'shijin','123456','史进','13309090014',1,2,10600.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2002-08-01','2023-10-20 16:35:33','2023-10-20 16:36:01',2),(15,'sunerniang','123456','孙二娘','13309090015',2,2,10900.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-05-01','2023-10-20 16:35:33','2023-10-20 16:36:03',2),(16,'luzhishen','123456','鲁智深','13309090016',1,2,9600.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2010-01-01','2023-10-20 16:35:33','2023-10-20 16:36:05',2),(17,'liying','12345678','李应','13309090017',1,1,5800.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-03-21','2023-10-20 16:35:33','2023-10-20 16:36:07',1),(18,'shiqian','123456','时迁','13309090018',1,2,10200.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01','2023-10-20 16:35:33','2023-10-20 16:36:09',2),(19,'gudasao','123456','顾大嫂','13309090019',2,2,10500.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-01-01','2023-10-20 16:35:33','2023-10-20 16:36:11',2),(20,'ruanxiaoer','123456','阮小二','13309090020',1,2,10800.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2018-01-01','2023-10-20 16:35:33','2023-10-20 16:36:13',2),(21,'ruanxiaowu','123456','阮小五','13309090021',1,5,5200.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01','2023-10-20 16:35:33','2023-10-20 16:36:15',3),(22,'ruanxiaoqi','123456','阮小七','13309090022',1,5,5500.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2016-01-01','2023-10-20 16:35:33','2023-10-20 16:36:17',3),(23,'ruanji','123456','阮籍','13309090023',1,5,5800.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-01-01','2023-10-20 16:35:33','2023-10-20 16:36:19',3),(24,'tongwei','123456','童威','13309090024',1,5,5000.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2006-01-01','2023-10-20 16:35:33','2023-10-20 16:36:21',3),(25,'tongmeng','123456','童猛','13309090025',1,5,4800.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2002-01-01','2023-10-20 16:35:33','2023-10-20 16:36:23',3),(26,'yanshun','123456','燕顺','13309090026',1,5,5400.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-01-01','2023-10-20 16:35:33','2023-11-08 22:12:46',3),(27,'lijun','123456','李俊','13309090027',1,4,6600.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2004-01-01','2023-10-20 16:35:33','2025-05-10 17:33:30',2),(28,'lizhong','123456','李忠','13309090028',1,5,5000.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-01-01','2023-10-20 16:35:33','2023-11-17 16:34:22',3),(30,'liyun','123456','李云','13309090030',1,NULL,NULL,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2020-03-01','2023-10-20 16:35:33','2023-10-20 16:36:31',NULL),(36,'linghuchong','123456','令狐冲','18809091212',1,2,6800.00,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2023-10-19','2023-10-20 20:44:54','2023-11-09 09:41:04',2),(37,'zzzzz','123456','张志洲','13300001111',1,2,7600.00,'','2025-03-03','2025-05-03 14:58:37','2025-05-03 14:58:41',1),(38,'ccccc','123456','陈晨臣','13300001111',1,2,7600.00,'','2025-03-03','2025-05-03 14:58:37','2025-05-03 14:58:41',2),(39,'yyyyy','123456','杨钰莹','13300001111',1,2,7600.00,'','2025-03-03','2025-05-03 14:58:37','2025-08-23 17:33:19',2),(40,'lianghaiyan','123456','梁海燕','13300001111',1,4,8500.00,'https://loremflickr.com/400/400?lock=6992607584580566','2024-11-11','2025-05-04 00:24:10','2025-05-04 00:24:10',1),(41,'miehaorang','123456','梅浩然','13011112222',2,2,7800.00,'https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/92b5e898-46d1-4e53-902d-d253b4822e77.png','2025-04-09','2025-05-04 00:35:00','2025-08-24 17:37:11',2),(44,'linpingzhi','123456','林平之','13300001111',1,3,8000.00,'https://loremflickr.com/400/400?lock=6992607584580566','2024-11-11','2025-05-04 00:36:10','2025-05-04 00:36:10',2);
/*!40000 ALTER TABLE `emp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_expr`
--

DROP TABLE IF EXISTS `emp_expr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp_expr` (
  `id` int NOT NULL AUTO_INCREMENT,
  `emp_id` int NOT NULL,
  `begin` date DEFAULT NULL,
  `end` date DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `job` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_expr`
--

LOCK TABLES `emp_expr` WRITE;
/*!40000 ALTER TABLE `emp_expr` DISABLE KEYS */;
INSERT INTO `emp_expr` VALUES (1,44,'1984-12-07','1950-09-14','in repr','aliquip commodo ad dolore aut'),(2,44,'1954-04-19','1986-06-08','Lorem non','cillum'),(13,41,'1969-09-11','1970-02-27','ex aliqua reprehe','aliqua'),(14,41,'1949-06-27','1979-08-11','adipisicing Ut sit e','consectetur ');
/*!40000 ALTER TABLE `emp_expr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_log`
--

DROP TABLE IF EXISTS `emp_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_log`
--

LOCK TABLES `emp_log` WRITE;
/*!40000 ALTER TABLE `emp_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `emp_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
  `user_id` int NOT NULL COMMENT '用户ID',
  `title` varchar(100) NOT NULL COMMENT '反馈标题',
  `content` text NOT NULL COMMENT '反馈内容',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `status` tinyint DEFAULT '0' COMMENT '状态：0-待处理，1-已处理，2-已忽略',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='意见反馈表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,2,'哈哈哈哈','哈哈哈哈哈哈哈哈哈哈哈','13599616155',0,'2025-08-24 17:34:01','2025-08-24 17:34:01'),(2,2,'哈哈哈哈','哈哈哈哈哈哈哈哈哈哈哈','13599616155',0,'2025-08-24 17:35:29','2025-08-24 17:35:29'),(3,2,'1111111','广告费苟富贵1','18123456978',0,'2025-08-24 17:36:00','2025-08-24 17:36:00'),(4,2,'1111111','广告费苟富贵1','18123456978',0,'2025-08-24 17:36:34','2025-08-24 17:36:34'),(5,2,'232323','1231231231','13566451235',0,'2025-08-24 17:36:46','2025-08-24 17:36:46');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge`
--

DROP TABLE IF EXISTS `knowledge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '推文编号',
  `author` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '知乎' COMMENT '作者',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主题',
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
  `publish_time` datetime COMMENT '发表时间',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图像',
  `update_time` datetime DEFAULT (now()) COMMENT '修改日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge`
--

LOCK TABLES `knowledge` WRITE;
/*!40000 ALTER TABLE `knowledge` DISABLE KEYS */;
INSERT INTO `knowledge` VALUES (1,'牛顿','高血压预防措施','高血压的预防措施主要包括有合理饮食、适当锻炼、减轻体重、积极治疗基础疾病等。\r\n\r\n1、合理饮食：\r\n\r\n高血压的发生和不良的饮食习惯有一定的关系，患者在日常生活中需要注意合理饮食，尽量以低脂、低盐、低糖的食物为主，多吃西兰花、西红柿、苹果、橙子、香蕉等食物，同时注意限制钠盐的摄入量，防止钠盐摄入过多，从而预防发生高血压。\r\n\r\n2、适当锻炼：\r\n\r\n在日常生活中还要多参加户外运动，比如做健身操、慢走、跑步、游泳、瑜伽等，通过适当锻炼能够提高机体的免疫力，达到预防高血压的目的。\r\n\r\n3、减轻体重：\r\n\r\n对于肥胖的人群，还要及时减轻自身的体重，防止身体过胖导致血压发生变化，应将自身的体重控制在合理的范围内，以此来预防高血压的发生。\r\n\r\n4、积极治疗基础疾病：\r\n\r\n患者需要积极治疗可能会诱发高血压的基础疾病，如肾小球肾炎、慢性肾盂肾炎等。\r\n\r\n除上述预防措施外，还需要保持积极乐观的心态，避免焦虑紧张，也有助于预防高血压。','2025-05-13 17:15:54','https://picx.zhimg.com/70/v2-6704ff006dbe185c85d69ddacc0374d5_1440w.avis?source=172ae18b&biz_tag=Post','2025-08-23 11:40:27'),(2,'合肥日报','高血压的主要原因有哪些','高血压的发病受到多因素、多机制的共同作用，包括遗传因素、生活习惯以及环境影响，总结如下：第一，遗传因素在高血压的发生中起着极其重要的作用。血管壁的弹性、肾脏调控盐分的能力、神经调控系统以及体内盐分平衡等可能受到基因的影响，即如家族中有人罹患高血压疾病，则其具有很大的遗传倾向，患者的血压水平也可能受到影响。研究显示，约有30%至50%的高血压病例与遗传有关。与此同时，高血压具有明显的家族聚集性。如果父母或其他近亲患有高血压，子女患病的风险会显著增加。研究发现，若父母中的一方患有高血压，子女患病风险会提高1.5至2倍；若双亲均患病，风险则会更高。第二，不良生活习惯，包括高盐饮食、缺乏运动、肥胖、酗酒与吸烟以及不规律生活。现代社会生活节奏快，患者也从而衍生许多不良习惯，这些习惯共同作用，便成为了高血压发生的重要原因。具体来说，人体如摄入过多的钠会致使血管内压力增加，促进钠水潴留，从而使得血压升高；而缺乏运动的患者大多久坐不动，这些都会导致体重增加、血管弹性减弱；有数据显示，超重和肥胖引发高血压的重要风险因素，因为脂肪堆积会致使血管阻力增加，进而诱发高血压；酒精和烟草均含有对身体不利的有害物质，这些物质会损伤血管内膜，促使血压升高；此外，如果作息不规律、压力大、情绪不稳等都容易引发血压波动。第三，年龄和性别。有统计称老年人罹患高血压的风险更高，高血压的发病率随着年龄增长而显著增加，血管弹性随着年龄增长而减少，因此导致血压逐渐升高。此外，还有数据显示中年男性较女性更易罹患高血压，但值得注意的是，绝经后的女性罹患高血压的风险更高，从统计数据来看，50岁以上的女性中，患高血压的比例迅速上升，甚至高于男性。而绝经后女性常伴随着脂质代谢异常、体重增加等相关问题，绝经后的雌激素水平下降，血管保护作用减弱，其罹患高血压的风险进一步增加。第四，环境因素。随着现代科技的发展，环境遭受破坏，出现了一系列环境问题，如空气污染等也可能会增加高血压的发病风险。最后，其他健康因素。现代人的健康问题多发，如慢性肾病、内分泌疾病等均可能会使部分器官在调节血压中的作用受到影响，进而容易引起高血压。','2025-05-16 17:17:42','https://pic2.zhimg.com/v2-941188e12b4b407bcfeda70b547ecd7f_1440w.jpg','2025-08-23 11:40:30'),(3,'威猛课堂','高血压的治疗与饮食营养食谱','一、高血压食疗调理方法\r\n适当摄入低脂肪、优质蛋白质食物。\r\n每日脂肪的摄入不超过50克在限量范围内选择富含不饱和脂肪酸的油脂和肉类，它们可能会减少动脉硬化的发生，对增加微血管弹性、预防血管破裂、防止高血压并发症有一定作用。\r\n\r\n薯类食物。如淀粉、面粉、米、红薯等，少进食含单糖和双糖类食物，如蔗糖、果糖等，以防止血脂增高。\r\n\r\n2. 饮食要清淡，不宜太成。\r\n\r\n人体摄入食盐(主要成分是氯化钠)量过多会造成体内水和钠潴留，从而导致血管管腔变细、血管阻力增加，使血压上升；增加肾脏负担，造成排钠障碍使血压升高；可能对抗降压药物而影响降压效果。\r\n\r\n3. 高钙饮食。\r\n\r\n高钙饮食是防治高血压的有效措施之一。钙具有强大的“除钠”作用，从而使血压维持稳定。所以，解决高血压的关键不是限钠、限镁或其他矿物质，而是补钙。\r\n\r\n因此，健康人群或高血压患者，都应多吃些含钙丰富的食物，如牛奶、豆类、鱼虾、芝麻等。\r\n\r\n4. 多吃含钾丰富的食物。\r\n\r\n高血压的典型特征是动脉壁增厚，但当给予足量的钾后，动脉壁便不再增厚。这主要是钾对血管有保护作用，可防止动脉壁不受血压的机械性损伤，从而降低了高血压病人中风的发病率。\r\n\r\n含钾丰富的食品主要有瘦肉、牛肉、鱼类及其他海产品、小白菜、油菜黄瓜、西红柿、土豆、橘子、香蕉、桃、葡萄干等蔬菜与水果。','2025-05-16 17:17:42','https://picx.zhimg.com/v2-040720c2936e7e19fc6daeb4a6eab52d_1440w.jpg','2025-08-23 11:40:33'),(4,'知乎','按摩这些穴位控制血压','详细的按摩穴位说明...','2025-05-16 09:59:42','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/e96d856c-117e-49bc-b291-4706e222ec46.png','2025-08-24 10:28:53'),(5,'知乎','高血压治疗，降血压的食疗推荐','1、洋葱炖肉可防动脉硬化洋葱150克，瘦肉50克，酱油、油、盐、味精适量等洋葱切片，肉切丝、放油、炒炒、洋葱、调料。2、海带绿豆汤可治疗高血压海带150克，绿豆、洗净，切碎各150克等每日2次。','2024-01-12 14:15:00','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/ebc91f8c-96f6-488d-ac51-838593441e57.png','2025-08-24 10:28:40'),(6,'知乎','高血压怎么测血压？资血压的症状是什么','血压测量指南...','2024-01-10 11:25:00','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/d5c951f3-7554-4c3f-a5a9-83d8aaba5c01.png','2025-08-24 10:28:20'),(7,'吱吱吱','中医按摩','中医按摩啊，要不要试试呢111','2025-08-24 02:33:58','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/6872e52a-a5a7-4010-a0f8-68cf60524d01.png','2025-08-24 14:41:26'),(8,'牛客','程序员加班有多严重','程序员加班有多严重。。。。。。。。现在市场大环境太差了，程序员下完班还得卷才能跟上时代节奏','2025-08-24 14:40:29','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/9d53a9f1-8bf0-4919-a2c6-4ed8854524a9.png','2025-08-24 14:41:19');
/*!40000 ALTER TABLE `knowledge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operate_log`
--

DROP TABLE IF EXISTS `operate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operate_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_emp_id` int unsigned DEFAULT NULL COMMENT '操作人ID',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作的类名',
  `method_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作的方法名',
  `method_params` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '方法参数',
  `return_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '返回值, 存储json格式',
  `cost_time` int DEFAULT NULL COMMENT '方法执行耗时, 单位:ms',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operate_log`
--

LOCK TABLES `operate_log` WRITE;
/*!40000 ALTER TABLE `operate_log` DISABLE KEYS */;
INSERT INTO `operate_log` VALUES (1,2,'2025-05-10 15:53:16','com.itheima.controller.ClazzController','update','[Clazz(id=5, name=大数据科学与大数据技术1班, room=209, beginDate=2024-08-01, endDate=2024-02-15, masterId=8, subject=3, createTime=2024-06-01T17:51:21, updateTime=2025-05-10T15:53:15.813758400, masterName=null, status=null)]','Result(code=1, msg=success, data=null)',52),(2,2,'2025-05-10 15:54:41','com.itheima.controller.ClazzController','save','[Clazz(id=null, name=物联网工程1班, room=305, beginDate=2021-05-31, endDate=2026-05-07, masterId=15, subject=6, createTime=2025-05-10T15:54:41.015172700, updateTime=2025-05-10T15:54:41.015172700, masterName=null, status=null)]','Result(code=1, msg=success, data=null)',3),(3,2,'2025-05-10 21:03:02','com.itheima.controller.ClazzController','save','[Clazz(id=null, name=计科2班, room=L1-303, beginDate=2025-05-08, endDate=2025-12-31, masterId=44, subject=1, createTime=2025-05-10T21:03:01.648396200, updateTime=2025-05-10T21:03:01.648396200, masterName=null, status=null)]','Result(code=1, msg=success, data=null)',3),(4,2,'2025-05-10 21:03:12','com.itheima.controller.ClazzController','update','[Clazz(id=3, name=软工1班, room=108, beginDate=2024-06-15, endDate=2024-12-25, masterId=6, subject=3, createTime=2024-06-01T17:45:40, updateTime=2025-05-10T21:03:11.695459700, masterName=null, status=null)]','Result(code=1, msg=success, data=null)',3),(5,2,'2025-05-10 21:03:18','com.itheima.controller.ClazzController','update','[Clazz(id=4, name=人智1班, room=105, beginDate=2024-07-20, endDate=2024-02-20, masterId=20, subject=4, createTime=2024-06-01T17:46:10, updateTime=2025-05-10T21:03:18.312988600, masterName=null, status=null)]','Result(code=1, msg=success, data=null)',5),(6,2,'2025-05-10 21:03:25','com.itheima.controller.ClazzController','update','[Clazz(id=6, name=网工1班, room=325, beginDate=2024-11-20, endDate=2024-05-10, masterId=36, subject=5, createTime=2024-11-15T11:35:46, updateTime=2025-05-10T21:03:25.184016800, masterName=null, status=null)]','Result(code=1, msg=success, data=null)',4),(7,2,'2025-05-10 21:03:34','com.itheima.controller.ClazzController','update','[Clazz(id=8, name=计科2班, room=L1-303, beginDate=2025-05-08, endDate=2025-12-31, masterId=44, subject=5, createTime=2025-05-10T21:03:02, updateTime=2025-05-10T21:03:33.886496200, masterName=null, status=null)]','Result(code=1, msg=success, data=null)',4);
/*!40000 ALTER TABLE `operate_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserve`
--

DROP TABLE IF EXISTS `reserve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserve` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '预约编号',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `d_id` int DEFAULT NULL COMMENT '医生id\r\n',
  `appoint_time` datetime DEFAULT NULL COMMENT '预约时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '待就诊' COMMENT '状态（待就诊，已完成）',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '不适症状',
  `diet_advice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '医生建议',
  `exercise_advice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `medication_advice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserve`
--

LOCK TABLES `reserve` WRITE;
/*!40000 ALTER TABLE `reserve` DISABLE KEYS */;
INSERT INTO `reserve` VALUES (1,3,1,'2025-05-14 19:29:52','待就诊','血压高，心烦意乱，睡不着',NULL,NULL,NULL,'2025-08-24 13:18:46'),(2,3,1,'2025-05-15 19:39:26','待就诊','血压高，心烦意乱，胸闷，呼气不顺','','','','2025-08-24 14:27:10'),(3,3,1,'2025-05-14 19:29:52','已就诊','胸闷，呼吸急促','控制饮食：清淡饮食，少油少盐，多食用新鲜蔬菜水果。','规律运动：每天坚持适度运动，以散步为主，避免剧烈运动。','按时服药，遵医嘱用药。服药期间如有不适及时就医','2025-08-24 13:18:46'),(4,3,1,'2025-05-14 19:29:52','已就诊','头疼，头昏目眩，精神疲惫','控制饮食：清淡饮食，少油少盐，多食用新鲜蔬菜水果。','规律运动：每天坚持适度运动，以散步为主，避免剧烈运动。','按时服药，遵医嘱用药。服药期间如有不适及时就医','2025-08-24 13:18:46'),(5,3,3,'2025-06-15 10:00:00','已就诊','感冒不舒服，咳嗽，嗓子眼','清淡饮食，多喝粥111','每天运动半小时111','按时吃药，遵循医嘱111','2025-08-24 13:18:46'),(6,3,3,'2025-07-14 09:00:00','待就诊','皮肤痤疮，丘疹半年，红疹子多伴有瘙痒',NULL,NULL,NULL,'2025-08-24 13:18:46'),(7,3,5,'2025-05-14 09:00:00','待就诊','皮肤痤疮，丘疹半年，红疹子多伴有瘙痒',NULL,NULL,NULL,'2025-08-24 13:18:46'),(8,3,1,'2025-08-16 09:00:00','待就诊','感冒发烧了很不舒服','',NULL,NULL,'2025-08-24 13:18:46'),(9,2,1,'2025-08-24 14:27:10','待就诊','头晕目眩肚子疼',NULL,NULL,NULL,'2025-08-24 14:27:18'),(10,5,1,'2025-08-24 14:33:12','待就诊','头晕目眩',NULL,NULL,NULL,'2025-08-24 14:33:27');
/*!40000 ALTER TABLE `reserve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `name` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '姓名',
  `no` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学号',
  `gender` tinyint unsigned NOT NULL COMMENT '性别, 1: 男, 2: 女',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '手机号',
  `id_card` char(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '身份证号',
  `is_college` tinyint unsigned NOT NULL COMMENT '是否来自于院校, 1:是, 0:否',
  `address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '联系地址',
  `degree` tinyint unsigned DEFAULT NULL COMMENT '最高学历, 1:初中, 2:高中, 3:大专, 4:本科, 5:硕士, 6:博士',
  `graduation_date` date DEFAULT NULL COMMENT '毕业时间',
  `clazz_id` int unsigned NOT NULL COMMENT '班级ID, 关联班级表ID',
  `violation_count` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '违纪次数',
  `violation_score` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '违纪扣分',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `no` (`no`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `id_card` (`id_card`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='学员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'段誉','2022000001',1,'18800000001','110120000300200001',1,'北京市昌平区建材城西路1号',1,'2021-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-15 16:20:59'),(2,'萧峰','2022000002',1,'18800210003','110120000300200002',1,'北京市昌平区建材城西路2号',2,'2022-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(3,'虚竹','2022000003',1,'18800013001','110120000300200003',1,'北京市昌平区建材城西路3号',2,'2024-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(4,'萧远山','2022000004',1,'18800003211','110120000300200004',0,'北京市昌平区建材城西路4号',5,'2024-07-01',1,2,15,'2024-11-14 21:22:19','2025-05-10 18:44:14'),(5,'阿朱','2022000005',2,'18800160002','110120000300200005',1,'北京市昌平区建材城西路5号',4,'2020-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(6,'阿紫','2022000006',2,'18800000034','110120000300200006',1,'北京市昌平区建材城西路6号',4,'2021-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(7,'游坦之','2022000007',1,'18800000067','110120000300200007',1,'北京市昌平区建材城西路7号',4,'2022-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(8,'康敏','2022000008',2,'18800000077','110120000300200008',1,'北京市昌平区建材城西路8号',5,'2024-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(9,'徐长老','2022000009',1,'18800000341','110120000300200009',1,'北京市昌平区建材城西路9号',3,'2024-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(10,'云中鹤','2022000010',1,'18800006571','110120000300200010',1,'北京市昌平区建材城西路10号',2,'2020-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(11,'钟万仇','2022000011',1,'18800000391','110120000300200011',1,'北京市昌平区建材城西路11号',4,'2021-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-15 16:21:24'),(12,'崔百泉','2022000012',1,'18800000781','110120000300200018',1,'北京市昌平区建材城西路12号',4,'2022-07-05',3,6,17,'2024-11-14 21:22:19','2025-05-10 16:53:53'),(13,'耶律洪基','2022000013',1,'18800008901','110120000300200013',1,'北京市昌平区建材城西路13号',4,'2024-07-01',2,0,0,'2024-11-14 21:22:19','2024-11-15 16:21:21'),(14,'天山童姥','2022000014',2,'18800009201','110120000300200014',1,'北京市昌平区建材城西路14号',4,'2024-07-01',1,0,0,'2024-11-14 21:22:19','2024-11-15 16:21:17'),(15,'刘竹庄','2022000015',1,'18800009401','110120000300200015',1,'北京市昌平区建材城西路15号',3,'2020-07-01',4,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(16,'李春来','2022000016',1,'18800008501','110120000300200016',1,'北京市昌平区建材城西路16号',4,'2021-07-01',4,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(17,'王语嫣','2022000017',2,'18800007601','110120000300200017',1,'北京市昌平区建材城西路17号',2,'2022-07-01',4,0,0,'2024-11-14 21:22:19','2024-11-14 21:22:19'),(18,'郑成功','2024001101',1,'13309092345','110110110110110110',0,'北京市昌平区回龙观街道88号',5,'2021-07-01',3,1,10,'2024-11-15 16:26:18','2025-05-10 18:44:05'),(19,'华盛顿','2024000001',1,'13011113333','350823200101016710',1,'福建省龙岩市',5,'2025-05-27',7,0,0,'2025-05-10 20:45:37','2025-05-10 20:45:37'),(20,'陈昌海','2024000002',1,'13099991111','355011200209090011',1,'福建省福州市',4,'2025-05-31',6,0,0,'2025-05-10 20:49:51','2025-05-10 20:49:51'),(21,'甲润六','2024000003',1,'18011112222','30110019990909781X',1,'广东省广州市',3,'2025-05-27',5,0,0,'2025-05-10 20:51:19','2025-05-10 20:51:19'),(22,'刘海燕','2024000004',2,'13011154747','280523200003036444',0,'浙江省杭州市',2,'2025-05-16',5,0,0,'2025-05-10 20:52:40','2025-05-10 20:52:40');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_healthyinfo`
--

DROP TABLE IF EXISTS `user_healthyinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_healthyinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int DEFAULT NULL COMMENT '用户id',
  `sbp` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收缩压',
  `dbp` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '舒张压',
  `heart` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '心率',
  `write_time` datetime DEFAULT NULL COMMENT '记录时间',
  `writetype` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '测量方式：家庭血压和诊室血压',
  `situation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '房颤：正常和异常',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_healthyinfo`
--

LOCK TABLES `user_healthyinfo` WRITE;
/*!40000 ALTER TABLE `user_healthyinfo` DISABLE KEYS */;
INSERT INTO `user_healthyinfo` VALUES (1,3,'120','78','60','2025-05-13 12:10:20','家庭血压','正常','2025-05-15 10:44:58'),(2,3,'130','82','65','2025-05-15 12:10:20','家庭血压','正常','2025-05-14 22:02:41'),(3,3,'125','71','65','2025-05-16 12:10:20','诊室血压','正常','2025-05-15 10:45:07'),(4,3,'133','67','70','2025-05-14 21:47:01','家庭血压','正常','2025-05-15 10:45:14'),(5,1,'123','67','70','2025-05-14 21:47:01','家庭血压','正常','2025-08-17 18:42:03'),(6,3,'135','69','65','2025-05-15 10:42:52','家庭血压','正常','2025-08-17 14:06:40'),(7,3,'110','61','56','2025-05-15 10:56:55','家庭血压','正常','2025-08-17 14:06:38'),(8,3,'92','60','70','2025-08-17 15:17:40','家庭血压','正常','2025-08-17 15:39:34'),(9,3,'133','69','76','2025-08-23 10:16:27','家庭血压','正常','2025-08-24 12:51:44'),(10,5,'142','75','75','2025-08-24 14:15:26','家庭血压','正常','2025-08-24 14:16:32');
/*!40000 ALTER TABLE `user_healthyinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信openid',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信昵称',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '123456' COMMENT '密码',
  `userid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `name` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '真实姓名',
  `sex` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '性别',
  `birthdate` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,NULL,NULL,'123456','350011199701016711','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/b9866466-fe39-4574-bb03-afc1d27c20a9.png','zz','小猪','男','2025-05-12','18156891111','2025-08-19 10:59:24','2025-08-24 14:43:42'),(2,NULL,NULL,'123456','350011199701016711','https://www.keaitupian.cn/cjpic/frombd/2/253/1985490445/2234330262.jpg','songjiang','成忠','女','2022-05-14','18156891111','2025-08-16 10:59:28','2025-08-24 10:59:16'),(3,'olBYd5WjjYMh5I_mQqfvq4CoFc24','WeChatUser','123456','350011199701016711','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/71057bcb-594f-4e2e-910d-a41399f1cb0d.png','ccc','陈琛','男','2024-07-06','18156891111','2025-06-26 10:59:33','2025-08-24 19:47:58'),(4,NULL,NULL,'123456','350011199701016711','https://www.keaitupian.cn/cjpic/frombd/2/253/1985490445/2234330262.jpg','ccx','晨晨星','女','2024-09-06','18156891111','2025-05-24 10:59:42','2025-08-24 10:59:16'),(5,NULL,NULL,'123456','350011199701016711','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/12b9f55f-abed-475d-9faf-079c628ff71f.png','yyy','杨瑶瑶','女','2018-06-17','13566458989','2025-07-24 10:59:52','2025-08-24 12:22:21'),(6,NULL,NULL,'123456','350020200606170102','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/d3a2a3ae-34f0-4a11-a7ef-a4ba5b16accd.png','11','花猫','男','2025-08-17','13255898922','2025-08-24 14:10:14','2025-08-24 14:43:50'),(7,NULL,NULL,'123456','350102200602015645','https://java-webai-1.oss-cn-beijing.aliyuncs.com/2025/08/13f74468-903c-447f-aa20-7b48669c19f9.png','zpb','猪扒包','男','2020-08-01','18156892344','2025-08-24 14:44:43','2025-08-24 14:44:42');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-30 17:50:04
