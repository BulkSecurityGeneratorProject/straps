package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.PropertyUnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PropertyUnit and its DTO PropertyUnitDTO.
 */
@Mapper(componentModel = "spring", uses = {BuildingMapper.class, AddressMapper.class, LookupMapper.class, MortgageMapper.class, RentRollMapper.class, LeaseMapper.class, LandLordMapper.class})
public interface PropertyUnitMapper extends EntityMapper<PropertyUnitDTO, PropertyUnit> {

    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "usageType.id", target = "usageTypeId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "mortgage.id", target = "mortgageId")
    @Mapping(source = "rentRoll.id", target = "rentRollId")
    PropertyUnitDTO toDto(PropertyUnit propertyUnit);

    @Mapping(source = "buildingId", target = "building")
    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "usageTypeId", target = "usageType")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "mortgageId", target = "mortgage")
    @Mapping(source = "rentRollId", target = "rentRoll")
    @Mapping(target = "projectedIncomes", ignore = true)
    @Mapping(target = "tenants", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "mortgages", ignore = true)
    @Mapping(target = "appliances", ignore = true)
    @Mapping(target = "rentRolls", ignore = true)
    PropertyUnit toEntity(PropertyUnitDTO propertyUnitDTO);

    default PropertyUnit fromId(Long id) {
        if (id == null) {
            return null;
        }
        PropertyUnit propertyUnit = new PropertyUnit();
        propertyUnit.setId(id);
        return propertyUnit;
    }
}
