package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.WarrantyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Warranty and its DTO WarrantyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WarrantyMapper extends EntityMapper<WarrantyDTO, Warranty> {



    default Warranty fromId(Long id) {
        if (id == null) {
            return null;
        }
        Warranty warranty = new Warranty();
        warranty.setId(id);
        return warranty;
    }
}
