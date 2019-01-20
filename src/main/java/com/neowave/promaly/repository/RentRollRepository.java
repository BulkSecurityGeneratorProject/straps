package com.neowave.promaly.repository;

import com.neowave.promaly.domain.RentRoll;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RentRoll entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentRollRepository extends JpaRepository<RentRoll, Long> {

}
