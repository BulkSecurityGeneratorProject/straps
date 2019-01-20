package com.neowave.promaly.web.rest;

import com.neowave.promaly.PromalyV5App;

import com.neowave.promaly.domain.Address;
import com.neowave.promaly.repository.AddressRepository;
import com.neowave.promaly.repository.search.AddressSearchRepository;
import com.neowave.promaly.service.AddressService;
import com.neowave.promaly.service.dto.AddressDTO;
import com.neowave.promaly.service.mapper.AddressMapper;
import com.neowave.promaly.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.neowave.promaly.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AddressResource REST controller.
 *
 * @see AddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PromalyV5App.class)
public class AddressResourceIntTest {

    private static final Long DEFAULT_LOCATION_ID = 1L;
    private static final Long UPDATED_LOCATION_ID = 2L;

    private static final Long DEFAULT_LOCATION_TYPE_ID = 1L;
    private static final Long UPDATED_LOCATION_TYPE_ID = 2L;

    private static final Long DEFAULT_ADDRESS_TYPE_ID = 1L;
    private static final Long UPDATED_ADDRESS_TYPE_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSLINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSLINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSLINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSLINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSLINE_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSLINE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TOLL_FREE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TOLL_FREE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AFTER_HOURS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AFTER_HOURS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_PREFIX = "BBBBBBBBBB";

    private static final String DEFAULT_GEOCODE = "AAAAAAAAAA";
    private static final String UPDATED_GEOCODE = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressService addressService;

    /**
     * This repository is mocked in the com.neowave.promaly.repository.search test package.
     *
     * @see com.neowave.promaly.repository.search.AddressSearchRepositoryMockConfiguration
     */
    @Autowired
    private AddressSearchRepository mockAddressSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAddressMockMvc;

    private Address address;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressResource addressResource = new AddressResource(addressService);
        this.restAddressMockMvc = MockMvcBuilders.standaloneSetup(addressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .locationId(DEFAULT_LOCATION_ID)
            .locationTypeId(DEFAULT_LOCATION_TYPE_ID)
            .addressTypeId(DEFAULT_ADDRESS_TYPE_ID)
            .description(DEFAULT_DESCRIPTION)
            .addressline1(DEFAULT_ADDRESSLINE_1)
            .addressline2(DEFAULT_ADDRESSLINE_2)
            .addressline3(DEFAULT_ADDRESSLINE_3)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .postalCode(DEFAULT_POSTAL_CODE)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .country(DEFAULT_COUNTRY)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .tollFreeNumber(DEFAULT_TOLL_FREE_NUMBER)
            .afterHoursNumber(DEFAULT_AFTER_HOURS_NUMBER)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .phonePrefix(DEFAULT_PHONE_PREFIX)
            .geocode(DEFAULT_GEOCODE)
            .version(DEFAULT_VERSION);
        return address;
    }

