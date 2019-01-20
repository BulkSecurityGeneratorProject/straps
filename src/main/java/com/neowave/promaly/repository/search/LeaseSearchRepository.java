package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Lease;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Lease entity.
 */
public interface LeaseSearchRepository extends ElasticsearchRepository<Lease, Long> {
}
