package com.neowave.promaly.repository;

import com.neowave.promaly.domain.Appliance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Appliance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplianceRepository extends JpaRepository<Appliance, Long> {

}
