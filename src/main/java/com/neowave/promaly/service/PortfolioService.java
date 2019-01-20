package com.neowave.promaly.service;

import com.neowave.promaly.domain.Portfolio;
import com.neowave.promaly.repository.PortfolioRepository;
import com.neowave.promaly.repository.search.PortfolioSearchRepository;
import com.neowave.promaly.service.dto.PortfolioDTO;
import com.neowave.promaly.service.mapper.PortfolioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Portfolio.
 */
@Service
@Transactional
public class PortfolioService {

    private final Logger log = LoggerFactory.getLogger(PortfolioService.class);

    private final PortfolioRepository portfolioRepository;

    private final PortfolioMapper portfolioMapper;

    private final PortfolioSearchRepository portfolioSearchRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, PortfolioMapper portfolioMapper, PortfolioSearchRepository portfolioSearchRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioMapper = portfolioMapper;
        this.portfolioSearchRepository = portfolioSearchRepository;
    }

    /**
     * Save a portfolio.
     *
     * @param portfolioDTO the entity to save
     * @return the persisted entity
     */
    public PortfolioDTO save(PortfolioDTO portfolioDTO) {
        log.debug("Request to save Portfolio : {}", portfolioDTO);

        Portfolio portfolio = portfolioMapper.toEntity(portfolioDTO);
        portfolio = portfolioRepository.save(portfolio);
        PortfolioDTO result = portfolioMapper.toDto(portfolio);
        portfolioSearchRepository.save(portfolio);
        return result;
    }

    /**
     * Get all the portfolios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PortfolioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Portfolios");
        return portfolioRepository.findAll(pageable)
            .map(portfolioMapper::toDto);
    }


    /**
     * Get one portfolio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PortfolioDTO> findOne(Long id) {
        log.debug("Request to get Portfolio : {}", id);
        return portfolioRepository.findById(id)
            .map(portfolioMapper::toDto);
    }

    /**
     * Delete the portfolio by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Portfolio : {}", id);
        portfolioRepository.deleteById(id);
        portfolioSearchRepository.deleteById(id);
    }

    /**
     * Search for the portfolio corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PortfolioDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Portfolios for query {}", query);
        return portfolioSearchRepository.search(queryStringQuery(query), pageable)
            .map(portfolioMapper::toDto);
    }
}
