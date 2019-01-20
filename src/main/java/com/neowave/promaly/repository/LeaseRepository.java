package com.neowave.promaly.repository;

import com.neowave.promaly.domain.Lease;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lease entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {

}
