package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@FunctionalInterface
public interface RecommendByTypeAndThematic {
    Flux<ResourceDTO> get(@Valid String kind, @Valid String thematic);
}
