-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: rudysjavaiadb
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `subjectbillgatesbill`
--

DROP TABLE IF EXISTS `subjectbillgatesbill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjectbillgatesbill` (
  `subject` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjectbillgatesbill`
--

LOCK TABLES `subjectbillgatesbill` WRITE;
/*!40000 ALTER TABLE `subjectbillgatesbill` DISABLE KEYS */;
INSERT INTO `subjectbillgatesbill` VALUES ('Computer Science'),('Maths'),('Physics'),('Chemistry'),('Design and Tech'),('French');
/*!40000 ALTER TABLE `subjectbillgatesbill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjectironmaniron`
--

DROP TABLE IF EXISTS `subjectironmaniron`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjectironmaniron` (
  `subject` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjectironmaniron`
--

LOCK TABLES `subjectironmaniron` WRITE;
/*!40000 ALTER TABLE `subjectironmaniron` DISABLE KEYS */;
INSERT INTO `subjectironmaniron` VALUES ('Math'),('Physics'),('Chemistry'),('Biology'),('Chemistry 2.0');
/*!40000 ALTER TABLE `subjectironmaniron` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjectrudyrudraksh`
--

DROP TABLE IF EXISTS `subjectrudyrudraksh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjectrudyrudraksh` (
  `subject` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjectrudyrudraksh`
--

LOCK TABLES `subjectrudyrudraksh` WRITE;
/*!40000 ALTER TABLE `subjectrudyrudraksh` DISABLE KEYS */;
INSERT INTO `subjectrudyrudraksh` VALUES ('Mathematics AA'),('Computer Science'),('Physics'),('Economics'),('English'),('Spanish');
/*!40000 ALTER TABLE `subjectrudyrudraksh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjectyukiyuki`
--

DROP TABLE IF EXISTS `subjectyukiyuki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjectyukiyuki` (
  `subject` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjectyukiyuki`
--

LOCK TABLES `subjectyukiyuki` WRITE;
/*!40000 ALTER TABLE `subjectyukiyuki` DISABLE KEYS */;
INSERT INTO `subjectyukiyuki` VALUES ('Comp Sci'),('Mathematics');
/*!40000 ALTER TABLE `subjectyukiyuki` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasksbillgatesbill`
--

DROP TABLE IF EXISTS `tasksbillgatesbill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasksbillgatesbill` (
  `ID` int(100) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `homeworkType` varchar(45) DEFAULT NULL,
  `description` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasksbillgatesbill`
--

LOCK TABLES `tasksbillgatesbill` WRITE;
/*!40000 ALTER TABLE `tasksbillgatesbill` DISABLE KEYS */;
INSERT INTO `tasksbillgatesbill` VALUES (NULL,'Homework','Physics Questions','2020-07-01','Physics','High','Test','');
/*!40000 ALTER TABLE `tasksbillgatesbill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasksironmaniron`
--

DROP TABLE IF EXISTS `tasksironmaniron`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasksironmaniron` (
  `ID` int(100) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `homeworkType` varchar(45) DEFAULT NULL,
  `description` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasksironmaniron`
--

LOCK TABLES `tasksironmaniron` WRITE;
/*!40000 ALTER TABLE `tasksironmaniron` DISABLE KEYS */;
INSERT INTO `tasksironmaniron` VALUES (NULL,'Homework','Chemistry Test','2020-06-21','Chemistry','Medium','null',''),(NULL,'Task','Sleep faster','2021-07-07','Biology','Medium','null','sleep fast.'),(NULL,'Task','Wake up earlier','2019-06-11',NULL,'Medium','Presentation','');
/*!40000 ALTER TABLE `tasksironmaniron` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasksrudyrudraksh`
--

DROP TABLE IF EXISTS `tasksrudyrudraksh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasksrudyrudraksh` (
  `ID` int(100) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `title` varchar(45) NOT NULL,
  `dueDate` date NOT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `priority` varchar(45) NOT NULL,
  `homeworkType` varchar(45) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasksrudyrudraksh`
--

LOCK TABLES `tasksrudyrudraksh` WRITE;
/*!40000 ALTER TABLE `tasksrudyrudraksh` DISABLE KEYS */;
INSERT INTO `tasksrudyrudraksh` VALUES (26,'Homework','HW 1','2021-03-10','Physics','Low','Report',''),(27,'Homework','HW 2','2021-02-28','Computer Science','High','Reading','');
/*!40000 ALTER TABLE `tasksrudyrudraksh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasksyukiyuki`
--

DROP TABLE IF EXISTS `tasksyukiyuki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasksyukiyuki` (
  `ID` int(100) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `homeworkType` varchar(45) DEFAULT NULL,
  `description` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasksyukiyuki`
--

LOCK TABLES `tasksyukiyuki` WRITE;
/*!40000 ALTER TABLE `tasksyukiyuki` DISABLE KEYS */;
INSERT INTO `tasksyukiyuki` VALUES (NULL,'Homework','Hi I\'m Yuki','2020-01-21','Mathematics','High','Report',''),(NULL,'Homework','Hi I\'m Kume','2020-01-22','null','High','null',''),(NULL,'Homework','Hi I\'m unimportant','2020-01-07','null','Low','null',''),(NULL,'Homework','Hi I\'m important','2020-01-21','null','Medium','null','');
/*!40000 ALTER TABLE `tasksyukiyuki` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usernamesandpasswords`
--

DROP TABLE IF EXISTS `usernamesandpasswords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usernamesandpasswords` (
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `subjectTable` varchar(90) DEFAULT NULL,
  `tasksTable` varchar(90) DEFAULT NULL,
  `timetablePath` longtext,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usernamesandpasswords`
--

LOCK TABLES `usernamesandpasswords` WRITE;
/*!40000 ALTER TABLE `usernamesandpasswords` DISABLE KEYS */;
INSERT INTO `usernamesandpasswords` VALUES ('billGates','billGates','Bill','Gates','2019-09-17','subjectbillGatesBill','tasksbillGatesBill','file:/C:/Users/Rudy/Desktop/timetable.png'),('ironMan','1234','Iron','Man','2021-06-17','subjectironManIron','tasksironManIron','file:/C:/Users/Rudy/Desktop/timetable.png'),('rudy','rudy','Rudraksh','Monga','2002-12-02','subjectrudyRudraksh','tasksrudyRudraksh','file:/C:/Users/Rudy/Desktop/dominik-luckmann-ZSe3VBOk6fw-unsplash.jpg'),('yuki','yuki','Yuki','Kume','2020-01-26','subjectyukiYuki','tasksyukiYuki',NULL);
/*!40000 ALTER TABLE `usernamesandpasswords` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-25  8:57:57
