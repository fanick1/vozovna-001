CREATE TABLE USERS
(
	ID BIGINT NOT NULL PRIMARY KEY,
	ENABLED SMALLINT NOT NULL,
	FIRSTNAME VARCHAR(50) NOT NULL,
	ISADMIN SMALLINT NOT NULL,
	LASTNAME VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(255) NOT NULL,
	USERCLASS INT NOT NULL,
	USERNAME VARCHAR(255) NOT NULL
);

CREATE TABLE VEHICLE
(
	ID BIGINT NOT NULL PRIMARY KEY,
	BRAND VARCHAR(20) NOT NULL,
	ENGINETYPE VARCHAR(20) NOT NULL,
	MAXDISTANCE INT NOT NULL,
	TYPE VARCHAR(40) NOT NULL,
	USERCLASS INT NOT NULL,
	VIN VARCHAR(17) NOT NULL,
	YEARMADE INT NOT NULL
);

CREATE TABLE DRIVES
(
 	ID BIGINT NOT NULL PRIMARY KEY,
 	ID_USER BIGINT NOT NULL,
 	ID_VEHICLE BIGINT NOT NULL,
 	DATE_FROM TIMESTAMP NOT NULL,
 	DATE_TO TIMESTAMP NOT NULL,
 	DISTANCE INT,
 	DRIVE_STATE INT NOT NULL
);

ALTER TABLE DRIVES
ADD FOREIGN KEY (ID_USER)
REFERENCES USERS(ID);

ALTER TABLE DRIVES
ADD FOREIGN KEY (ID_VEHICLE)
REFERENCES VEHICLE(ID);

CREATE TABLE SERVICEINTERVAL
(
	ID BIGINT NOT NULL PRIMARY KEY,
	VEHICLE_ID BIGINT NOT NULL,
	DESCRIPTION VARCHAR(255) NOT NULL,
	INSPECTIONINTERVAL INT NOT NULL
);

CREATE TABLE SERVICEINTERVAL_DATED
(
	SERVICEINTERVAL_ID BIGINT NOT NULL,
	DATED DATE NOT NULL
);

ALTER TABLE SERVICEINTERVAL_DATED
ADD FOREIGN KEY (SERVICEINTERVAL_ID)
REFERENCES SERVICEINTERVAL(ID);


-- Musi byt v tomto poradi
DELETE FROM SERVICEINTERVAL_DATED;
DELETE FROM SERVICEINTERVAL;
DELETE FROM DRIVES;
DELETE FROM USERS;
DELETE FROM VEHICLE;



-- Users
INSERT INTO USERS (ID, ENABLED, FIRSTNAME, ISADMIN, LASTNAME, PASSWORD, USERCLASS, USERNAME)
VALUES
	(1, true, 'Jozef', false, 'Triscik', 'pa165', 1, 'joztri'),
	(2, true, 'Eva', false, 'Neduchalova', 'pa165', 1, 'evaned'),
	(3, true, 'Lukas', false, 'Hajek', 'pa165', 1, 'lukhaj'),
	(4, true, 'Frantisek', false, 'Veverka', 'pa165', 1, 'fravev'),
	(5, true, 'Eva', false, 'Kucirkova', 'pa165', 1, 'evakuc'),
	(6, true, 'User', false, 'User', 'user', 1, 'user'),
	(7, true, 'Admin', true, 'Admin', 'admin', 1, 'admin');

-- Vehicles
INSERT INTO VEHICLE (ID, BRAND, ENGINETYPE, MAXDISTANCE, TYPE, USERCLASS, VIN, YEARMADE)
VALUES
	(1, 'Ford', 'V-16', 30000, 'Mustang', 1, '87488238403318474', 1948),
	(2, 'Mercedes', 'V-8', 27000, 'Benz', 1, '87499d00103318474', 2007),
	(3, 'Peugeot', 'V6 2.7 HDi', 40000, '607', 2, '8-e99d-33k0331844', 2006),
	(4, 'Skoda', '16V 1.4', 18000, 'Fabia Combi', 3, '8-e99d-33xxx31874', 2001),
	(5, 'Skoda', '1.6 GLX', 15000, 'Felicia', 3, '8-e99d-33k03aA-44', 1996),
	(6, 'Tatra', 'T3A', 35000, '815', 3, '3-e99d-33k03318fX', 1983);

