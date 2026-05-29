## 3. Dataset Acquisition

We selected the Chinook dataset because it provides a well-structured relational database model that is highly suitable for a music store management system. The dataset was obtained from the official Chinook GitHub repository. Following Option C, we downloaded the schema and seed SQL files, then imported them into MySQL Workbench to create and populate our local database.

---

## 4. System Architecture

The application was designed using a layered architecture to improve maintainability and ensure a clear separation of responsibilities between components.

### DAO Layer

Handles all database interactions using the Data Access Object (DAO) pattern.

### Business Logic Layer

Contains service classes responsible for processing application logic and managing data flow.

### View Layer

Implemented using Java Swing with a dashboard-style interface to display statistics and charts.

### Design Patterns Used

* **Singleton Pattern:** Applied in the `DatabaseConnection` class to ensure only one database connection instance exists throughout the application's lifecycle.
* **Observer Pattern:** Used to automatically refresh charts whenever the underlying JTable data changes.

---

## 5. Database Design

The database consists of five core tables: `Artist`, `Album`, `Track`, `Customer`, and `Invoice`. Primary Key (PK) and Foreign Key (FK) constraints were implemented to maintain data integrity and relationships between tables. Additionally, indexing was applied to important columns to improve query and filtering performance.

---

## 6. System Features

The application includes several key features:

### Authentication System

Provides secure login functionality with password hashing and role-based access control for Admin and Viewer users.

![Login Screen](project/images/z7878545712718_cff823019d29efd5caf959df5c9f2dbd.jpg)

### CRUD Operations

Supports Create, Read, Update, and Delete operations for artist and album management, including pagination and column sorting.

![Dashboard](project/images/z7878545749183_b7ba477e88d875ad6b3215fa662172bc.jpg)

### Data Visualization

Displays interactive KPI panels and charts using the JFreeChart library.

---

## 7. Modern Java Features Used

Several modern Java features were incorporated into the project:

* **Records:** `AlbumChartDTO` was implemented as an immutable Data Transfer Object (DTO).
* **Stream API:** Used for efficient filtering, grouping, and aggregation of artist-related statistics.
* **var Keyword:** Applied for local type inference to improve readability, especially in the `updateAllData` method.

---

## 8. Unit Tests

JUnit 5 was used to implement unit tests for the DAO layer and validation logic. These tests verify CRUD functionality and ensure database connectivity and query operations work correctly.

---

## 9. REST/Export Integration

For the integration component, we implemented Excel export functionality using the Apache POI library. Users can export the current table view into an `.xlsx` file with formatted headers and automatically adjusted column widths.

---

## 10. Challenges & Reflections

One of the biggest challenges during development was maintaining stable database connections while ensuring smooth synchronization between background data processing and UI updates. To address this issue, we used `SwingWorker` for background tasks, allowing the Event Dispatch Thread (EDT) to remain responsive.

![alt text](project/images/887abba4-86f5-47d3-822d-e8b1e73f4d71.png)

Through this project, we gained valuable experience in multi-threading, database integration, and modern object-oriented programming principles in Java.

---

## 11. Conclusion & Future Work

Overall, the project successfully fulfills the required objectives and delivers a functional and maintainable desktop application. In the future, the system could be further improved by migrating to frameworks such as Spring Boot or integrating a cloud-based database solution.

---

## 12. References

* Apache POI Project. (2026). *POI Overview*. Retrieved from https://poi.apache.org/
* JFreeChart Library. (2026). *Documentation and User Guide*. Retrieved from http://www.jfree.org/jfreechart/
* Oracle. (2026). *Java Documentation*. Retrieved from https://docs.oracle.com/en/java/

---

## Developed By

* Truong Gia Huy
* Nguyen Ngoc Nhu Y
