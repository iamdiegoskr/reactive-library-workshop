package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteResourceUseCase implements Function<String, Mono<Void>> {

    private final ResourceRepository repository;
    private final ResourceMapper mapper;

    public DeleteResourceUseCase(ResourceRepository repository, ResourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "El id es requerido para eliminar el recurso");
        return repository.deleteById(id);
    }
}
