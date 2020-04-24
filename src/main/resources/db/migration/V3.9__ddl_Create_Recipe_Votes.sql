CREATE TABLE recipe_votes
(
    id            BINARY(16) NOT NULL,
    recipe_id     BINARY(16) NOT NULL,
    user_id       BINARY(16) NOT NULL,
    positive_vote boolean    NOT NULL,
    negative_vote boolean    NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
