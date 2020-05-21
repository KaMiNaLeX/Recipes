CREATE TABLE recipe_comments
(
    id            BINARY(16)    NOT NULL,
    recipe_id     BINARY(16)    NOT NULL,
    creator_id    BINARY(16)    NOT NULL,
    text          varchar(1000) NOT NULL,
    creation_date DATETIME(6)   NOT NULL,
    update_date   DATETIME(6)   NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
