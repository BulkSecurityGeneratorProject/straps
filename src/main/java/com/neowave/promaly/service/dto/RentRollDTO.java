package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RentRoll entity.
 */
public class RentRollDTO implements Serializable {

    private Long id;

    private Double securityDeposit;

    private Double bankGuarantee;

    private Double leaseRecoveryTiming;

    private Long propertyId;

    private Long buildingId;

    private Long propertyUnitId;

    private Long inflationTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public Double getBankGuarantee() {
        return bankGuarantee;
    }

    public void setBankGuarantee(Double bankGuarantee) {
        this.bankGuarantee = bankGuarantee;
    }

    public Double getLeaseRecoveryTiming() {
        return leaseRecoveryTiming;
    }

    public void setLeaseRecoveryTiming(Double leaseRecoveryTiming) {
        this.leaseRecoveryTiming = leaseRecoveryTiming;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getPropertyUnitId() {
        return propertyUnitId;
    }

    public void setPropertyUnitId(Long propertyUnitId) {
        this.propertyUnitId = propertyUnitId;
    }

    public Long getInflationTypeId() {
        return inflationTypeId;
    }

    public void setInflationTypeId(Long lookupId) {
        this.inflationTypeId = lookupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RentRollDTO rentRollDTO = (RentRollDTO) o;
        if (rentRollDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rentRollDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RentRollDTO{" +
            "id=" + getId() +
            ", securityDeposit=" + getSecurityDeposit() +
            ", bankGuarantee=" + getBankGuarantee() +
            ", leaseRecoveryTiming=" + getLeaseRecoveryTiming() +
            ", property=" + getPropertyId() +
            ", building=" + getBuildingId() +
            ", propertyUnit=" + getPropertyUnitId() +
            ", inflationType=" + getInflationTypeId() +
            "}";
    }
}
