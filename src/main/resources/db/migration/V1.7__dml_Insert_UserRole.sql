INSERT INTO USER_ROLE (user_id, role_id)
values ((select id from USER u where u.login = 'kamina'),(select id from ROLE r where r.role = 'ADMIN'));
INSERT INTO USER_ROLE (user_id, role_id)
values ((select id from USER u where u.login = 'kamina'),(select id from ROLE r where r.role = 'AUTHOR'));
INSERT INTO USER_ROLE (user_id, role_id)
values ((select id from USER u where u.login = 'kamina'),(select id from ROLE r where r.role = 'VIEWER'));
INSERT INTO USER_ROLE (user_id, role_id)
values ((select id from USER u where u.login = 'Siren'),(select id from ROLE r where r.role = 'VIEWER'));
INSERT INTO USER_ROLE (user_id, role_id)
values ((select id from USER u where u.login = 'Crone'),(select id from ROLE r where r.role = 'VIEWER'));
INSERT INTO USER_ROLE (user_id, role_id)
values ((select id from USER u where u.login = 'Plague Maiden'),(select id from ROLE r where r.role = 'VIEWER'));
INSERT INTO USER_ROLE (user_id, role_id)
values ((select id from USER u where u.login = 'Sylvan'),(select id from ROLE r where r.role = 'VIEWER'));
INSERT INTO USER_ROLE (user_id, role_id)
values ((select id from USER u where u.login = 'Mucknixer'),(select id from ROLE r where r.role = 'VIEWER'));
