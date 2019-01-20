package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Contract;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Contract entity.
 */
public interface ContractSearchRepository extends ElasticsearchRepository<Contract, Long> {
}
