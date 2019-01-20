package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.LookupTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LookupType and its DTO LookupTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LookupTypeMapper extends EntityMapper<LookupTypeDTO, LookupType> {



    default LookupType fromId(Long id) {
        if (id == null) {
            return null;
        }
        LookupType lookupType = new LookupType();
        lookupType.setId(id);
        return lookupType;
    }
}
