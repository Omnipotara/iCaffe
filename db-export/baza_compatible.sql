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
  CONSTRAINT `angazovanje_ibfk_3` FOREIGN KEY (`prodavacId`) REFERENCES `prodavac` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `angazovanje_ibfk_4` FOREIGN KEY (`terminId`) REFERENCES `termin_dezurstva` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `angazovanje` */

insert  into `angazovanje`(`prodavacId`,`terminId`,`datum`) values 
(1,1,'2025-09-10'),
(1,2,'2025-09-11'),
(1,3,'2025-09-11');

/*Table structure for table `kategorija_musterije` */

DROP TABLE IF EXISTS `kategorija_musterije`;

CREATE TABLE `kategorija_musterije` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) DEFAULT NULL,
  `popust` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `kategorija_musterije` */

insert  into `kategorija_musterije`(`id`,`naziv`,`popust`) values 
(1,'Obican',0),
(5,'Nolifer',50),
(6,'Pro',30);

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `musterija` */

insert  into `musterija`(`id`,`email`,`username`,`password`,`kategorijaId`,`preostaloVreme`) values 
(1,'gio','gio','gio',1,3570);

/*Table structure for table `musterija_ulogovani` */

DROP TABLE IF EXISTS `musterija_ulogovani`;

CREATE TABLE `musterija_ulogovani` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `ukupnaCena` decimal(10,2) DEFAULT NULL,
  `prodavacId` bigint(20) NOT NULL,
  `musterijaId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `racun_ibfk_1` (`prodavacId`),
  KEY `racun_ibfk_2` (`musterijaId`),
  CONSTRAINT `racun_ibfk_1` FOREIGN KEY (`prodavacId`) REFERENCES `prodavac` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `racun_ibfk_2` FOREIGN KEY (`musterijaId`) REFERENCES `musterija` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `racun` */

insert  into `racun`(`id`,`datum`,`ukupnaCena`,`prodavacId`,`musterijaId`) values 
(30,'2025-09-11',0.00,1,1),
(31,'2025-09-11',12045.00,1,1),
(32,'2025-09-11',60522.00,1,1),
(34,'2025-09-12',200.00,1,1),
(35,'2025-09-12',200.00,1,1),
(38,'2025-09-12',100.00,1,1);

/*Table structure for table `stavka_racuna` */

DROP TABLE IF EXISTS `stavka_racuna`;

CREATE TABLE `stavka_racuna` (
  `racunId` bigint(20) NOT NULL,
  `rb` bigint(20) NOT NULL,
  `kolicina` bigint(20) NOT NULL,
  `jedinicnaCena` decimal(10,2) NOT NULL,
  `uslugaId` bigint(20) NOT NULL,
  `cenaStavke` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`racunId`,`rb`),
  KEY `rb` (`rb`),
  KEY `stavka_racuna_ibfk_1` (`uslugaId`),
  CONSTRAINT `stavka_racuna_ibfk_1` FOREIGN KEY (`uslugaId`) REFERENCES `usluga` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stavka_racuna` */

insert  into `stavka_racuna`(`racunId`,`rb`,`kolicina`,`jedinicnaCena`,`uslugaId`,`cenaStavke`) values 
(30,0,10,9.00,1,90.00),
(31,1,1,0.00,3,0.00),
(31,2,5,9.00,1,45.00),
(31,3,20,600.00,4,12000.00),
(32,1,100,600.00,4,60000.00),
(32,2,58,9.00,1,522.00),
(34,1,2,100.00,1000,200.00),
(35,1,2,100.00,1000,200.00),
(38,1,1,100.00,1000,100.00);

/*Table structure for table `termin_dezurstva` */

DROP TABLE IF EXISTS `termin_dezurstva`;

CREATE TABLE `termin_dezurstva` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `smena` varchar(20) DEFAULT NULL,
  `vremeOd` time DEFAULT NULL,
  `vremeDo` time DEFAULT NULL,
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
  `naziv` varchar(50) DEFAULT NULL,
  `cena` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `usluga` */

insert  into `usluga`(`id`,`naziv`,`cena`) values 
(1,'Koka Kola Batoouu',9.00),
(3,'Koka Kola ',0.00),
(4,'dsfF',600.00),
(1000,'Sat vremena',100.00);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
