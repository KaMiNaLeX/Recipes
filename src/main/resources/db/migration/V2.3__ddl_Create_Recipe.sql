CREATE TABLE recipe
(
    id                    BINARY(16)   NOT NULL,
    name                  varchar(255) NOT NULL,
    name_ru               varchar(255) NOT NULL,
    cooking_difficulty    varchar(50)  NOT NULL,
    cooking_difficulty_ru varchar(50)  NOT NULL,-- defines cocking difficulty for, will be stores as enum
    cooking_time          int          NOT NULL, -- total time in seconds
    positive_votes        int          NOT NULL,
    negative_votes        int          NOT NULL,
    author_id             BINARY(16)   NOT NULL,
    last_modified         DATETIME(6)  NOT NULL,
    img_source            varchar(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;