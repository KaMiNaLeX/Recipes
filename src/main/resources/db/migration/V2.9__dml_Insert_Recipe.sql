INSERT INTO RECIPE (id, name, name_ru, cooking_difficulty, cooking_difficulty_ru, cooking_time, positive_votes,
                    negative_votes, author_id,
                    last_modified, img_source)
VALUES (unhex(replace(uuid(), '-', '')), 'Borsh', 'Борщ', 'EASY', 'ЛЕГКО', 20, 0, 0,
        (select id from USER u where u.login = 'kamina'), '2019-12-04 20:00:00', 'noImage');
INSERT INTO RECIPE (id, name, name_ru, cooking_difficulty, cooking_difficulty_ru, cooking_time, positive_votes,
                    negative_votes, author_id,
                    last_modified, img_source)
VALUES (unhex(replace(uuid(), '-', '')), 'Pasta with ketchup', 'Макароны с кетчупом', 'EASY', 'ЛЕГКО', 10, 0, 0,
        (select id from USER u where u.login = 'kamina'), '2019-12-04 20:00:00', 'noImage');
INSERT INTO RECIPE (id, name, name_ru, cooking_difficulty, cooking_difficulty_ru, cooking_time, positive_votes,
                    negative_votes, author_id,
                    last_modified, img_source)
VALUES (unhex(replace(uuid(), '-', '')), 'Pizza', 'Пицца', 'HARD', 'ТЯЖЕЛО', 40, 0, 0,
        (select id from USER u where u.login = 'Siren'), '2019-12-04 20:00:00', 'noImage');
INSERT INTO RECIPE (id, name, name_ru, cooking_difficulty, cooking_difficulty_ru, cooking_time, positive_votes,
                    negative_votes, author_id,
                    last_modified, img_source)
VALUES (unhex(replace(uuid(), '-', '')), 'Omlet', 'Омлет', 'EASY', 'ЛЕГКО', 10, 0, 0,
        (select id from USER u where u.login = 'Siren'), '2019-12-13 20:00:00', 'noImage');
