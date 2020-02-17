-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: mall
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `gdID` int(11) NOT NULL AUTO_INCREMENT,
  `gdName` varchar(20) NOT NULL,
  `gdCount` int(11) NOT NULL,
  `gdPrice` varchar(20) NOT NULL,
  `gdClass` varchar(20) NOT NULL,
  `gdTime` date NOT NULL,
  PRIMARY KEY (`gdID`),
  UNIQUE KEY `gdName` (`gdName`)
) ENGINE=InnoDB AUTO_INCREMENT=2020100032 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (2020100001,'腌制黄瓜',1000,'15.00元/袋','泡菜类-贡盐泡菜','2020-01-02'),(2020100002,'辣白菜',1000,'25.00元/袋','泡菜类-韩国泡菜','2020-01-02'),(2020100003,'重庆藤椒500g/袋',12000,'30.00元/袋','花椒类-青花椒','2020-01-02'),(2020100004,'四川汉源花椒500g/袋',10500,'30.00元/袋','花椒类-红花椒','2020-01-02'),(2020100005,'吉得利500g/袋',2000,'15.00元/袋','花椒类-红花椒','2020-01-03'),(2020100006,'蜀大侠500g/袋',1500,'48.00元/袋','花椒类-红花椒','2020-01-03'),(2020100007,'椒达人500g/袋',1800,'30.00元/袋','花椒类-青花椒','2020-01-03'),(2020100008,'厨邦金品生抽',16420,'32.85元/瓶','酱油类','2020-01-03'),(2020100009,'海天招牌味极鲜',31000,'58.00元/瓶','酱油类','2020-01-03'),(2020100010,'千禾零添加酱油',21750,'48.00元/瓶','酱油类','2020-01-03'),(2020100011,'欣和六月鲜特级酱油',2825,'60.90元/瓶','酱油类','2020-01-04'),(2020100012,'李锦记薄盐生抽',1229,'47.00元/瓶','酱油类','2020-01-04'),(2020100013,'老恒和太油',2240,'169.00元/瓶','酱油类','2020-01-04'),(2020100014,'日本进口 WADAKAN/和田宽',40,'39.00元/瓶','酱油类','2020-01-04'),(2020100015,'海天酱油金标生抽',69939,'26.00元/瓶','酱油类','2020-01-04'),(2020100016,'鲁花自然鲜',5023,'43.00元/瓶','酱油类','2020-01-04'),(2020100017,'九三清纯玉米油',4377,'69.80元/件','食用油类','2020-01-04'),(2020100018,'鲁花5S压榨一级花生油',2178,'103.00元/件','食用油类','2020-01-04'),(2020100019,'金龙鱼食用植物调和油',15519,'43.00元/件','食用油类','2020-01-04'),(2020100020,'欧丽薇兰橄榄油礼盒装750ml*6瓶',10886,'444.00元/件','食用油类','2020-01-05'),(2020100021,'蓓琳娜原装进口PDO特级初榨橄榄油',3790,'100.00元/件','食用油类','2020-01-05'),(2020100022,'多力葵花籽油250ml*4瓶',30,'484.00元/件','食用油类','2020-01-05'),(2020100023,'胡姬花古法小榨花生油5L*2组合装',1019,'339.80元/件','食用油类','2020-01-05'),(2020100024,'贝蒂斯特级初榨橄榄油礼盒750ml*2瓶',336,'288.00元/件','食用油类','2020-01-05'),(2020100025,'太太乐鸡精调味料227g/袋',3020,'9.90元/袋','其他类','2020-01-05'),(2020100026,'李锦记旧庄蚝油调味料510g/瓶',191,'35.00元/瓶','其他类','2020-01-05'),(2020100027,'黄灯笼辣椒酱100g',1222,'34.90元/瓶','其他类','2020-01-06'),(2020100028,'毛哥酸萝卜老鸭汤炖汤料350g*5袋',57172,'38.80元/件','其他类','2020-01-06'),(2020100029,'海天招牌拌饭酱300g*3',101,'28.80元/件','其他类','2020-01-08'),(2020100030,'蜀九香火锅底料牛油调味品615g',34311,'48.00元/袋','其他类','2020-01-11');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leaveamsg`
--

DROP TABLE IF EXISTS `leaveamsg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leaveamsg` (
  `msgCustomer` varchar(20) NOT NULL,
  `msgContent` varchar(100) NOT NULL,
  `msgTime` date NOT NULL,
  PRIMARY KEY (`msgCustomer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leaveamsg`
--

LOCK TABLES `leaveamsg` WRITE;
/*!40000 ALTER TABLE `leaveamsg` DISABLE KEYS */;
INSERT INTO `leaveamsg` VALUES ('aoluomi','一直吃这个牌子的生抽，味道好，比超市便宜，要是在发货包装上再防撞包装一下就更好了。','2020-01-21'),('cyi__','确实香，一分价钱一分货，这个花椒用来做菜肯定好吃！以后一定常来买，希望能多多进行优惠活动！','2020-01-17'),('hhhhhhh','作家冰心曾经说过：“成功的花，人们只惊羡她现时的明艳！然而当初她的芽儿，浸透了奋斗的泪泉，洒遍了牺牲的血雨。”','2020-01-22'),('wangfei','又快又好，快递真正送货上门，直接送到三楼。一直买太太乐鸡精，好吃。','2020-01-20'),('yikeX','温暖的滋味，精致的匠心，丰富而纯粹。一起传承中华美食，关心一日三餐。记得，要好好吃饭。','2020-01-18'),('zz85855','蚝油不错，包装的也严密，哈哈哈，辛苦快递小哥了，人很好，必须给点赞','2020-01-18'),('和my','垃圾，从来没有见过这么慢的快递','2020-02-12'),('图匠','每次搞活动必囤的辣椒酱，酸汤肥牛、擂椒皮蛋、凉拌木耳必备啊！谢谢客服的耐心解答，这次选了礼盒装就为了中间其中有三个特辣，在我家这也属于快消品，娃中午要在学校吃饭，带去学校简直下饭神器，嘿嘿。','2020-01-21'),('明天8','海天招牌好好吃，每餐必备，满满的都是料，购买N次啦，还是没吃够，吃完继续回购，包装很是完美，快递也很给力！','2020-01-20'),('清出于谭','宝贝包装很好，收到一点都没有漏泄破碎，谢谢，至于味道，我就是在别人处嚐了味才买的，当然是喜欢的喽。','2020-01-21'),('漫yu雨','常购此款，只要有优惠就买！大品牌值得信赖！炒菜特别好','2020-01-21'),('端木18','一个薄薄的纸箱子装来到，没有任何保护，变形了，不满意！不知道是不是正品，没有下次了。','2020-02-03');
/*!40000 ALTER TABLE `leaveamsg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `odID` int(11) NOT NULL AUTO_INCREMENT,
  `odCustomer` varchar(20) NOT NULL,
  `odGood` varchar(20) NOT NULL,
  `odCount` varchar(20) NOT NULL,
  `odPayStat` varchar(20) NOT NULL,
  `odExpStat` varchar(20) NOT NULL,
  `odExpNum` bigint(20) DEFAULT NULL,
  `odTime` date NOT NULL,
  PRIMARY KEY (`odID`)
) ENGINE=InnoDB AUTO_INCREMENT=2020200014 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2020200001,'yikeX','腌制黄瓜','2袋','已付款','已发货',773023753354512,'2020-01-15'),(2020200002,'hero','重庆藤椒500g/袋','3袋','已付款','未发货',NULL,'2020-01-15'),(2020200003,'一个个aa','腌制黄瓜','1袋','未付款','未发货',NULL,'2020-01-15'),(2020200004,'十一','辣白菜','4袋','已付款','已发货',773023753454734,'2020-01-15'),(2020200005,'野原235','多力葵花籽油250ml*4瓶','3件','已付款','已发货',773023753354628,'2020-01-15'),(2020200006,'小骨头qin','黄灯笼辣椒酱100g','2瓶','未付款','未发货',NULL,'2020-01-15'),(2020200007,'jsting','金龙鱼食用植物调和油','1件','已付款','未发货',NULL,'2020-01-15'),(2020200008,'立特力','厨邦金品生抽','3瓶','已付款','已发货',773023753678622,'2020-01-15'),(2020200009,'st2zz','四川汉源花椒500g/袋','10袋','已付款','已发货',672023753238456,'2020-01-18'),(2020200010,'zz85855','日本进口 WADAKAN/和田宽','1瓶','已付款','已发货',672033455734534,'2020-01-18'),(2020200011,'雪','鲁花5S压榨一级花生油','2件','已付款','未发货',NULL,'2020-01-18'),(2020200012,'spiaofei','海天招牌味极鲜','1瓶','未付款','未发货',NULL,'2020-01-18');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `staffID` int(11) NOT NULL AUTO_INCREMENT,
  `staffName` varchar(20) NOT NULL,
  `loginName` varchar(20) NOT NULL,
  `addOp` varchar(10) NOT NULL,
  `delOp` varchar(10) NOT NULL,
  `editOp` varchar(10) NOT NULL,
  PRIMARY KEY (`staffID`),
  UNIQUE KEY `loginName` (`loginName`)
) ENGINE=InnoDB AUTO_INCREMENT=20200003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (20200001,'莫俊杰','staff_mo','无','无','无'),(20200002,'李子维','staff_li','有','有','有');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sysuser`
--

DROP TABLE IF EXISTS `sysuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sysuser` (
  `sysName` varchar(20) NOT NULL,
  `sysPwd` varchar(20) NOT NULL,
  `sysEncrypted` varchar(20) NOT NULL,
  `sysAnswer` varchar(20) NOT NULL,
  PRIMARY KEY (`sysName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sysuser`
--

LOCK TABLES `sysuser` WRITE;
/*!40000 ALTER TABLE `sysuser` DISABLE KEYS */;
INSERT INTO `sysuser` VALUES ('admin','123456','您的名字是什么？','刘志远'),('staff_li','123456','您的名字是什么？','李子维'),('staff_mo','123456','您的名字是什么？','莫俊杰');
/*!40000 ALTER TABLE `sysuser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-17 22:17:51
