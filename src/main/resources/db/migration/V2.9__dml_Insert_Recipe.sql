INSERT INTO RECIPE (id, name, cooking_difficulty, cooking_time, positive_votes, negative_votes, author_id,
                    last_modified)
VALUES (unhex(replace(uuid(), '-', '')), 'Borsh', 'EASY', 20, 0, 0,
        (select id from USER u where u.login = 'kamina'), '2019-12-04 20:00:00');
INSERT INTO RECIPE (id, name, cooking_difficulty, cooking_time, positive_votes, negative_votes, author_id,
                    last_modified)
VALUES (unhex(replace(uuid(), '-', '')), 'Fried chicken', 'MEDIUM', 30, 0, 0,
        (select id from USER u where u.login = 'kamina'), '2019-12-04 20:00:00');
INSERT INTO RECIPE (id, name, cooking_difficulty, cooking_time, positive_votes, negative_votes, author_id,
                    last_modified)
VALUES (unhex(replace(uuid(), '-', '')), 'Boiled potato', 'EASY', 20, 0, 0,
        (select id from USER u where u.login = 'kamina'), '2019-12-04 20:00:00');
INSERT INTO RECIPE (id, name, cooking_difficulty, cooking_time, positive_votes, negative_votes, author_id,
                    last_modified)
VALUES (unhex(replace(uuid(), '-', '')), 'Pasta with ketchup', 'EASY', 10, 0, 0,
        (select id from USER u where u.login = 'kamina'), '2019-12-04 20:00:00');
INSERT INTO RECIPE (id, name, cooking_difficulty, cooking_time, positive_votes, negative_votes, author_id,
                    last_modified)
VALUES (unhex(replace(uuid(), '-', '')), 'Pizza', 'HARD', 40, 0, 0,
        (select id from USER u where u.login = 'kamina'), '2019-12-04 20:00:00');