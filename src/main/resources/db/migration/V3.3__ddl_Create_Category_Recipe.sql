CREATE TABLE category_recipe
(
    id          BINARY(16) NOT NULL,
    recipe_id   BINARY(16) NOT NULL,
    category_id BINARY(16) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;