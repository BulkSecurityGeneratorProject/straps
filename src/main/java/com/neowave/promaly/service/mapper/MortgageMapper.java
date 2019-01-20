package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.MortgageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mortgage and its DTO MortgageDTO.
 */
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, BuildingMapper.class, PropertyUnitMapper.class})
public interface MortgageMapper extends EntityMapper<MortgageDTO, Mortgage> {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "propertyUnit.id", target = "propertyUnitId")
    MortgageDTO toDto(Mortgage mortgage);

    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "buildingId", target = "building")
    @Mapping(source = "propertyUnitId", target = "propertyUnit")
    Mortgage toEntity(MortgageDTO mortgageDTO);

    default Mortgage fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mortgage mortgage = new Mortgage();
        mortgage.setId(id);
        return mortgage;
    }
}
