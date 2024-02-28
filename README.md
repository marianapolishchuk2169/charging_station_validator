# Charging Station Validator Project

This project implements a RESTful service to manage electric vehicle charging stations with features like validation, internationalization support, and a mock data access layer.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Web MVC**
- **Spring Validation**
- **Spring Internationalization (i18n)**: 
- **Mockito**
- **JUnit**
- **Java Enums and Collections**
- **Lombok**
- **Gradle**

For ease of testing and demonstration, a Postman collection is available that includes pre-configured requests for interacting with the API. You can access and download the collection from the following link:

[Postman Collection](https://www.postman.com/spacecraft-explorer-61698248/workspace/charging-station-validator/collection/27153723-febe7ef1-dd2d-478f-9030-73a2bc71541d?action=share&creator=27153723)

**Please note:** In order for the Postman collection to work properly, the project must be running locally. Ensure that you have started the `ChargingStationValidatorApplication`

## Packages Overview

### `controller`
- #### `ChargingStationController`
  Handles HTTP requests for managing charging stations, supporting operations like creation and retrieval by ID. Includes internationalization for error messages.

### `dao`
- #### `ChargingStationDao`
  Interface defining data access operations for charging stations.

- #### `DummyChargingStationDaoImpl`
  Implements `ChargingStationDao` using a `HashMap` to simulate database operations for storing and retrieving `ChargingStation` objects.

### `internationalization`
- #### `InternationalizationConfig`
  Configures internationalization aspects of the application, setting up message sources for multi-language support and configuring locale resolution.

### `models`
- #### `ChargingStation`
  Represents a charging station, including properties like ID, title, description, address, public status, geo-coordinates, and connectors.

- #### `ChargingConnector`
  Represents a charging connector, including its ID, type, and maximum power.

- #### `GeoCoordinates`
  Holds geographical coordinates of a charging station, with validation for longitude and latitude.

- #### `ChargingConnectorType`
  Enum defining supported types of charging connectors (e.g., CSS, CHAdeMO, Type1, Type2).

### `service`
- #### `ChargingStationService`
  Service layer interface for operations related to charging stations, such as saving and finding by ID.

- #### `ChargingStationServiceImpl`
  Implements `ChargingStationService`, performing data access operations through `DummyDao`.

### `validator`
- #### `ChargingStationValidator`
  Implements custom validation logic for `ChargingStation` objects based on conditional annotations.

- #### `ConditionalValidation`, `ConditionalValidations`
  Custom annotations for defining conditional validation rules for `ChargingStation` objects.

### Start Application
- #### `ChargingStationValidatorApplication`
  Main class for running the Spring Boot application.

## Testing Overview

### `controller`
- #### `ChargingStationControllerTest`
  Unit tests for `ChargingStationController`, verifying behavior for valid and invalid inputs and handling of validation errors with internationalization.

### `service`
- #### `ChargingStationServiceTest`
  Unit tests for `ChargingStationServiceImpl`, ensuring correct interaction with `DummyDao` and functionality of service methods.

