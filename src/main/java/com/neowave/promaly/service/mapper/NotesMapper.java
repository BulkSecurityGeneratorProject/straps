package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.NotesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Notes and its DTO NotesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotesMapper extends EntityMapper<NotesDTO, Notes> {



    default Notes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notes notes = new Notes();
        notes.setId(id);
        return notes;
    }
}
