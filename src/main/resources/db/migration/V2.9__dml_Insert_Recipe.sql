INSERT INTO RECIPE (id, name, cooking_difficulty, cooking_time, positive_votes, negative_votes, author_id,
                    last_modified, img_source)
VALUES (unhex(replace(uuid(), '-', '')), 'Borsh', 'EASY', 20, 0, 0,
        (select id from USER u where u.login = 'kamina'), '2019-12-04 20:00:00', null);
INSERT INTO RECIPE (id, name, cooking_difficulty, cooking_time, positive_votes, negative_votes, author_id,
                    last_modified, img_source)
VALUES (unhex(replace(uuid(), '-', '')), 'Pasta with ketchup', 'EASY', 10, 0, 0,
        (select id from USER u where u.login = 'kamina'), '2019-12-04 20:00:00', null);
INSERT INTO RECIPE (id, name, cooking_difficulty, cooking_time, positive_votes, negative_votes, author_id,
                    last_modified, img_source)
VALUES (unhex(replace(uuid(), '-', '')), 'Pizza', 'HARD', 40, 0, 0,
        (select id from USER u where u.login = 'Siren'), '2019-12-04 20:00:00', null);
INSERT INTO RECIPE (id, name, cooking_difficulty, cooking_time, positive_votes, negative_votes, author_id,
                    last_modified, img_source)
VALUES (unhex(replace(uuid(), '-', '')), 'Omlet', 'EASY', 10, 0, 0,
        (select id from USER u where u.login = 'Siren'), '2019-12-13 20:00:00', null);