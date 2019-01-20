package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.PropertyVendorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PropertyVendor and its DTO PropertyVendorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PropertyVendorMapper extends EntityMapper<PropertyVendorDTO, PropertyVendor> {



    default PropertyVendor fromId(Long id) {
        if (id == null) {
            return null;
        }
        PropertyVendor propertyVendor = new PropertyVendor();
        propertyVendor.setId(id);
        return propertyVendor;
    }
}
