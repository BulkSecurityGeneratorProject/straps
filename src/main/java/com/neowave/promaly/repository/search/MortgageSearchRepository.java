package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Mortgage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Mortgage entity.
 */
public interface MortgageSearchRepository extends ElasticsearchRepository<Mortgage, Long> {
}
