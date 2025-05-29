ALTER TABLE users_roles
ADD CONSTRAINT fk_users_roles_user_id FOREIGN KEY(user_id) REFERENCES users(user_id),
ADD CONSTRAINT fk_users_roles_role_id FOREIGN KEY(role_id) REFERENCES roles(role_id);