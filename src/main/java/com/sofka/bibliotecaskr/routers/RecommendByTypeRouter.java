package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.usecases.RecommendResourceByTypeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RecommendByTypeRouter {


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

}
