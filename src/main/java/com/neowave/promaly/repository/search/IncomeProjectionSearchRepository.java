package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.IncomeProjection;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IncomeProjection entity.
 */
public interface IncomeProjectionSearchRepository extends ElasticsearchRepository<IncomeProjection, Long> {
}
