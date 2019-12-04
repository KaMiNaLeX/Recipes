CREATE TABLE recipe_ingredient
(
    id            BINARY(16)  NOT NULL,
    recipe_id     BINARY(16)  NOT NULL,
    ingredient_id BINARY(16)  NOT NULL,
    amount        float       NOT NULL, -- float?
    note          varchar(255),
    unit          varchar(50) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;