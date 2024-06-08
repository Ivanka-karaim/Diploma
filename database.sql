-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: diploma
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (1,'teacher@gmail.com_karaimivanna@gmail.com'),(2,'karaimivanna@gmail.com_teacher2@gmail.com'),(3,'responsible@gmail.com_karaimivanna@gmail.com'),(4,'responsible@gmail.com_teacher@gmail.com'),(5,'karaimivanna@gmail.com_teacher3@gmail.com'),(6,'teacher@gmail.com_chornovil_andriana@example.com');
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) NOT NULL,
  `text` varchar(255) NOT NULL,
  `post_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
  KEY `FKgcgdcgly6u49hf4g8y2di3g4p` (`user_id`),
  CONSTRAINT `FKgcgdcgly6u49hf4g8y2di3g4p` FOREIGN KEY (`user_id`) REFERENCES `usr` (`email`),
  CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'2024-05-15 13:15:43.224912','ЦЕ дуже цікаво',3,'karaimivanna@gmail.com'),(2,'2024-05-15 13:18:58.367492','До якого числа потрібно здати роботи?',3,'karaimivanna@gmail.com'),(3,'2024-05-16 09:33:56.835996','Блять',3,'karaimivanna@gmail.com'),(4,'2024-05-16 09:34:26.818042','Коли це буде?',1,'karaimivanna@gmail.com'),(6,'2024-05-16 15:52:55.812096','Якого числа це відбудеться?',3,'karaimivanna@gmail.com'),(7,'2024-05-16 16:06:17.510717','Якого числа це відбудеться?',1,'karaimivanna@gmail.com'),(8,'2024-05-16 16:06:58.066088','Якого числа це відбудеться?',1,'karaimivanna@gmail.com'),(9,'2024-05-16 16:09:58.627027','Якого числа це відбудеться?',1,'teacher@gmail.com'),(20,'2024-05-16 16:38:56.967544','Якого числа це відбудеться?',16,'responsible@gmail.com'),(21,'2024-05-16 16:39:16.422736','Коли це буде?',16,'responsible@gmail.com'),(22,'2024-05-16 16:40:29.525661','Які документи потрібно мати при собі для реєстрації на ЄВІ та ЄФВВ?',16,'biletskyi_dmytro@example.com'),(23,'2024-05-16 16:40:39.645221','Чи потрібно попередньо зареєструватися онлайн для участі у вступних магістерських екзаменах?',16,'biletskyi_dmytro@example.com'),(24,'2024-05-16 16:40:43.888672','Які дати та час проведення вступних магістерських екзаменів?',16,'biletskyi_dmytro@example.com'),(25,'2024-05-16 16:41:00.348354','Чи є можливість зареєструватися на вступні магістерські екзамени за кордоном?',16,'bilous_yevhen@example.com'),(26,'2024-05-16 16:41:07.937639','Чи є підготовчі курси або матеріали для підготовки до вступних магістерських екзаменів?',16,'bilous_yevhen@example.com'),(27,'2024-05-16 16:41:13.483205','Чи існують певні вимоги до рівня знань для складання ЄВІ та ЄФВВ?',16,'bilous_yevhen@example.com'),(28,'2024-05-16 16:41:35.953136','Чи можна складати вступні магістерські екзамени дистанційно?',16,'chernysh_oleksii@example.com'),(29,'2024-05-16 16:41:40.078703','Чи є можливість перенести дату вступних магістерських екзаменів у випадку непередбачених обставин?',16,'chernysh_oleksii@example.com'),(30,'2024-05-16 16:42:37.355531','Куди будуть дійсні результати вступних магістерських екзаменів?',16,'chernysh_oleksii@example.com'),(31,'2024-05-16 16:42:49.952856','Які можливості для вступу на магістратуру надає реєстрація в Кампусі для студентів КПІ?',16,'chernysh_oleksii@example.com'),(32,'2024-05-16 16:43:02.265133','Чи є обмеження за кількістю спроб складання вступних магістерських екзаменів?',16,'chernysh_oleksii@example.com'),(33,'2024-05-16 16:43:34.524471','Яким чином оцінюється результат вступних магістерських екзаменів?',16,'chornovil_andriana@example.com'),(34,'2024-05-16 16:43:37.988024','Чи є можливість отримати додаткові бали за певні досягнення або досвід у сфері, пов\'язаній із спеціальністю?',16,'chornovil_andriana@example.com'),(35,'2024-05-16 16:43:53.847019','Які додаткові переваги отримують студенти, які складають вступні магістерські екзамени в Кампусі?',16,'chornovil_andriana@example.com'),(36,'2024-05-16 16:44:14.252098','Чи є можливість отримати консультацію щодо підготовки до вступних магістерських екзаменів?',16,'dmytrenko_anastasiia@example.com'),(37,'2024-05-16 16:44:19.988957','Чи є можливість подати апеляцію у разі незадовільного результату вступних магістерських екзаменів?',16,'dmytrenko_anastasiia@example.com'),(38,'2024-05-16 16:44:31.609243','Чи є можливість складати вступні магістерські екзамени в інші дати у випадку конфлікту з іншими обов\'язками?',16,'dmytrenko_anastasiia@example.com'),(39,'2024-05-16 16:44:34.097973','Чи є можливість отримати додаткові рекомендації щодо підготовки до вступних магістерських екзаменів?',16,'dmytrenko_anastasiia@example.com'),(40,'2024-05-16 16:44:38.706807','Які можливості для подальшого навчання відкриває участь у вступних магістерських екзаменах?',16,'dmytrenko_anastasiia@example.com'),(41,'2024-05-16 19:22:35.944955','Чи обовязково здавати ЄФВВ?',16,'karaimivanna@gmail.com'),(42,'2024-05-16 19:23:56.574296','Де в кампусі реєстрація?',16,'karaimivanna@gmail.com'),(54,'2024-05-17 11:33:20.701428','Коли реєстрація відбувається?',16,'karaimivanna@gmail.com'),(55,'2024-06-04 21:13:30.333587','Для якої це спеціальності?',18,'karaimivanna@gmail.com'),(56,'2024-06-04 21:13:57.269537','Коли це буде?',18,'karaimivanna@gmail.com'),(57,'2024-06-04 21:18:07.302734','Для якої це спеціальності?',18,'karaimivanna@gmail.com'),(58,'2024-06-04 21:18:13.420974','Коли це буде?',18,'karaimivanna@gmail.com');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grup`
