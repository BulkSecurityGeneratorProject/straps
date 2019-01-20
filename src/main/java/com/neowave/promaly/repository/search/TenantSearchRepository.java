package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Tenant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tenant entity.
 */
public interface TenantSearchRepository extends ElasticsearchRepository<Tenant, Long> {
}
