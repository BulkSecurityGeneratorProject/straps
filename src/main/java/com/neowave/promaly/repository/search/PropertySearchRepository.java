package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Property;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Property entity.
 */
public interface PropertySearchRepository extends ElasticsearchRepository<Property, Long> {
}
