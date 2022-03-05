-- MySQL dump 10.13  Distrib 8.0.23, for osx10.16 (x86_64)
-- Author:Dongchen Yao
-- Host: localhost    Database: takeout
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary view structure for view `collect_store`
--

DROP TABLE IF EXISTS `collect_store`;
/*!50001 DROP VIEW IF EXISTS `collect_store`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8 */;
/*!50001 CREATE VIEW `collect_store` AS SELECT 
 1 AS `collectid`,
 1 AS `userid`,
 1 AS `storeid`,
 1 AS `storename`,
 1 AS `storeinfo`,
 1 AS `storeimage`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `store_order`
--

DROP TABLE IF EXISTS `store_order`;
/*!50001 DROP VIEW IF EXISTS `store_order`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8 */;
/*!50001 CREATE VIEW `store_order` AS SELECT 
 1 AS `storeuserid`,
 1 AS `orderid`,
 1 AS `userid`,
 1 AS `price`,
 1 AS `address`,
 1 AS `createdtime`,
 1 AS `updatedtime`,
 1 AS `status`,
 1 AS `foods`,
 1 AS `storeid`,
 1 AS `sendtime`,
 1 AS `deliveryinfo`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `storefoods`
--

DROP TABLE IF EXISTS `storefoods`;
/*!50001 DROP VIEW IF EXISTS `storefoods`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8 */;
/*!50001 CREATE VIEW `storefoods` AS SELECT 
 1 AS `stroeid`,
 1 AS `userid`,
 1 AS `storename`,
 1 AS `storeinfo`,
 1 AS `storeimage`,
 1 AS `foodtypeid`,
 1 AS `typename`,
 1 AS `foodid`,
 1 AS `foodname`,
 1 AS `foodinfo`,
 1 AS `foodprice`,
 1 AS `foodstock`,
 1 AS `foodimage`,
 1 AS `foodnum`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `take_address`
--

DROP TABLE IF EXISTS `take_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `take_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `createdtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `phone` varchar(255) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `take_address_1` (`userid`),
  CONSTRAINT `take_address_1` FOREIGN KEY (`userid`) REFERENCES `take_userinfo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `take_address`
--

LOCK TABLES `take_address` WRITE;
/*!40000 ALTER TABLE `take_address` DISABLE KEYS */;
INSERT INTO `take_address` VALUES (1,5,'123','2021-05-08 01:35:45','2021-05-08 01:35:45','123','123'),(3,5,'222','2021-05-08 01:36:18','2021-05-08 01:36:18','222','222'),(9,7,'22','2021-05-08 16:07:36','2021-05-08 16:07:36','22','22');
/*!40000 ALTER TABLE `take_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `take_collect`
--

DROP TABLE IF EXISTS `take_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `take_collect` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `storeid` int NOT NULL,
  `createdtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `take_collect_1` (`userid`),
  KEY `take_collect_2` (`storeid`),
  CONSTRAINT `take_collect_1` FOREIGN KEY (`userid`) REFERENCES `take_userinfo` (`id`) ON DELETE CASCADE,
  CONSTRAINT `take_collect_2` FOREIGN KEY (`storeid`) REFERENCES `take_store` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `take_collect`
--

LOCK TABLES `take_collect` WRITE;
/*!40000 ALTER TABLE `take_collect` DISABLE KEYS */;
/*!40000 ALTER TABLE `take_collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `take_comment`
--

DROP TABLE IF EXISTS `take_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `take_comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `storeid` int NOT NULL,
  `orderid` int NOT NULL,
  `comment` varchar(255) NOT NULL,
  `createdtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `take_comment_1` (`userid`),
  KEY `take_comment_2` (`storeid`),
  KEY `take_comment_3` (`orderid`),
  CONSTRAINT `take_comment_1` FOREIGN KEY (`userid`) REFERENCES `take_userinfo` (`id`) ON DELETE CASCADE,
  CONSTRAINT `take_comment_2` FOREIGN KEY (`storeid`) REFERENCES `take_store` (`id`) ON DELETE CASCADE,
  CONSTRAINT `take_comment_3` FOREIGN KEY (`orderid`) REFERENCES `take_order` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `take_comment`
--

LOCK TABLES `take_comment` WRITE;
/*!40000 ALTER TABLE `take_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `take_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `take_delivery`
--

DROP TABLE IF EXISTS `take_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `take_delivery` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `createdtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `delivery_1` (`userid`),
  CONSTRAINT `delivery_1` FOREIGN KEY (`userid`) REFERENCES `take_userinfo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `take_delivery`
--

LOCK TABLES `take_delivery` WRITE;
/*!40000 ALTER TABLE `take_delivery` DISABLE KEYS */;
INSERT INTO `take_delivery` VALUES (1,8,'2021-05-24 13:07:51'),(3,7,'2021-05-25 01:19:38');
/*!40000 ALTER TABLE `take_delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `take_foods`
--

DROP TABLE IF EXISTS `take_foods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `take_foods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `foodtypeid` int NOT NULL,
  `foodname` varchar(255) NOT NULL,
  `foodinfo` varchar(255) NOT NULL,
  `foodprice` varchar(255) NOT NULL,
  `foodstock` varchar(255) NOT NULL,
  `createdtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `foodimage` text NOT NULL,
  `foodnum` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `take_foods_1` (`foodtypeid`),
  CONSTRAINT `take_foods_1` FOREIGN KEY (`foodtypeid`) REFERENCES `take_foodtype` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `take_foods`
--

LOCK TABLES `take_foods` WRITE;
/*!40000 ALTER TABLE `take_foods` DISABLE KEYS */;
INSERT INTO `take_foods` VALUES (28,6,'111','111','2','-1','2021-05-24 14:14:56','2021-05-25 01:38:01','https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1095903005,1370184727&fm=26&gp=0.jpg',3),(29,6,'奶茶1','不知道','13','-1','2021-05-25 06:12:54','2021-05-25 06:12:54','http://bmob.zhx.notbucai.com/2021/05/25/8d3cf768cb784c0caa226bd1b27a7c9e.png',15);
/*!40000 ALTER TABLE `take_foods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `take_foodtype`
--

DROP TABLE IF EXISTS `take_foodtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `take_foodtype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `storeid` int NOT NULL,
  `typename` varchar(255) NOT NULL,
  `createdtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `take_foodtype_1` (`storeid`),
  CONSTRAINT `take_foodtype_1` FOREIGN KEY (`storeid`) REFERENCES `take_store` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `take_foodtype`
--

LOCK TABLES `take_foodtype` WRITE;
/*!40000 ALTER TABLE `take_foodtype` DISABLE KEYS */;
INSERT INTO `take_foodtype` VALUES (6,2,'222','2021-05-14 07:31:29','2021-05-14 07:31:29');
/*!40000 ALTER TABLE `take_foodtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `take_order`
--

DROP TABLE IF EXISTS `take_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `take_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `price` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(255) NOT NULL,
  `createdtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(50) DEFAULT '待收货',
  `foods` text NOT NULL,
  `storeid` int NOT NULL,
  `sendtime` varchar(255) DEFAULT NULL,
  `deliveryinfo` text,
  PRIMARY KEY (`id`),
  KEY `order1` (`userid`),
  KEY `order2` (`storeid`),
  CONSTRAINT `order1` FOREIGN KEY (`userid`) REFERENCES `take_userinfo` (`id`) ON DELETE CASCADE,
  CONSTRAINT `order2` FOREIGN KEY (`storeid`) REFERENCES `take_store` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `take_order`
--

LOCK TABLES `take_order` WRITE;
/*!40000 ALTER TABLE `take_order` DISABLE KEYS */;
INSERT INTO `take_order` VALUES (12,7,'1110.0','22\n电话:22\n地址:22','2021-05-24 13:39:27','2021-05-25 00:46:37','待评价','[{\"id\":26,\"num\":10,\"takeFoods\":{\"foodimage\":\"https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d3444508248,1197396609\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"foodinfo\":\"111\",\"foodname\":\"111\",\"foodnum\":10,\"foodprice\":\"111\",\"id\":26}}]',2,NULL,NULL),(13,7,'111.0','22\n电话:22\n地址:22','2021-05-24 14:08:53','2021-05-25 00:51:22','待评价','[{\"id\":26,\"num\":1,\"takeFoods\":{\"foodimage\":\"https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d3444508248,1197396609\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"foodinfo\":\"111\",\"foodname\":\"111\",\"foodnum\":10,\"foodprice\":\"111\",\"id\":26}}]',2,NULL,'{\"account\":\"15712341234\",\"birthday\":\"天地同寿\",\"id\":8,\"password\":\"******\",\"portrait\":\"https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u\\u003d1026161159,221580774\\u0026fm\\u003d85\\u0026app\\u003d79\\u0026f\\u003dJPG?w\\u003d121\\u0026h\\u003d75\\u0026s\\u003d34B5773693706C23065C807602000073\",\"sex\":\"外星人\",\"username\":\"系统默认配送员\"}'),(14,7,'2.0','22\n电话:22\n地址:22','2021-05-24 14:13:37','2021-05-25 00:46:46','待评价','[{\"id\":27,\"num\":1,\"takeFoods\":{\"foodimage\":\"https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d1095903005,1370184727\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"foodinfo\":\"111\",\"foodname\":\"111\",\"foodnum\":10,\"foodprice\":\"2\",\"id\":27}}]',2,'2021-05-24 23:13:32','{\"account\":\"15712341234\",\"birthday\":\"天地同寿\",\"id\":8,\"password\":\"******\",\"portrait\":\"https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u\\u003d1026161159,221580774\\u0026fm\\u003d85\\u0026app\\u003d79\\u0026f\\u003dJPG?w\\u003d121\\u0026h\\u003d75\\u0026s\\u003d34B5773693706C23065C807602000073\",\"sex\":\"外星人\",\"username\":\"系统默认配送员\"}'),(15,7,'2.0','22\n电话:22\n地址:22','2021-05-24 14:15:17','2021-05-24 14:15:17','待收货','[{\"id\":28,\"num\":1,\"takeFoods\":{\"foodimage\":\"https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d1095903005,1370184727\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"foodinfo\":\"111\",\"foodname\":\"111\",\"foodnum\":10,\"foodprice\":\"2\",\"id\":28}}]',2,'2021-05-24 23:15:13','{\"account\":\"15712341234\",\"birthday\":\"天地同寿\",\"id\":8,\"password\":\"******\",\"portrait\":\"https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u\\u003d1026161159,221580774\\u0026fm\\u003d85\\u0026app\\u003d79\\u0026f\\u003dJPG?w\\u003d121\\u0026h\\u003d75\\u0026s\\u003d34B5773693706C23065C807602000073\",\"sex\":\"外星人\",\"username\":\"系统默认配送员\"}'),(16,7,'4.0','22\n电话:22\n地址:22','2021-05-24 14:26:15','2021-05-25 00:52:44','待评价','[{\"id\":28,\"num\":2,\"takeFoods\":{\"foodimage\":\"https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d1095903005,1370184727\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"foodinfo\":\"111\",\"foodname\":\"111\",\"foodnum\":9,\"foodprice\":\"2\",\"id\":28}}]',2,'2021-05-24 23:26:09','{\"account\":\"15712341234\",\"birthday\":\"天地同寿\",\"id\":8,\"password\":\"******\",\"portrait\":\"https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u\\u003d1026161159,221580774\\u0026fm\\u003d85\\u0026app\\u003d79\\u0026f\\u003dJPG?w\\u003d121\\u0026h\\u003d75\\u0026s\\u003d34B5773693706C23065C807602000073\",\"sex\":\"外星人\",\"username\":\"系统默认配送员\"}'),(17,7,'4.0','22\n电话:22\n地址:22','2021-05-25 00:45:10','2021-05-25 00:54:14','待评价','[{\"id\":28,\"num\":2,\"takeFoods\":{\"foodimage\":\"https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d1095903005,1370184727\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"foodinfo\":\"111\",\"foodname\":\"111\",\"foodnum\":7,\"foodprice\":\"2\",\"id\":28}}]',2,'2021-05-25 09:45:04','{\"account\":\"22212341234\",\"birthday\":\"天地同寿\",\"id\":7,\"password\":\"123\",\"portrait\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d2915828601,496412029\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"sex\":\"外星人\",\"username\":\"222\"}'),(18,7,'2.0','22\n电话:22\n地址:22','2021-05-25 00:45:56','2021-05-25 00:45:56','待收货','[{\"id\":28,\"num\":1,\"takeFoods\":{\"foodimage\":\"https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d1095903005,1370184727\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"foodinfo\":\"111\",\"foodname\":\"111\",\"foodnum\":5,\"foodprice\":\"2\",\"id\":28}}]',2,'2021-05-25 09:45:49','{\"account\":\"22212341234\",\"birthday\":\"天地同寿\",\"id\":7,\"password\":\"123\",\"portrait\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d2915828601,496412029\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"sex\":\"外星人\",\"username\":\"222\"}'),(19,7,'2.0','22\n电话:22\n地址:22','2021-05-25 01:38:01','2021-05-25 01:38:01','待收货','[{\"id\":28,\"num\":1,\"takeFoods\":{\"foodimage\":\"https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d1095903005,1370184727\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"foodinfo\":\"111\",\"foodname\":\"111\",\"foodnum\":4,\"foodprice\":\"2\",\"id\":28}}]',2,'2021-05-25 10:37:56','{\"account\":\"22212341234\",\"birthday\":\"天地同寿\",\"id\":7,\"password\":\"123\",\"portrait\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u\\u003d2915828601,496412029\\u0026fm\\u003d26\\u0026gp\\u003d0.jpg\",\"sex\":\"外星人\",\"username\":\"222\"}');
/*!40000 ALTER TABLE `take_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `take_store`
--

DROP TABLE IF EXISTS `take_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `take_store` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `storename` varchar(255) NOT NULL,
  `storeinfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `storeimage` text NOT NULL,
  `createdtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `take_store_1` (`userid`),
  CONSTRAINT `take_store_1` FOREIGN KEY (`userid`) REFERENCES `take_userinfo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `take_store`
--

LOCK TABLES `take_store` WRITE;
/*!40000 ALTER TABLE `take_store` DISABLE KEYS */;
INSERT INTO `take_store` VALUES (2,7,'test2','test2','https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2471826223,1654648024&fm=26&gp=0.jpg','2021-05-14 07:30:19','2021-05-14 07:30:19');
/*!40000 ALTER TABLE `take_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `take_userinfo`
--

DROP TABLE IF EXISTS `take_userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `take_userinfo` (
  `username` varchar(255) NOT NULL,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `portrait` text NOT NULL,
  `createdtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sex` varchar(10) DEFAULT '外星人',
  `birthday` varchar(100) DEFAULT '天地同寿',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `take_userinfo`
--

LOCK TABLES `take_userinfo` WRITE;
/*!40000 ALTER TABLE `take_userinfo` DISABLE KEYS */;
INSERT INTO `take_userinfo` VALUES ('111','12312341234','123',5,'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2915828601,496412029&fm=26&gp=0.jpg','2021-05-07 21:57:29','2021-05-14 07:03:15','外星人','天地同寿'),('222','22212341234','123',7,'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2915828601,496412029&fm=26&gp=0.jpg','2021-05-08 16:07:25','2021-05-08 16:07:25','外星人','天地同寿'),('系统默认配送员','15712341234','******',8,'https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u=1026161159,221580774&fm=85&app=79&f=JPG?w=121&h=75&s=34B5773693706C23065C807602000073','2021-05-24 13:07:37','2021-05-24 13:07:37','外星人','天地同寿');
/*!40000 ALTER TABLE `take_userinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `userinfo_comment`
--

DROP TABLE IF EXISTS `userinfo_comment`;
/*!50001 DROP VIEW IF EXISTS `userinfo_comment`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8 */;
/*!50001 CREATE VIEW `userinfo_comment` AS SELECT 
 1 AS `username`,
 1 AS `account`,
 1 AS `portrait`,
 1 AS `userid`,
 1 AS `storeid`,
 1 AS `orderid`,
 1 AS `comment`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `collect_store`
--

/*!50001 DROP VIEW IF EXISTS `collect_store`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `collect_store` AS select `a`.`id` AS `collectid`,`a`.`userid` AS `userid`,`b`.`id` AS `storeid`,`b`.`storename` AS `storename`,`b`.`storeinfo` AS `storeinfo`,`b`.`storeimage` AS `storeimage` from (`take_collect` `a` join `take_store` `b`) where (`a`.`storeid` = `b`.`id`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `store_order`
--

/*!50001 DROP VIEW IF EXISTS `store_order`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `store_order` AS select `a`.`userid` AS `storeuserid`,`b`.`id` AS `orderid`,`b`.`userid` AS `userid`,`b`.`price` AS `price`,`b`.`address` AS `address`,`b`.`createdtime` AS `createdtime`,`b`.`updatedtime` AS `updatedtime`,`b`.`status` AS `status`,`b`.`foods` AS `foods`,`b`.`storeid` AS `storeid`,`b`.`sendtime` AS `sendtime`,`b`.`deliveryinfo` AS `deliveryinfo` from (`take_store` `a` join `take_order` `b`) where (`a`.`id` = `b`.`storeid`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `storefoods`
--

/*!50001 DROP VIEW IF EXISTS `storefoods`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `storefoods` AS select `a`.`id` AS `stroeid`,`a`.`userid` AS `userid`,`a`.`storename` AS `storename`,`a`.`storeinfo` AS `storeinfo`,`a`.`storeimage` AS `storeimage`,`b`.`id` AS `foodtypeid`,`b`.`typename` AS `typename`,`c`.`id` AS `foodid`,`c`.`foodname` AS `foodname`,`c`.`foodinfo` AS `foodinfo`,`c`.`foodprice` AS `foodprice`,`c`.`foodstock` AS `foodstock`,`c`.`foodimage` AS `foodimage`,`c`.`foodnum` AS `foodnum` from ((`take_store` `a` join `take_foodtype` `b`) join `take_foods` `c`) where ((`a`.`id` = `b`.`storeid`) and (`b`.`id` = `c`.`foodtypeid`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `userinfo_comment`
--

/*!50001 DROP VIEW IF EXISTS `userinfo_comment`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `userinfo_comment` AS select `a`.`username` AS `username`,`a`.`account` AS `account`,`a`.`portrait` AS `portrait`,`b`.`userid` AS `userid`,`b`.`storeid` AS `storeid`,`b`.`orderid` AS `orderid`,`b`.`comment` AS `comment` from (`take_userinfo` `a` join `take_comment` `b`) where (`a`.`id` = `b`.`userid`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-25 14:13:37
