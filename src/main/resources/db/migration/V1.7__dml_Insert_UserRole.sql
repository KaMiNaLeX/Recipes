INSERT INTO USER_ROLE (id, user_id, role_id)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'kamina'),(select id from ROLE r where r.name = 'ADMIN'));
INSERT INTO USER_ROLE (id, user_id, role_id)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'kamina'),(select id from ROLE r where r.name = 'AUTHOR'));
INSERT INTO USER_ROLE (id ,user_id, role_id)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'kamina'),(select id from ROLE r where r.name = 'VIEWER'));
INSERT INTO USER_ROLE (id, user_id, role_id)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'Siren'),(select id from ROLE r where r.name = 'VIEWER'));
INSERT INTO USER_ROLE (id, user_id, role_id)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'Crone'),(select id from ROLE r where r.name = 'VIEWER'));
INSERT INTO USER_ROLE (id, user_id, role_id)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'Plague Maiden'),(select id from ROLE r where r.name = 'VIEWER'));
INSERT INTO USER_ROLE (id, user_id, role_id)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'Sylvan'),(select id from ROLE r where r.name = 'VIEWER'));
INSERT INTO USER_ROLE (id ,user_id, role_id)
values (unhex(replace(uuid(), '-', '')), (select id from USER u where u.login = 'Mucknixer'),(select id from ROLE r where r.name = 'VIEWER'));
