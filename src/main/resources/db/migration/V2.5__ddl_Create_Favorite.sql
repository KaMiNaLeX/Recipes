CREATE TABLE favorite
(
    id        BINARY(16)  NOT NULL,
    user_id   BINARY(16)  NOT NULL,
    recipe_id BINARY(16)  NOT NULL,
    added_at  DATETIME(6) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;