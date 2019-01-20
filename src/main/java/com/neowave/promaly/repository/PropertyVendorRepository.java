package com.neowave.promaly.repository;

import com.neowave.promaly.domain.PropertyVendor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PropertyVendor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyVendorRepository extends JpaRepository<PropertyVendor, Long> {

}
