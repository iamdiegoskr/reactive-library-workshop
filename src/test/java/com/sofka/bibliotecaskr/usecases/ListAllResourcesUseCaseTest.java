package com.sofka.bibliotecaskr.usecases;


import com.sofka.bibliotecaskr.collections.Resource;
import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class ListAllResourcesUseCaseTest {

    ResourceRepository repository;
    ListAllResourcesUseCase listUseCase;

    @Autowired
    private ResourceMapper mapper;


    @BeforeEach
    public void setup(){
        repository = mock(ResourceRepository.class);
        listUseCase = new ListAllResourcesUseCase(repository, mapper);
    }

    @Test
    void getValidationTest(){
        var resource =  new Resource();
        resource.setId("xxxx-xxxx");
        resource.setName("Bily y mandi");
        resource.setKind("Libro");
        resource.setThematic("Aventura");
        resource.setQuantityAvailable(100);
        resource.setAmountBorrowed(1);
        resource.setLocalDate(LocalDate.parse("2021-11-18"));
        when(repository.findAll()).thenReturn(Flux.just(resource));

        StepVerifier.create(listUseCase.get())
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getId().equals("xxxx-xxxx");
                    assert questionDTO.getName().equals("Bily y mandi");
                    assert questionDTO.getKind().equals("Libro");
                    assert questionDTO.getThematic().equals("Aventura");
                    assert questionDTO.getQuantityAvailable().equals(100);
                    assert questionDTO.getAmountBorrowed().equals(1);
                    assert questionDTO.getLocalDate().equals(LocalDate.parse("2021-11-18"));
                    return true;
                })
                .verifyComplete();

        verify(repository).findAll();
    }

}