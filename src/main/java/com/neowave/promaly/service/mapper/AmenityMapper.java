package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.AmenityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Amenity and its DTO AmenityDTO.
 */
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, BuildingMapper.class, PropertyUnitMapper.class})
public interface AmenityMapper extends EntityMapper<AmenityDTO, Amenity> {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "propertyUnit.id", target = "propertyUnitId")
    AmenityDTO toDto(Amenity amenity);

    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "buildingId", target = "building")
    @Mapping(source = "propertyUnitId", target = "propertyUnit")
    Amenity toEntity(AmenityDTO amenityDTO);

    default Amenity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Amenity amenity = new Amenity();
        amenity.setId(id);
        return amenity;
    }
}
