/*
INSERT INTO recipes.user_role (id, role)
values (unhex(replace(uuid(), '-', '')), 'USER');
INSERT INTO recipes.user_role (id, role)
values (unhex(replace(uuid(), '-', '')), 'ADMIN');