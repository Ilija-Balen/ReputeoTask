# REPUTEO Application

This repository contains the REPUTEO Spring Boot application, including REST endpoints and RabbitMQ integration.

---

## Postman Collection

The full Postman collection used for testing this application is exported as:

`REPUTEO.postman_collection.json`

You can import this collection into Postman to test all available endpoints.

---

## Running the Application

You can run the application in several ways:

### 1. From IntelliJ IDEA

- Open the project in IntelliJ.
- Run the `main` method in the Spring Boot application class.

### 2. Using Maven

Run this command in your project root directory:
mvn spring-boot: run


### 3. As a Docker container

#### Build the Docker image:

docker build -t reputeotask .
#### Run the Docker container:
docker run -d -p 8080:8080 --network=your_network_name reputeotask

## RabbitMQ Setup

RabbitMQ is configured to run locally in a Docker container.

The endpoint  
`http://localhost:8080/api/v1/post/rabbit`  
will only work if RabbitMQ is reachable by the app (i.e., the app runs in the same Docker network or can access RabbitMQ otherwise).

---

## Application Flow

- **User** can be created independently.
- **Post** can be created with or without associating a user.
- To create a post, **content** is required.
- If the post has a user, the user **must exist first**.

---

## Filtering Posts

You can filter posts by the name of the creator using:
GET http://localhost:8080/api/v1/post?createdByName=ilija&page=0&size=10&sort=id,desc

There are other methods like `findAllByNameOfAUser` implemented, which can be discussed in detail during the interview.

---

