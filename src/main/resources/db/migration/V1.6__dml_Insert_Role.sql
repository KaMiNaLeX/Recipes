INSERT INTO ROLE (id, role, description)
values (unhex(replace(uuid(), '-', '')), 'USER', 'simple user');
INSERT INTO ROLE (id, role, description)
values (unhex(replace(uuid(), '-', '')), 'ADMIN', 'all privileges');