package com.suryapradipta.orderservice.order;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.suryapradipta.orderservice.stubs.InventoryClientStub;
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
    public static WireMockServer wireMockServer = new WireMockServer(0);

    @LocalServerPort
    private Integer port;

    static {
        postgreSQLContainer.start();
        wireMockServer.start();
        System.setProperty("wiremock.server.port", String.valueOf(wireMockServer.port()));
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void placeOrder() {
        String requestBody = """
                {
                    "orderNumber": "ORD-12345",
                    "skuCode": "SKU12345",
                    "price": 100.00,
                    "quantity": 2
                }
                """;

        InventoryClientStub.stubInventoryCall(wireMockServer, "SKU12345", 2);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(201)
                .body(equalTo("Order placed successfully"));
    }
}