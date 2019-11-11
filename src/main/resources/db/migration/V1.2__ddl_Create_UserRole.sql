CREATE TABLE user_role
(
    id   BINARY(16)   NOT NULL,
    role varchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;