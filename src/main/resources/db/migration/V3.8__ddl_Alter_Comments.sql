ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_RECIPE_ID FOREIGN KEY (recipe_id) REFERENCES RECIPE (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_USER_ID FOREIGN KEY (creator_id) REFERENCES USER (id);
