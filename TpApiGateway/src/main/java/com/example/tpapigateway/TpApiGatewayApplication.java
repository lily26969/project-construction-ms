package com.example.tpapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@EnableDiscoveryClient
@SpringBootApplication
public class TpApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpApiGatewayApplication.class, args);
    }
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){

        return builder.routes()

                .route("finance-service", r->r.path("/api/**")
                        .uri("http://localhost:8089"))
                .build();
    }
}
