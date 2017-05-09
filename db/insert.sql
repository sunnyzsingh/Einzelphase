INSERT INTO BOX VALUES (1,'ladyluck', 'brownpanther', 'straw', 12, 'inside',4, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/ladyluck.jpg', TRUE, FALSE);
INSERT INTO BOX VALUES (2,'blackhouse', 'lana', 'straw', 12, 'outside',4, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/blackhouse.jpg', TRUE, FALSE);
INSERT INTO BOX VALUES (3,'brook', 'mana', 'sawdust', 11, 'outside',5, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/brook.jpg', TRUE, FALSE);
INSERT INTO BOX VALUES (4,'jeapardy', 'dana', 'straw', 12, 'inside',6, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/jeapardy.jpg', TRUE, FALSE);
INSERT INTO BOX VALUES (5,'ladystallion', 'sana', 'sawdust', 10, 'inside',6, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/ladystallion.jpg', TRUE, FALSE);
INSERT INTO BOX VALUES (6,'liberty', 'nana', 'sawdust', 9, 'outside',4, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/liberty.jpg', TRUE, FALSE);
INSERT INTO BOX VALUES (7,'lopew', 'jasper', 'straw', 13, 'inside',4, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/lopew.jpg', TRUE, FALSE);
INSERT INTO BOX VALUES (8,'thebeat', 'casper', 'sawdust', 8, 'inside',5, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/thebeat.jpg', FALSE, FALSE);
INSERT INTO BOX VALUES (9,'whitecharm', 'rocky', 'straw', 9, 'inside',4, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/whitecharm.jpg', TRUE, FALSE);
INSERT INTO BOX VALUES (10,'woodstar', 'sane', 'sawdust', 12, 'inside',4, '/Users/Sunny/Coding/Java/Einzelphase/src/resources/woodstar.jpg', FALSE, FALSE);


INSERT INTO Reservation VALUES
(1, 'Rasmina', 'rocky', '2017-03-11 13:00:00', '2017-03-11 15:00:00', FALSE ),
(2, 'Sunny', 'lana', '2017-04-11 13:00:00', '2017-04-11 15:00:00', FALSE ),
(3, 'Kate', 'brownpanther', '2017-05-11 13:00:00', '2017-05-11 15:00:00', FALSE ),
(4, 'Lori', 'mana', '2017-06-11 13:00:00', '2017-06-11 15:00:00', FALSE ),
(5, 'Fiona', 'sana', '2017-07-11 11:00:00', '2017-11-11 15:00:00', FALSE ),
(6, 'Rasmina', 'jasper', '2017-08-11 10:00:00', '2017-11-11 15:00:00', FALSE ),
(7, 'Rasmina', 'casper', '2017-09-11 09:00:00', '2017-11-11 15:00:00', FALSE ),
(8, 'Rasmina', 'sana', '2017-10-11 13:23:00', '2017-11-11 15:00:00', FALSE ),
(9, 'Stefan', 'dana', '2017-11-11 13:00:00', '2017-11-11 15:00:00', FALSE ),
(10, 'Stefan', 'sane', '2017-11-11 13:00:00', '2017-11-11 15:00:00', FALSE );

/**
Gut jtz fehlt dir nur auf andere Computer zutesten!
Gut jtz fehlt dir nur auf andere Computer zutesten!
https://reset.inso.tuwien.ac.at/git/individual/sepm/2017ss/1428182
 */
INSERT INTO receipt (r_id, b_id, totalrate, clientname, start)
 VALUES
(1,9,8, 'Rasmina',  '2017-03-11 13:00:00'),
(2,2,8,'Sunny', '2017-04-11 13:00:00'),
(3,1,8, 'Kate', '2017-05-11 13:00:00'),
(4,3,10, 'Lori', '2017-06-11 13:00:00'),
(5,5,30, 'Fiona', '2017-07-11 11:00:00'),
(6,7,24, 'Rasmina', '2017-08-11 10:00:00'),
(7,8,35, 'Rasmina', '2017-09-11 09:00:00'),
(8,5,12, 'Rasmina', '2017-10-11 13:23:00'),
(9,4,12, 'Stefan', '2017-11-11 13:00:00'),
(10,10,8, 'Stefan', '2017-11-11 13:00:00');