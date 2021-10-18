package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.usecases.*;
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
    public RouterFunction<ServerResponse> create(CreateResourceUseCase saveResourceUseCase) {
        Function<ResourceDTO, Mono<ServerResponse>> executor = resourceDTO -> saveResourceUseCase.apply(resourceDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));

        return route(
                POST("/add").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ResourceDTO.class).flatMap(executor)
        );
    }

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

    @Bean
    public RouterFunction<ServerResponse> delete(DeleteResourceUseCase deleteUseCase) {
        return route(
                DELETE("/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> resourceAvailability(CheckAvailabilityUseCase checkAvailabilityUseCase) {
        return route(GET("/resources/availability/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(checkAvailabilityUseCase.apply(request.pathVariable("id")), String.class)
                        )
        );
    }


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

    @Bean
    public RouterFunction<ServerResponse> backResource(ReturnResourceUseCase returnResourceUseCase) {
        return route(
                PUT("/resources/return/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(returnResourceUseCase.apply(request.pathVariable("id")), String.class))
                        .onErrorResume(error -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recommendByType(RecommendResourceByTypeUseCase recommendByTypeUseCase) {
        return route(
                GET("/resources/recommendByType/{type}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(recommendByTypeUseCase.get(request.pathVariable("type")), ResourceDTO.class)
                        ).onErrorResume(error -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recommendByThematic(RecommendResourceByThematicUseCase resourceByThematicUseCase) {
        return route(
                GET("/resources/recommendByThematic/{thematic}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(resourceByThematicUseCase.get(request.pathVariable("thematic")), ResourceDTO.class)
                        ).onErrorResume(error -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recommendByTypeAndThematic(RecommendByTypeAndThematicUseCase recommendByTypeAndThematicUseCase) {
        return route(
                GET("/resources/recommendByTypeAndThematic/{kind}/{thematic}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(recommendByTypeAndThematicUseCase.get(request.pathVariable("kind"),
                                request.pathVariable("thematic")), ResourceDTO.class)
                        ).onErrorResume(error -> ServerResponse.badRequest().build())
        );
    }


}
