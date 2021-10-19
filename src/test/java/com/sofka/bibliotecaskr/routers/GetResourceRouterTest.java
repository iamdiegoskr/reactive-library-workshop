package com.sofka.bibliotecaskr.routers;

import com.sofka.bibliotecaskr.collections.Resource;
import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import com.sofka.bibliotecaskr.usecases.CreateResourceUseCase;
import com.sofka.bibliotecaskr.usecases.GetResourceUseCase;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetResourceRouter.class, GetResourceUseCase.class, ResourceMapper.class})
class GetResourceRouterTest {

    @MockBean
    private ResourceRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetEmployeeById()
    {
        var resource = new Resource();
        resource.setId("xxx");
        resource.setName("Huelo el miedo");
        resource.setKind("Libro");
        resource.setThematic("Romance");
        resource.setQuantityAvailable(4);
        resource.setAmountBorrowed(0);
        resource.setLocalDate(LocalDate.parse("2021-10-18"));

        Mockito.when(repository.findById("xxx")).thenReturn(Mono.just(resource));

        webTestClient.get().uri("/resources/{id}", "xxx")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("xxx")
                .jsonPath("$.name").isEqualTo("Huelo el miedo")
                .jsonPath("$.kind").isEqualTo("Libro")
                .jsonPath("$.thematic").isEqualTo("Romance")
                .jsonPath("$.quantityAvailable").isEqualTo(4)
                .jsonPath("$.amountBorrowed").isEqualTo(0)
                .jsonPath("$.localDate").isEqualTo("2021-10-18");


        Mockito.verify(repository, times(1)).findById("xxx");
    }

}