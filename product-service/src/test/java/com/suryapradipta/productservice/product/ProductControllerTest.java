package com.suryapradipta.productservice.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.mongodb.MongoDBContainer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @LocalServerPort
    private Integer port;

    static {
        mongoDBContainer.start();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void createProduct() {
        String requestBody = """
                {
                   "name": "Product 1",
                   "description": "Product 1 description",
                   "price": 19
                 }
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/products")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Product 1"))
                .body("description", Matchers.equalTo("Product 1 description"))
                .body("price", Matchers.equalTo(19));
    }

    @Test
    void getAllProducts() {
        // Create a product first
        String requestBody = """
                {
                   "name": "Product 2",
                   "description": "Product 2 description",
                   "price": 29
                 }
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/products")
                .then()
                .statusCode(201);

        // Get all products and verify at least one exists
        RestAssured.given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.greaterThanOrEqualTo(1));
    }

    @Test
    void deleteProduct() {
        // Create a product and extract its id
        String requestBody = """
                {
                   "name": "Product 3",
                   "description": "Product 3 description",
                   "price": 39
                 }
                """;

        String productId = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/products")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Delete the product
        RestAssured.given()
                .when()
                .delete("/api/products/{id}", productId)
                .then()
                .statusCode(204);

        // Verify the product is deleted
        RestAssured.given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("id", Matchers.not(Matchers.hasItem(productId)));
    }
}