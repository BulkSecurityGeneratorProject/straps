package com.neowave.promaly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DiscountPlan.
 */
@Entity
@Table(name = "discount_plan")
@Document(indexName = "discountplan")
public class DiscountPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "applied_to_plan")
    private Long appliedToPlan;

    @Column(name = "applied_with_plan")
    private Long appliedWithPlan;

    @Column(name = "discount_rate")
    private Double discountRate;

    @Column(name = "discount_unit")
    private Long discountUnit;

    @Column(name = "max_discount_amt")
    private Double maxDiscountAmt;

    @Column(name = "description")
    private String description;

    @Column(name = "conditional")
    private Boolean conditional;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public DiscountPlan planId(Long planId) {
        this.planId = planId;
        return this;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public DiscountPlan planName(String planName) {
        this.planName = planName;
        return this;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Long getAppliedToPlan() {
        return appliedToPlan;
    }

    public DiscountPlan appliedToPlan(Long appliedToPlan) {
        this.appliedToPlan = appliedToPlan;
        return this;
    }

    public void setAppliedToPlan(Long appliedToPlan) {
        this.appliedToPlan = appliedToPlan;
    }

    public Long getAppliedWithPlan() {
        return appliedWithPlan;
    }

    public DiscountPlan appliedWithPlan(Long appliedWithPlan) {
        this.appliedWithPlan = appliedWithPlan;
        return this;
    }

    public void setAppliedWithPlan(Long appliedWithPlan) {
        this.appliedWithPlan = appliedWithPlan;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public DiscountPlan discountRate(Double discountRate) {
        this.discountRate = discountRate;
        return this;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Long getDiscountUnit() {
        return discountUnit;
    }

    public DiscountPlan discountUnit(Long discountUnit) {
        this.discountUnit = discountUnit;
        return this;
    }

    public void setDiscountUnit(Long discountUnit) {
        this.discountUnit = discountUnit;
    }

    public Double getMaxDiscountAmt() {
        return maxDiscountAmt;
    }

    public DiscountPlan maxDiscountAmt(Double maxDiscountAmt) {
        this.maxDiscountAmt = maxDiscountAmt;
        return this;
    }

    public void setMaxDiscountAmt(Double maxDiscountAmt) {
        this.maxDiscountAmt = maxDiscountAmt;
    }

    public String getDescription() {
        return description;
    }

    public DiscountPlan description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isConditional() {
        return conditional;
    }

    public DiscountPlan conditional(Boolean conditional) {
        this.conditional = conditional;
        return this;
    }

    public void setConditional(Boolean conditional) {
        this.conditional = conditional;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscountPlan discountPlan = (DiscountPlan) o;
        if (discountPlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discountPlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiscountPlan{" +
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
