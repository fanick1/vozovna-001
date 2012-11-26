create schema sa;

--drop table Drives;
--drop table ServiceInterval;
--drop table ServiceInterval_dated;
--drop table Users;
--drop table Vehicle;

create table Drives (
    id bigint not null,
    date_from timestamp,
    date_to timestamp,
    distance integer,
    drive_state integer,
    id_user bigint,
    id_vehicle bigint,
    primary key (id)
);

create table ServiceInterval (
    id bigint not null,
    description varchar(255) not null,
    inspectionInterval integer not null,
    vehicle_id bigint,
    primary key (id)
);

create table ServiceInterval_dated (
    ServiceInterval_id bigint not null,
    dated date
);

create table Users (
    id bigint not null,
    firstName varchar(50),
    isAdmin boolean not null,
    lastName varchar(50),
    userClass integer not null,
    primary key (id)
);

create table Vehicle (
    id bigint not null,
    brand varchar(255),
    engineType varchar(255),
    maxDistance integer not null,
    type varchar(255),
    userClass integer,
    vin varchar(255),
    yearMade integer not null,
    primary key (id)
);


--insert into Drives (id, date_from , date_to, distance, drive_state, id_user, id_vehicle)
--            values (-1, '2012-10-01-00.00.00.000000' , '2012-10-07-00.00.00.000000', 60, null, -1, -1);
            
--insert into ServiceInterval (id, description, inspectionInterval, vehicle_id)
--            values (-1, 'window cleaning!', 30, -1);
            
--insert into ServiceInterval_dated ( ServiceInterval_id , dated)
--            values ( -1 , '2012-10-15');
            
--insert into Users (id, firstName, isAdmin, lastName, userClass) 
--            values (-1, 'Arnold', false, 'Rimmer', 1);
            
--insert into Vehicle (id, brand, engineType, maxDistance, type, userClass, vin, yearMade )
--            values (-1, 'Značečka', 'TDCI', 5000, 'Autobus', 1, 'ABC 98-76', 2011);


--alter table Drives add constraint FK7A818449E4A23A2 foreign key (id_vehicle) references Vehicle;
--alter table Drives add constraint FK7A8184498D77E68C foreign key (id_user) references Users;
--alter table ServiceInterval add constraint FK2FED427AFE4FAEA8 foreign key (vehicle_id) references Vehicle;
--alter table ServiceInterval_dated add constraint FK2F76871F27DDA08 foreign key (ServiceInterval_id) references ServiceInterval;
--create sequence hibernate_sequence;