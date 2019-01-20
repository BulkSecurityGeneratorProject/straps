package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.TenantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tenant and its DTO TenantDTO.
 */
@Mapper(componentModel = "spring", uses = {PropertyUnitMapper.class})
public interface TenantMapper extends EntityMapper<TenantDTO, Tenant> {

    @Mapping(source = "propertyUnit.id", target = "propertyUnitId")
    TenantDTO toDto(Tenant tenant);

    @Mapping(source = "propertyUnitId", target = "propertyUnit")
    Tenant toEntity(TenantDTO tenantDTO);

    default Tenant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tenant tenant = new Tenant();
        tenant.setId(id);
        return tenant;
    }
}
