package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.DocumentStoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DocumentStore and its DTO DocumentStoreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentStoreMapper extends EntityMapper<DocumentStoreDTO, DocumentStore> {



    default DocumentStore fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentStore documentStore = new DocumentStore();
        documentStore.setId(id);
        return documentStore;
    }
}
