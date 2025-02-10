
# Courier Tracking Application

This project is a Spring Boot-based application designed to track courier locations and interact with a PostgreSQL database. The application is packaged and deployed using Docker, with PostgreSQL as the database backend.

## Prerequisites

Before you can run the project, make sure the following software is installed on your system:

- Docker and Docker Compose
- Maven
- Java 17
- Git

## Project Setup

1. **Clone the repository**:  
   Clone this repository to your local machine using Git:

   ```bash
   git clone https://github.com/mihrinurgrn/courier-tracking.git
   ```

2. **Configure the `application.properties` file**:  
   Configure your Spring Boot application to connect to the PostgreSQL database. This can be done via environment variables in Docker Compose or by modifying `application.properties` directly.

3. **Update the `docker-compose.yml` file**:  
   The `docker-compose.yml` file should define both the PostgreSQL service and the Spring Boot application service. Make sure the correct database credentials and configurations are provided for both services.

### Example `docker-compose.yml`:
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: postgres-db
    environment:
      POSTGRES_DB: couriertracking
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: secret
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

  app:
    build: .
    container_name: courier-app
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/couriertracking
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: secret
    ports:
      - "8080:8080"

volumes:
  pgdata:
```

## Build the Project

After configuring Docker Compose and ensuring that the necessary database configurations are in place, build the project using Maven:

```bash
mvn clean package
```

This will generate the JAR file necessary to run the application.

## Start the Application with Docker Compose

Once the JAR is built, you can start the application along with the PostgreSQL database using Docker Compose. In the root directory of the project, run:

```bash
docker-compose up --build
```

This will:

- Build and start the Docker containers for both PostgreSQL and the Spring Boot application.
- Make the Spring Boot application available at `localhost:8080`.

## Verify the Application

After the Docker containers are up, you can verify the running application by navigating to `http://localhost:8080` in your web browser.

## Database Configuration

The application uses PostgreSQL for storing data. The database schema and initial data are automatically created when the containers are started. Here’s the SQL used to create the `store` table and insert data:

```sql
-- Create table if not exists
CREATE TABLE IF NOT EXISTS store (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

-- Insert data if not exists
INSERT INTO store (name, latitude, longitude)
VALUES ('Ataşehir MMM Migros', 40.9923307, 29.1244229)
ON CONFLICT (name) DO NOTHING;

INSERT INTO store (name, latitude, longitude)
VALUES ('Novada MMM Migros', 40.986106, 29.1161293)
ON CONFLICT (name) DO NOTHING;

INSERT INTO store (name, latitude, longitude)
VALUES ('Beylikdüzü 5M Migros', 41.0066851, 28.6552262)
ON CONFLICT (name) DO NOTHING;

INSERT INTO store (name, latitude, longitude)
VALUES ('Ortaköy MMM Migros', 41.055783, 29.0210292)
ON CONFLICT (name) DO NOTHING;

INSERT INTO store (name, latitude, longitude)
VALUES ('Caddebostan MMM Migros', 40.9632463, 29.0630908)
ON CONFLICT (name) DO NOTHING;
```

This data is inserted into the database only if the store doesn't already exist (based on the store name).

## Manual Run Instructions

1. **Build the Project**: If you prefer to build the project manually before running it in Docker, use Maven:

    ```bash
    mvn clean package
    ```

2. **Build the Docker Image**: To build the Docker image of the application, run:

    ```bash
    docker build -t courier-app .
    ```

3. **Run the Application**: After building the image, run the application and PostgreSQL using Docker Compose:

    ```bash
    docker-compose up
    ```

This command will launch both the PostgreSQL container and the Spring Boot application container.

## API Usage and Testing With Postman

The application tracks courier location data and stores it in the database. You can interact with the application through its exposed API endpoints.

Some of the key endpoints (just examples) may include:

- **POST /update-courier-location**: This endpoint accepts the courier’s location data.
    - **Request Body**: `CourierLocationRequest` (in JSON format)
- **GET /courier/{courierId}/total-distance**: This endpoint is used to calculate the total distance traveled by a courier, based on their recorded location data.


### Postman Collection

You can download and import the Postman collection from the json file:

docs/postman/couriertracking.postman-collection.json


## Running Tests

If you want to run tests for the application, you can use Maven to execute the tests:

```bash
mvn test
```

This will run all the unit tests configured in the project.
