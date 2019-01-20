package com.neowave.promaly.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of WarrantySearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class WarrantySearchRepositoryMockConfiguration {

    @MockBean
    private WarrantySearchRepository mockWarrantySearchRepository;

}
