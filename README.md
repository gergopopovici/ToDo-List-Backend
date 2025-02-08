# ToDoList App  

## Overview  
The ToDoList App is a structured, multi-module Java project designed for task management. It provides both desktop (JavaFX) and web-based (Servlets, Spring Boot) UIs, supports JDBC & JPA database integration, and follows a layered architecture for maintainability and scalability.  

## Features  
- **Multi-Layered Architecture**  
  - **Data Access Layer (DAL):** Supports in-memory storage, JDBC, and Spring Data JPA.  
  - **Business Logic Layer (BLL):** Handles validation and exception management.  
  - **Presentation Layer (PL):** JavaFX for desktop and Servlets for web UI.  
- **Multi-Module Gradle Project**  
  - `backend` – Business logic & data layer  
  - `desktop` – JavaFX UI  
  - `web` – Servlets & JSON API  
  - `spring-module` – Spring Boot API  
- **Database Support**  
  - In-memory, JDBC (PostgreSQL, MySQL), and Hibernate.  
  - Uses HikariCP for connection pooling.  
- **Web API (JSON-based REST API)**  
  - Uses DTOs and Jakarta Validation API for data validation.  
  - HTML-based UI with Thymeleaf/Handlebars.  
- **Authentication & Security**  
  - Session-based authentication (Servlets).  
  - JWT & Spring Security (Spring module).  

---

## API Endpoints  

### To-Do Endpoints  
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `api/todos` | List all to-dos |
| GET | `api/todos/{id}` | Get a specific to-do |
| POST | `api/todos` | Create a new to-do |
| PUT | `api/todos/{id}` | Update a to-do |
| DELETE | `api/todos/{id}` | Delete a to-do |

### Task Endpoints  
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `api/todos/{id}/tasks` | List all tasks for a to-do |
| POST | `api/todos/{id}/tasks` | Add a new task to a to-do |
| DELETE | `api/todos/{id}/tasks/{taskId}` | Delete a specific task |

### Query Parameter Example  
You can filter by ID using query parameters:  
`http://localhost:8080/ToDoList-pgim2289/todos?id=2`  

---

## Testing  
- Use Postman or cURL for API testing.  
- Run unit tests with:  
  ```sh
  ./gradlew test  


# Webserver
gradle undeploy
gradle deploy
& "$env:CATALINA_HOME\bin\startup.bat"
& "$env:CATALINA_HOME\bin\shutdown.bat"

# TODO Samples
{
"title": "Complete Spring Project",
"description": "Finish the implementation and testing of the Spring project.",
"date": "2024-12-15T10:00:00",
"priority": 2
}

{
"title": "Buy Groceries",
"description": "Get milk, eggs, and bread from the store.",
"date": "2024-02-10T15:30:00",
"priority": 1
}

{
"title": "Prepare Presentation",
"description": "Create slides and practice for the Monday meeting.",
"date": "2024-02-12T09:00:00",
"priority": 3
}

{
"title": "Gym Workout",
"description": "Follow a full-body strength training routine.",
"date": "2024-02-11T18:00:00",
"priority": 2
}

{
"title": "Read a Book",
"description": "Finish reading 'Clean Code' by Robert C. Martin.",
"date": "2024-02-15T20:00:00",
"priority": 1
}

# TASK Sample

{
"description":"Hello"
}


{
"description": "Research topic for presentation"
}

{
"description": "Write first draft of report"
}

{
"description": "Review and edit document"
}

{
"description": "Submit final version"
}

# TODO spring url
http://localhost:8081/api/todos

# TODO web url
http://localhost:8080/ToDoList-pgim2289/todos

# TODO task url
http://localhost:8081/api/todos/1/tasks

# TODO query paramter url
http://localhost:8080/ToDoList-pgim2289/todos?id=2
