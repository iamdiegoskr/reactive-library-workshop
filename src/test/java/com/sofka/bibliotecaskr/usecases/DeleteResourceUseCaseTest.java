package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteResourceUseCaseTest {

    @SpyBean
    private DeleteResourceUseCase deleteResourceUseCase;

    @MockBean
    private ResourceRepository repository;

    @Test
    void deleteResource(){
        var resourceDTO = new ResourceDTO("xxx","Jairito el demente","Libro",
                "Aventura",33,0, LocalDate.parse("2021-10-12"));

        Mockito.when(repository.deleteById("xxx")).thenReturn(Mono.empty());

        var result = deleteResourceUseCase.apply("xxx").block();
        assertNull(result);
    }

}