--

DROP TABLE IF EXISTS `grup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grup` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `course` int NOT NULL,
  `speciality` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grup`
--

LOCK TABLES `grup` WRITE;
/*!40000 ALTER TABLE `grup` DISABLE KEYS */;
INSERT INTO `grup` VALUES (1,4,'121','ТВ-01'),(2,4,'121','ТІ-01'),(3,3,'121','ТВ-11'),(4,3,'122','ТМ-12'),(5,2,'122','ТМ-21'),(6,2,'121','ТВ-22');
/*!40000 ALTER TABLE `grup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) NOT NULL,
  `text` varchar(255) NOT NULL,
  `viewed` bit(1) NOT NULL,
  `chat_id` bigint NOT NULL,
  `recipient_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmejd0ykokrbuekwwgd5a5xt8a` (`chat_id`),
  KEY `FKa44gvx9ovkiybu5ptripvskip` (`recipient_id`),
  KEY `FK70bv6o4exfe3fbrho7nuotopf` (`user_id`),
  CONSTRAINT `FK70bv6o4exfe3fbrho7nuotopf` FOREIGN KEY (`user_id`) REFERENCES `usr` (`email`),
  CONSTRAINT `FKa44gvx9ovkiybu5ptripvskip` FOREIGN KEY (`recipient_id`) REFERENCES `usr` (`email`),
  CONSTRAINT `FKmejd0ykokrbuekwwgd5a5xt8a` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'2024-05-15 13:23:15.328000','Привіт',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(2,'2024-05-15 13:23:30.796000','Ти пам\'ятаєш, що завтра потрібно бути на кафедрі?',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(3,'2024-05-15 13:25:52.657000','Добрий день, так',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(4,'2024-05-15 13:33:30.354000','Завтра буду',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(5,'2024-05-15 23:00:56.601000','sbrbbre',_binary '\0',2,'teacher2@gmail.com','karaimivanna@gmail.com'),(6,'2024-05-15 23:12:12.213000','fvsfdv',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(7,'2024-05-15 23:49:31.458000','fbdb',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(8,'2024-05-15 23:50:39.102000','v cx   f',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(9,'2024-05-15 23:50:40.297000','fbfdb',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(10,'2024-05-15 23:50:41.925000','fbd',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(11,'2024-05-16 09:35:23.840000','авиіукикурі',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(12,'2024-05-16 09:35:25.295000','куруікипіукіи',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(13,'2024-05-16 09:35:26.631000','куіиаии',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(14,'2024-05-16 09:36:19.436000','кумпфцумфцмуцвм',_binary '\0',2,'teacher2@gmail.com','karaimivanna@gmail.com'),(15,'2024-05-16 09:41:00.553000','dsvSDV',_binary '',4,'teacher@gmail.com','responsible@gmail.com'),(16,'2024-05-16 19:26:54.794000','Добрий день',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(17,'2024-05-16 23:32:52.035000','Привіт, будеш завтра на кафедрі?',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(18,'2024-05-16 23:33:40.710000','Привіт, так буду',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(19,'2024-05-17 09:27:29.509000','Привіт',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(20,'2024-05-17 11:35:00.750000','Привіт',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(21,'2024-05-29 16:37:57.790000','гпмигнрвмфуцгшисфшцу',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(22,'2024-05-29 16:37:58.723000','куимкуиуки',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(23,'2024-05-29 16:37:59.265000','икуикуи',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(24,'2024-05-29 16:37:59.655000','куиук',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(25,'2024-05-29 16:37:59.936000','иук',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(26,'2024-05-29 16:38:01.323000','иукиу',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(27,'2024-05-30 21:24:27.270000','Привіт',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(28,'2024-05-30 21:24:42.034000','Привіт',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(29,'2024-05-30 21:24:58.363000','Збери будь ласка завтра список студентів, що хочуть відвідати виставку',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(30,'2024-05-30 21:25:04.630000','Добрий день',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(31,'2024-05-30 21:25:15.228000','список майже зіюрала, ще декілька людей думають',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(32,'2024-05-30 21:25:20.843000','Супер',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(33,'2024-05-30 21:25:45.762000','І також не забудь про зустріч старост в деканаті',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(34,'2024-05-30 21:25:51.033000','А коли вона буде?',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(35,'2024-05-30 21:25:59.733000','о 17 годині в 5 корпусі',_binary '',1,'karaimivanna@gmail.com','teacher@gmail.com'),(36,'2024-05-30 21:26:02.371000','Дякую',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com'),(37,'2024-05-30 21:26:10.162000','Обовязковов буду',_binary '',1,'teacher@gmail.com','karaimivanna@gmail.com');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participant`
--

DROP TABLE IF EXISTS `participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `chat_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh68oo0phu6c647tqhvmqxbcbv` (`chat_id`),
  KEY `FKacykr2e8mm40518pux75vwug9` (`user_id`),
  CONSTRAINT `FKacykr2e8mm40518pux75vwug9` FOREIGN KEY (`user_id`) REFERENCES `usr` (`email`),
  CONSTRAINT `FKh68oo0phu6c647tqhvmqxbcbv` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participant`
--

LOCK TABLES `participant` WRITE;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
INSERT INTO `participant` VALUES (1,1,'teacher@gmail.com'),(2,1,'karaimivanna@gmail.com'),(3,2,'karaimivanna@gmail.com'),(4,2,'teacher2@gmail.com'),(5,3,'responsible@gmail.com'),(6,3,'karaimivanna@gmail.com'),(7,4,'responsible@gmail.com'),(8,4,'teacher@gmail.com'),(9,5,'karaimivanna@gmail.com'),(10,5,'teacher3@gmail.com'),(11,6,'teacher@gmail.com'),(12,6,'chornovil_andriana@example.com');
/*!40000 ALTER TABLE `participant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) NOT NULL,
  `description` text,
  `title` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `author_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6i9e0diws1ifutrjnjtn8udm3` (`author_id`),
  CONSTRAINT `FK6i9e0diws1ifutrjnjtn8udm3` FOREIGN KEY (`author_id`) REFERENCES `usr` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'2024-05-15 12:57:52.865872','Доброго дня студенти. 25 травня 2024 року о 10:00 відбудеться ректорський контроль для усіх студентів нашого університету. Велтке прохання усіх приєднатись. До зустрічі','Ректорський контроль','IMPORTANT','teacher@gmail.com'),(3,'2024-05-15 13:08:00.023499','Дорогі студенти, які курсові роботи мають бути здані до 30 травня в деканата нашого факультету. ','Здача курсових робіт','IMPORTANT','responsible@gmail.com'),(4,'2024-05-15 20:23:37.421602','Завтра о 18:00 відбудеться попередній захист дипломних робіт','Здача дипломної роботи','IMPORTANT','teacher@gmail.com'),(10,'2024-05-16 09:39:33.077327','куіаикуиикуикуиіикуаиаиіи','іукиіукиіиіуки','IMPORTANT','teacher@gmail.com'),(16,'2024-05-16 16:38:51.123276','Шановні студенти 4 курсу! З 7 до 29 травня відбувається реєстрація на вступні магістерські екзамени, а саме ЄВІ та ЄФВВ(для ваших спеціальностей воно також обовязкове). Нагадуємо Результати будуть дійсні в будь-якому навчальному закладі України.  Також для студентів КПІ надано можливість режструватись в Кампусі, заходьте у вкладку Вступ 2024 (магістри) та заповнюйте анкету. Успіхів Вам! Швиденько реєструйтесь!','Реєстрація на ЄВІ/ ЄФВВ','IMPORTANT','responsible@gmail.com'),(17,'2024-05-16 23:35:15.805522','Привіт, завтра бути на кафедрі о 17 годині\r\n','Зустріч','IMPORTANT','teacher@gmail.com'),(18,'2024-05-17 09:30:05.002330','Дорогі студенти, для спеціальності 121, реєстрація на ЄФВВ обовязкова','ЄФВВ','IMPORTANT','teacher@gmail.com');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_user`
--

DROP TABLE IF EXISTS `post_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `viewed` bit(1) NOT NULL,
  `post_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn8o5op5bt06e3yw49kd4epa2g` (`post_id`),
  KEY `FKkrusbc50ov45rhcoevh4irx3t` (`user_id`),
  CONSTRAINT `FKkrusbc50ov45rhcoevh4irx3t` FOREIGN KEY (`user_id`) REFERENCES `usr` (`email`),
  CONSTRAINT `FKn8o5op5bt06e3yw49kd4epa2g` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_user`
--

LOCK TABLES `post_user` WRITE;
/*!40000 ALTER TABLE `post_user` DISABLE KEYS */;
INSERT INTO `post_user` VALUES (1,_binary '',1,'karaimivanna@gmail.com'),(2,_binary '',1,'teacher@gmail.com'),(3,_binary '',3,'karaimivanna@gmail.com'),(4,_binary '',3,'teacher@gmail.com'),(7,_binary '',10,'karaimivanna@gmail.com'),(8,_binary '',10,'teacher@gmail.com'),(94,_binary '\0',16,' hryhorenko_olga@example.com'),(95,_binary '\0',16,' kovalchuk_artem@example.com'),(96,_binary '\0',16,' kuzmenko_nataliia@example.com'),(97,_binary '\0',16,' lytvyn_anna@example.com'),(98,_binary '',16,'biletskyi_dmytro@example.com'),(99,_binary '',16,'bilous_yevhen@example.com'),(100,_binary '',16,'chernysh_oleksii@example.com'),(101,_binary '',16,'chornovil_andriana@example.com'),(102,_binary '',16,'dmytrenko_anastasiia@example.com'),(103,_binary '',16,'karaimivanna@gmail.com'),(104,_binary '',16,'teacher@gmail.com'),(105,_binary '\0',16,'honcharuk_viktor@example.com'),(106,_binary '\0',16,'koval_andrii@example.com'),(107,_binary '\0',16,'kovalchuk_iryna@example.com'),(108,_binary '\0',16,'kovalchuk_viktoriia@example.com'),(109,_binary '\0',16,'kravchenko_oksana@example.com'),(110,_binary '\0',16,'zaharchenko_volodymyr@example.com'),(111,_binary '\0',17,'kravchuk_vladyslav@example.com'),(112,_binary '\0',17,'lisenko_vitalii@example.com'),(113,_binary '\0',17,'lytvynenko_valentyna@example.com'),(114,_binary '\0',17,'melnyk_serhii@example.com'),(115,_binary '\0',17,'pavlenko_mykhailo@example.com'),(116,_binary '\0',17,'pavliuchenko_nina@example.com'),(117,_binary '\0',17,'petrov_oleksandr@gmail.com'),(118,_binary '\0',17,'polyakov_stanislav@example.com'),(120,_binary '\0',17,'savchenko_tetiana@example.com'),(121,_binary '\0',17,'teacher3@gmail.com'),(122,_binary '\0',18,'honcharuk_viktor@example.com'),(123,_binary '\0',18,'koval_andrii@example.com'),(124,_binary '\0',18,'kovalchuk_iryna@example.com'),(125,_binary '\0',18,'kovalchuk_viktoriia@example.com'),(126,_binary '\0',18,'kravchenko_oksana@example.com'),(127,_binary '\0',18,'zaharchenko_volodymyr@example.com'),(128,_binary '\0',18,' hryhorenko_olga@example.com'),(129,_binary '\0',18,' kovalchuk_artem@example.com'),(130,_binary '\0',18,' kuzmenko_nataliia@example.com'),(131,_binary '\0',18,' lytvyn_anna@example.com'),(132,_binary '\0',18,'biletskyi_dmytro@example.com'),(133,_binary '\0',18,'bilous_yevhen@example.com'),(134,_binary '\0',18,'chernysh_oleksii@example.com'),(135,_binary '\0',18,'chornovil_andriana@example.com'),(136,_binary '\0',18,'dmytrenko_anastasiia@example.com'),(137,_binary '',18,'karaimivanna@gmail.com'),(138,_binary '',18,'teacher@gmail.com');
/*!40000 ALTER TABLE `post_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `response`
--

DROP TABLE IF EXISTS `response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `response` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) NOT NULL,
  `text` varchar(255) NOT NULL,
  `to_comment_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkch90hw56bd41avs4sldpf30v` (`to_comment_id`),
  KEY `FKrei1w5andp4yrhw0xiuwnhjc8` (`user_id`),
  CONSTRAINT `FKkch90hw56bd41avs4sldpf30v` FOREIGN KEY (`to_comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `FKrei1w5andp4yrhw0xiuwnhjc8` FOREIGN KEY (`user_id`) REFERENCES `usr` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `response`
--

LOCK TABLES `response` WRITE;
/*!40000 ALTER TABLE `response` DISABLE KEYS */;
INSERT INTO `response` VALUES (1,'2024-05-15 13:18:58.382931','До 30 травня.',2,'\"chatGptApi\"'),(2,'2024-05-15 13:20:38.100505','Так, я погоджуюсь',1,'karaimivanna@gmail.com'),(3,'2024-05-16 09:34:26.828153','25 травня 2024 року o 10:00',4,'\"chatGptApi\"'),(4,'2024-05-16 09:34:51.143414','в четвер',4,'karaimivanna@gmail.com'),(6,'2024-05-16 15:52:55.825780','30 травня',6,'\"chatGptApi\"'),(7,'2024-05-16 16:06:17.546508','25 травня 2024 - True',7,'\"chatGptApi\"'),(8,'2024-05-16 16:06:58.099640','25 травня 2024 року',8,'\"chatGptApi\"'),(9,'2024-05-16 16:09:58.643031','25 травня 2024 року.',9,'\"chatGptApi\"'),(15,'2024-05-16 16:39:16.429745','З 7 до 29 травня.',21,'\"chatGptApi\"'),(19,'2024-05-16 16:42:37.361532','Результати будуть дійсні в будь-якому навчальному закладі України.',30,'\"chatGptApi\"'),(20,'2024-05-16 16:42:49.957857','Відповідь: Реєстрація в Кампусі надає можливість студентам КПІ заповнювати анкету для вступу на магістратуру.',31,'\"chatGptApi\"'),(23,'2024-05-16 16:44:38.712809','Результати вступних магістерських екзаменів будуть дійсні в будь-якому навчальному закладі України.',40,'\"chatGptApi\"'),(24,'2024-05-16 16:47:58.601704','Я гадаю, що є така можливість',37,'dmytrenko_anastasiia@example.com'),(25,'2024-05-16 19:22:35.955954','Так',41,'\"chatGptApi\"'),(28,'2024-05-16 19:26:03.151307','Там є анкета',42,'karaimivanna@gmail.com'),(34,'2024-05-17 09:25:25.277074','Так, буде зустріч',39,'karaimivanna@gmail.com'),(37,'2024-05-17 11:33:20.708938','Реєстрація відбувається з 7 до 29 травня.',54,'\"chatGptApi\"'),(38,'2024-05-17 11:34:15.266941','З 7 до 29',54,'karaimivanna@gmail.com'),(39,'2024-06-04 21:13:30.364445','Для спеціальності 121.',55,'\"chatGptApi\"'),(40,'2024-06-04 21:18:07.336883','Відповідь: 121',57,'\"chatGptApi\"');
/*!40000 ALTER TABLE `response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saved`
--

DROP TABLE IF EXISTS `saved`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saved` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcv673kg0lyhnt0cf61dqo8ser` (`post_id`),
  KEY `FKo4j2fm8s88m42lrik43yps0y7` (`user_id`),
  CONSTRAINT `FKcv673kg0lyhnt0cf61dqo8ser` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FKo4j2fm8s88m42lrik43yps0y7` FOREIGN KEY (`user_id`) REFERENCES `usr` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saved`
--

LOCK TABLES `saved` WRITE;
/*!40000 ALTER TABLE `saved` DISABLE KEYS */;
INSERT INTO `saved` VALUES (4,16,'teacher@gmail.com'),(8,16,'karaimivanna@gmail.com');
/*!40000 ALTER TABLE `saved` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKga5eqqpv2sbueb5tu43uaa671` (`group_id`),
  KEY `FKpo6ieu9n8a7ylnq2n5wffuk1` (`user_id`),
  CONSTRAINT `FKga5eqqpv2sbueb5tu43uaa671` FOREIGN KEY (`group_id`) REFERENCES `grup` (`id`),
  CONSTRAINT `FKpo6ieu9n8a7ylnq2n5wffuk1` FOREIGN KEY (`user_id`) REFERENCES `usr` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` VALUES (1,2,'teacher@gmail.com'),(2,3,'teacher@gmail.com'),(3,1,'teacher2@gmail.com'),(4,1,'teacher3@gmail.com');
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` varchar(255) NOT NULL,
  `roles` enum('STUDENT','TEACHER','CURATOR','RESPONSIBLE','MAIN','LEADER_GROUP') DEFAULT NULL,
  KEY `FKfpm8swft53ulq2hl11yplpr5` (`user_id`),
  CONSTRAINT `FKfpm8swft53ulq2hl11yplpr5` FOREIGN KEY (`user_id`) REFERENCES `usr` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES ('karaimivanna@gmail.com','STUDENT'),('teacher@gmail.com','CURATOR'),('teacher@gmail.com','TEACHER'),('responsible@gmail.com','RESPONSIBLE'),('teacher2@gmail.com','CURATOR'),('teacher2@gmail.com','TEACHER'),('teacher3@gmail.com','CURATOR'),('teacher3@gmail.com','TEACHER'),('svyripaivanna@gmail.com','STUDENT'),('petrov_oleksandr@gmail.com','STUDENT'),('kovalchuk_iryna@example.com','STUDENT'),('lisenko_vitalii@example.com','STUDENT'),(' hryhorenko_olga@example.com','STUDENT'),('koval_andrii@example.com','STUDENT'),('shevchenko_yulia@example.com','STUDENT'),('pavlenko_mykhailo@example.com','STUDENT'),(' kuzmenko_nataliia@example.com','STUDENT'),('melnyk_serhii@example.com','STUDENT'),('kravchenko_oksana@example.com','STUDENT'),('vasyliev_denis@example.com','STUDENT'),('dmytrenko_anastasiia@example.com','STUDENT'),('tkachuk_igor@example.com','STUDENT'),('lytvynenko_valentyna@example.com','STUDENT'),('zhukov_pavlo@example.com','STUDENT'),('savchenko_tetiana@example.com','STUDENT'),('kovalchuk_viktoriia@example.com','STUDENT'),('chernysh_oleksii@example.com','STUDENT'),('semenova_alina@example.com','STUDENT'),('biletskyi_dmytro@example.com','STUDENT'),('shevchenko_mariia@example.com','STUDENT'),(' kovalchuk_artem@example.com','STUDENT'),('honcharuk_viktor@example.com','STUDENT'),('shevchuk_olena@example.com','STUDENT'),('polyakov_stanislav@example.com','STUDENT'),(' lytvyn_anna@example.com','STUDENT'),('kravchuk_vladyslav@example.com','STUDENT'),('pavliuchenko_nina@example.com','STUDENT'),('chornovil_andriana@example.com','STUDENT'),('bilous_yevhen@example.com','STUDENT'),('moroz_oleg@example.com','TEACHER'),('ivanova_liudmyla@example.com','TEACHER'),('zaharchenko_volodymyr@example.com','TEACHER'),('kovalenko_oksana@example.com','TEACHER'),('horbachov_maxim@example.com','TEACHER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usr`
--

DROP TABLE IF EXISTS `usr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usr` (
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `patronymic` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `group_id` bigint DEFAULT NULL,
  PRIMARY KEY (`email`),
  KEY `FKbkg4w2pgcm3093kxyvo6p37u3` (`group_id`),
  CONSTRAINT `FKbkg4w2pgcm3093kxyvo6p37u3` FOREIGN KEY (`group_id`) REFERENCES `grup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usr`
--

LOCK TABLES `usr` WRITE;
/*!40000 ALTER TABLE `usr` DISABLE KEYS */;
INSERT INTO `usr` VALUES (' hryhorenko_olga@example.com',_binary '','Ольга ','1111','Володимирівна ','Григоренко ',1),(' kovalchuk_artem@example.com',_binary '','Артем ','1111','Михайлович ','Ковальчук ',1),(' kuzmenko_nataliia@example.com',_binary '','Наталія ','1111','Анатоліївна ','Кузьменко ',1),(' lytvyn_anna@example.com',_binary '','Анна ','1111','Іванівна ','Литвин ',1),('\"chatGptApi\"',_binary '\0','віртуоз','111111','Кпішник','Університетний ',NULL),('biletskyi_dmytro@example.com',_binary '','Дмитро ','1111','Олегович ','Білецький ',1),('bilous_yevhen@example.com',_binary '','Євген ','1111','Васильович ','Білоус ',1),('chernysh_oleksii@example.com',_binary '','Олексій ','1111','Миколайович ','Черниш ',1),('chornovil_andriana@example.com',_binary '','Андріана ','1111','Юріївна ','Чорновіл',1),('dmytrenko_anastasiia@example.com',_binary '','Анастасія ','1111','Ігорівна ','Дмитренко ',1),('honcharuk_viktor@example.com',_binary '','Віктор ','1111','Олексійович','Гончарук ',2),('horbachov_maxim@example.com',_binary '','Максим ','2222','Сергійович ','Горбачов ',NULL),('ivanova_liudmyla@example.com',_binary '','Людмила ','2222','Степанівна ','Іванова ',NULL),('karaimivanna@gmail.com',_binary '','Іванна','123456','Володимирівна','Караїм',1),('koval_andrii@example.com',_binary '','Андрій ','1111','Вікторович ','Коваль ',2),('kovalchuk_iryna@example.com',_binary '','Ірина ','1111','Сергіївна ','Ковальчук ',2),('kovalchuk_viktoriia@example.com',_binary '','Вікторія ','1111','Василівна ','Ковальчук ',2),('kovalenko_oksana@example.com',_binary '','Оксана ','2222','Вікторівна ','Коваленко ',NULL),('kravchenko_oksana@example.com',_binary '','Оксана ','1111','Іванівна ','Кравченко ',2),('kravchuk_vladyslav@example.com',_binary '','Владислав ','1111','Олегович ','Кравчук ',3),('lisenko_vitalii@example.com',_binary '','Віталій ','1111','Олегович ','Лисенко ',3),('lytvynenko_valentyna@example.com',_binary '','Валентина ','1111','Сергіївна ','Литвиненко ',3),('melnyk_serhii@example.com',_binary '','Сергій ','1111','Володимирович ','Мельник ',3),('moroz_oleg@example.com',_binary '','Олег ','2222','Вікторович ','Мороз ',NULL),('pavlenko_mykhailo@example.com',_binary '','Михайло ','1111','Ігорович ','Павленко ',3),('pavliuchenko_nina@example.com',_binary '','Ніна ','1111','Михайлівна ','Павлюченко',3),('petrov_oleksandr@gmail.com',_binary '','Олександр ','1212','Іванович ','Петров',3),('polyakov_stanislav@example.com',_binary '','Станіслав ','1111','Валерійович ','Поляков ',3),('responsible@gmail.com',_binary '','Ольга','1212','Василівна','Свинчук',NULL),('savchenko_tetiana@example.com',_binary '','Тетяна ','1111','Юріївна ','Савченко ',3),('semenova_alina@example.com',_binary '','Аліна ','1111','Сергіївна ','Семенова ',4),('shevchenko_mariia@example.com',_binary '','Марія ','1111','Андріївна ','Шевченко ',4),('shevchenko_yulia@example.com',_binary '','Юлія ','1111','Олександрівна ','Шевченко ',4),('shevchuk_olena@example.com',_binary '','Олена ','1111','Петрівна ','Шевчук ',4),('svyripaivanna@gmail.com',_binary '','Іванна','123456','Володимирівна','Свиріпа',4),('teacher@gmail.com',_binary '','Оксана','123123','Антонівна','Дацюк',1),('teacher2@gmail.com',_binary '','Андрій','123123','Петрович','Мусієнко',4),('teacher3@gmail.com',_binary '','Наталія','123123','Володимирівна','Федорова',3),('tkachuk_igor@example.com',_binary '','Ігор ','1111','Віталійович ','Ткачук ',4),('vasyliev_denis@example.com',_binary '','Денис ','1111','Петрович ','Васильєв ',5),('zaharchenko_volodymyr@example.com',_binary '','Володимир ','2222','Петрович ','Захарченко ',2),('zhukov_pavlo@example.com',_binary '','Павло ','1111','Олександрович ','Жуков ',5);
/*!40000 ALTER TABLE `usr` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-08 14:35:43
