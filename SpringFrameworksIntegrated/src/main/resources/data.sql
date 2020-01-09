insert into kdlib.book values ('01DSA', 'Data Structures & Algorithms', 150.00, to_date('24-Apr-2018'), 10, 4);
insert into kdlib.book values ('02Java', 'Java Core for Dummies', 80.00, to_date('23-Apr-2018'), 12, 1);
insert into kdlib.book values ('03JEE', 'Advanced Java for Dummies', 200.00, to_date('22-Apr-2018'), 5, 1);
insert into kdlib.book values ('04DP', 'Head First Design Patterns', 450.00, to_date('21-Apr-2018'), 3, 1);

insert into kdlib.user values (1, 'Saurabh Kedia', 'Bangalore', '999999999', 3);
insert into kdlib.user values (2, 'John Snow', 'Winterfell', '8888888888', 3);
insert into kdlib.user values (3, 'Cersi Lannister', 'King''s Landings', '7777777777', 3);
insert into kdlib.user values (4, 'Arya Stark', 'Winterfell', '6666666666', 3);

insert into kdlib.book_rent_register values (1, 1, to_date('01-May-2018'), null);
insert into kdlib.book_rent_register values (2, 1, to_date('01-May-2018'), null);
insert into kdlib.book_rent_register values (3, 1, to_date('01-May-2018'), null);
insert into kdlib.book_rent_register values (4, 2, to_date('01-May-2018'), null);
insert into kdlib.book_rent_register values (5, 3, to_date('01-May-2018'), null);

insert into kdlib.book_rent values (1, '01DSA');
insert into kdlib.book_rent values (1, '02Java');
insert into kdlib.book_rent values (1, '03JEE');
insert into kdlib.book_rent values (2, '04DP');
insert into kdlib.book_rent values (4, '01DSA');
insert into kdlib.book_rent values (5, '01DSA');
