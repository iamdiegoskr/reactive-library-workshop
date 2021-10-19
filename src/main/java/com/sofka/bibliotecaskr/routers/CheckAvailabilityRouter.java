package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.usecases.CheckAvailabilityUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CheckAvailabilityRouter {

    @Bean
    public RouterFunction<ServerResponse> resourceAvailability(CheckAvailabilityUseCase checkAvailabilityUseCase) {
        return route(GET("/resources/availability/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(checkAvailabilityUseCase.apply(request.pathVariable("id")), String.class)
                        )
        );
    }

}
