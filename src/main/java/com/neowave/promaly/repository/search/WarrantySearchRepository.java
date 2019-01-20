package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Warranty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Warranty entity.
 */
public interface WarrantySearchRepository extends ElasticsearchRepository<Warranty, Long> {
}
