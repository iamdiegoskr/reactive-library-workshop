package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import com.sofka.bibliotecaskr.usecases.CreateResourceUseCase;
import com.sofka.bibliotecaskr.usecases.DeleteResourceUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DeleteResourceRouter.class, DeleteResourceUseCase.class, ResourceMapper.class})
class DeleteResourceRouterTest {

    @MockBean
    private ResourceRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testDeleteEmployee()
    {
        Mono<Void> voidReturn  = Mono.empty();

        Mockito.when(repository.deleteById("xxx")).thenReturn(voidReturn);

        webTestClient.delete().uri("/delete/{id}", "xxx")
                .exchange()
                .expectStatus().isAccepted();
    }

}