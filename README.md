# Warehouse Management System

![JavaFX](https://img.shields.io/badge/JavaFX-v23-blue.svg)
![Maven](https://img.shields.io/badge/Maven-Build-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-22-orange.svg)

The **Warehouse Management System** is a JavaFX-based desktop application that helps streamline warehouse operations, including inventory management, order processing and transportation. The system is designed for 2 nd Year 1st Semester Rapid Application Development (IS2104) Assignment at UCSC.

## Technologies Used
- **Java 22**: Core programming language.
- **JavaFX 23**: GUI framework for desktop applications.
- **Maven**: Build and dependency management.
- **MySQL (MariaDB)** (or other databases): Lightweight local database for data storage.

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/warehouse-management-system.git
   cd warehouse-management-system
   ```
2. **Download Maven**
   ```bash
    Visit the [Apache Maven download page](https://maven.apache.org/download.cgi).
    Download the latest **binary zip archive** (e.g., `apache-maven-3.x.x-bin.zip`).
   ```

3. **Build the project** Use Maven to compile and package the project:
    ```bash
    mvn clean install
    ```
    
4. **Run the application** After building, use Maven to run the application:
    ```bash
    mvn javafx:run
    ```

