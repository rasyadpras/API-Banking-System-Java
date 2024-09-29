# Banking System API

### Description

The Banking API is a back-end application developed using Spring Boot
This application offers various features, including user authentication (registration and login)
opening and closing bank accounts, managing user profiles, administering bank branch data
and facilitating both transfer and cash transactions. The API is designed to provide a secures
and efficient platform for handling essential banking operations.

### Feature

- Users can authenticate their accounts (register and login).
- Admins have the right to assign roles, manage bank accounts, and add bank branch data.
- Bank staff can update account status (verify and unblock accounts).
- Users can perform transfer transactions and access their own transfer transaction history.
- Bank staff can add cash transactions, and only the user can view their own transaction history.
- Both bank staff and admins have the right to manage user profile data.

### Documentation

To view the full API documentation, navigate to the following endpoint after starting the 
application: http://localhost:8080/docs



### Instalation Guide

1. Ensure you have installed at least Java Development Kit (JDK) version 17 and Maven on your computer.
2. Clone this repository to your local machine:
``` bash
git clone https://git.enigmacamp.com/enigma-camp/enigmacamp-2.0/batch-31-java/timfinalproject1/challenge-booking-room.git
```
3. Open a terminal or command prompt and navigate to the project directory where you saved the files.

###### Notes

- The developer used Java version 17 to build this application.
  You can adjust the JDK version you are using by modifying the properties in the `pom.xml` file.
- Ensure you have correctly configured the `application.properties` file.
