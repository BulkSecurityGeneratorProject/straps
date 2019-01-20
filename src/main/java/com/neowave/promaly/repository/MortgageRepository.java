package com.neowave.promaly.repository;

import com.neowave.promaly.domain.Mortgage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Mortgage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MortgageRepository extends JpaRepository<Mortgage, Long> {

}
