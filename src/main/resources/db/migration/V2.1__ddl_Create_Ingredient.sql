CREATE TABLE ingredient
(
    id       BINARY(16)   NOT NULL,
    name     varchar(255) NOT NULL,
    calories double       NOT NULL,
    type     varchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;