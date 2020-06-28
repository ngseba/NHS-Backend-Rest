-- DATA FOR INITIALIZATION: --------------------------------------------------------------------------------------------


INSERT INTO
	nhs.client_apps (id, name, password, status)
VALUES
	(1, 'NHS_ADMIN_UI', '$2a$10$HWH5u3XDwT/KY2uu/Px87.ieezR0NJPBqKxd1WVK/M06kGbKtaW9y', 1)
ON DUPLICATE KEY UPDATE
	id = id
;

INSERT INTO
    nhs.roles (id, name)
VALUES (1, 'ADMIN'), (2, 'USER')
ON DUPLICATE KEY UPDATE
    id = id
;

INSERT INTO
    nhs.client_apps_roles (client_app_id, role_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE
    role_id = role_id
;

INSERT INTO
	nhs.admins (id, email, password, first_name, last_name, phone_ro, status, role)
VALUES
	(1, 'masteradmin@nhs.ro', '$2a$10$9vU.PAYYj2VNibIj9LvaW.4nFWkzH7qTQhynZ7a97146sucR.s1V2', 'Master', 'Admin', '0040700100100', 1, 'ADMIN')
ON DUPLICATE KEY UPDATE
	id = id
;

-- TODO: Rename this to "data.sql" after truncating/dropping the database security tables. "Spring.jpa.hibernate.ddl-auto" needs to be set to "update" and "schema.sql" must not be available.