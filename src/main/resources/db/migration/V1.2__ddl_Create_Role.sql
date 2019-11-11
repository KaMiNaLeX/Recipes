CREATE TABLE role
(
    id          BINARY(16)   NOT NULL,
    role        varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;