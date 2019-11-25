INSERT INTO ROLE (id, name, description)
values (unhex(replace(uuid(), '-', '')), 'AUTHOR', 'can create recipes');
INSERT INTO ROLE (id, name, description)
values (unhex(replace(uuid(), '-', '')), 'ADMIN', 'all privileges');
INSERT INTO ROLE (id, name, description)
values (unhex(replace(uuid(), '-', '')), 'VIEWER', 'can only view');