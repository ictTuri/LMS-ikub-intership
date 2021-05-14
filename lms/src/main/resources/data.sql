INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_SECRETARY');
INSERT INTO roles (name) VALUES ('ROLE_STUDENT');

INSERT INTO users (email,firstName,lastName,username,password,activated)
 VALUES ('admin@gmail.com','Admin','Admin','Admin','STUDENT',true);
 INSERT INTO users (email,firstName,lastName,username,password,activated)
 VALUES ('secretary@gmail.com','Secretary','Secretary','Secretary','STUDENT',true);
 INSERT INTO users (email,firstName,lastName,username,password,activated)
 VALUES ('student@gmail.com','Student','Student','Student','STUDENT',true);
 
 INSERT INTO user_role (user,role) VALUES (1,1);
 INSERT INTO user_role (user,role) VALUES (2,2);
 INSERT INTO user_role (user,role) VALUES (3,3);
 
 INSERT INTO books (name) VALUES ('2 Year Vacation');
 INSERT INTO books (name) VALUES ('3 Friends');
 INSERT INTO books (name) VALUES ('Evxheni Grande');
 INSERT INTO books (name) VALUES ('Flag Pirates');
 INSERT INTO books (name) VALUES ('Kids of Pal Street');
 INSERT INTO books (name) VALUES ('Anna Karenina');
 INSERT INTO books (name) VALUES ('General of dead army');
 INSERT INTO books (name) VALUES ('That Mauntain');
 