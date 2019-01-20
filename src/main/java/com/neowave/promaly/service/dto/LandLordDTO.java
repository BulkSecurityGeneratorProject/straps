package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LandLord entity.
 */
public class LandLordDTO implements Serializable {

    private Long id;

    private Long contactId;

    private Double percentageOwnership;

    private Long ownershipType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Double getPercentageOwnership() {
        return percentageOwnership;
    }

    public void setPercentageOwnership(Double percentageOwnership) {
        this.percentageOwnership = percentageOwnership;
    }

    public Long getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(Long ownershipType) {
        this.ownershipType = ownershipType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LandLordDTO landLordDTO = (LandLordDTO) o;
        if (landLordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), landLordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LandLordDTO{" +
            "id=" + getId() +
            ", contactId=" + getContactId() +
            ", percentageOwnership=" + getPercentageOwnership() +
            ", ownershipType=" + getOwnershipType() +
            "}";
    }
}
