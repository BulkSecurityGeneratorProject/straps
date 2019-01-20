package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Amenity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Amenity entity.
 */
public interface AmenitySearchRepository extends ElasticsearchRepository<Amenity, Long> {
}
