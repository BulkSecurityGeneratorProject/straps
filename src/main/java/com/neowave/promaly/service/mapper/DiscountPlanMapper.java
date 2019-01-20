package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.DiscountPlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DiscountPlan and its DTO DiscountPlanDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiscountPlanMapper extends EntityMapper<DiscountPlanDTO, DiscountPlan> {



    default DiscountPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        DiscountPlan discountPlan = new DiscountPlan();
        discountPlan.setId(id);
        return discountPlan;
    }
}
