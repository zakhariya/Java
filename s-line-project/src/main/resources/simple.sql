-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 192.168.0.55    Database: simple
-- ------------------------------------------------------
-- Server version	5.5.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agents`
--

DROP TABLE IF EXISTS `agents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agents`
--

LOCK TABLES `agents` WRITE;
/*!40000 ALTER TABLE `agents` DISABLE KEYS */;
INSERT INTO `agents` VALUES (1,' МЕЕ А/С/ MEE A/S, генеральный агент в Украине- ООО \"МЭРСК УКРАИНА ЛТД\"  / ТОВ \"МЕРСК УКРАЇНА ЛТД\" /   Maersk Ukraine Ltd '),(2,' ООО \"ФОРМАГ ГРУП\"/ТОВ \"ФОРМАГ ГРУП\"/ \"FORMAG GROUP \" Ltd '),(6,'\'ООО \" ФОРМАГ ЭЙДЖЕНСИЗ \"  / ТОВ \" ФОРМАГ ЕЙДЖЕНСIЗ \" /  FORMAG AGENCIES LTD'),(3,'ДОЧЕРНЕЕ  ПРЕДПРИЯТИЕ  С ИНОСТРАННЫМИ ИНВЕСТИЦИЯМИ \"ЭКОНОМУ ИНТЕРНЭШНЛ ШИППИНГ ЭДЖЕНСИ ЛИМИТЕД\"   /  ДОЧІРНЄ ПІДПРИЄМСТВО З ІНОЗЕМНИМИ ІНВЕСТИЦІЯМИ \"ЕКОНОМУ ІНТЕРНЕШНЛ ШІППІНГ ЕДЖЕНСІ ЛІМІТЕД\" /\"ECONOMOU INTERNATIONAL SHIPPING AGENCY LTD\" SC'),(5,'ООО \" АМИТИ ШИППИНГ УКРАИНА \"  / ТОВ \"АМІТІ ШИППІНГ УКРАЇНА\"/  \" AMITY SHIPPING UKRAINE \" LTD '),(7,'ООО \"АРЕНА МАРИН \" / ТОВ \"АРЕНА МАРИН \"/ \"ARENA MARINE \" LTD'),(8,'ООО \"АРКАС-УКРАИНА\" / ТОВ \"АРКАС-УКРАЇНА\"/  \"ARKAS-UKRAINE\" LLC'),(9,'ООО \"БИ ЭС ЭЙ ЮКРЭЙН ЛТД \"/ ТОВ \"БІ ЕС ЕЙ ЮКРЕЙН ЛТД\" /  \"BSA UKRAINE LTD\" '),(10,'ООО \"БЛЕК СИ ЛИНК\" / ТОВ \"БЛЄК СІ ЛІНК\" / \"BLACK SEA LINK\" LLC'),(11,'ООО \"ВАЯ МАЛТИМА\" /ТОВ \"ВАЯ МАЛТІМА\" /  VIA MULTIMA  LTD'),(12,'ООО \"ДЖИ ВИ ЭС СЕРВИС \"/ТОВ \"ДЖІ ВІ ЕС СЕРВІС\"/JVS SERVICE-LLC'),(4,'ООО \"КОСКО ШИППИНГ Лайнз (Украина) Ко.,Лтд\" /ТОВ \"КОСКО ШИППІНГ ЛАЙНЗ (УКРАЇНА) КО.,ЛТД\" /   LLC \"COSCO SHIPPING LINES (UKRAINE) CO., LTD\"'),(13,'ООО \"МЕЖДУНАРОДНЫЙ ЭКСПЕДИТОРСКИЙ ЦЕНТР\"/ТОВ \"МІЖНАРОДНИЙ ЕКСПЕДИТОРСЬКИЙ ЦЕНТР\"/  MEZHDUNARODNIY EKSPEDITORSKIY TSENTR LIMITED'),(14,'ООО \"МШК УКРАИНА\" /ТОВ \"МШК УКРАЇНА\"/ \"MSC UKRAINE \" LLC'),(15,'ООО \"ОВЭРСИС ТРАНСПОРТ УКРАИНА\" /ТОВ \"ОВЕРСІС ТРАНСПОРТ УКРАЇНА\" /   \"OVERSEAS TRANSPORT UKRAINE\" LTD'),(16,'ООО \"ОДЕМАРА - ИНТЕР\" / ТОВ \"ОДЕМАРА-ІНТЕР\" / \"ODEMARA -INTER\" LTD'),(17,'ООО \"С.М.Т. - ЛТД\" / ТОВ \"С.М.Т. - ЛТД\" /\"C.M.T-LTD\" LLC'),(18,'ООО \"СМА СИ ДЖИ ЭМ ШИППИНГ ЭДЖЕНСИЗ УКРАИНА\" / ТОВ \"СМА СІ ДЖИ ЕМ ШИППІНГ ЕДЖЕНСІЗ УКРАЇНА\" /                  \"CMA CGM SHIPPING AGENCIES UKRAINE\" LLC'),(19,'ООО \"ТРАНС-ОУШЕН ЭКСПРЕСС\" / ТОВ \"ТРАНС-ОУШЕН ЕКСПРЕС\" /\"TRANS-OCEAN EXPRESS\" LTD'),(20,'ООО \"ХОЙЕР УКРАИНА\" / ТОВ \"ХОЙЕР УКРАЇНА\" / LLC “HOYER UKRAINE”'),(21,'ООО \"ЦИМ ИНТЕГРЕЙТЕД ШИПИНГ ЮКРЕЙН СЕРВИСЕЗ ЛТД\" /ТОВ \"ЦІМ ІНТЕГРЕЙТЕД ШИПІНГ ЮКРЕЙН СЕРВІСЕЗ ЛТД.\" /                \"ZIM INTEGRATED SHIPPING UKRAINE SERVICES LTD\" LLC'),(22,'ООО \"ЭЛИТ БЛЕК СИ\"/ ТОВ \"ЕЛІТ БЛЕК СІ\" /LLC \" ELITE BLACK SEA\"'),(24,'ООО \"ЮНАЙТЕД ЛАЙНЕР ЭЙДЖЕНСИ\" / ТОВ \"ЮНАЙТЕД ЛАЙНЕР ЕЙДЖЕНСI\"'),(23,'ООО \"ЮНИ-ОРИЕНТ ШИППИНГ ЭЙДЖЕНСИ\" / ТОВ \"ЮНІ-ОРІЕНТ ШИППІНГ ЕЙДЖЕНСІ\" / UNI-ORIENT SHIPPING AGENCY-LTD');
/*!40000 ALTER TABLE `agents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carriers`
--

DROP TABLE IF EXISTS `carriers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carriers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `OKPO` varchar(45) DEFAULT NULL,
  `ITN` varchar(45) DEFAULT NULL,
  `payment_account` varchar(45) DEFAULT NULL,
  `bank` varchar(155) DEFAULT NULL,
  `contract_number` varchar(45) DEFAULT NULL,
  `contract_date` date DEFAULT NULL,
  `contract_expire` date DEFAULT NULL,
  `contact_info` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carriers`
--

LOCK TABLES `carriers` WRITE;
/*!40000 ALTER TABLE `carriers` DISABLE KEYS */;
INSERT INTO `carriers` VALUES (1,'ТОВ \"Укрфенікс\"',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'ТОВ \"Сава Консалтинг\"',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `carriers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_counterparties`
--

DROP TABLE IF EXISTS `client_counterparties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_counterparties` (
  `client_id` int(22) NOT NULL,
  `counterparty_id` int(22) NOT NULL,
  `contract_number` varchar(45) DEFAULT NULL,
  `contract_date` date DEFAULT NULL,
  KEY `client_id_idx` (`client_id`),
  KEY `counterparties_id_idx` (`counterparty_id`),
  CONSTRAINT `client_id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `counterparty_id` FOREIGN KEY (`counterparty_id`) REFERENCES `counterparties` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_counterparties`
--

LOCK TABLES `client_counterparties` WRITE;
/*!40000 ALTER TABLE `client_counterparties` DISABLE KEYS */;
INSERT INTO `client_counterparties` VALUES (1,1,NULL,NULL),(1,2,NULL,NULL);
/*!40000 ALTER TABLE `client_counterparties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `OKPO` varchar(45) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `ITN` varchar(45) DEFAULT NULL,
  `payment_account` varchar(45) DEFAULT NULL,
  `MFO` varchar(45) DEFAULT NULL,
  `bank` varchar(155) DEFAULT NULL,
  `contract_number` varchar(45) DEFAULT NULL,
  `contract_date` date DEFAULT NULL,
  `manager_code` varchar(5) DEFAULT NULL,
  `contact_info` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'ТОВ «С-ЛАЙН ЛОДЖИСТИК»','43167437','Україна, 65045, м. Одеса, ','431674315535',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `container_types`
