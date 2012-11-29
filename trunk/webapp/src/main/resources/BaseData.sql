DELETE FROM USERS
DELETE FROM VEHICLE
--COMMIT

-- Users
INSERT INTO USERS (ID, ENABLED, FIRSTNAME, ISADMIN, LASTNAME, PASSWORD, USERCLASS, USERNAME)
VALUES
	(1, true, 'Jozef', true, 'Triscik', 'pa165', 1, 'joztri'),
	(2, true, 'Eva', true, 'Neduchalova', 'pa165', 1, 'evaned'),
	(3, true, 'Lukas', true, 'Hajek', 'pa165', 1, 'lukhaj'),
	(4, true, 'Frantisek', true, 'Veverka', 'pa165', 1, 'fravev'),
	(5, true, 'Eva', true, 'Kucirkova', 'pa165', 1, 'evakuc'),              ,
	(6, true, 'User', true, 'User', 'user', 1, 'user'),
	(7, true, 'Admin', true, 'Admin', 'admin', 1, 'admin')

-- Vehicles
INSERT INTO VEHICLE (ID, BRAND, ENGINETYPE, MAXDISTANCE, TYPE, USERCLASS, VIN, YEARMADE)
VALUES
	(1, 'Ford', 'V-16', 30000, 'Mustang', 1, '87488238403318474', 1948),
	(2, 'Mercedes', 'V-8', 27000, 'Benz', 1, '87499d00103318474', 2007),
	(3, 'Peugeot', 'V6 2.7 HDi', 40000, '607', 2, '8-e99d-33k03318474', 2006),
	(4, 'Skoda', '16V 1.4', 18000, 'Fabia Combi', 3, '8-e99d-33xxx318474', 2001),
	(5, 'Skoda', '1.6 GLX', 15000, 'Felicia', 3, '8-e99d-33k03aA-474', 1996),
	(6, 'Tatra', 'T3A', 35000, '815', 3, '3-e99d-33k03318ffX', 1983)
