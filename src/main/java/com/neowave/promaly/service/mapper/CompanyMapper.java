package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {LookupMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {

    @Mapping(source = "type.id", target = "typeId")
    CompanyDTO toDto(Company company);

    @Mapping(source = "typeId", target = "type")
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "capabilities", ignore = true)
    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "buildings", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "managementCompanies", ignore = true)
    Company toEntity(CompanyDTO companyDTO);

    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
