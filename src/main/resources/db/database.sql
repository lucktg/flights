--drop table flight;
--drop table airport;
--drop table airline;

create table airport(
airport_code varchar(10) not null primary key,
airport_name varchar(250) not null,
city varchar(250),
city_code varchar(10),
country_code varchar(10) not null,
country_name varchar(250) not null,
latitude varchar(30) not null,
longitude varchar(30) not null
);

create table airline(
airline_code varchar(10) not null primary key,
airline_name varchar(250) not null,
phone_number varchar(20)
);

create table flight(
flight_number varchar(10) not null ,
airline_code varchar(10) not null references airline(airline_code),
departure_date timestamp not null,
departure_terminal varchar(10),
departure_airport_code varchar(10) not null references airport(airport_code),
arrival_date timestamp not null,
arrival_terminal varchar(10),
arrival_airport_code varchar(10) not null references airport(airport_code),
service_type varchar(10)
);

alter table flight add primary key (flight_number, airline_code);