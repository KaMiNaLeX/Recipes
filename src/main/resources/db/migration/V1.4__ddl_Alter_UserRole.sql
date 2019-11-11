/*
ALTER TABLE user_role
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE user_role
    ADD CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES role (id);
 */