-- DATA FOR INITIALIZATION: --------------------------------------------------------------------------------------------

INSERT IGNORE INTO nhs.client_apps (id, name, password, status) VALUES (1, 'NHS Admin UI', '$2a$10$HWH5u3XDwT/KY2uu/Px87.ieezR0NJPBqKxd1WVK/M06kGbKtaW9y', 1);

INSERT IGNORE INTO nhs.roles (id, name) VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO nhs.client_apps_roles (client_app_id, role_id) VALUES (1, 1);

-- TODO: Rename this file to "data.sql" when necessary.