-- Drives
INSERT INTO DRIVES (ID, ID_USER, ID_VEHICLE, DATE_FROM, DATE_TO, DISTANCE, DRIVE_STATE)
VALUES
	(1, 1, 1, '2012-03-18 00:00:00', '2012-03-21 00:00:00', 0, 1),   -- joztri, reserved
	(2, 1, 2, '2011-05-14 00:00:00', '2011-05-15 00:00:00', 0, 4),   -- joztri, cancelled
	(3, 1, 1, '2012-01-30 00:00:00', '2012-02-02 00:00:00', 25, 3),  -- joztri, finished
	(4, 1, 6, '2012-06-04 00:00:00', '2012-06-10 00:00:00', 0, 2),   -- joztri, ongoing

	(5, 2, 1, '2012-03-22 00:00:00', '2012-03-24 00:00:00', 0, 1),   -- evaned, reserved
	(6, 2, 2, '2011-08-14 00:00:00', '2011-08-15 00:00:00', 0, 4),   -- evaned, cancelled
	(7, 2, 2, '2012-01-12 00:00:00', '2012-02-14 00:00:00', 114, 3), -- evaned, finished
	(8, 2, 1, '2012-06-03 00:00:00', '2012-06-10 00:00:00', 0, 2),   -- evaned, ongoing

	(9, 3, 1, '2012-05-19 00:00:00', '2012-05-24 00:00:00', 0, 1),   -- lukhaj, reserved
	(10, 3, 2, '2011-05-19 00:00:00', '2011-05-24 00:00:00', 0, 4),  -- lukhaj, cancelled
	(11, 3, 2, '2012-05-16 00:00:00', '2012-05-18 00:00:00', 99, 3), -- lukhaj, finished
	(12, 3, 3, '2012-06-03 00:00:00', '2012-06-10 00:00:00', 0, 2),  -- lukhaj, ongoing

	(13, 4, 1, '2012-11-12 00:00:00', '2012-11-12 00:00:00', 0, 1),   -- fravev, reserved
	(14, 4, 3, '2011-05-19 00:00:00', '2011-05-24 00:00:00', 0, 4),  -- fravev, cancelled
	(15, 4, 2, '2012-02-01 00:00:00', '2012-02-03 00:00:00', 35, 3), -- fravev, finished
	(16, 4, 4, '2012-06-02 00:00:00', '2012-06-05 00:00:00', 0, 2),  -- fravev, ongoing

	(17, 5, 1, '2012-11-12 00:00:00', '2012-11-12 00:00:00', 0, 1),  -- evakuc, reserved
	(18, 5, 3, '2011-05-12 00:00:00', '2011-05-13 00:00:00', 0, 4),  -- evakuc, cancelled
	(19, 5, 2, '2012-02-20 00:00:00', '2012-02-23 00:00:00', 17, 3), -- evakuc, finished
	(20, 5, 5, '2012-06-01 00:00:00', '2012-06-01 00:00:00', 0, 2);  -- evakuc, ongoing

-- Service intervals
INSERT INTO SERVICEINTERVAL (ID, VEHICLE_ID, DESCRIPTION, INSPECTIONINTERVAL)
VALUES
	(1, 1, 'Vymena koles', 183),
	(2, 2, 'Vymena koles', 183),
	(3, 3, 'Vymena koles', 183),
	(4, 4, 'Vymena koles', 183),
	(5, 5, 'Vymena koles', 183),
	(6, 6, 'Vymena koles', 183),

	(7, 1, 'Kontrola brzd', 365),
	(8, 2, 'Kontrola brzd', 365),
	(9, 3, 'Kontrola brzd', 365),
	(10, 4, 'Kontrola brzd', 365),
	(11, 5, 'Kontrola brzd', 365),
	(12, 6, 'Kontrola brzd', 365),

	(13, 1, 'Standardni servis', 548),
	(14, 2, 'Standardni servis', 548),
	(15, 3, 'Standardni servis', 548),
	(16, 4, 'Standardni servis', 548),
	(17, 5, 'Standardni servis', 548),
	(18, 6, 'Standardni servis', 548);

-- Service interval dates
INSERT INTO SERVICEINTERVAL_DATED (SERVICEINTERVAL_ID, DATED)
VALUES
	(1,'2012-03-03'),
	(1,'2012-09-03'),
	(1,'2013-03-03'),
	(2,'2012-05-07'),
	(2,'2012-11-07'),
	(2,'2013-05-07'),
	(3,'2012-01-16'),
	(3,'2012-07-16'),
	(3,'2013-01-16'),
	(4,'2012-02-24'),
	(4,'2012-07-24'),
	(4,'2013-02-24'),
	(5,'2012-03-12'),
	(5,'2012-09-12'),
	(5,'2013-03-12'),
	(6,'2012-02-09'),
	(6,'2012-08-09'),
	(6,'2013-02-09'),

	(7,'2012-03-03'),
	(7,'2013-03-03'),
	(7,'2014-03-03'),
	(8,'2012-05-07'),
	(8,'2013-05-07'),
	(8,'2014-05-07'),
	(9,'2012-01-16'),
	(9,'2013-01-16'),
	(9,'2014-01-16'),
	(10,'2012-02-24'),
	(10,'2013-02-24'),
	(10,'2014-02-24'),
	(11,'2012-03-12'),
	(11,'2013-03-12'),
	(11,'2014-03-12'),
	(12,'2012-02-09'),
	(12,'2013-02-09'),
	(12,'2014-02-09'),

	(13,'2012-05-24'),
	(13,'2013-11-24'),
	(13,'2015-05-24'),
	(14,'2012-01-07'),
	(14,'2013-07-07'),
	(14,'2015-01-07'),
	(15,'2012-11-13'),
	(15,'2014-05-13'),
	(15,'2015-11-13'),
	(16,'2012-03-03'),
	(16,'2014-09-03'),
	(16,'2015-03-03'),
	(17,'2012-08-18'),
	(17,'2014-02-18'),
	(17,'2015-08-18'),
	(18,'2012-07-30'),
	(18,'2014-01-30'),
	(18,'2015-07-30');