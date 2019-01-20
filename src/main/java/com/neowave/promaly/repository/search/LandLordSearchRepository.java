package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.LandLord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LandLord entity.
 */
public interface LandLordSearchRepository extends ElasticsearchRepository<LandLord, Long> {
}
