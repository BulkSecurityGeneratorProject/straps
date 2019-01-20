package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private Long id;

    private Long locationId;

    private Long locationTypeId;

    private Long addressTypeId;

    private String description;

    private String addressline1;

    private String addressline2;

    private String addressline3;

    private String city;

    private String state;

    private String postalCode;

    private String countryCode;

    private String country;

    private String phoneNumber;

    private String tollFreeNumber;

    private String afterHoursNumber;

    private String faxNumber;

    private String emailAddress;

    private String phonePrefix;

    private String geocode;

    private String version;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(Long locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public Long getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Long addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getAddressline3() {
        return addressline3;
    }

    public void setAddressline3(String addressline3) {
        this.addressline3 = addressline3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTollFreeNumber() {
        return tollFreeNumber;
    }

    public void setTollFreeNumber(String tollFreeNumber) {
        this.tollFreeNumber = tollFreeNumber;
    }

    public String getAfterHoursNumber() {
        return afterHoursNumber;
    }

    public void setAfterHoursNumber(String afterHoursNumber) {
        this.afterHoursNumber = afterHoursNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if (addressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
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
            ", company=" + getCompanyId() +
            "}";
    }
}
