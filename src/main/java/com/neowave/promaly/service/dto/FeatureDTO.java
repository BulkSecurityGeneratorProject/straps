package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Feature entity.
 */
public class FeatureDTO implements Serializable {

    private Long id;

    private String description;

    private Long typeId;

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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long lookupId) {
        this.typeId = lookupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeatureDTO featureDTO = (FeatureDTO) o;
        if (featureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), featureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeatureDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", type=" + getTypeId() +
            "}";
    }
}
