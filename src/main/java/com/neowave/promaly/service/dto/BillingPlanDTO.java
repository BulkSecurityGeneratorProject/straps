package com.neowave.promaly.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BillingPlan entity.
 */
public class BillingPlanDTO implements Serializable {

    private Long id;

    private String planName;

    private Long category;

    private Long memberType;

    private String description;

    private String prorationDesc;

    private LocalDate effectiveDate;

    private Long effectiveStatus;

    private String association;

    private Long orderType;

    private Long accountingBook;

    private Double rates;

    private Long currency;

    private Long basis;

    private Double initiationFees;

    private String coupons;

    private Boolean prorated;

    private String glcode;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getMemberType() {
        return memberType;
    }

    public void setMemberType(Long memberType) {
        this.memberType = memberType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProrationDesc() {
        return prorationDesc;
    }

    public void setProrationDesc(String prorationDesc) {
        this.prorationDesc = prorationDesc;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Long getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(Long effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public Long getOrderType() {
        return orderType;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }

    public Long getAccountingBook() {
        return accountingBook;
    }

    public void setAccountingBook(Long accountingBook) {
        this.accountingBook = accountingBook;
    }

    public Double getRates() {
        return rates;
    }

    public void setRates(Double rates) {
        this.rates = rates;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getBasis() {
        return basis;
    }

    public void setBasis(Long basis) {
        this.basis = basis;
    }

    public Double getInitiationFees() {
        return initiationFees;
    }

    public void setInitiationFees(Double initiationFees) {
        this.initiationFees = initiationFees;
    }

    public String getCoupons() {
        return coupons;
    }

    public void setCoupons(String coupons) {
        this.coupons = coupons;
    }

    public Boolean isProrated() {
        return prorated;
    }

    public void setProrated(Boolean prorated) {
        this.prorated = prorated;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BillingPlanDTO billingPlanDTO = (BillingPlanDTO) o;
        if (billingPlanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingPlanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingPlanDTO{" +
            "id=" + getId() +
            ", planName='" + getPlanName() + "'" +
            ", category=" + getCategory() +
            ", memberType=" + getMemberType() +
            ", description='" + getDescription() + "'" +
            ", prorationDesc='" + getProrationDesc() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", effectiveStatus=" + getEffectiveStatus() +
            ", association='" + getAssociation() + "'" +
            ", orderType=" + getOrderType() +
            ", accountingBook=" + getAccountingBook() +
            ", rates=" + getRates() +
            ", currency=" + getCurrency() +
            ", basis=" + getBasis() +
            ", initiationFees=" + getInitiationFees() +
            ", coupons='" + getCoupons() + "'" +
            ", prorated='" + isProrated() + "'" +
            ", glcode='" + getGlcode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
