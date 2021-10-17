package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.mappers.MappersUtils;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

public class ListAllResourcesUseCase implements Supplier<Flux<ResourceDTO>> {

    private final ResourceRepository repository;
    private final MappersUtils mapper;

    public ListAllResourcesUseCase(ResourceRepository repository, MappersUtils mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<ResourceDTO> get() {
        return repository.findAll().map(mapper.mapperToResourceDTO());
    }
}