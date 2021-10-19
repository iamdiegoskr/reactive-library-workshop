package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.usecases.RecommendResourceByThematicUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RecommendByThematicRouter {

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

}
