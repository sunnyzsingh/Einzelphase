CREATE table box(
id int primary key auto_increment,
boxname varchar(40),
horsename varchar(40),
litter varchar(40) check(litter in ('straw', 'sawdust')),
size numeric(20,3),
location varchar(30) check (location in ('inside','outside')),
dailyrate numeric(30,3),
picture varchar(255),
window boolean,
deleted BOOLEAN DEFAULT FALSE
);

Create table reservation(
r_id int primary key auto_increment,
clientname varchar(30),
horsename varchar(40),
start timestamp,
end timestamp,
deleted boolean DEFAULT FALSE
);

create sequence rec_id start with 12343 increment by 2 no cycle;

create table receipt(
  receipt_id integer default rec_id.nextval,
  r_id integer references reservation(r_id),
  b_id integer references box(id),
  totalrate NUMERIC(20,3),
  clientname VARCHAR(30),
  start TIMESTAMP,
  primary key(receipt_id,r_id, b_id)

);