CREATE TABLE ingredient
(
    id          BINARY(16)  NOT NULL,
    name        varchar(50) NOT NULL UNIQUE,
    description varchar(255),
    calories    double      NOT NULL,
    type        varchar(50) NOT NULL, -- enum
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;