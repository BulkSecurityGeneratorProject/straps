package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.PortfolioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Portfolio and its DTO PortfolioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PortfolioMapper extends EntityMapper<PortfolioDTO, Portfolio> {


    @Mapping(target = "projectedIncomes", ignore = true)
    @Mapping(target = "properties", ignore = true)
    Portfolio toEntity(PortfolioDTO portfolioDTO);

    default Portfolio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Portfolio portfolio = new Portfolio();
        portfolio.setId(id);
        return portfolio;
    }
}
