create schema if not exists kdlib;
create table if not exists kdlib.book(
	isbn varchar(25) primary key,
	name varchar(50) not null,
	price number,
	published_date date,
	total_quantity number null,
	rented_quantity number null
);
create table if not exists kdlib.user(
	user_id number primary key,
	name varchar(50) not null,
	address varchar(150) not null,
	ph_no varchar(15) not null,
	rent_pass number null
);
create table if not exists kdlib.book_rent_register(
	rent_id number primary key,
	user_id number not null,
	rented_date date not null,
	return_date date null,
	foreign key(user_id) references kdlib.user(user_id)
);
create table if not exists kdlib.book_rent(
	rent_id number not null,
	book_isbn varchar(25) not null,
	foreign key(book_isbn) references kdlib.book(isbn),
	foreign key(rent_id) references kdlib.book_rent_register(rent_id)
);