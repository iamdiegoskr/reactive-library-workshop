package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.usecases.CreateResourceUseCase;
import com.sofka.bibliotecaskr.usecases.GetResourceUseCase;
import com.sofka.bibliotecaskr.usecases.ListAllResourcesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> getAll(ListAllResourcesUseCase listResourceUseCase) {
        return route(GET("/resources"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listResourceUseCase.get(), ResourceDTO.class)
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> get(GetResourceUseCase getUseCase) {
        return route(
                GET("/resources/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getUseCase.apply(
                                request.pathVariable("id")),
                                ResourceDTO.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> save(CreateResourceUseCase saveResourceUseCase) {
        Function<ResourceDTO, Mono<ServerResponse>> executor = resourceDTO -> saveResourceUseCase.apply(resourceDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));

        return route(
                POST("/add").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ResourceDTO.class).flatMap(executor)
        );
    }



}
