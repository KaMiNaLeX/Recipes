ALTER TABLE cooking_steps
    ADD CONSTRAINT FK_COOKING_STEPS_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipe (id);