insert into message (content, username, time_stamp) values ('Pierwsza testowa wiadomość', 'admin', '2010-12-13 13:12:11');
insert into message (content ,username, time_stamp) values ('Druga testowa wiadomość' , 'user', '2022-01-02 11:13:15');


insert into role (id, name) values(1, 'ADMIN');
insert into role (id, name) values(2, 'USER');
insert into user (id, username, password) values(1, 'admin', '$2a$10$csIVj5k8CB74npTTstUQuO3ObjmaM6y8cRMxvsKqpHmT3zNQxY/IK');
insert into user (id, username, password) values(2, 'user', '$2a$10$m7BBzoGFKzb8e3/q5AK/SuC.qNKnyh.GcHkKnJYXsvJGQREhZe0Zu');
insert into user_role (user_id, role_id) values(1,1);
insert into user_role (user_id, role_id) values(1,2);
insert into user_role (user_id, role_id) values(2,2);


--hasło pass