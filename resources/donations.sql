CREATE DATABASE aap_donations
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

CREATE TABLE `donations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `country` varchar(500) DEFAULT NULL,
  `state` varchar(500) DEFAULT NULL,
  `district` varchar(500) DEFAULT NULL,
  `transactionId` varchar(500) DEFAULT NULL,
  `amountStr` varchar(45) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `dateStr` varchar(100) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63224 DEFAULT CHARSET=utf8