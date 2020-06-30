-- DATA FOR INITIALIZATION: --------------------------------------------------------------------------------------------

-- OUR 2 CLIENT APPS, ADMIN UI AND MEDICOM: ----------------------------------------------------------------------------

INSERT INTO
	nhs.client_apps (id, name, password, status)
VALUES
	(1, 'NHS_ADMIN_UI', '$2a$10$HWH5u3XDwT/KY2uu/Px87.ieezR0NJPBqKxd1WVK/M06kGbKtaW9y', 1),
	(2, 'MEDICOM', '$2a$10$E.ieaVi1glgq6YN0PwcG7eCrz4HRAcahbGfyp/66bDPM0WnP.QaU6', 1)
ON DUPLICATE KEY UPDATE
	id = id
;

-- OUR 2 POSSIBLE CLIENT APP ROLES (INCLUDING ADMINS' ONLY ROLE): ------------------------------------------------------

INSERT INTO
    nhs.roles (id, name)
VALUES (1, 'ADMIN'), (2, 'USER')
ON DUPLICATE KEY UPDATE
    id = id
;

-- ASSOCIATING THE 2 CLIENT APPS WITH THEIR ROLES: ---------------------------------------------------------------------

INSERT INTO
    nhs.client_apps_roles (client_app_id, role_id)
VALUES (1, 1), (2, 2)
ON DUPLICATE KEY UPDATE
    role_id = role_id
;

-- THE ADMIN UI MASTER ADMIN: ------------------------------------------------------------------------------------------

INSERT INTO
	nhs.admins (id, email, password, first_name, last_name, phone_ro, status, role)
VALUES
	(1, 'masteradmin@nhs.ro', '$2a$10$9vU.PAYYj2VNibIj9LvaW.4nFWkzH7qTQhynZ7a97146sucR.s1V2', 'Master', 'Admin', '0040700100100', 1, 'ADMIN')
ON DUPLICATE KEY UPDATE
	id = id
;

-- THE FIRST NHS-REGISTERED ENTITIES (AND THEIR RELATIONSHIPS): --------------------------------------------------------

INSERT INTO
	nhs.institutions (id, type, cui, name, address, phone_ro, email, website)
VALUES
	(1, 'GP_OFFICE', 'RO0000000001', 'Health SRL', 'Str. Sanatatii nr. 1, Cluj-Napoca', '0040264100100', 'contact@health.ro', 'health.ro')
ON DUPLICATE KEY UPDATE
	id = id
;

INSERT INTO
	nhs.doctors (cnp, title, first_name, last_name, email, phone_ro, medical_license, specialties)
VALUES
	('1900101123456', 'DR', 'Adam', 'Evesman', 'adam.evesman@health.ro', '0040700101101', '0000000001', 'FAMILY_MEDICINE')
ON DUPLICATE KEY UPDATE
	cnp = cnp
;

INSERT INTO
	nhs.doctors_institutions (doctor_cnp, institution_id)
VALUES
	('1900101123456', 1)
ON DUPLICATE KEY UPDATE
	doctor_cnp = doctor_cnp,
	institution_id = institution_id
;
