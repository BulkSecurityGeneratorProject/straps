package com.neowave.promaly.repository;

import com.neowave.promaly.domain.BillingPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BillingPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingPlanRepository extends JpaRepository<BillingPlan, Long> {

}
