INSERT INTO favorite (id, user_id, recipe_id, added_at)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'kamina'),
        (select id
         from RECIPE r
         where r.name = 'Borsh'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        '2019-12-04 20:00:00');

INSERT INTO favorite (id, user_id, recipe_id, added_at)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'kamina'),
        (select id
         from RECIPE r
         where r.name = 'Fried chicken'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        '2019-12-04 20:00:00');

INSERT INTO favorite (id, user_id, recipe_id, added_at)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'kamina'),
        (select id
         from RECIPE r
         where r.name = 'Boiled potato'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        '2019-12-04 20:00:00');

INSERT INTO favorite (id, user_id, recipe_id, added_at)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'kamina'),
        (select id
         from RECIPE r
         where r.name = 'Pasta with ketchup'
           AND r.author_id = (select id from USER u where u.login = 'kamina')), '2019-12-04 20:00:00');

INSERT INTO favorite (id, user_id, recipe_id, added_at)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'kamina'),
        (select id
         from RECIPE r
         where r.name = 'Pizza'
           AND r.author_id = (select id from USER u where u.login = 'kamina')),
        '2019-12-04 20:00:00');