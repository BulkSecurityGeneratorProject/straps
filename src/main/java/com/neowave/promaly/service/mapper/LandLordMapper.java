package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.LandLordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LandLord and its DTO LandLordDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LandLordMapper extends EntityMapper<LandLordDTO, LandLord> {


    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "buildings", ignore = true)
    @Mapping(target = "propertyUnits", ignore = true)
    LandLord toEntity(LandLordDTO landLordDTO);

    default LandLord fromId(Long id) {
        if (id == null) {
            return null;
        }
        LandLord landLord = new LandLord();
        landLord.setId(id);
        return landLord;
    }
}
