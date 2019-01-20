package com.neowave.promaly.repository;

import com.neowave.promaly.domain.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Building entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query(value = "select distinct building from Building building left join fetch building.owners left join fetch building.companies left join fetch building.leases",
        countQuery = "select count(distinct building) from Building building")
    Page<Building> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct building from Building building left join fetch building.owners left join fetch building.companies left join fetch building.leases")
    List<Building> findAllWithEagerRelationships();

    @Query("select building from Building building left join fetch building.owners left join fetch building.companies left join fetch building.leases where building.id =:id")
    Optional<Building> findOneWithEagerRelationships(@Param("id") Long id);

}
