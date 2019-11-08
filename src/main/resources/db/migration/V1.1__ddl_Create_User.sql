CREATE TABLE user
(
  id         BINARY(16)   NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name  varchar(255) NOT NULL,
  email      varchar(255) NOT NULL UNIQUE,
  login      varchar(255) NOT NULL UNIQUE,
  password   varchar(255) NOT NULL,
  user_role  varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


