package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.CompanyCapabilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompanyCapability and its DTO CompanyCapabilityDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface CompanyCapabilityMapper extends EntityMapper<CompanyCapabilityDTO, CompanyCapability> {

    @Mapping(source = "company.id", target = "companyId")
    CompanyCapabilityDTO toDto(CompanyCapability companyCapability);

    @Mapping(source = "companyId", target = "company")
    CompanyCapability toEntity(CompanyCapabilityDTO companyCapabilityDTO);

    default CompanyCapability fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyCapability companyCapability = new CompanyCapability();
        companyCapability.setId(id);
        return companyCapability;
    }
}