    @Before
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testAddress.getLocationTypeId()).isEqualTo(DEFAULT_LOCATION_TYPE_ID);
        assertThat(testAddress.getAddressTypeId()).isEqualTo(DEFAULT_ADDRESS_TYPE_ID);
        assertThat(testAddress.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAddress.getAddressline1()).isEqualTo(DEFAULT_ADDRESSLINE_1);
        assertThat(testAddress.getAddressline2()).isEqualTo(DEFAULT_ADDRESSLINE_2);
        assertThat(testAddress.getAddressline3()).isEqualTo(DEFAULT_ADDRESSLINE_3);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testAddress.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAddress.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testAddress.getTollFreeNumber()).isEqualTo(DEFAULT_TOLL_FREE_NUMBER);
        assertThat(testAddress.getAfterHoursNumber()).isEqualTo(DEFAULT_AFTER_HOURS_NUMBER);
        assertThat(testAddress.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testAddress.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testAddress.getPhonePrefix()).isEqualTo(DEFAULT_PHONE_PREFIX);
        assertThat(testAddress.getGeocode()).isEqualTo(DEFAULT_GEOCODE);
        assertThat(testAddress.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(1)).save(testAddress);
    }

    @Test
    @Transactional
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(0)).save(address);
    }

    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationTypeId").value(hasItem(DEFAULT_LOCATION_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].addressTypeId").value(hasItem(DEFAULT_ADDRESS_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].addressline1").value(hasItem(DEFAULT_ADDRESSLINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressline2").value(hasItem(DEFAULT_ADDRESSLINE_2.toString())))
            .andExpect(jsonPath("$.[*].addressline3").value(hasItem(DEFAULT_ADDRESSLINE_3.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tollFreeNumber").value(hasItem(DEFAULT_TOLL_FREE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].afterHoursNumber").value(hasItem(DEFAULT_AFTER_HOURS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phonePrefix").value(hasItem(DEFAULT_PHONE_PREFIX.toString())))
            .andExpect(jsonPath("$.[*].geocode").value(hasItem(DEFAULT_GEOCODE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID.intValue()))
            .andExpect(jsonPath("$.locationTypeId").value(DEFAULT_LOCATION_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.addressTypeId").value(DEFAULT_ADDRESS_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.addressline1").value(DEFAULT_ADDRESSLINE_1.toString()))
            .andExpect(jsonPath("$.addressline2").value(DEFAULT_ADDRESSLINE_2.toString()))
            .andExpect(jsonPath("$.addressline3").value(DEFAULT_ADDRESSLINE_3.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.tollFreeNumber").value(DEFAULT_TOLL_FREE_NUMBER.toString()))
            .andExpect(jsonPath("$.afterHoursNumber").value(DEFAULT_AFTER_HOURS_NUMBER.toString()))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.phonePrefix").value(DEFAULT_PHONE_PREFIX.toString()))
            .andExpect(jsonPath("$.geocode").value(DEFAULT_GEOCODE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .locationId(UPDATED_LOCATION_ID)
            .locationTypeId(UPDATED_LOCATION_TYPE_ID)
            .addressTypeId(UPDATED_ADDRESS_TYPE_ID)
            .description(UPDATED_DESCRIPTION)
            .addressline1(UPDATED_ADDRESSLINE_1)
            .addressline2(UPDATED_ADDRESSLINE_2)
            .addressline3(UPDATED_ADDRESSLINE_3)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .postalCode(UPDATED_POSTAL_CODE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .country(UPDATED_COUNTRY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .tollFreeNumber(UPDATED_TOLL_FREE_NUMBER)
            .afterHoursNumber(UPDATED_AFTER_HOURS_NUMBER)
            .faxNumber(UPDATED_FAX_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .phonePrefix(UPDATED_PHONE_PREFIX)
            .geocode(UPDATED_GEOCODE)
            .version(UPDATED_VERSION);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testAddress.getLocationTypeId()).isEqualTo(UPDATED_LOCATION_TYPE_ID);
        assertThat(testAddress.getAddressTypeId()).isEqualTo(UPDATED_ADDRESS_TYPE_ID);
        assertThat(testAddress.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAddress.getAddressline1()).isEqualTo(UPDATED_ADDRESSLINE_1);
        assertThat(testAddress.getAddressline2()).isEqualTo(UPDATED_ADDRESSLINE_2);
        assertThat(testAddress.getAddressline3()).isEqualTo(UPDATED_ADDRESSLINE_3);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testAddress.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddress.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAddress.getTollFreeNumber()).isEqualTo(UPDATED_TOLL_FREE_NUMBER);
        assertThat(testAddress.getAfterHoursNumber()).isEqualTo(UPDATED_AFTER_HOURS_NUMBER);
        assertThat(testAddress.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testAddress.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testAddress.getPhonePrefix()).isEqualTo(UPDATED_PHONE_PREFIX);
        assertThat(testAddress.getGeocode()).isEqualTo(UPDATED_GEOCODE);
        assertThat(testAddress.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(1)).save(testAddress);
    }

    @Test
    @Transactional
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(0)).save(address);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Get the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Address in Elasticsearch
        verify(mockAddressSearchRepository, times(1)).deleteById(address.getId());
    }

    @Test
    @Transactional
    public void searchAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        when(mockAddressSearchRepository.search(queryStringQuery("id:" + address.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(address), PageRequest.of(0, 1), 1));
        // Search the address
        restAddressMockMvc.perform(get("/api/_search/addresses?query=id:" + address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationTypeId").value(hasItem(DEFAULT_LOCATION_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].addressTypeId").value(hasItem(DEFAULT_ADDRESS_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].addressline1").value(hasItem(DEFAULT_ADDRESSLINE_1)))
            .andExpect(jsonPath("$.[*].addressline2").value(hasItem(DEFAULT_ADDRESSLINE_2)))
            .andExpect(jsonPath("$.[*].addressline3").value(hasItem(DEFAULT_ADDRESSLINE_3)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].tollFreeNumber").value(hasItem(DEFAULT_TOLL_FREE_NUMBER)))
            .andExpect(jsonPath("$.[*].afterHoursNumber").value(hasItem(DEFAULT_AFTER_HOURS_NUMBER)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].phonePrefix").value(hasItem(DEFAULT_PHONE_PREFIX)))
            .andExpect(jsonPath("$.[*].geocode").value(hasItem(DEFAULT_GEOCODE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = new Address();
        address1.setId(1L);
        Address address2 = new Address();
        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);
        address2.setId(2L);
        assertThat(address1).isNotEqualTo(address2);
        address1.setId(null);
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressDTO.class);
        AddressDTO addressDTO1 = new AddressDTO();
        addressDTO1.setId(1L);
        AddressDTO addressDTO2 = new AddressDTO();
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
        addressDTO2.setId(addressDTO1.getId());
        assertThat(addressDTO1).isEqualTo(addressDTO2);
        addressDTO2.setId(2L);
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
        addressDTO1.setId(null);
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addressMapper.fromId(null)).isNull();
    }
}
