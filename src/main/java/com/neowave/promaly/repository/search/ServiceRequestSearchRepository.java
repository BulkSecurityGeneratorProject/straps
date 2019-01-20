package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.ServiceRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ServiceRequest entity.
 */
public interface ServiceRequestSearchRepository extends ElasticsearchRepository<ServiceRequest, Long> {
}
