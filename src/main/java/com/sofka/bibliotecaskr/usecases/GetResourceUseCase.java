package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetResourceUseCase implements Function<String, Mono<ResourceDTO>> {

    private final ResourceRepository repository;
    private final ResourceMapper mapper;

    public GetResourceUseCase(ResourceMapper mapper, ResourceRepository repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<ResourceDTO> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return repository.findById(id).map(resource -> mapper.mapEntityToResourceDTO().apply(resource));
    }
}
