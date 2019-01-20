package com.neowave.promaly.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ServiceRequest entity.
 */
public class ServiceRequestDTO implements Serializable {

    private Long id;

    private Long companyId;

    private Long propertyUnitsId;

    private Long propertiesId;

    private Long propertyVendorsId;

    private Long category;

    private Long subCategory;

    private Long urgency;

    private Long unitId;

    private Long propertyId;

    private Instant requestDateTime;

    private String description;

    private Long assignmentStatus;

    private String version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPropertyUnitsId() {
        return propertyUnitsId;
    }

    public void setPropertyUnitsId(Long propertyUnitsId) {
        this.propertyUnitsId = propertyUnitsId;
    }

    public Long getPropertiesId() {
        return propertiesId;
    }

    public void setPropertiesId(Long propertiesId) {
        this.propertiesId = propertiesId;
    }

    public Long getPropertyVendorsId() {
        return propertyVendorsId;
    }

    public void setPropertyVendorsId(Long propertyVendorsId) {
        this.propertyVendorsId = propertyVendorsId;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Long subCategory) {
        this.subCategory = subCategory;
    }

    public Long getUrgency() {
        return urgency;
    }

    public void setUrgency(Long urgency) {
        this.urgency = urgency;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Instant getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(Instant requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(Long assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceRequestDTO serviceRequestDTO = (ServiceRequestDTO) o;
        if (serviceRequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceRequestDTO{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", propertyUnitsId=" + getPropertyUnitsId() +
            ", propertiesId=" + getPropertiesId() +
            ", propertyVendorsId=" + getPropertyVendorsId() +
            ", category=" + getCategory() +
            ", subCategory=" + getSubCategory() +
            ", urgency=" + getUrgency() +
            ", unitId=" + getUnitId() +
            ", propertyId=" + getPropertyId() +
            ", requestDateTime='" + getRequestDateTime() + "'" +
            ", description='" + getDescription() + "'" +
            ", assignmentStatus=" + getAssignmentStatus() +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
