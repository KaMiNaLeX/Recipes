CREATE TABLE role
(
    id          BINARY(16)   NOT NULL,
    name        varchar(6) NOT NULL,
    description varchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;