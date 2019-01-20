package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Property entity.
 */
public class PropertyDTO implements Serializable {

    private Long id;

    private String description;

    private Double grossArea;

    private Double netArea;

    private Long portfolioId;

    private Long addressId;

    private Long usageTypeId;

    private Long statusId;

    private Long leaseId;

    private Set<LandLordDTO> owners = new HashSet<>();

    private Set<CompanyDTO> companies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGrossArea() {
        return grossArea;
    }

    public void setGrossArea(Double grossArea) {
        this.grossArea = grossArea;
    }

    public Double getNetArea() {
        return netArea;
    }

    public void setNetArea(Double netArea) {
        this.netArea = netArea;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getUsageTypeId() {
        return usageTypeId;
    }

    public void setUsageTypeId(Long lookupId) {
        this.usageTypeId = lookupId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long lookupId) {
        this.statusId = lookupId;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public Set<LandLordDTO> getOwners() {
        return owners;
    }

    public void setOwners(Set<LandLordDTO> landLords) {
        this.owners = landLords;
    }

    public Set<CompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<CompanyDTO> companies) {
        this.companies = companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropertyDTO propertyDTO = (PropertyDTO) o;
        if (propertyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", grossArea=" + getGrossArea() +
            ", netArea=" + getNetArea() +
            ", portfolio=" + getPortfolioId() +
            ", address=" + getAddressId() +
            ", usageType=" + getUsageTypeId() +
            ", status=" + getStatusId() +
            ", lease=" + getLeaseId() +
            "}";
    }
}
