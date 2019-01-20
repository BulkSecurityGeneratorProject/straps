package com.neowave.promaly.repository;

import com.neowave.promaly.domain.PropertyUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PropertyUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyUnitRepository extends JpaRepository<PropertyUnit, Long> {

    @Query(value = "select distinct property_unit from PropertyUnit property_unit left join fetch property_unit.leases left join fetch property_unit.owners",
        countQuery = "select count(distinct property_unit) from PropertyUnit property_unit")
    Page<PropertyUnit> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct property_unit from PropertyUnit property_unit left join fetch property_unit.leases left join fetch property_unit.owners")
    List<PropertyUnit> findAllWithEagerRelationships();

    @Query("select property_unit from PropertyUnit property_unit left join fetch property_unit.leases left join fetch property_unit.owners where property_unit.id =:id")
    Optional<PropertyUnit> findOneWithEagerRelationships(@Param("id") Long id);

}
