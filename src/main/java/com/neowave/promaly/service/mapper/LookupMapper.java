package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.LookupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lookup and its DTO LookupDTO.
 */
@Mapper(componentModel = "spring", uses = {ContractMapper.class})
public interface LookupMapper extends EntityMapper<LookupDTO, Lookup> {

    @Mapping(source = "contract.id", target = "contractId")
    LookupDTO toDto(Lookup lookup);

    @Mapping(source = "contractId", target = "contract")
    Lookup toEntity(LookupDTO lookupDTO);

    default Lookup fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lookup lookup = new Lookup();
        lookup.setId(id);
        return lookup;
    }
}
