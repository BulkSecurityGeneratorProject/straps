package com.neowave.promaly.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PropertyVendor entity.
 */
public class PropertyVendorDTO implements Serializable {

    private Long id;

    private Long companyId;

    private Long ranking;

    private LocalDate startDate;

    private LocalDate endDate;

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

    public Long getRanking() {
        return ranking;
    }

    public void setRanking(Long ranking) {
        this.ranking = ranking;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

        PropertyVendorDTO propertyVendorDTO = (PropertyVendorDTO) o;
        if (propertyVendorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertyVendorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertyVendorDTO{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", ranking=" + getRanking() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
