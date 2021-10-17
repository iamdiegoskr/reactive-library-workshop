package com.sofka.bibliotecaskr.repositories;

import com.sofka.bibliotecaskr.collections.Resource;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ResourceRepository extends ReactiveCrudRepository<Resource, String> {
}
