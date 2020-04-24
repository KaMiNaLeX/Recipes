ALTER TABLE recipe_votes
    ADD CONSTRAINT FK_RV_RECIPE_ID FOREIGN KEY (recipe_id) REFERENCES RECIPE (id);

ALTER TABLE recipe_votes
    ADD CONSTRAINT FK_RV_USER_ID FOREIGN KEY (user_id) REFERENCES USER (id);

ALTER TABLE recipe_votes
    ADD CONSTRAINT FK_RV_recipe_user UNIQUE (recipe_id, user_id);
