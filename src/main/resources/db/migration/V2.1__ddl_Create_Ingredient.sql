CREATE TABLE ingredient
(
    id          BINARY(16)  NOT NULL,
    name        varchar(50) NOT NULL UNIQUE,
    name_ru        varchar(50) NOT NULL UNIQUE,
    description varchar(255),
    description_ru varchar(255),
    calories    double      NOT NULL,
    type        varchar(50) NOT NULL,
    type_ru        varchar(50) NOT NULL,-- enum
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;