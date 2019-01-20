package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.InvoiceHeader;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the InvoiceHeader entity.
 */
public interface InvoiceHeaderSearchRepository extends ElasticsearchRepository<InvoiceHeader, Long> {
}
