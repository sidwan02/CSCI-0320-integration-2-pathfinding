-- MySQL dump 10.16  Distrib 10.1.26-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: db
-- ------------------------------------------------------
-- Server version	10.1.26-MariaDB-0+deb9u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `node`
--

DROP TABLE IF EXISTS `node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `node` (
  `id` varchar(4) DEFAULT NULL,
  `latitude` decimal(6,4) DEFAULT NULL,
  `longitude` decimal(6,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `node`
--

LOCK TABLES `node` WRITE;
/*!40000 ALTER TABLE `node` DISABLE KEYS */;
INSERT INTO `node` VALUES ('/n/0',41.8200,-71.4000),('/n/1',41.8203,-71.4000),('/n/2',41.8206,-71.4000),('/n/3',41.8200,-71.4003),('/n/4',41.8203,-71.4003),('/n/5',41.8206,-71.4003);
/*!40000 ALTER TABLE `node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `graticuleEdge`
--

DROP TABLE IF EXISTS `graticuleEdge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `graticuleEdge` (
  `id` varchar(4) DEFAULT NULL,
  `name` varchar(18) DEFAULT NULL,
  `type` varchar(11) DEFAULT NULL,
  `start` varchar(4) DEFAULT NULL,
  `end` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `graticuleEdge`
--

LOCK TABLES `graticuleEdge` WRITE;
/*!40000 ALTER TABLE `graticuleEdge` DISABLE KEYS */;
INSERT INTO `graticuleEdge` VALUES ('/w/0','Chihiro Ave','residential','/n/0','/n/1'),('/w/1','Chihiro Ave','residential','/n/1','/n/2'),('/w/2','Radish Spirit Blvd','residential','/n/0','/n/3'),('/w/3','Sootball Ln','residential','/n/1','/n/4'),('/w/4','Kamaji Pl','residential','/n/2','/n/5'),('/w/5','Yubaba St','residential','/n/3','/n/4'),('/w/6','Yubaba St','residential','/n/4','/n/5');
/*!40000 ALTER TABLE `graticuleEdge` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-22 15:20:24
