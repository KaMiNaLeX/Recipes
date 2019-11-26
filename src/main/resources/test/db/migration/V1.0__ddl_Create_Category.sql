CREATE TABLE category
(
    id          BINARY(16)   NOT NULL,
    name        varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    tag         varchar(255) NOT NULL,
    PRIMARY KEY (id)
);