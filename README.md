# Spring Boot Project

## Overview

This project is a Spring Boot application using Java 17. It is designed to provide a simple and efficient structure for building RESTful web services and other types of applications.

## Features

- Spring Boot 3
- Java 17
- H2 Database (in-memory)
- JPA for database operations
- CRUD operations for a `Book` entity
- Custom exceptions (e.g., `BookAlreadyExistsException`)

## Getting Started

### Prerequisites

- Java 17

### Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/your-username/your-repo-name.git
    cd your-repo-name
    ```

2. **Build the project**:
    ```sh
    mvn clean install
    ```

3. **Run the application**:
    ```sh
    mvn spring-boot:run
    ```

### Running Tests

To run tests, use the following command:
```sh
mvn test
