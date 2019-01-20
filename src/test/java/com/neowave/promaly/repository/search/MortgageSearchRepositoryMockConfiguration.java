package com.neowave.promaly.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of MortgageSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MortgageSearchRepositoryMockConfiguration {

    @MockBean
    private MortgageSearchRepository mockMortgageSearchRepository;

}
