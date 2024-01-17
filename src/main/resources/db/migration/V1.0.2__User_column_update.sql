alter table users
    modify status enum('ACTIVE','INACTIVE','PENDING') default 'PENDING';