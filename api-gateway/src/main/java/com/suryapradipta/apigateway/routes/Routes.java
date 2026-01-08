package com.suryapradipta.apigateway.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> customRoutes() {
        return route("product_service")
                .route(RequestPredicates.path("/api/products/**"), http())
                .before(uri("http://localhost:8080"))
                .build()


                .and(route("order_service")
                        .route(RequestPredicates.path("/api/orders/**"), http())
                        .before(uri("http://localhost:8081"))
                        .build())

                .and(route("inventory_service")
                        .route(RequestPredicates.path("/api/inventories/**"), http())
                        .before(uri("http://localhost:8082"))
                        .build())

                .and(route("fallbackRoute")
                        .GET("/fallbackRoute", request -> ServerResponse
                                .status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Service Unavailable, please try again later.")).build())

                ;

    }
}
