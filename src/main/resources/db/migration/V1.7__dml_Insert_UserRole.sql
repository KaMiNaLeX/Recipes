INSERT INTO USER_ROLE (created_on, user_id, role_id)
values ('2019-11-16 12:00:00', (select id from USER u where u.login = 'kamina'),(select id from ROLE r where r.name = 'ADMIN'));
INSERT INTO USER_ROLE (created_on, user_id, role_id)
values ('2019-11-16 12:00:01', (select id from USER u where u.login = 'kamina'),(select id from ROLE r where r.name = 'USER'));
INSERT INTO USER_ROLE (created_on, user_id, role_id)
values ('2019-11-16 12:00:03', (select id from USER u where u.login = 'Siren'),(select id from ROLE r where r.name = 'USER'));
INSERT INTO USER_ROLE (created_on, user_id, role_id)
values ('2019-11-16 12:00:04', (select id from USER u where u.login = 'Crone'),(select id from ROLE r where r.name = 'USER'));
INSERT INTO USER_ROLE (created_on, user_id, role_id)
values ('2019-11-16 12:00:05', (select id from USER u where u.login = 'Plague Maiden'),(select id from ROLE r where r.name = 'USER'));
INSERT INTO USER_ROLE (created_on, user_id, role_id)
values ('2019-11-16 12:00:06', (select id from USER u where u.login = 'Sylvan'),(select id from ROLE r where r.name = 'USER'));
INSERT INTO USER_ROLE (created_on ,user_id, role_id)
values ('2019-11-16 12:00:07', (select id from USER u where u.login = 'Mucknixer'),(select id from ROLE r where r.name = 'USER'));
