package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.RentRollDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RentRoll and its DTO RentRollDTO.
 */
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, BuildingMapper.class, PropertyUnitMapper.class, LookupMapper.class})
public interface RentRollMapper extends EntityMapper<RentRollDTO, RentRoll> {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "propertyUnit.id", target = "propertyUnitId")
    @Mapping(source = "inflationType.id", target = "inflationTypeId")
    RentRollDTO toDto(RentRoll rentRoll);

    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "buildingId", target = "building")
    @Mapping(source = "propertyUnitId", target = "propertyUnit")
    @Mapping(source = "inflationTypeId", target = "inflationType")
    RentRoll toEntity(RentRollDTO rentRollDTO);

    default RentRoll fromId(Long id) {
        if (id == null) {
            return null;
        }
        RentRoll rentRoll = new RentRoll();
        rentRoll.setId(id);
        return rentRoll;
    }
}
