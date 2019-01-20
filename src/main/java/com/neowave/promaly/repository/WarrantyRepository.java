package com.neowave.promaly.repository;

import com.neowave.promaly.domain.Warranty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Warranty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Long> {

}
