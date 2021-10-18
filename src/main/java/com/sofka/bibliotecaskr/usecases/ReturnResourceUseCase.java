package com.sofka.bibliotecaskr.usecases;

import com.sofka.bibliotecaskr.mappers.ResourceMapper;
import com.sofka.bibliotecaskr.repositories.ResourceRepository;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class ReturnResourceUseCase  implements Function<String, Mono<String>> {

    private final ResourceRepository repository;
    private final ResourceMapper mapper;
    private final UpdateResourceUseCase updateResourceUseCase;

    public ReturnResourceUseCase(ResourceRepository repository, ResourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.updateResourceUseCase = new UpdateResourceUseCase(mapper,repository);
    }

    @Override
    public Mono<String> apply(String id) {
        return repository.findById(id).flatMap(resource -> {
            if(resource.getAmountBorrowed()>0){
                resource.setAmountBorrowed(resource.getAmountBorrowed()-1);
                return updateResourceUseCase.apply(mapper.toResourceDto(resource))
                        .thenReturn("El recurso fue devuelto con exito");
            }
            return Mono.just("No hay recursos por devolver");
        });
    }
}
