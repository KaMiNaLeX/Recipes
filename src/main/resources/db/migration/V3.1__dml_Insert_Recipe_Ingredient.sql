INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, unit)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Borsh'
                                            AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from INGREDIENT i where i.name = 'Potato'), 1200, '', 'GRAMM');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, unit)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Borsh'
                                            AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from INGREDIENT i where i.name = 'Pike'), 1000, '', 'GRAMM');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, amount, note, unit)
values (unhex(replace(uuid(), '-', '')), (select id
                                          from RECIPE r
                                          where r.name = 'Borsh'
                                            AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from INGREDIENT i where i.name = 'Milk'), 100, '', 'MML');
