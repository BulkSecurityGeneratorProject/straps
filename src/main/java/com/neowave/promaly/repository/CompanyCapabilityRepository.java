package com.neowave.promaly.repository;

import com.neowave.promaly.domain.CompanyCapability;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompanyCapability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyCapabilityRepository extends JpaRepository<CompanyCapability, Long> {

}
