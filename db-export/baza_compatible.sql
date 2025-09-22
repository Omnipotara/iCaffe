/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 8.0.18 : Database - ecaffe_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ecaffe_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ecaffe_db`;

/*Table structure for table `angazovanje` */

DROP TABLE IF EXISTS `angazovanje`;

CREATE TABLE `angazovanje` (
  `prodavacId` bigint(20) NOT NULL,
  `terminId` bigint(20) NOT NULL,
  `datum` date NOT NULL,
  PRIMARY KEY (`prodavacId`,`terminId`,`datum`),
  KEY `idTermin` (`terminId`),
  CONSTRAINT `angazovanje_ibfk_3` FOREIGN KEY (`prodavacId`) REFERENCES `prodavac` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `angazovanje_ibfk_4` FOREIGN KEY (`terminId`) REFERENCES `termin_dezurstva` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `angazovanje` */

insert  into `angazovanje`(`prodavacId`,`terminId`,`datum`) values 
(1,1,'2025-09-22');

/*Table structure for table `kategorija_musterije` */

DROP TABLE IF EXISTS `kategorija_musterije`;

CREATE TABLE `kategorija_musterije` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `popust` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `kategorija_musterije` */

insert  into `kategorija_musterije`(`id`,`naziv`,`popust`) values 
(1,'Obican',0),
(5,'Nolifer',50),
(6,'Pro',30),
(13,'TestKategorija',15);

/*Table structure for table `musterija` */

DROP TABLE IF EXISTS `musterija`;

CREATE TABLE `musterija` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `kategorijaId` bigint(20) NOT NULL,
  `preostaloVreme` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `musterija_ibfk_1` (`kategorijaId`),
  CONSTRAINT `musterija_ibfk_1` FOREIGN KEY (`kategorijaId`) REFERENCES `kategorija_musterije` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `musterija` */

insert  into `musterija`(`id`,`email`,`username`,`password`,`kategorijaId`,`preostaloVreme`) values 
(1,'gio','gio','gio123',6,10724),
(2,'igrac@gmail.com','igrac','123',6,7200),
(8,'test@test.com','TestKorisnik','123456',1,4600);

/*Table structure for table `musterija_ulogovani` */

DROP TABLE IF EXISTS `musterija_ulogovani`;

CREATE TABLE `musterija_ulogovani` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `musterija_ulogovani` */

/*Table structure for table `prodavac` */

DROP TABLE IF EXISTS `prodavac`;

CREATE TABLE `prodavac` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `prezime` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `prodavac` */

insert  into `prodavac`(`id`,`ime`,`prezime`,`email`,`username`,`password`) values 
(1,'Ognjen','Pavlovic','gio','gio','gio'),
(2,'Admin','Admin','admin','admin','admin');

/*Table structure for table `racun` */

DROP TABLE IF EXISTS `racun`;

CREATE TABLE `racun` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `datum` datetime NOT NULL,
  `ukupnaCena` decimal(10,2) NOT NULL,
  `prodavacId` bigint(20) NOT NULL,
  `musterijaId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `racun_ibfk_1` (`prodavacId`),
  KEY `racun_ibfk_2` (`musterijaId`),
  CONSTRAINT `racun_ibfk_1` FOREIGN KEY (`prodavacId`) REFERENCES `prodavac` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `racun_ibfk_2` FOREIGN KEY (`musterijaId`) REFERENCES `musterija` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `racun` */

