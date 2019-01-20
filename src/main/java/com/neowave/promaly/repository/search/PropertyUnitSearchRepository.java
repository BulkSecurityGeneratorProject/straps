package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.PropertyUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PropertyUnit entity.
 */
public interface PropertyUnitSearchRepository extends ElasticsearchRepository<PropertyUnit, Long> {
}
