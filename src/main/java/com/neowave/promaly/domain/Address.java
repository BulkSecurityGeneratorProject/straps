package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Document(indexName = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_type_id")
    private Long locationTypeId;

    @Column(name = "address_type_id")
    private Long addressTypeId;

    @Column(name = "description")
    private String description;

    @Column(name = "addressline_1")
    private String addressline1;

    @Column(name = "addressline_2")
    private String addressline2;

    @Column(name = "addressline_3")
    private String addressline3;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country")
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "toll_free_number")
    private String tollFreeNumber;

    @Column(name = "after_hours_number")
    private String afterHoursNumber;

    @Column(name = "fax_number")
    private String faxNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "phone_prefix")
    private String phonePrefix;

    @Column(name = "geocode")
    private String geocode;

    @Column(name = "version")
    private String version;

    @ManyToOne
    @JsonIgnoreProperties("addresses")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public Address locationId(Long locationId) {
        this.locationId = locationId;
        return this;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationTypeId() {
        return locationTypeId;
    }

    public Address locationTypeId(Long locationTypeId) {
        this.locationTypeId = locationTypeId;
        return this;
    }

    public void setLocationTypeId(Long locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public Long getAddressTypeId() {
        return addressTypeId;
    }

    public Address addressTypeId(Long addressTypeId) {
        this.addressTypeId = addressTypeId;
        return this;
    }

    public void setAddressTypeId(Long addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public String getDescription() {
        return description;
    }

    public Address description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public Address addressline1(String addressline1) {
        this.addressline1 = addressline1;
        return this;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public Address addressline2(String addressline2) {
        this.addressline2 = addressline2;
        return this;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getAddressline3() {
        return addressline3;
    }

    public Address addressline3(String addressline3) {
        this.addressline3 = addressline3;
        return this;
    }

    public void setAddressline3(String addressline3) {
        this.addressline3 = addressline3;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Address state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Address postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Address countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public Address country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTollFreeNumber() {
        return tollFreeNumber;
    }

    public Address tollFreeNumber(String tollFreeNumber) {
        this.tollFreeNumber = tollFreeNumber;
        return this;
    }

    public void setTollFreeNumber(String tollFreeNumber) {
        this.tollFreeNumber = tollFreeNumber;
    }

    public String getAfterHoursNumber() {
        return afterHoursNumber;
    }

    public Address afterHoursNumber(String afterHoursNumber) {
        this.afterHoursNumber = afterHoursNumber;
        return this;
    }

    public void setAfterHoursNumber(String afterHoursNumber) {
        this.afterHoursNumber = afterHoursNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public Address faxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Address emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public Address phonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
        return this;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getGeocode() {
        return geocode;
    }

    public Address geocode(String geocode) {
        this.geocode = geocode;
        return this;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public String getVersion() {
        return version;
    }

    public Address version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Company getCompany() {
        return company;
    }

    public Address company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        if (address.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", locationId=" + getLocationId() +
            ", locationTypeId=" + getLocationTypeId() +
            ", addressTypeId=" + getAddressTypeId() +
            ", description='" + getDescription() + "'" +
            ", addressline1='" + getAddressline1() + "'" +
            ", addressline2='" + getAddressline2() + "'" +
            ", addressline3='" + getAddressline3() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", tollFreeNumber='" + getTollFreeNumber() + "'" +
            ", afterHoursNumber='" + getAfterHoursNumber() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", phonePrefix='" + getPhonePrefix() + "'" +
            ", geocode='" + getGeocode() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
