package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.DiscountPlan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DiscountPlan entity.
 */
public interface DiscountPlanSearchRepository extends ElasticsearchRepository<DiscountPlan, Long> {
}
