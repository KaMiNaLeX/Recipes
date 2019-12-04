ALTER TABLE recipe
    ADD CONSTRAINT FK_RECIPE_author_id FOREIGN KEY (author_id) REFERENCES USER (id);
ALTER TABLE recipe
    ADD CONSTRAINT FK_RECIPE_author_name UNIQUE (author_id,name);