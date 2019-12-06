INSERT INTO category_recipe (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Borsh'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from category c where c.name = 'Salads'));

INSERT INTO category_recipe (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Pasta with ketchup'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from category c where c.name = 'Salads'));

INSERT INTO category_recipe (id, recipe_id, category_id)
values (unhex(replace(uuid(), '-', '')),
        (select id
         from RECIPE r
         where r.name = 'Borsh'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        (select id from category c where c.name = 'Russia kitchen'));