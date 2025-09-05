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
  PRIMARY KEY (`prodavacId`,`terminId`),
  KEY `idTermin` (`terminId`),
  CONSTRAINT `angazovanje_ibfk_3` FOREIGN KEY (`prodavacId`) REFERENCES `prodavac` (`id`),
  CONSTRAINT `angazovanje_ibfk_4` FOREIGN KEY (`terminId`) REFERENCES `termin_dezurstva` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `angazovanje` */

/*Table structure for table `kategorija_musterije` */

DROP TABLE IF EXISTS `kategorija_musterije`;

CREATE TABLE `kategorija_musterije` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) DEFAULT NULL,
  `popust` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `kategorija_musterije` */

insert  into `kategorija_musterije`(`id`,`naziv`,`popust`) values 
(1,'Obican',0),
(2,'Pro',50);

/*Table structure for table `musterija` */

DROP TABLE IF EXISTS `musterija`;

CREATE TABLE `musterija` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `kategorijaId` bigint(20) DEFAULT NULL,
  `preostaloVreme` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `musterija_ibfk_1` (`kategorijaId`),
  CONSTRAINT `musterija_ibfk_1` FOREIGN KEY (`kategorijaId`) REFERENCES `kategorija_musterije` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `musterija` */

insert  into `musterija`(`id`,`email`,`username`,`password`,`kategorijaId`,`preostaloVreme`) values 
(1,'gio','gio','gio',1,3900);

/*Table structure for table `musterija_ulogovani` */

DROP TABLE IF EXISTS `musterija_ulogovani`;

CREATE TABLE `musterija_ulogovani` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `kategorijaId` bigint(20) DEFAULT NULL,
  `preostaloVreme` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `musterija_ibfk_1` (`kategorijaId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `musterija_ulogovani` */

/*Table structure for table `prodavac` */

DROP TABLE IF EXISTS `prodavac`;

CREATE TABLE `prodavac` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) DEFAULT NULL,
  `prezime` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
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
  `datum` date NOT NULL,
  `ukupnacena` decimal(10,0) DEFAULT NULL,
  `prodavacId` bigint(20) NOT NULL,
  `musterijaId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `prodavacId` (`prodavacId`),
  KEY `musterijaId` (`musterijaId`),
  CONSTRAINT `racun_ibfk_1` FOREIGN KEY (`prodavacId`) REFERENCES `prodavac` (`id`),
  CONSTRAINT `racun_ibfk_2` FOREIGN KEY (`musterijaId`) REFERENCES `musterija` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `racun` */

/*Table structure for table `stavka_racuna` */

DROP TABLE IF EXISTS `stavka_racuna`;

CREATE TABLE `stavka_racuna` (
  `racunId` bigint(20) NOT NULL,
  `rb` bigint(20) NOT NULL AUTO_INCREMENT,
  `kolicina` bigint(20) NOT NULL,
  `jedinicnaCena` decimal(10,0) NOT NULL,
  `uslugaId` bigint(20) NOT NULL,
  `cenaStavke` decimal(10,0) GENERATED ALWAYS AS ((`jedinicnaCena` * `kolicina`)) STORED,
  PRIMARY KEY (`racunId`,`rb`),
  KEY `rb` (`rb`),
  KEY `uslugaId` (`uslugaId`),
  CONSTRAINT `stavka_racuna_ibfk_1` FOREIGN KEY (`uslugaId`) REFERENCES `usluga` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stavka_racuna` */

/*Table structure for table `termin_dezurstva` */

DROP TABLE IF EXISTS `termin_dezurstva`;

CREATE TABLE `termin_dezurstva` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `smena` varchar(20) DEFAULT NULL,
  `vremeOd` time DEFAULT NULL,
  `vremeDo` time DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `termin_dezurstva` */

/*Table structure for table `usluga` */

DROP TABLE IF EXISTS `usluga`;

CREATE TABLE `usluga` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) DEFAULT NULL,
  `cena` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `usluga` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
