package com.neowave.promaly.service.mapper;

import com.neowave.promaly.domain.*;
import com.neowave.promaly.service.dto.BillingPlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BillingPlan and its DTO BillingPlanDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BillingPlanMapper extends EntityMapper<BillingPlanDTO, BillingPlan> {



    default BillingPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        BillingPlan billingPlan = new BillingPlan();
        billingPlan.setId(id);
        return billingPlan;
    }
}
