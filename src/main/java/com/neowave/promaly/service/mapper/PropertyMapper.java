package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.PropertyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Property and its DTO PropertyDTO.
 */
@Mapper(componentModel = "spring", uses = {PortfolioMapper.class, AddressMapper.class, LookupMapper.class, LeaseMapper.class, LandLordMapper.class, CompanyMapper.class})
public interface PropertyMapper extends EntityMapper<PropertyDTO, Property> {

    @Mapping(source = "portfolio.id", target = "portfolioId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "usageType.id", target = "usageTypeId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "lease.id", target = "leaseId")
    PropertyDTO toDto(Property property);

    @Mapping(source = "portfolioId", target = "portfolio")
    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "usageTypeId", target = "usageType")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "leaseId", target = "lease")
    @Mapping(target = "projectedIncomes", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "buildings", ignore = true)
    @Mapping(target = "mortgages", ignore = true)
    @Mapping(target = "rentRolls", ignore = true)
    Property toEntity(PropertyDTO propertyDTO);

    default Property fromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
}
