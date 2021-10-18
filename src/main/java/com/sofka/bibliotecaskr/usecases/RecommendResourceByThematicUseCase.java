package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class RecommendResourceByThematicUseCase implements RecommendByThematic{

    private final ResourceRepository repository;
    private final ResourceMapper mapper;

    public RecommendResourceByThematicUseCase(ResourceMapper mapper, ResourceRepository repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<ResourceDTO> get(String thematic) {
        return repository.findAllByThematic(thematic).map(resource -> mapper.mapEntityToResourceDTO().apply(resource));
    }
}
