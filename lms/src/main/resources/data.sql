INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('SECRETARY');
INSERT INTO roles (name) VALUES ('STUDENT');

INSERT INTO users (email,first_name,last_name,username,password,activated)
 VALUES ('admin@gmail.com','Admin','Admin','Admin','$2y$10$ybeXeYapiq93dUOaJtTTn.by/Ydjv0.w4ZXN86PivBjYvXq1xSjOS ',true);
 INSERT INTO users (email,first_name,last_name,username,password,activated)
 VALUES ('secretary@gmail.com','Secretary','Secretary','Secretary','$2y$10$J3bXbVr7gbsPN4wNbqxCaOuqSGW5RGDiH.Kgnf/dfSsDvDXu3SK8q ',true);
 INSERT INTO users (email,first_name,last_name,username,password,activated)
 VALUES ('student@gmail.com','Student','Student','Student','$2y$10$tUqazVvIFcsG/09uZ.9K4udggm/Oy/WnazskQ10iUv.sr/LmnBeQW ',true);
 
 INSERT INTO user_role (user_id,role_id) VALUES (1,1);
 INSERT INTO user_role (user_id,role_id) VALUES (2,2);
 INSERT INTO user_role (user_id,role_id) VALUES (3,3);
 
 INSERT INTO books (title) VALUES ('2 Year Vacation');
 INSERT INTO books (title) VALUES ('3 Friends');
 INSERT INTO books (title) VALUES ('Evxheni Grande');
 INSERT INTO books (title) VALUES ('Flag Pirates');
 INSERT INTO books (title) VALUES ('Kids of Pal Street');
 INSERT INTO books (title) VALUES ('Anna Karenina');
 INSERT INTO books (title) VALUES ('General of dead army');
 INSERT INTO books (title) VALUES ('That Mauntain');
 