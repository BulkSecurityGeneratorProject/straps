package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.CompanyCapability;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CompanyCapability entity.
 */
public interface CompanyCapabilitySearchRepository extends ElasticsearchRepository<CompanyCapability, Long> {
}
