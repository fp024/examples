DROP TABLE IF EXISTS book;
CREATE TABLE `book` (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  author VARCHAR(255),
  register_date DATETIME,
  PRIMARY KEY (id)
);


INSERT INTO book (name, author, register_date)
VALUES ('apple banana book 01', 'author 01', '2024-07-28 08:00:00');
INSERT INTO book (name, author, register_date)
VALUES ('kiwi book 02', 'author 02', '2024-07-28 09:00:00');
INSERT INTO book (name, author, register_date)
VALUES ('tree book 03', 'author 03', '2024-07-28 10:00:00');
INSERT INTO book (name, author, register_date)
VALUES ('banana book 04', 'author 04', '2024-07-28 11:00:00');
INSERT INTO book (name, author, register_date)
VALUES ('milk book 05', 'author 05', '2024-07-28 12:00:00');