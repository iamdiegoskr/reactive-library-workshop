package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.collections.Resource;
import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.when;


import java.time.LocalDate;
import java.util.Objects;


@SpringBootTest
class CreateResourceUseCaseTest {

    @SpyBean
    private CreateResourceUseCase createResourceUseCase;

    @MockBean
    private ResourceRepository repository;


    @Test
    void createResource(){

        var resourceDT0 = new ResourceDTO("yyy-yyy","Willi wonka","Libro","Aventura",
                30,0, LocalDate.parse("2020-10-18"));

        var resource = new Resource();
        resource.setId("yyy-yyy");
        resource.setName("Willi wonka");
        resource.setKind("Libro");
        resource.setThematic("Aventura");
        resource.setQuantityAvailable(30);
        resource.setAmountBorrowed(0);
        resource.setLocalDate(LocalDate.parse("2020-10-18"));

        when(repository.save(Mockito.any(Resource.class))).thenReturn(Mono.just(resource));

        var result = createResourceUseCase.apply(resourceDT0);

        Assertions.assertEquals(Objects.requireNonNull(result.block()).getId(),"yyy-yyy");
        Assertions.assertEquals(Objects.requireNonNull(result.block()).getName(),"Willi wonka");
        Assertions.assertEquals(Objects.requireNonNull(result.block()).getKind(),"Libro");
        Assertions.assertEquals(Objects.requireNonNull(result.block()).getThematic(),"Aventura");
        Assertions.assertEquals(Objects.requireNonNull(result.block()).getQuantityAvailable(),30);
        Assertions.assertEquals(Objects.requireNonNull(result.block()).getAmountBorrowed(),0);
        Assertions.assertEquals(Objects.requireNonNull(result.block()).getLocalDate(),LocalDate.parse("2020-10-18"));
    }

}