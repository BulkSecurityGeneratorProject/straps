package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.PropertyVendor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PropertyVendor entity.
 */
public interface PropertyVendorSearchRepository extends ElasticsearchRepository<PropertyVendor, Long> {
}
