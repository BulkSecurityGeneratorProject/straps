package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DiscountPlan entity.
 */
public class DiscountPlanDTO implements Serializable {

    private Long id;

    private Long planId;

    private String planName;

    private Long appliedToPlan;

    private Long appliedWithPlan;

    private Double discountRate;

    private Long discountUnit;

    private Double maxDiscountAmt;

    private String description;

    private Boolean conditional;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Long getAppliedToPlan() {
        return appliedToPlan;
    }

    public void setAppliedToPlan(Long appliedToPlan) {
        this.appliedToPlan = appliedToPlan;
    }

    public Long getAppliedWithPlan() {
        return appliedWithPlan;
    }

    public void setAppliedWithPlan(Long appliedWithPlan) {
        this.appliedWithPlan = appliedWithPlan;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Long getDiscountUnit() {
        return discountUnit;
    }

    public void setDiscountUnit(Long discountUnit) {
        this.discountUnit = discountUnit;
    }

    public Double getMaxDiscountAmt() {
        return maxDiscountAmt;
    }

    public void setMaxDiscountAmt(Double maxDiscountAmt) {
        this.maxDiscountAmt = maxDiscountAmt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isConditional() {
        return conditional;
    }

    public void setConditional(Boolean conditional) {
        this.conditional = conditional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiscountPlanDTO discountPlanDTO = (DiscountPlanDTO) o;
        if (discountPlanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discountPlanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiscountPlanDTO{" +
            "id=" + getId() +
            ", planId=" + getPlanId() +
            ", planName='" + getPlanName() + "'" +
            ", appliedToPlan=" + getAppliedToPlan() +
            ", appliedWithPlan=" + getAppliedWithPlan() +
            ", discountRate=" + getDiscountRate() +
            ", discountUnit=" + getDiscountUnit() +
            ", maxDiscountAmt=" + getMaxDiscountAmt() +
            ", description='" + getDescription() + "'" +
            ", conditional='" + isConditional() + "'" +
            "}";
    }
}
