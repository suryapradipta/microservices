package com.suryapradipta.orderservice.order;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @ServiceConnection
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17.2");

    @LocalServerPort
    private Integer port;

    static {
        postgreSQLContainer.start();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void placeOrder() {
        String orderDtoJson = """
                    {
                        "orderNumber": "ORD-12345",
                        "skuCode": "SKU-001",
                        "price": 100.00,
                        "quantity": 2
                    }
                    """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(orderDtoJson)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(201)
                .body(equalTo("Order placed successfully"));
    }
}