create table if not exists employee_kevin (
    id int not null primary key AUTO_INCREMENT,
    name varchar(255),
    register varchar(255) not null,
    age int,
    address varchar(255) not null,
    salary double not null,
    email varchar(255) not null,
    department varchar(255) not null,
    created_at date,
    updated_at date
);

create table if not exists project_kevin (
    id int not null primary key AUTO_INCREMENT,
    name varchar(255) not null,
    created_at date,
    updated_at date
);

create table if not exists employee_x_project_kevin (
    id_employee int not null,
    id_project int not null,
    constraint fk_id_employee foreign key ( id_employee ) references employee_kevin( id ),
    constraint fk_id_project foreign key ( id_project ) references project_kevin( id )
);

create table if not exists user_kevin (
    id int not null primary key AUTO_INCREMENT,
    username varchar(255) not null,
    password varchar(255) not null,
    role enum('ADMIN', 'USER')
);