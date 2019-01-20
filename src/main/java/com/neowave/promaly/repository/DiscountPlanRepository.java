package com.neowave.promaly.repository;

import com.neowave.promaly.domain.DiscountPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DiscountPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountPlanRepository extends JpaRepository<DiscountPlan, Long> {

}
