package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.BillingPlan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BillingPlan entity.
 */
public interface BillingPlanSearchRepository extends ElasticsearchRepository<BillingPlan, Long> {
}
