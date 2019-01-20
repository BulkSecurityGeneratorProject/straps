package com.neowave.promaly.repository.search;

import com.neowave.promaly.domain.Notes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Notes entity.
 */
public interface NotesSearchRepository extends ElasticsearchRepository<Notes, Long> {
}
