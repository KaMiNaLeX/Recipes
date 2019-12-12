ALTER TABLE category_recipe
    ADD CONSTRAINT FK_CATEGORY_RECIPE_recipe_category UNIQUE (recipe_id, category_id);