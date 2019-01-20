package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.IncomeProjectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IncomeProjection and its DTO IncomeProjectionDTO.
 */
@Mapper(componentModel = "spring", uses = {PortfolioMapper.class, PropertyMapper.class, BuildingMapper.class, PropertyUnitMapper.class, LookupMapper.class})
public interface IncomeProjectionMapper extends EntityMapper<IncomeProjectionDTO, IncomeProjection> {

    @Mapping(source = "portfolio.id", target = "portfolioId")
    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "propertyUnit.id", target = "propertyUnitId")
    @Mapping(source = "assetLevelType.id", target = "assetLevelTypeId")
    IncomeProjectionDTO toDto(IncomeProjection incomeProjection);

    @Mapping(source = "portfolioId", target = "portfolio")
    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "buildingId", target = "building")
    @Mapping(source = "propertyUnitId", target = "propertyUnit")
    @Mapping(source = "assetLevelTypeId", target = "assetLevelType")
    IncomeProjection toEntity(IncomeProjectionDTO incomeProjectionDTO);

    default IncomeProjection fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncomeProjection incomeProjection = new IncomeProjection();
        incomeProjection.setId(id);
        return incomeProjection;
    }
}
