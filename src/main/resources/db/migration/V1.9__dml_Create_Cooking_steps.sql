CREATE TABLE cooking_steps
(
    id          BINARY(16)   NOT NULL,
    number      int NOT NULL,
    name        varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    content     blob NOT NULL,
    -- thumbnail
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;