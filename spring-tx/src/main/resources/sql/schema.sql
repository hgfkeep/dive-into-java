create table book
(
    id    int primary key auto_increment,
    name  varchar(255)           not null,
    price double(10, 2) unsigned not null,
    num   int unsigned           not null
) ENGINE = InnoDB;

create table account
(
    id              int primary key auto_increment,
    name            varchar(255)           not null,
    account_balance double(10, 2) unsigned not null
) ENGINE = InnoDB;