--

DROP TABLE IF EXISTS `container_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `container_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `container_types`
--

LOCK TABLES `container_types` WRITE;
/*!40000 ALTER TABLE `container_types` DISABLE KEYS */;
INSERT INTO `container_types` VALUES (1,'20′DV',NULL),(2,'40’DV',NULL),(3,'40’HQ',NULL),(4,'45’HQ',NULL);
/*!40000 ALTER TABLE `container_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `counterparties`
--

DROP TABLE IF EXISTS `counterparties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `counterparties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `OKPO` varchar(45) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `ITN` varchar(45) DEFAULT NULL,
  `payment_account` varchar(45) DEFAULT NULL,
  `MFO` varchar(45) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `contact_info` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `counterparties`
--

LOCK TABLES `counterparties` WRITE;
/*!40000 ALTER TABLE `counterparties` DISABLE KEYS */;
INSERT INTO `counterparties` VALUES (1,'ООО \"ВИВА ЛОГИСТИК\"','40002349','Україна, 01010, м.Київ, ВУЛИЦЯ ІВАНА МАЗЕПИ, будинок 16, офіс 5','123456789',NULL,NULL,NULL,NULL),(2,'ТОВ \"ЄВРОКАРГО\"','38109792','65041 Україна,Одеська обл.,м.Одеса,Хаджибейська дор. 4/1',NULL,NULL,NULL,NULL,NULL),(3,'ТОВ \"С-ЛАЙН МОТОРС УКРАЇНА\"','43059073','04050, Україна, м.Київ, ВУЛИЦЯ МЕЛЬНИКОВА, будинок 81, корпус А',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `counterparties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `id` int(22) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `second_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `passport_code` varchar(45) DEFAULT NULL,
  `passport_issued_by` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `position` int(11) NOT NULL DEFAULT '1',
  `code` varchar(5) NOT NULL,
  `fired` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `passport_code_UNIQUE` (`passport_code`),
  KEY `position_idx` (`position`),
  CONSTRAINT `position` FOREIGN KEY (`position`) REFERENCES `positions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Артем','Игоревич','Лиманов','КЕ','Одессой','+38 (067) 363-88-89','Украина, г. Одесса',3,'LA',0),(2,'Владимир','Александрович','Буланов','КК','Одессой','+38 (095) 096-27-18','Украина, г. Одесса',4,'VB',0),(3,'Анастасия','Александровна','Ященко','КМ','Одессой','+38 (095) 220-60-85','Украина, г. Одесса',5,'NY',0),(4,'Мария',NULL,'Питушкан',NULL,NULL,NULL,NULL,1,'MP',0),(8,'fgfg',NULL,'fggf',NULL,NULL,NULL,NULL,1,'gg',0),(9,'string','string','string','string','string','string','string',4,'LAA',0);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `line_agents`
--

DROP TABLE IF EXISTS `line_agents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `line_agents` (
  `line_id` int(22) NOT NULL,
  `agent_id` int(22) NOT NULL,
  KEY `line_id_idx` (`line_id`),
  KEY `agent_id_idx` (`agent_id`),
  CONSTRAINT `agent_id` FOREIGN KEY (`agent_id`) REFERENCES `agents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `line_id` FOREIGN KEY (`line_id`) REFERENCES `lines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `line_agents`
--

LOCK TABLES `line_agents` WRITE;
/*!40000 ALTER TABLE `line_agents` DISABLE KEYS */;
INSERT INTO `line_agents` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,8),(10,9),(11,10),(12,10),(13,11),(14,12),(15,13),(16,14),(17,15),(18,16),(19,16),(20,16),(21,16),(22,16),(23,17),(24,17),(25,18),(26,18),(27,19),(28,20),(29,21),(30,22),(31,23),(32,23),(34,24);
/*!40000 ALTER TABLE `line_agents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lines`
--

DROP TABLE IF EXISTS `lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CTIS_code` varchar(45) DEFAULT NULL,
  `name` varchar(155) DEFAULT NULL,
  `OKPO` varchar(45) DEFAULT NULL,
  `ITN` varchar(45) DEFAULT NULL,
  `payment_account` varchar(45) DEFAULT NULL,
  `bank` varchar(155) DEFAULT NULL,
  `contact_info` text,
  `contract_number` varchar(45) DEFAULT NULL,
  `contract_date` date DEFAULT NULL,
  `contract_expire` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lines`
--

LOCK TABLES `lines` WRITE;
/*!40000 ALTER TABLE `lines` DISABLE KEYS */;
INSERT INTO `lines` VALUES (1,'MAE','MAERSK  LINE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'UASC','United Arab Shipping Company (S.A.G)',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'HS','Hamburg Sud',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'COSCO','COSCO',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'MOL','Mitsui O.S.K. Lines, Ltd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'TUR','Turkon Container Transporation & Shipping Inc.',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,'CSUA','CONTAINERSHIPS',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,'ARK','ARKAS Line',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,'TAR','TARROS International S.p.A.',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'EMC','Evergreen Marine Corporation',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'NYK','NYK-LINE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,'ACOL','Admiral Container Lines Inc',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,'OOCL','OOCL (Europe) Limited',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,'ASG','ASGARD TASIMACILIK VE TIC LTD STI',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(15,'BMC','BMC LINE SHIPPING',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(16,'MSC','Mediterranean Shipping Company',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(17,'HLC','Hapag-Lloyd AG',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(18,'CCLL','Cargo Container Line limited',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,'ECL','Egyptian Container Line   ',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,'EMCL','East Mediterranean Cont. Line ',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'MTS','MTS Logistics',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,'NCL','Namma Container Lines',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,'HMM','Hyundai Merchant Marine  Ltd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,'STC','STOLT TANK CONTAINERS BV',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'APL','American President Line',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,'CMA','CMA CGM',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'YANG','Yang Ming Line',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'HOYER','HOYER GmbH Internationale Fachspedition” (Германия)',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,'ZIM','ZIM Integrated Shipping Services Ltd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(30,'CMC','CRESCENT MOON COMPANY',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(31,'MGS','MARGUISA MARITIME SL',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(32,'HDASCO','HAFEZ  DARYA  ARYA  SHIPPING LINE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(33,'SOC','Shipper Own Conteiner',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(34,'ONE','OCEAN NETWORK EXPRESS PTE.LTD.',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `positions`
--

DROP TABLE IF EXISTS `positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `positions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(155) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `position_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `positions`
--

LOCK TABLES `positions` WRITE;
/*!40000 ALTER TABLE `positions` DISABLE KEYS */;
INSERT INTO `positions` VALUES (1,'Брокер'),(2,'Бухгалтер'),(3,'Директор'),(4,'Менеджер'),(5,'Оперативный менеджер'),(6,'Сейлз менеджер'),(7,'Экспедитор');
/*!40000 ALTER TABLE `positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER'),(3,'ROLE_USERR');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(22) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `employee_id` int(22) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `employee_id_UNIQUE` (`employee_id`),
  CONSTRAINT `employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','$2a$08$nN2JwFuR0fhkhLs6QVANNefihwuU2X34YDPx6aWkmAvfGRPlp8mgK',NULL,1,1),(3,'user','$2a$08$AQs9Vv4/gUejoJF3UXOAyuKpFW5wz830votCPATiqaKsMGlrsYi3y',NULL,1,NULL),(5,'test','$2a$08$mUlKzshznnz7S9xeRm8qm.vw9Zx1WcaVKtlhv101gHDkuagppxD..','email',1,9),(7,'testrr','$2a$08$7rBLb9VMUWmT7TfpFuBYuOwiBjsKNks4xJ.6oXGHrw/tAh/czXrsS',NULL,1,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `user_id` int(22) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `user_id_idx` (`user_id`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(3,2),(7,3),(5,3),(5,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work`
--

DROP TABLE IF EXISTS `work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work` (
  `idwork` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idwork`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work`
--

LOCK TABLES `work` WRITE;
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-25 13:49:59