insert  into `racun`(`id`,`datum`,`ukupnaCena`,`prodavacId`,`musterijaId`) values 
(45,'2025-09-17 00:00:00',196.00,1,1),
(46,'2025-09-17 00:00:00',70.00,1,1),
(47,'2025-09-17 00:00:00',70.00,1,1),
(52,'2025-09-18 00:00:00',300.00,1,1),
(53,'2025-09-19 13:11:26',100.00,1,1),
(54,'2025-09-19 19:36:28',580.00,1,1),
(55,'2025-09-19 19:55:49',20.00,1,1),
(56,'2025-09-21 01:42:40',91.00,1,1),
(57,'2025-09-21 13:12:14',91.00,1,1),
(58,'2025-09-21 13:12:17',91.00,1,1),
(59,'2025-09-21 13:12:19',91.00,1,1),
(60,'2025-09-21 13:12:21',91.00,1,1),
(72,'2025-09-22 17:02:33',14.00,1,2),
(73,'2025-09-22 17:02:42',147.00,1,1),
(74,'2025-09-22 17:03:28',14.00,1,2),
(76,'2025-09-22 18:19:24',90.00,1,8),
(77,'2025-09-22 18:55:57',200.00,1,8);

/*Table structure for table `stavka_racuna` */

DROP TABLE IF EXISTS `stavka_racuna`;

CREATE TABLE `stavka_racuna` (
  `racunId` bigint(20) NOT NULL,
  `rb` bigint(20) NOT NULL,
  `kolicina` bigint(20) NOT NULL,
  `jedinicnaCena` decimal(10,2) NOT NULL,
  `uslugaId` bigint(20) NOT NULL,
  `cenaStavke` decimal(10,2) NOT NULL,
  PRIMARY KEY (`racunId`,`rb`),
  KEY `rb` (`rb`),
  KEY `stavka_racuna_ibfk_1` (`uslugaId`),
  CONSTRAINT `stavka_racuna_ibfk_1` FOREIGN KEY (`uslugaId`) REFERENCES `usluga` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stavka_racuna` */

insert  into `stavka_racuna`(`racunId`,`rb`,`kolicina`,`jedinicnaCena`,`uslugaId`,`cenaStavke`) values 
(45,1,5,20.00,1,100.00),
(45,2,2,90.00,2,180.00),
(46,1,1,100.00,1000,100.00),
(47,1,1,100.00,1000,100.00),
(52,1,1,20.00,1,20.00),
(52,2,2,90.00,4,180.00),
(52,3,1,100.00,1000,100.00),
(53,1,1,100.00,1000,100.00),
(54,1,15,20.00,1,300.00),
(54,2,2,90.00,4,180.00),
(54,3,1,100.00,1000,100.00),
(55,1,1,20.00,1,20.00),
(56,1,2,20.00,1,40.00),
(56,2,1,90.00,2,90.00),
(57,1,2,20.00,1,40.00),
(57,2,1,90.00,2,90.00),
(58,1,2,20.00,1,40.00),
(58,2,1,90.00,2,90.00),
(59,1,2,20.00,1,40.00),
(59,2,1,90.00,2,90.00),
(60,1,2,20.00,1,40.00),
(60,2,1,90.00,2,90.00),
(72,1,1,20.00,1,20.00),
(73,1,1,20.00,1,20.00),
(73,2,1,100.00,1000,100.00),
(73,3,1,90.00,2,90.00),
(74,1,1,20.00,1,20.00),
(76,1,1,90.00,2,90.00),
(77,1,10,20.00,1,200.00);

/*Table structure for table `termin_dezurstva` */

DROP TABLE IF EXISTS `termin_dezurstva`;

CREATE TABLE `termin_dezurstva` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `smena` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `vremeOd` time NOT NULL,
  `vremeDo` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `termin_dezurstva` */

insert  into `termin_dezurstva`(`id`,`smena`,`vremeOd`,`vremeDo`) values 
(1,'PRVA','08:00:00','16:00:00'),
(2,'DRUGA','16:00:00','00:00:00'),
(3,'TRECA','00:00:00','08:00:00');

/*Table structure for table `usluga` */

DROP TABLE IF EXISTS `usluga`;

CREATE TABLE `usluga` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cena` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `usluga` */

insert  into `usluga`(`id`,`naziv`,`cena`) values 
(1,'Bananica',20.00),
(2,'Koka Kola',90.00),
(4,'Kafa',90.00),
(1000,'Sat vremena',100.00);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
