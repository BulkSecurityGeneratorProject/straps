package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.ServiceRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServiceRequest and its DTO ServiceRequestDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceRequestMapper extends EntityMapper<ServiceRequestDTO, ServiceRequest> {



    default ServiceRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setId(id);
        return serviceRequest;
    }
}
