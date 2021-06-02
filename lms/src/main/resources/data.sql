INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('SECRETARY');
INSERT INTO roles (name) VALUES ('STUDENT');

INSERT INTO users (email,first_name,last_name,username,password,activated)
 VALUES ('admin@gmail.com','Admin','Admin','admin','$2y$10$ybeXeYapiq93dUOaJtTTn.by/Ydjv0.w4ZXN86PivBjYvXq1xSjOS',true);
 INSERT INTO users (email,first_name,last_name,username,password,activated)
 VALUES ('secretary@gmail.com','Secretary','Secretary','secretary','$2y$10$J3bXbVr7gbsPN4wNbqxCaOuqSGW5RGDiH.Kgnf/dfSsDvDXu3SK8q',true);
 INSERT INTO users (email,first_name,last_name,username,password,activated)
 VALUES ('student@gmail.com','Student','Student','student','$2y$10$tUqazVvIFcsG/09uZ.9K4udggm/Oy/WnazskQ10iUv.sr/LmnBeQW',true);
 
 INSERT INTO user_role (user_id,role_id) VALUES (1,1);
 INSERT INTO user_role (user_id,role_id) VALUES (2,2);
 INSERT INTO user_role (user_id,role_id) VALUES (3,3);
 
 INSERT INTO books (title, taken) VALUES ('2 Year Vacation', false);
 INSERT INTO books (title, taken) VALUES ('3 Friends', false);
 INSERT INTO books (title, taken) VALUES ('Evxheni Grande', false);
 INSERT INTO books (title, taken) VALUES ('Flag Pirates', false);
 INSERT INTO books (title, taken) VALUES ('Kids of Pal Street', false);
 INSERT INTO books (title, taken) VALUES ('Anna Karenina', false);
 INSERT INTO books (title, taken) VALUES ('General of dead army', false);
 INSERT INTO books (title, taken) VALUES ('That Mauntain', false);
 