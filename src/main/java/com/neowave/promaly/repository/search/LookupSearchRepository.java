package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Lookup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Lookup entity.
 */
public interface LookupSearchRepository extends ElasticsearchRepository<Lookup, Long> {
}
