package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.BuildingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Building and its DTO BuildingDTO.
 */
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, LookupMapper.class, LandLordMapper.class, CompanyMapper.class, LeaseMapper.class})
public interface BuildingMapper extends EntityMapper<BuildingDTO, Building> {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "assetType.id", target = "assetTypeId")
    BuildingDTO toDto(Building building);

    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "assetTypeId", target = "assetType")
    @Mapping(target = "projectedIncomes", ignore = true)
    @Mapping(target = "propertyUnits", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "mortgages", ignore = true)
    @Mapping(target = "rentRolls", ignore = true)
    Building toEntity(BuildingDTO buildingDTO);

    default Building fromId(Long id) {
        if (id == null) {
            return null;
        }
        Building building = new Building();
        building.setId(id);
        return building;
    }
}
