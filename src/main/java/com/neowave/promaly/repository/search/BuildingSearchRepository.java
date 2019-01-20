package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Building;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Building entity.
 */
public interface BuildingSearchRepository extends ElasticsearchRepository<Building, Long> {
}
