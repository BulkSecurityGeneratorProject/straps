package com.neowave.promaly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ServiceRequest.
 */
@Entity
@Table(name = "service_request")
@Document(indexName = "servicerequest")
public class ServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "property_units_id")
    private Long propertyUnitsId;

    @Column(name = "properties_id")
    private Long propertiesId;

    @Column(name = "property_vendors_id")
    private Long propertyVendorsId;

    @Column(name = "category")
    private Long category;

    @Column(name = "sub_category")
    private Long subCategory;

    @Column(name = "urgency")
    private Long urgency;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "request_date_time")
    private Instant requestDateTime;

    @Column(name = "description")
    private String description;

    @Column(name = "assignment_status")
    private Long assignmentStatus;

    @Column(name = "version")
    private String version;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public ServiceRequest companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPropertyUnitsId() {
        return propertyUnitsId;
    }

    public ServiceRequest propertyUnitsId(Long propertyUnitsId) {
        this.propertyUnitsId = propertyUnitsId;
        return this;
    }

    public void setPropertyUnitsId(Long propertyUnitsId) {
        this.propertyUnitsId = propertyUnitsId;
    }

    public Long getPropertiesId() {
        return propertiesId;
    }

    public ServiceRequest propertiesId(Long propertiesId) {
        this.propertiesId = propertiesId;
        return this;
    }

    public void setPropertiesId(Long propertiesId) {
        this.propertiesId = propertiesId;
    }

    public Long getPropertyVendorsId() {
        return propertyVendorsId;
    }

    public ServiceRequest propertyVendorsId(Long propertyVendorsId) {
        this.propertyVendorsId = propertyVendorsId;
        return this;
    }

    public void setPropertyVendorsId(Long propertyVendorsId) {
        this.propertyVendorsId = propertyVendorsId;
    }

    public Long getCategory() {
        return category;
    }

    public ServiceRequest category(Long category) {
        this.category = category;
        return this;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getSubCategory() {
        return subCategory;
    }

    public ServiceRequest subCategory(Long subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public void setSubCategory(Long subCategory) {
        this.subCategory = subCategory;
    }

    public Long getUrgency() {
        return urgency;
    }

    public ServiceRequest urgency(Long urgency) {
        this.urgency = urgency;
        return this;
    }

    public void setUrgency(Long urgency) {
        this.urgency = urgency;
    }

    public Long getUnitId() {
        return unitId;
    }

    public ServiceRequest unitId(Long unitId) {
        this.unitId = unitId;
        return this;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public ServiceRequest propertyId(Long propertyId) {
        this.propertyId = propertyId;
        return this;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Instant getRequestDateTime() {
        return requestDateTime;
    }

    public ServiceRequest requestDateTime(Instant requestDateTime) {
        this.requestDateTime = requestDateTime;
        return this;
    }

    public void setRequestDateTime(Instant requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public String getDescription() {
        return description;
    }

    public ServiceRequest description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAssignmentStatus() {
        return assignmentStatus;
    }

    public ServiceRequest assignmentStatus(Long assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
        return this;
    }

    public void setAssignmentStatus(Long assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public String getVersion() {
        return version;
    }

    public ServiceRequest version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
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
        ServiceRequest serviceRequest = (ServiceRequest) o;
        if (serviceRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
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
