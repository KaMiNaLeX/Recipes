CREATE TABLE cooking_steps
(
    id             BINARY(16)   NOT NULL,
    number         int          NOT NULL,
    name           varchar(255) NOT NULL,
    name_ru        varchar(255) NOT NULL,
    description    varchar(255) NOT NULL,
    description_ru varchar(255) NOT NULL,
    img_source     varchar(255),
    active         boolean      NOT NULL,
    recipe_id      BINARY(16)   NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;