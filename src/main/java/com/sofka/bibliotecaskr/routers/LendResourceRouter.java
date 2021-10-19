package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.usecases.LendResourceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LendResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> lend(LendResourceUseCase lendUseCase) {
        return route(
                PUT("/resources/lend/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(lendUseCase.apply(request.pathVariable("id")), String.class))
                        .onErrorResume(error -> ServerResponse.badRequest().build())
        );
    }

}