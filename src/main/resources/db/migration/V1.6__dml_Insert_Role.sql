INSERT INTO ROLE (id, name,name_ru, description, description_ru)
values (unhex(replace(uuid(), '-', '')), 'USER','КЛИЕНТ', 'can create recipes','может создавать рецепты');
INSERT INTO ROLE (id, name,name_ru, description, description_ru)
values (unhex(replace(uuid(), '-', '')), 'ADMIN','АДМИН', 'all privileges','все привелегии');
