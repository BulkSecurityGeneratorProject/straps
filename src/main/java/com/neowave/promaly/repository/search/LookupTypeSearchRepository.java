package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.LookupType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LookupType entity.
 */
public interface LookupTypeSearchRepository extends ElasticsearchRepository<LookupType, Long> {
}
