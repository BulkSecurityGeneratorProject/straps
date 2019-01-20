package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CompanyCapability entity.
 */
public class CompanyCapabilityDTO implements Serializable {

    private Long id;

    private Long capabilityId;

    private Long license;

    private Long certfication;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCapabilityId() {
        return capabilityId;
    }

    public void setCapabilityId(Long capabilityId) {
        this.capabilityId = capabilityId;
    }

    public Long getLicense() {
        return license;
    }

    public void setLicense(Long license) {
        this.license = license;
    }

    public Long getCertfication() {
        return certfication;
    }

    public void setCertfication(Long certfication) {
        this.certfication = certfication;
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

        CompanyCapabilityDTO companyCapabilityDTO = (CompanyCapabilityDTO) o;
        if (companyCapabilityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyCapabilityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyCapabilityDTO{" +
            "id=" + getId() +
            ", capabilityId=" + getCapabilityId() +
            ", license=" + getLicense() +
            ", certfication=" + getCertfication() +
            ", company=" + getCompanyId() +
            "}";
    }
}
