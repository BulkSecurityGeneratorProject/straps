package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.ApplianceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Appliance and its DTO ApplianceDTO.
 */
@Mapper(componentModel = "spring", uses = {PropertyUnitMapper.class})
public interface ApplianceMapper extends EntityMapper<ApplianceDTO, Appliance> {

    @Mapping(source = "propertyUnit.id", target = "propertyUnitId")
    ApplianceDTO toDto(Appliance appliance);

    @Mapping(source = "propertyUnitId", target = "propertyUnit")
    Appliance toEntity(ApplianceDTO applianceDTO);

    default Appliance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appliance appliance = new Appliance();
        appliance.setId(id);
        return appliance;
    }
}
