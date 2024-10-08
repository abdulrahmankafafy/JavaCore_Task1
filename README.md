

This is a Java-based desktop application developed using Swing for the GUI and plain JDBC for database operations. The application manages user profiles and departments, allowing CRUD (Create, Read, Update, Delete) operations on these entities. The application connects to a MySQL database to store and retrieve information.

## Features

- **User Management**: Add, update, delete, and view user profiles.
- **Department Management**: Add, update, delete, and view departments.
- **Top Salaries**: View the top 5 highest salaries in selected departments.
- **Age Calculation**: Automatically calculates a user's age based on their national ID.
- **Data Validation**: Input validation for fields like name, username, password, national ID, phone number, and salary.

## Technologies Used

- **Java SE**: Core Java is used to build the application.
- **Swing**: Provides the graphical user interface (GUI) for the application.
- **JDBC (Java Database Connectivity)**: Used to connect and execute SQL queries on a MySQL database.
- **MySQL**: The database system used to store user profiles and department data.

## Project Structure

- **`org.example.dao`**: Contains the Data Access Object (DAO) interfaces and implementations for interacting with the database.
  - **`framework`**: Interfaces for UserProfileDAO and DepartmentDAO.
  - **`implementation`**: Implementations of DAO interfaces using JDBC.
- **`org.example.entity`**: Contains entity classes `UserProfile` and `Department`, representing the database tables.
- **`org.example.model`**: Contains model classes `UserModel`, `DepartmentModel`, and `UserError` used for transferring data within the application.
- **`org.example.utilities`**: Utility classes like `DatabaseConnection` for managing database connections and `NationalIdUtils` for extracting birth dates from national IDs.
- **`org.example`**: Contains the main class `Main` to launch the application and `MainFrame` for the Swing GUI.

## Database Schema

Ensure your MySQL database is set up with the following schema:

### Database: `task1`

```sql
CREATE DATABASE task1;

USE task1;

CREATE TABLE department (
    DEPARTMENT_ID INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID INT,
    DEPARTMENT_NAME VARCHAR(100)
);

CREATE TABLE user_profile (
    USER_ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    User_Name VARCHAR(100) NOT NULL,
    Password VARCHAR(100) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    National_Id VARCHAR(14) UNIQUE NOT NULL,
    Phone VARCHAR(15) NOT NULL,
    Department_Id INT,
    Salary DOUBLE NOT NULL,
    birth_date DATE NOT NULL,
    FOREIGN KEY (Department_Id) REFERENCES department(DEPARTMENT_ID)
);
```

## How to Run the Application

### Prerequisites

- **JDK 22**: Make sure Java is installed and the `JAVA_HOME` environment variable is set.
- **MySQL**: Ensure MySQL is installed and running.

### Steps

1. **Clone the Repository**: Clone the project repository to your local machine.
2. **Set Up the Database**: Use the provided SQL script to create the `task1` database and tables.
3. **Update Database Connection**: Ensure that the `DatabaseConnection` class in the `utilities` package has the correct credentials to connect to your MySQL database.
   - Example:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/task1";
     private static final String USER = "kafafy";
     private static final String PASSWORD = "123!";
     ```
4. **Run the Application**: Run the `Main` class in the `org.example` package. The Swing GUI should launch, allowing you to manage users and departments.

### Using the Application

- **Users Tab**: Manage user profiles, including adding, updating, deleting, and viewing all users. You can also view the top 5 salaries in each department.
- **Departments Tab**: Manage departments by adding, updating, deleting, and viewing all departments.

## Validation

The application includes the following validation rules:
- **Name, Username, Password, Address, and National ID**: These fields cannot be null or empty.
- **National ID**: Must be 14 digits long and unique and there're some other validations such as checking the first couple of numbers to extract the age correctly.
- **Phone Number**: Must start with `+20` or `002` and be 13 or 14 digits long.
- **Salary**: Must be a positive number.
- **Age**: Automatically calculated based on the national ID, with acceptable age between 18 and 100 years.

