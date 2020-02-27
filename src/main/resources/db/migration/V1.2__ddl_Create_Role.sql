CREATE TABLE role
(
    id             BINARY(16)   NOT NULL,
    name           varchar(6)   NOT NULL,
    name_ru        varchar(11)  NOT NULL,
    description    varchar(255) NOT NULL,
    description_ru varchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;