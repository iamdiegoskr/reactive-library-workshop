package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Objects;

@Service
@Validated
public class LendResourceUseCase implements Function<String, Mono<String>> {

    private final ResourceRepository repository;
    private final ResourceMapper mapper;
    private final UpdateResourceUseCase updateResourceUseCase;

    public LendResourceUseCase(ResourceRepository repository, ResourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.updateResourceUseCase = new UpdateResourceUseCase(mapper,repository);
    }

    @Override
    public Mono<String> apply(String id) {
        Objects.requireNonNull(id,"El id es requerido");
        return repository.findById(id).flatMap(resource -> {
            if (resource.getQuantityAvailable()>resource.getAmountBorrowed()){
                resource.setAmountBorrowed(resource.getAmountBorrowed()+1);
                resource.setLocalDate(LocalDate.now());
                return updateResourceUseCase.apply(mapper.toResourceDto(resource)).thenReturn("El recurso fue prestado con exito");
            }
            return Mono.just("El recurso no esta disponible en el momento, regresa pronto");
        });
    }
}
