CREATE TABLE user
(
    id        int          NOT NULL AUTO_INCREMENT,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL,
    email     varchar(255) NOT NULL UNIQUE,
    login     varchar(255) NOT NULL UNIQUE,
    password  varchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


