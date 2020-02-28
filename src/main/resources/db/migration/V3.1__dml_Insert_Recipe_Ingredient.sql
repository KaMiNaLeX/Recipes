INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, note_ru, unit, unit_ru)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Borsh'
                                            AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from INGREDIENT i where i.name = 'Potatoes'), 5, '', '', 'PIECE', 'ШТУК');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, note_ru, unit, unit_ru)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Borsh'
                                            AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from INGREDIENT i where i.name = 'Carrot'), 3, '', '', 'PIECE', 'ШТУК');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, note_ru, unit, unit_ru)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Borsh'
                                            AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from INGREDIENT i where i.name = 'Bay Leaves'), 100, '', '', 'LEAF', 'ЛИСТ');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, note_ru, unit, unit_ru)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Pasta with ketchup'
                                            AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from INGREDIENT i where i.name = 'Pasta'), 200, '', '', 'GRAM', 'ГРАММ');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, note_ru, unit, unit_ru)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Pasta with ketchup'
                                            AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from INGREDIENT i where i.name = 'Ketchup'), 0, '', '', 'BY_TASTE', 'ПО_ВКУСУ');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, note_ru, unit, unit_ru)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Omlet'
                                            AND r.author_id = (select id from USER u where u.login = 'Siren')),
        (select id from INGREDIENT i where i.name = 'Egg'), 3, '', '', 'PIECE', 'ШТУК');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, note_ru, unit, unit_ru)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Pizza'
                                            AND r.author_id = (select id from USER u where u.login = 'Siren')),
        (select id from INGREDIENT i where i.name = 'Egg'), 3, '', '', 'PIECE', 'ШТУК');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, note_ru, unit, unit_ru)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Pizza'
                                            AND r.author_id = (select id from USER u where u.login = 'Siren')),
        (select id from INGREDIENT i where i.name = 'Cheese'), 100, '', '', 'GRAM', 'ГРАММ');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, note_ru, unit, unit_ru)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Pizza'
                                            AND r.author_id = (select id from USER u where u.login = 'Siren')),
        (select id from INGREDIENT i where i.name = 'Tomato'), 3, '', '', 'PIECE', 'ШТУК');
