package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.usecases.UpdateResourceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UpdateResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> update(UpdateResourceUseCase updateResourceUseCase) {
        Function<ResourceDTO, Mono<ServerResponse>> executor = resourceDTO -> updateResourceUseCase.apply(resourceDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));

        return route(
                PUT("/resources/edit")
                        .and(accept(MediaType.APPLICATION_JSON)), request -> request
                        .bodyToMono(ResourceDTO.class)
                        .flatMap(executor)
        );
    }

}
