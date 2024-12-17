# EventPilot API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.4-brightgreen)
![Gradle](https://img.shields.io/badge/Gradle-Build-blue)
![H2 Database](https://img.shields.io/badge/Database-H2-lightgrey)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-4fc08d)
![JUnit](https://img.shields.io/badge/Testing-JUnit5-25a162)
![License](https://img.shields.io/badge/License-MIT-yellow)
![Build](https://img.shields.io/badge/Build-Passing-brightgreen)
![Status](https://img.shields.io/badge/Status-Complete-success)
![Node.js](https://img.shields.io/badge/Node.js-14.x-green)
![Docker](https://img.shields.io/badge/Docker-Supported-blue)

## Introduction

**EventPilot API** is a backend solution for managing and exploring activity data with features such as searching, pagination, and sorting. This project is designed to be lightweight, modular, and easy to deploy using Docker.

---

## Motivation

I developed this project to demonstrate my skills in building scalable backend APIs and making thoughtful design decisions. Activity data often needs to be retrieved efficiently with filtering, sorting, and error handling capabilities. This project showcases:
1. A clean, RESTful API design.
2. Effective use of in-memory databases for rapid prototyping.
3. Integration of tools like Swagger for seamless API documentation.
4. Scalability principles that align with real-world systems.

---

## Why I Made These Choices

### 1. Database: H2

I chose **H2** as the database for the following reasons:
- It’s an in-memory database, which makes it ideal for quick setup and testing.
- It eliminates the need for external configuration during development.
- It’s fast and lightweight, which suits this project since it’s not intended for production use.

If this were a production system, I would opt for a more robust database like PostgreSQL or MySQL for persistence.

---

### 2. Repository Design

To interact with the database, I implemented a repository layer that abstracts query logic. This allows for clean and maintainable code, making it easy to test and extend.

---

### 3. RESTful API

The API follows RESTful principles to ensure simplicity, scalability, and compatibility with client applications. Each endpoint is designed to serve a specific purpose:
- **`GET /activities`**: Retrieve all activities with support for pagination and sorting.
- **`GET /activities/search`**: Search for activities by title with support for pagination and sorting.

These endpoints are structured to be intuitive and flexible, giving clients control over how they retrieve data.

---

### 4. Pagination and Sorting

Pagination and sorting were added to improve scalability and usability. Clients can specify the page number, page size, and the field to sort by.

**Example Query**:
- **`GET /activities/search?title=Tour&page=0&size=5&sortBy=price`**

This query retrieves the first 5 activities containing “Tour” in their title, sorted by price.

---

### 5. Error Handling

Error handling was implemented to ensure the API provides clear feedback to clients. For example:
- If a search query is invalid (e.g., missing the title), the API returns an appropriate error message:
  ```json
  {
      "error": "Title parameter cannot be empty"
  }
  ````

  This approach ensures that clients can debug issues easily and reduces the likelihood of misuse.

---

### 6. Swagger Documentation

To make the API easier to use, I integrated Swagger for interactive documentation.

#### How to Use Swagger
1. Start the application and navigate to:

http://localhost:8080/swagger-ui/index.html

2. You’ll see detailed documentation for all endpoints, including:
- **`GET /activities`**: View all activities with pagination and sorting.
- **`GET /activities/search`**: Search for activities by title.

Swagger allows you to test the API directly through the UI by filling in the parameters and executing requests.

---

### 7. Frontend Decisions

I linked only the **Title Search** feature to the backend API as specified in the task requirements. However, I implemented additional filters (e.g., price, rating, and special offers) on the **frontend** to enhance the user experience. 

#### Why Keep Filters on the Frontend?
- **Flexibility**: Filtering on the frontend allows users to adjust filters in real time without making additional API calls.
- **Performance**: For smaller datasets, frontend filtering is faster as it avoids unnecessary backend processing.

This design provides a balance between meeting task requirements and adding extra features to showcase the frontend’s capabilities.

---

## Future Improvements

This project fulfills the current requirements, I also added some inline TODOs but here are also ideas for future enhancements:

### Backend:
1. **Persistent Database**:
- Replace H2 with a database like PostgreSQL or MySQL for real-world usage.
- Use database migrations to manage schema changes.
2. **Security**:
- Add authentication and authorization mechanisms, such as API keys or OAuth tokens.
- Use encryption for sensitive data and secure communication with HTTPS.

### Frontend:
1. **Advanced Filtering**:
- Add support for backend filtering if the dataset grows significantly.
2. **Improved UI/UX**:
- Enhance the user interface for better usability and design.

---
### Deployment

This project includes Docker support for easy containerization and deployment. Below are the steps for deploying the application using Docker and some ideas for future improvements.

#### 1. **Containerization**
- The application is packaged using Docker for consistent deployment across environments.
- Both the backend and frontend services are containerized and managed using `docker-compose`.

---

# How to Run the Project
## Prerequisites
Ensure Docker is installed on your system.

## Running the Application

1. **Navigate to the project root directory**:
   ```bash
   cd <project_root_directory>

# How to Run the Project

## Running the Application

1. **Navigate to the project root directory**:
   ```bash
   cd <project_root_directory>

2.	**Build and run the application using Docker Compose**:
	```bash
	docker-compose up --build


3. **Access the services**:
   - **Backend**: [http://localhost:8080](http://localhost:8080)
   - **Frontend**: [http://localhost:8081](http://localhost:8081)

To stop the application and remove the containers, run:
	
	docker-compose down

---

## Built By
**Melih Can Aydın**
Connect with me on:  
- [GitHub](https://github.com/melihcanaydin)  
- [LinkedIn](https://www.linkedin.com/in/melihcanaydin/)  

