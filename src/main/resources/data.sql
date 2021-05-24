INSERT INTO TB_EVENTS (name, description, startDate, endDate, startTime, endTime, emailContact, amountFreeTickets, amountPayedTickets, priceTicket) VALUES ('Conservatorio', 'Conservatorio Descrição', '2021-01-01', '2021-02-02', '01:01:01', '02:02:02', 'conservatorio@email.com', 10, 100, 1000);
INSERT INTO TB_EVENTS (name, description, startDate, endDate, startTime, endTime, emailContact, amountFreeTickets, amountPayedTickets, priceTicket) VALUES ('Galeria de Arte', 'Galeria Descrição', '2021-03-03', '2021-04-04', '03:03:03', '04:04:04', 'galeriaart@email.com', 20, 200, 2000);
INSERT INTO TB_EVENTS (name, description, startDate, endDate, startTime, endTime, emailContact, amountFreeTickets, amountPayedTickets, priceTicket) VALUES ('Banda Blues', 'Banda Blues Descrição', '2021-05-05', '2021-06-06', '05:05:05', '06:06:06', 'bandablues@email.com', 30, 300, 3000);



INSERT INTO TB_USERS (id, name, email) VALUES (1, 'Floyd', 'floyd@email.com');
INSERT INTO TB_ADMINS (user_id, phoneNumber) VALUES (1, '(15) 1111-1111');

INSERT INTO TB_USERS (id, name, email) VALUES (2, 'Getulio', 'getulio@email.com');
INSERT INTO TB_ADMINS (user_id, phoneNumber) VALUES (2, '(15) 2222-2222');

INSERT INTO TB_USERS (id, name, email) VALUES (3, 'Xuxava', 'xuxava@email.com');
INSERT INTO TB_ADMINS (user_id, phoneNumber) VALUES (3, '(15) 3333-3333');


INSERT INTO TB_USERS (id, name, email) VALUES (4, 'Ricardo', 'ricardo@email.com');
INSERT INTO TB_ATTENDEES (user_id, balance) VALUES (4, 100);

INSERT INTO TB_USERS (id, name, email) VALUES (5, 'João', 'joao@email.com');
INSERT INTO TB_ATTENDEES (user_id, balance) VALUES (5, 200);

INSERT INTO TB_USERS (id, name, email) VALUES (6, 'Sandro', 'sandro@email.com');
INSERT INTO TB_ATTENDEES (user_id, balance) VALUES (6, 300);


INSERT INTO TB_PLACES (id, name, address) VALUES (1, 'Endereço1', 'Ruanumero1');
INSERT INTO TB_PLACES (id, name, address) VALUES (2, 'Endereço2', 'Ruanumero2');
INSERT INTO TB_PLACES (id, name, address) VALUES (3, 'Endereço3', 'Ruanumero3');