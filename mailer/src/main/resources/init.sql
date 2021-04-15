DROP TABLE IF EXISTS polygraphy_email_list;

CREATE TABLE `polygraphy_email_list` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(120) NULL,
`company` VARCHAR(120) NULL,
`city` VARCHAR(120) NULL,
`email` VARCHAR(120) NOT NULL UNIQUE,
`sent` TINYINT NULL DEFAULT 0,
`subscribed` TINYINT NULL DEFAULT 1,
`md5` VARCHAR(200) NULL,
`last_modyfied` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`id`));

INSERT INTO polygraphy_email_list (`name`, `company`, `email`) VALUES ('Александр', 'LPR.UA', 'admin@lpr.ua');
INSERT INTO polygraphy_email_list (`email`) VALUES ('alexander.zakhariya@gmail.com');
INSERT INTO polygraphy_email_list (`name`, `email`) VALUES ('Alexander', 'master-kap@ukr.net');
