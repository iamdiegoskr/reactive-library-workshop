package com.sofka.bibliotecaskr.mappers;

import com.sofka.bibliotecaskr.collections.Resource;
import com.sofka.bibliotecaskr.dtos.ResourceDTO;
import org.springframework.stereotype.Component;


import java.util.function.Function;

@Component
public class ResourceMapper {

    public Function<ResourceDTO, Resource> mapperToResourceEntity() {
        return updateResource -> {
            var resource = new Resource();
            resource.setId(updateResource.getId());
            resource.setName(updateResource.getName());
            resource.setKind(updateResource.getKind());
            resource.setThematic(updateResource.getThematic());
            resource.setQuantityAvailable(updateResource.getQuantityAvailable());
            resource.setAmountBorrowed(updateResource.getAmountBorrowed());
            resource.setLocalDate(updateResource.getLocalDate());
            return resource;
        };
    }

    public Function<Resource, ResourceDTO> mapEntityToResourceDTO() {
        return entity -> new ResourceDTO(
                entity.getId(),
                entity.getName(),
                entity.getKind(),
                entity.getThematic(),
                entity.getQuantityAvailable(),
                entity.getAmountBorrowed(),
                entity.getLocalDate()
        );
    }

}
