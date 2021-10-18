package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class CheckAvailabilityUseCase implements Function<String, Mono<String>> {

    private final ResourceRepository repository;

    public CheckAvailabilityUseCase(ResourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> apply(String id) {
        Objects.requireNonNull(id, "El id no puede ser nulo");
        return repository.findById(id)
                .map(resource ->
                        resource.getQuantityAvailable() > resource.getAmountBorrowed()
                                ? "El recurso esta disponible, quedan " + (resource.getQuantityAvailable() - resource.getAmountBorrowed()) + " disponibles"
                                : "el recurso no esta disponible " + " fue prestado " + resource.getLocalDate()
                );
    }
}
