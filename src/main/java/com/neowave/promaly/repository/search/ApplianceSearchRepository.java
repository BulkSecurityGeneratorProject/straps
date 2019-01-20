package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Appliance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Appliance entity.
 */
public interface ApplianceSearchRepository extends ElasticsearchRepository<Appliance, Long> {
}
