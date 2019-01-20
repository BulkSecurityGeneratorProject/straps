package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Feature;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Feature entity.
 */
public interface FeatureSearchRepository extends ElasticsearchRepository<Feature, Long> {
}
