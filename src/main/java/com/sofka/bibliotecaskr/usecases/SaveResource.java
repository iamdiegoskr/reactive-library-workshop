package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface SaveResource {
    Mono<ResourceDTO> apply(@Valid ResourceDTO resourceDTO);
}
