CREATE TABLE category
(
    id             BINARY(16)   NOT NULL,
    name           varchar(255) NOT NULL UNIQUE,
    name_ru        varchar(255) NOT NULL UNIQUE,
    description    varchar(255) NOT NULL,
    description_ru varchar(255) NOT NULL,
    tag            varchar(255) NOT NULL,
    tag_ru         varchar(255) NOT NULL,
    img_source     varchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
