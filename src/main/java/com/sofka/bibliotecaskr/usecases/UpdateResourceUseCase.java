package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.collections.Resource;
import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateResourceUseCase implements SaveResource{

    private final ResourceRepository repository;
    private final ResourceMapper mapper;

    public UpdateResourceUseCase(ResourceMapper mapper, ResourceRepository repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<ResourceDTO> apply(ResourceDTO resourceDTO) {
        Objects.requireNonNull(resourceDTO.getId(), "El id es requerido");
        return repository.save(mapper.toResource(resourceDTO))
                .map(mapper::toResourceDto);
    }
}
