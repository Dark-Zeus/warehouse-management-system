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
   git clone https://github.com/Dark-Zeus/warehouse-management-system.git
   cd warehouse-management-system
   ```
2. **Download Maven & Setup Maven**
  
    1. Visit the [Apache Maven download page](https://maven.apache.org/download.cgi).
    2. Download the latest **binary zip archive** (e.g., `apache-maven-3.x.x-bin.zip`).
    3. Extract the downloaded zip file to a directory of your choice (e.g., C:\apache-maven-3.x.x )
    4. Open the Start menu, search for “Environment Variables,” and select “Edit the system environment variables.”
    5. In the System Properties window, click on “Environment Variables.”
    6. Under System variables, click “New” and enter MAVEN_HOME as the variable name and the path to your Maven directory as the variable value (e.g., C:\apache-maven-3.x.x).
    7. Find the Path variable under System variables, select it, and click “Edit.”
    8. Click “New” and add %MAVEN_HOME%\bin to the list. Click “OK” to save the changes.

3. **Verify Maven Installation**
   1. Open a new command prompt window.
   2. Type `mvn -version` and press Enter.
   3. You should see the Maven version information displayed, confirming that Maven is installed correctly.

4. **Verify You Have JDK 22 or higher is installed**
   1. In the Command Prompt, type java -version and press Enter.
      ```bash
      java version "22.0.1" 2024-10-15
      Java(TM) SE Runtime Environment (build 22.0.1+10)
      Java HotSpot(TM) 64-Bit Server VM (build 22.0.1+10, mixed mode)
      ```

   2. In the Command Prompt, type `javac -version` and press Enter.
   3. You should see output similar to:
      ```bash
      javac 22.0.1
      ```
   4. Ensure the version number is 22 or higher in both cases.

4. **Build the project** Use Maven to compile and package the project:
    ```bash
    mvn clean install
    ```
    
5. **Run the application** After building, use Maven to run the application:
    ```bash
    mvn javafx:run
    ```

