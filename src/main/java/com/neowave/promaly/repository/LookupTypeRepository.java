package com.neowave.promaly.repository;

import com.neowave.promaly.domain.LookupType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LookupType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LookupTypeRepository extends JpaRepository<LookupType, Long> {

}
