<div align="center">
    <div style="height:120px;">
        <h1 style="display:inline;color:dodgerblue;">DDD-Shopping Cart API</h1>
        <h6>by Ege Demirbaş</h6>
    </div>
    <img src="https://upload.wikimedia.org/wikipedia/tr/2/2e/Java_Logo.svg" width="5%" style="margin-right: 2.0rem;"/>
    <img src="https://upload.wikimedia.org/wikipedia/commons/4/4e/Docker_%28container_engine%29_logo.svg" width="30%"/>
    <a>
        <a style="margin-right:0px;margin-left:60px;color:green;">Powered by</a>
        <img src="https://upload.wikimedia.org/wikipedia/commons/4/44/Spring_Framework_Logo_2018.svg" width="20%"/>
    </a>


## Project Structure
```plaintext
Explanation of Each Layer
Application Layer: 
Contains application-specific logic, handling commands and coordinating use cases. DTOs are defined here for external communication.
Domain Layer: 
Defines the core business logic, including entities (Cart, Item, Promotion), aggregates, and value objects. Business rules, validations, and promotion logic are encapsulated here.
Infrastructure Layer:
 Contains file I/O and, if needed, in-memory or stub persistence mechanisms for simulating a repository. Any additional configurations for flexibility could also be placed here.
Interfaces Layer: 
Provides the CLI for interacting with the application, handling input and output to support the command structure.

This structure enables a clear separation of concerns, ensuring that each module is testable, maintainable, and easy to extend. Unit tests in each layer help maintain test-driven development practices and ensure that each component adheres to its responsibilities.
```

## Running Project
This guide explains how to set up and run the project in a Dockerized environment using Docker Compose. The docker-compose.yml file defines two main services: myapp (the Spring Boot application) and postgres (the PostgreSQL database).

Prerequisites
Docker: Ensure Docker is installed and running on your machine. Install Docker.
Docker Compose: Verify Docker Compose is available. It is often bundled with Docker Desktop.
Steps to Run the Project
Build and Start Services:

Navigate to the project root directory containing docker-compose.yml.

Run the following command to build the services and start the application:
```bash
docker-compose up --build
```
<br>

## Simulating Cases
Project contains script.sql file which has the necessary information to simulate cart case scenarios.

<br>

# API Documentation for CartController

The CartController class provides endpoints for managing the shopping cart, including adding and removing items, resetting the cart, and fetching cart details. This REST API uses HATEOAS for linking related resources and OpenAPI annotations for Swagger documentation.

### Base URL
`/v1/shopping-carts`

---

## Endpoints

### 1. Display Cart
Fetches the details of a cart by `cartId`.

- **URL**: `GET /v1/shopping-carts/{cartId}`
- **Description**: Returns the current state of the specified cart.
- **Path Parameters**:
    - `cartId` (Long): The unique identifier of the cart.
- **Responses**:
    - **200 OK**: Successfully fetched the cart.
        - **Body**: A `CartResponseDto` containing cart details.
- **Links**:
    - `self`: Link to the current cart.
    - `reset`: Link to reset the cart.

---

### 2. Add Item to Cart
Adds an item to the cart.

- **URL**: `POST /v1/shopping-carts/{cartId}/add-item`
- **Description**: Adds a new item specified by the request body to the cart.
- **Path Parameters**:
    - `cartId` (Long): The unique identifier of the cart.
- **Request Body**: `AddItemRequestDto` containing details about the item to be added.
- **Responses**:
    - **201 Created**: Item successfully added.
        - **Body**: A `CartResultResponseDto` showing the updated cart state.
- **Links**:
    - `self`: Link to the add-item action.
    - `displayCart`: Link to view the current cart.
    - `reset`: Link to reset the cart.

---

### 3. Add VasItem to Cart
Adds a value-added service (VasItem) to the cart.

- **URL**: `POST /v1/shopping-carts/{cartId}/add-vasItem`
- **Description**: Adds a VasItem specified in the request body to the cart.
- **Path Parameters**:
    - `cartId` (Long): The unique identifier of the cart.
- **Request Body**: `AddVasItemRequestDto` containing details about the VasItem.
- **Responses**:
    - **201 Created**: VasItem successfully added.
        - **Body**: A `CartResultResponseDto` showing the updated cart state.
- **Links**:
    - `self`: Link to the add-vasItem action.
    - `displayCart`: Link to view the current cart.
    - `reset`: Link to reset the cart.

---

### 4. Remove Item from Cart
Removes an item from the cart by its `itemId`.

- **URL**: `DELETE /v1/shopping-carts/{cartId}/{itemId}`
- **Description**: Deletes a specified item from the cart.
- **Path Parameters**:
    - `cartId` (Long): The unique identifier of the cart.
    - `itemId` (Long): The unique identifier of the item to remove.
- **Responses**:
    - **200 OK**: Item successfully removed.
        - **Body**: A `CartResultResponseDto` showing the updated cart state.
- **Links**:
    - `self`: Link to the remove-item action.
    - `displayCart`: Link to view the current cart.
    - `reset`: Link to reset the cart.

---

### 5. Reset Cart
Removes all items and applied promotions from the specified cart.

- **URL**: `DELETE /v1/shopping-carts/{cartId}/reset`
- **Description**: Empties the cart and removes all promotions.
- **Path Parameters**:
    - `cartId` (Long): The unique identifier of the cart.
- **Responses**:
    - **200 OK**: Cart successfully reset.
        - **Body**: A `CartResultResponseDto` showing the reset cart state.
- **Links**:
    - `self`: Link to the reset-cart action.
    - `displayCart`: Link to view the current cart.


</div>