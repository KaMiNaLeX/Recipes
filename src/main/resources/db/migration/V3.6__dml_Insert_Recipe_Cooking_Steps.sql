INSERT INTO recipe_cooking_steps(id, description, description_ru, img_source, recipe_id)
values (unhex(replace(uuid(), '-', '')), 'cooking step', 'шаг приготовления',
        'http://localhost:4200/getFile/noImage.png',
        (select id
         from RECIPE r
         where r.name = 'Borsh'
           AND r.author_id = (select id from USER u where u.login = 'kamina')));
INSERT INTO recipe_cooking_steps(id, description, description_ru, img_source, recipe_id)
values (unhex(replace(uuid(), '-', '')), 'cooking step', 'шаг приготовления',
        'http://localhost:4200/getFile/noImage.png',
        (select id
         from RECIPE r
         where r.name = 'Pasta with ketchup'
           AND r.author_id = (select id from USER u where u.login = 'kamina')));
INSERT INTO recipe_cooking_steps(id, description, description_ru, img_source, recipe_id)
values (unhex(replace(uuid(), '-', '')), 'cooking step', 'шаг приготовления',
        'http://localhost:4200/getFile/noImage.png',
        (select id
         from RECIPE r
         where r.name = 'Pizza'
           AND r.author_id = (select id from USER u where u.login = 'Siren')));
INSERT INTO recipe_cooking_steps(id, description, description_ru, img_source, recipe_id)
values (unhex(replace(uuid(), '-', '')), 'cooking step', 'шаг приготовления',
        'http://localhost:4200/getFile/noImage.png',
        (select id
         from RECIPE r
         where r.name = 'Omlet'
           AND r.author_id = (select id from USER u where u.login = 'Siren')))
