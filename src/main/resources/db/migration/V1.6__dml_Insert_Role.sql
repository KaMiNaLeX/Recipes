INSERT INTO ROLE (id, name,name_ru, description, description_ru)
values (unhex(replace(uuid(), '-', '')), 'AUTHOR','АВТОР', 'can create recipes','может создавать рецепты');
INSERT INTO ROLE (id, name,name_ru, description, description_ru)
values (unhex(replace(uuid(), '-', '')), 'ADMIN','АДМИН', 'all privileges','все привелегии');
INSERT INTO ROLE (id, name,name_ru, description, description_ru)
values (unhex(replace(uuid(), '-', '')), 'VIEWER','НАБЛЮДАТЕЛЬ', 'can only view','может только просматривать рецепты');