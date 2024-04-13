
<div style="text-align: center">
<img src="front/src/assets/logo_p6.png" alt="ORION Logo" width="400"/>
</div>

# ORION - MDD (Monde de Dév)

Welcome to ORION, a software development company dedicated to creating innovative solutions. We are excited to introduce MDD (Monde de Dév), the next-generation social network tailored for developers.

## About MDD

MDD aims to revolutionize the way developers connect and collaborate. Our platform facilitates networking among developers seeking employment opportunities, fostering connections and collaboration within the developer community.

This repository contains both the front-end and back-end components of a full-stack application.

## Technologies Used

### Front-End

![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![Angular Material](https://img.shields.io/badge/Material%20UI-0081CB?style=for-the-badge&logo=material-ui&logoColor=white)
![Node.js](https://img.shields.io/badge/Node.js-43853D?style=for-the-badge&logo=node.js&logoColor=white)
![npm](https://img.shields.io/badge/npm-CB3837?style=for-the-badge&logo=npm&logoColor=white)

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3. Utilizes Angular Material for UI components.

### Back-End

![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens)

Backend API developed with Spring Boot, secured with Spring Security and JWT. It communicates with a MySQL database.

### Database Setup

Before running the application, you need to set up a MySQL database. Follow these steps to create and configure the database:

1. **Install MySQL**  
   If you haven't already installed MySQL, download and install it from the [official MySQL website](https://dev.mysql.com/downloads/mysql/).

2. **Create the Database**  
   Log in to your MySQL server and create a new database:

   ```sql
   CREATE DATABASE db_mdd;

### Required Environment Variables for MySQL

- `MYSQL_URL`: The url of your MySQL database. For example, `jdbc:mysql://localhost:3306/`.
- `MYSQL_DATABASE`: The name of your MySQL database. For example, `db_mdd`.
- `MYSQL_USER`: Your MySQL username.mysql_database
- `MYSQL_PASSWORD`: Your MySQL password.

## Application Installation

1. Ensure you have the necessary dependencies installed: Java, Node.js, Maven.
2. Clone this repository to your local machine.
3. Navigate to the back-end project directory and execute the command `mvn clean install` to download dependencies and compile the project.
4. Navigate to the front-end project directory and run the command `npm install` to install front-end dependencies.

## Running the Application

1. Navigate to the back-end project directory and execute the command `mvn spring-boot:run` to start the back-end application.
2. Navigate to the front-end project directory and execute the command `npm run start` to launch the front-end application.

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.



