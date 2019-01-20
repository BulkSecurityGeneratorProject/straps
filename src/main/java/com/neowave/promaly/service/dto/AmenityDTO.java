package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Amenity entity.
 */
public class AmenityDTO implements Serializable {

    private Long id;

    private String description;

    private Long propertyId;

    private Long buildingId;

    private Long propertyUnitId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmenityDTO amenityDTO = (AmenityDTO) o;
        if (amenityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amenityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmenityDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", property=" + getPropertyId() +
            ", building=" + getBuildingId() +
            ", propertyUnit=" + getPropertyUnitId() +
            "}";
    }
}
