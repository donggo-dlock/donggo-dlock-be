alter table reviews
modify column password varchar(255) not null;

alter table reviews drop column title;