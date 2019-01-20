package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Tenant entity.
 */
public class TenantDTO implements Serializable {

    private Long id;

    private Long leaseId;

    private Long contact;

    private Long propertyUnitId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public Long getPropertyUnitId() {
        return propertyUnitId;
    }

    public void setPropertyUnitId(Long propertyUnitId) {
        this.propertyUnitId = propertyUnitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TenantDTO tenantDTO = (TenantDTO) o;
        if (tenantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantDTO{" +
            "id=" + getId() +
            ", leaseId=" + getLeaseId() +
            ", contact=" + getContact() +
            ", propertyUnit=" + getPropertyUnitId() +
            "}";
    }
}
