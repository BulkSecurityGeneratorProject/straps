package com.neowave.promaly.repository;

import com.neowave.promaly.domain.IncomeProjection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IncomeProjection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeProjectionRepository extends JpaRepository<IncomeProjection, Long> {

}
