package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.mappers.MappersUtils;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateResourceUseCase implements SaveResource{

    private final ResourceRepository repository;
    private final MappersUtils mapper;

    public CreateResourceUseCase(MappersUtils mapper, ResourceRepository repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<ResourceDTO> apply(ResourceDTO resourceDTO) {
        return repository
                .save(mapper.mapperToResourceEntity().apply(resourceDTO))
                .map(resource -> mapper.mapperToResourceDTO().apply(resource));
    }
}
