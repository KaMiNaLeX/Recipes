ALTER TABLE recipe_ingredient
    ADD CONSTRAINT FK_RECIPE_INGREDIENT_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipe (id);

ALTER TABLE recipe_ingredient
    ADD CONSTRAINT FK_RECIPE_INGREDIENT_ingredient_id FOREIGN KEY (ingredient_id) REFERENCES ingredient (id);