package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.RentRoll;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RentRoll entity.
 */
public interface RentRollSearchRepository extends ElasticsearchRepository<RentRoll, Long> {
}
