CREATE TABLE user_role
(
    created_on DATETIME(6) NOT NULL,
    user_id BINARY(16) NOT NULL,
    role_id BINARY(16) NOT NULL,
    PRIMARY KEY (created_on)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;