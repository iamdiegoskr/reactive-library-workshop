package com.sofka.bibliotecaskr.repositories;

import com.sofka.bibliotecaskr.collections.Resource;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ResourceRepository extends ReactiveCrudRepository<Resource, String> {
    Flux<Resource> findAllByKind(final String kind);
    Flux<Resource> findAllByThematic(final String thematic);
}
