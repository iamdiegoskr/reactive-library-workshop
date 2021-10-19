package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.collections.Resource;
import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import com.sofka.bibliotecaskr.usecases.CreateResourceUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CreateResourceRouter.class, CreateResourceUseCase.class, ResourceMapper.class})
class CreateResourceRouterTest {

    @MockBean
    private ResourceRepository repository;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    void testRouterCreateResource() {

        Resource resource = new Resource();
        resource.setId("xxx");
        resource.setName("Keloke");
        resource.setKind("Libro");
        resource.setThematic("Aventura");
        resource.setQuantityAvailable(12);
        resource.setAmountBorrowed(0);
        resource.setLocalDate(LocalDate.parse("2021-10-18"));


        ResourceDTO resourceDTO = new ResourceDTO(resource.getId(),
                resource.getName(),resource.getKind(),resource.getThematic(),resource.getQuantityAvailable(),
                resource.getAmountBorrowed(),resource.getLocalDate());

        Mono<Resource> resourceMono = Mono.just(resource);
        when(repository.save(any())).thenReturn(resourceMono);

        webTestClient.post()
                .uri("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(resourceDTO), ResourceDTO.class)
                .exchange()
                .expectStatus().isOk();
    }

}