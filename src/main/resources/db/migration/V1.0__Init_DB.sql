use recipes;
CREATE TABLE User
(
    `id`        int          NOT NULL AUTO_INCREMENT,
    `firstname` varchar(255) NOT NULL,
    `lastname`  varchar(255) NOT NULL,
    `email`    varchar(255) NOT NULL UNIQUE,
    `login`     varchar(255) NOT NULL UNIQUE,
    `password`  varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

