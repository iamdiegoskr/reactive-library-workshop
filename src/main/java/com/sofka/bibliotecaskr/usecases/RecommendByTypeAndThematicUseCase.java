package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class RecommendByTypeAndThematicUseCase implements RecommendByTypeAndThematic{

    private final ResourceRepository repository;
    private final ResourceMapper mapper;

    public RecommendByTypeAndThematicUseCase(ResourceMapper mapper, ResourceRepository repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<ResourceDTO> get(String kind, String thematic) {
        return repository.findAllByKindAndThematic(kind,thematic).map(mapper::toResourceDto);
    }
}
