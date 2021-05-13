# LMS_IKub_Intership

This is a Rest Api Spring Boot Library Management System that manages books and users!

**Authors**: Artur Molla <br />
**Version**: 1.0.0

---
## Overview
This project is Rest API application that serves as an Library
management system. Managing three types of users (Admin,Secretary and Student) 
and different books on the library.

#### Registration, Login with (JWT)
After registration, users will wait for his accout activation.After activation he can login
and proceed as per his/her role and authorities granted.
Admin after login he/her can have access on managing the users, activating them and deleting.
Secretary after login can have access on creating, viewing, updating and deleting books 
from inventory.

#### Database Schema

![Schema](https://github.com/ictTuri/LMS_IKub_Internship/blob/main/img/diagram.png?raw=true)

---
#### Database Schema Shortened Explanation
A user :
* can have one or more roles
* has an unique email
* has an unique username

A book :
* has a title
* title is unique

---
## Architecture
This application is created using Spring Boot 2.4  <br />
*Languages*: JAVA, SQL<br />
*Tools*: STS Spring Tool SUite, Sonarlint, Postgresql and H2 for testing, Jpa Hibernate, Lombok<br />
JUnit, Logger, Spring Security Jwt<br />
*Type of Application*: Rest Api <br />