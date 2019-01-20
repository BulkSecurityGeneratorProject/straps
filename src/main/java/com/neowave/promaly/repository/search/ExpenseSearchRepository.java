package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Expense;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Expense entity.
 */
public interface ExpenseSearchRepository extends ElasticsearchRepository<Expense, Long> {
}
