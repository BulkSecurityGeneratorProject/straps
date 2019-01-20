package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Expense entity.
 */
public class ExpenseDTO implements Serializable {

    private Long id;

    private Double amount;

    private Long currency;

    private Long propertyId;

    private Long buildingId;

    private Long propertyUnitId;

    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
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

        ExpenseDTO expenseDTO = (ExpenseDTO) o;
        if (expenseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expenseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExpenseDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", currency=" + getCurrency() +
            ", property=" + getPropertyId() +
            ", building=" + getBuildingId() +
            ", propertyUnit=" + getPropertyUnitId() +
            ", type=" + getTypeId() +
            "}";
    }
}
