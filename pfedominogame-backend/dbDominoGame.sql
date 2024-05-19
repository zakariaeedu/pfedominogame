-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dbdominogame
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `domino`
--

DROP TABLE IF EXISTS `domino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `domino` (
  `domino_id` bigint NOT NULL AUTO_INCREMENT,
  `left_value` int NOT NULL,
  `right_value` int NOT NULL,
  `game_id` bigint DEFAULT NULL,
  `player_id` bigint DEFAULT NULL,
  `winner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`domino_id`),
  KEY `FK3cva5fgkxnu7cc2xdefgj118g` (`game_id`),
  KEY `FKgxqfmqlotrcq4wy6aepjm3ulq` (`player_id`),
  KEY `FK43r3ekrcvy4wqe4vkd0nuy7j9` (`winner_id`),
  CONSTRAINT `FK3cva5fgkxnu7cc2xdefgj118g` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`),
  CONSTRAINT `FK43r3ekrcvy4wqe4vkd0nuy7j9` FOREIGN KEY (`winner_id`) REFERENCES `player` (`player_id`),
  CONSTRAINT `FKgxqfmqlotrcq4wy6aepjm3ulq` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domino`
--

LOCK TABLES `domino` WRITE;
/*!40000 ALTER TABLE `domino` DISABLE KEYS */;
INSERT INTO `domino` VALUES (1,1,1,2,1,NULL);
/*!40000 ALTER TABLE `domino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `game_id` bigint NOT NULL,
  `status` enum('NEW','IN_PROGRESS','FINISHED') DEFAULT NULL,
  `current_player_id` bigint DEFAULT NULL,
  `player1_id` bigint DEFAULT NULL,
  `player2_id` bigint DEFAULT NULL,
  `winner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`game_id`),
  KEY `FK4de0v58935dtit7o35m9s98y6` (`current_player_id`),
  KEY `FKuj22waqa0w9ju8c0re84u3pj` (`player1_id`),
  KEY `FKf6omt0g5ph9r6rd6j2uih24cm` (`player2_id`),
  KEY `FKdaoa02um96nkw564trbq0jhxg` (`winner_id`),
  CONSTRAINT `FK4de0v58935dtit7o35m9s98y6` FOREIGN KEY (`current_player_id`) REFERENCES `player` (`player_id`),
  CONSTRAINT `FKdaoa02um96nkw564trbq0jhxg` FOREIGN KEY (`winner_id`) REFERENCES `player` (`player_id`),
  CONSTRAINT `FKf6omt0g5ph9r6rd6j2uih24cm` FOREIGN KEY (`player2_id`) REFERENCES `player` (`player_id`),
  CONSTRAINT `FKuj22waqa0w9ju8c0re84u3pj` FOREIGN KEY (`player1_id`) REFERENCES `player` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (2,'IN_PROGRESS',1,1,2,NULL),(3,'NEW',2,2,NULL,NULL);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_seq`
--

DROP TABLE IF EXISTS `game_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_seq`
--

LOCK TABLES `game_seq` WRITE;
/*!40000 ALTER TABLE `game_seq` DISABLE KEYS */;
INSERT INTO `game_seq` VALUES (101);
/*!40000 ALTER TABLE `game_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gamestatus`
--

DROP TABLE IF EXISTS `gamestatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gamestatus` (
  `gamestatus_id` bigint NOT NULL,
  `player_id` bigint DEFAULT NULL,
  `game_id` bigint DEFAULT NULL,
  PRIMARY KEY (`gamestatus_id`),
  KEY `FKntxux5d7eok0yga9rkqlkf6x1` (`player_id`),
  KEY `FK1s1cagrnfio6syhr0ayvn3bgt` (`game_id`),
  CONSTRAINT `FK1s1cagrnfio6syhr0ayvn3bgt` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`),
  CONSTRAINT `FKntxux5d7eok0yga9rkqlkf6x1` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gamestatus`
--

LOCK TABLES `gamestatus` WRITE;
/*!40000 ALTER TABLE `gamestatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `gamestatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `move`
--

DROP TABLE IF EXISTS `move`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `move` (
  `move_id` bigint NOT NULL,
  `move_number` int NOT NULL,
  `side` char(1) NOT NULL,
  `domino_id` bigint DEFAULT NULL,
  `game_id` bigint DEFAULT NULL,
  `player_id` bigint DEFAULT NULL,
  PRIMARY KEY (`move_id`),
  KEY `FK3a9g4hppgfycv8g8gvrcnmktt` (`domino_id`),
  KEY `FK6khbbqe4c9jmywgvi9nehpkd1` (`game_id`),
  KEY `FKfyg07nm0qyopn03emleo4jhxi` (`player_id`),
  CONSTRAINT `FK3a9g4hppgfycv8g8gvrcnmktt` FOREIGN KEY (`domino_id`) REFERENCES `domino` (`domino_id`),
  CONSTRAINT `FK6khbbqe4c9jmywgvi9nehpkd1` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`),
  CONSTRAINT `FKfyg07nm0qyopn03emleo4jhxi` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `move`
--

LOCK TABLES `move` WRITE;
/*!40000 ALTER TABLE `move` DISABLE KEYS */;
INSERT INTO `move` VALUES (1,0,'d',1,2,1),(2,0,'g',1,2,1);
/*!40000 ALTER TABLE `move` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `move_seq`
--

DROP TABLE IF EXISTS `move_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `move_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `move_seq`
--

LOCK TABLES `move_seq` WRITE;
/*!40000 ALTER TABLE `move_seq` DISABLE KEYS */;
INSERT INTO `move_seq` VALUES (101);
/*!40000 ALTER TABLE `move_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
  `player_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `score` int NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'AAAAAAAAA',1,'BBBBBBBBBB'),(2,'FFFFFFFFF',1,'FFFFFFFF'),(3,'fgfgfg',1,'khjkh'),(4,'email',2,NULL);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score` (
  `score_id` bigint NOT NULL AUTO_INCREMENT,
  `game_score` int NOT NULL,
  `score` int NOT NULL,
  `game_id` bigint DEFAULT NULL,
  `player_id` bigint DEFAULT NULL,
  PRIMARY KEY (`score_id`),
  KEY `FK7kisjkdga5kr3mk2kwcxhkpi7` (`game_id`),
  KEY `FK7fnyc71v3bhevmxexy2fwx695` (`player_id`),
  CONSTRAINT `FK7fnyc71v3bhevmxexy2fwx695` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`),
  CONSTRAINT `FK7kisjkdga5kr3mk2kwcxhkpi7` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-12 18:38:05
