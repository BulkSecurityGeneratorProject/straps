package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.LeaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lease and its DTO LeaseDTO.
 */
@Mapper(componentModel = "spring", uses = {LookupMapper.class})
public interface LeaseMapper extends EntityMapper<LeaseDTO, Lease> {

    @Mapping(source = "type.id", target = "typeId")
    LeaseDTO toDto(Lease lease);

    @Mapping(source = "typeId", target = "type")
    @Mapping(target = "propertyUnits", ignore = true)
    @Mapping(target = "buildings", ignore = true)
    Lease toEntity(LeaseDTO leaseDTO);

    default Lease fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lease lease = new Lease();
        lease.setId(id);
        return lease;
    }
}
