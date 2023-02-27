# Property Manager Test

This project is a test for the Agrotis company, it represents a managing API to attend a Front End.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- A running instance of postgresql or MySQL database

## Getting Started

1. Clone the repository: git clone git@github.com:Rafael0101/propertymanager.git
2. Navigate to the project directory: cd propertymanager
3. Build the project: mvn clean install
4. Configure environment variables for the database (see below)
5. Run the application: java -jar target/propertymanager.jar

## Running Tests

To run the tests, use the following command: mvn test

## Configuring Environment Variables

To connect to the database, the application requires the following environment variables to be set:

- DB_USERNAME: the username for the database
- DB_PASSWORD: the password for the database
- DB_URL: the URL for the database

To set these environment variables, follow these steps:

1. Open a terminal window.
2. Set the DB_USERNAME, DB_PASSWORD, and DB_URL environment variables using the appropriate values for your database: 

```
    export DB_USERNAME=your-username
    export DB_PASSWORD=your-password
    export DB_URL=jdbc:postgresql://localhost:5432/your-database
```
   
  Note: Replace the values your-username, your-password, and your-database with your own values.

3. Run the application as described in the "Getting Started" section above.

## Postman Collection
A Postman collection is included in the project to help test the API endpoints. To use the collection, follow these steps:

Open the Postman application.
Click on the "Import" button -> link -> paste this link: https://api.postman.com/collections/21084189-2a55e66a-360a-4d89-9dac-4f0f4e951075?access_key=PMAT-01GT8GKCVNV23Z64PF7RKCP98Y
The collection should now be imported into Postman.
Update the environment variables in the collection to match your local environment.
Use the collection to test the API endpoints.

## Disclaimer

Este README foi escrito em inglês para fim de boas práticas.

Qualquer dúvida sobre este projeto, bem como instalação e uso fique a vontade para me contatar aqui ou por email: rafanoliveira13@gmail.com.
