package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the IncomeProjection entity.
 */
public class IncomeProjectionDTO implements Serializable {

    private Long id;

    private Integer period;

    private Double projectedValue;

    private Long portfolioId;

    private Long propertyId;

    private Long buildingId;

    private Long propertyUnitId;

    private Long assetLevelTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getProjectedValue() {
        return projectedValue;
    }

    public void setProjectedValue(Double projectedValue) {
        this.projectedValue = projectedValue;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
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

    public Long getAssetLevelTypeId() {
        return assetLevelTypeId;
    }

    public void setAssetLevelTypeId(Long lookupId) {
        this.assetLevelTypeId = lookupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncomeProjectionDTO incomeProjectionDTO = (IncomeProjectionDTO) o;
        if (incomeProjectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incomeProjectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncomeProjectionDTO{" +
            "id=" + getId() +
            ", period=" + getPeriod() +
            ", projectedValue=" + getProjectedValue() +
            ", portfolio=" + getPortfolioId() +
            ", property=" + getPropertyId() +
            ", building=" + getBuildingId() +
            ", propertyUnit=" + getPropertyUnitId() +
            ", assetLevelType=" + getAssetLevelTypeId() +
            "}";
    }
}
