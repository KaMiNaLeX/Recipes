INSERT INTO recipe_category (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Borsh'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from category c where c.name = 'Soups'));

INSERT INTO recipe_category (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Borsh'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from category c where c.name = 'Russian kitchen'));

INSERT INTO recipe_category (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Pasta with ketchup'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from category c where c.name = 'Vegetarian food'));

INSERT INTO recipe_category (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Pasta with ketchup'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from category c where c.name = 'Dinner'));

INSERT INTO recipe_category (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Omlet'
           AND r.author_id = (select id from USER u where u.login = 'Siren')),
        (select id from category c where c.name = 'Breakfast'));
INSERT INTO recipe_category (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Omlet'
           AND r.author_id = (select id from USER u where u.login = 'Siren')),
        (select id from category c where c.name = 'Vegetarian food'));

INSERT INTO recipe_category (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Pizza'
           AND r.author_id = (select id from USER u where u.login = 'Siren')),
        (select id from category c where c.name = 'Vegetarian food'));

INSERT INTO recipe_category (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Pizza'
           AND r.author_id = (select id from USER u where u.login = 'Siren')),
        (select id from category c where c.name = 'Dinner'));

