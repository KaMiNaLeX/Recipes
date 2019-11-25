ALTER TABLE user_role
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES USER (id);
ALTER TABLE user_role
    ADD CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES ROLE (id);
ALTER TABLE user_role
    ADD CONSTRAINT UC_user_role UNIQUE (user_id,role_id);