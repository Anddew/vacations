CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE if exists locations;
CREATE TABLE locations
(
    id      uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    country varchar(2),
    city    varchar(50)
);
INSERT INTO locations
VALUES ('11111111-1111-1111-1111-111111111111', 'BY', 'Minsk'),
       ('11111111-1111-1111-1111-111111111112', 'LV', 'Riga'),
       ('11111111-1111-1111-1111-111111111113', 'GE', 'Tbilisi');


DROP TABLE if exists departments CASCADE;
CREATE TABLE departments
(
    id   uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    name varchar(100)
);
INSERT INTO departments
VALUES ('22222222-2222-2222-2222-111111111111', 'IT'),
       ('22222222-2222-2222-2222-111111111112', 'Service'),
       ('22222222-2222-2222-2222-111111111113', 'DropOps');

DROP TABLE if exists employees CASCADE;
CREATE TABLE employees
(
    id           uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    firstName    varchar(100),
    lastName     varchar(100),
    departmentId uuid,
    FOREIGN KEY (departmentId) REFERENCES departments (id)
);
INSERT INTO employees
VALUES ('33333333-3333-3333-3333-111111111111', 'Andrei', 'Bylinovich',
        '22222222-2222-2222-2222-111111111111'),
       ('33333333-3333-3333-3333-111111111112', 'Dmitry', 'Podvalnikov',
        '22222222-2222-2222-2222-111111111111'),
       ('33333333-3333-3333-3333-111111111113', 'Juris', 'Krikis', '22222222-2222-2222-2222-111111111111'),
       ('33333333-3333-3333-3333-111111111114', 'Dmitry', 'Tabolich', '22222222-2222-2222-2222-111111111112'),
       ('33333333-3333-3333-3333-111111111115', 'Sergei', 'Korobov', '22222222-2222-2222-2222-111111111113');

DROP TABLE if exists vacations;
CREATE TABLE vacations
(
    id         uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    employeeId uuid,
    approverId uuid,
    start      date,
    until      date,
    FOREIGN KEY (employeeId) REFERENCES employees (id),
    FOREIGN KEY (approverId) REFERENCES employees (id)
);
INSERT INTO vacations
VALUES ('44444444-4444-4444-4444-111111111111', '33333333-3333-3333-3333-111111111111',
        '33333333-3333-3333-3333-111111111113', '2014-01-02', '2014-01-28'),
       ('44444444-4444-4444-4444-111111111112', '33333333-3333-3333-3333-111111111112',
        '33333333-3333-3333-3333-111111111112', '2018-01-16', '2014-02-13'),
       ('44444444-4444-4444-4444-111111111113', '33333333-3333-3333-3333-111111111115',
        '33333333-3333-3333-3333-111111111112', '2020-05-01', '2020-05-08');
