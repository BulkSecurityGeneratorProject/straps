package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.DocumentStore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DocumentStore entity.
 */
public interface DocumentStoreSearchRepository extends ElasticsearchRepository<DocumentStore, Long> {
}
