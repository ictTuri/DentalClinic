# Rezervation System for Dental Clinics

This is a Rest Api Spring Boot Appointment System that manages Dental Clinic appointments and other processes!

**Author**: Artur Molla <br />
**Version**: 1.0.0

---
## Overview
This project is Rest API application that serves as appointment system for clinics.
Managing four types of users (Admin,Secretary,Doctor and Public) 
and different books on the library.

#### Registration, Login with (JWT)
After registration, public users will get a success message. After registering public user can login
and proceed as per his/her role and authorities granted. Can view free scheduled times for next 7 days
and also set an appoinment with the doctor of choice or without doctor at all.
Admin after login he/her can have access on managing the users, read on report data and also has access on
like the user secretary.
Secretary user after login can approve the appointments, suggest another time for the visit, 
change the doctor for an approved visit and also set the visit status to done if doctor writes a feedback 
on the appointment.
Doctor after login can see his approved appointments, his cancelled ones, his free schedule for 7 days and 
also write feedback for an appointment which he/she attends.

#### Database Schema

![Schema](https://github.com/ictTuri/DentalClinic/blob/main/img/ER_diagram.png?raw=true)

---
#### Database Schema Shortened Explanation
A User :
* can have one role
* has an unique email
* has an unique NID
* has an unique phone number
* has an unique username

An Appointment:
* has a Date
* has a start time
* has a end time
* has a Dentist Username
* has a Patient id

A Feedback
* has a Creation Date
* has a Description
---
#### Operation Explained
_User_ Admin :
* Access to all users
* Access to create, read, update and delete to users
* Access to all reports
* Access to approve, close, change time and change dentist from appointments

_User_ Secretary:
* Access to approve, close, change time and change dentist from appointments
* Access to all reports
* Can view all appointemnts
* Can view all free times for next 7 days

_User_ Public
* After registering user has access to view free slots for next 7 days
* Can rezerve an appointment
* Can view his/her appointments

---
#### Maven Testing
* Run Maven Clean and Then Maven Test
* Test Profile set "sql"
* 20% so far code coverage on tests (little bit challenging)

---
## Build
Clone this repo through a bash terminal and type the following commands:
```
git clone https://github.com/ictTuri/DentalClinic.git
```
* To Run on Postgresql database:
Move to lms/src/main/resources/application-dev.properties 
Create your own database and update the properties.
At lms/src/main/resources/application.properties make sure profile is dev 
Run the app and it will populate initial data:
" NID: A00000001A, password: admin "
" NID: S00000001S, password: secretary "
" NID: D00000001D, password: dentist "
" NID: D00000002D, password: dentist "
" NID: U00000001U, password: user "
" NID: U00000002U, password: user "
" NID: U00000003U, password: user "

---
#### Controllers 
* Swagger UI api Docs

![Schema](https://raw.githubusercontent.com/ictTuri/DentalClinic/blob/main/img/api.PNG?raw=true)

---
* Appointments-Controller Operations

![Schema](https://raw.githubusercontent.com/ictTuri/DentalClinic/blob/main/img/appointments.PNG?raw=true)

---
* Login-Logout-Controller Operations

![Schema](https://raw.githubusercontent.com/ictTuri/DentalClinic/blob/main/img/loginlogout.PNG?raw=true)

---
* Register-Controller Operations

![Schema](https://raw.githubusercontent.com/ictTuri/DentalClinic/blob/main/img/register.PNG?raw=true)

---
* Reports-Controller Operations

![Schema](https://raw.githubusercontent.com/ictTuri/DentalClinic/blob/main/img/reports.PNG?raw=true)

---
* Users-Controller Operations

![Schema](https://raw.githubusercontent.com/ictTuri/DentalClinic/blob/main/img/users.PNG?raw=true)

---

---
## Architecture
This application is created using Spring Boot 2.5.5  <br />
* Languages*: JAVA 8, Streams, Lambdas, Functional Programming, SQL, Spring Boot Framework<br />
* Tools*: Maven,STS Spring Tool Suite, Sonarlint, Postgresql and H2 for testing, jaCoCo, Jpa Hibernate, Lombok<br />
JUnit, Slf4j, Spring Security Jwt<br />
* Type of Application*: Rest Api <br />