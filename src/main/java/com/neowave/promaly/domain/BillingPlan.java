package com.neowave.promaly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A BillingPlan.
 */
@Entity
@Table(name = "billing_plan")
@Document(indexName = "billingplan")
public class BillingPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "category")
    private Long category;

    @Column(name = "member_type")
    private Long memberType;

    @Column(name = "description")
    private String description;

    @Column(name = "proration_desc")
    private String prorationDesc;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "effective_status")
    private Long effectiveStatus;

    @Column(name = "association")
    private String association;

    @Column(name = "order_type")
    private Long orderType;

    @Column(name = "accounting_book")
    private Long accountingBook;

    @Column(name = "rates")
    private Double rates;

    @Column(name = "currency")
    private Long currency;

    @Column(name = "basis")
    private Long basis;

    @Column(name = "initiation_fees")
    private Double initiationFees;

    @Column(name = "coupons")
    private String coupons;

    @Column(name = "prorated")
    private Boolean prorated;

    @Column(name = "glcode")
    private String glcode;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public BillingPlan planName(String planName) {
        this.planName = planName;
        return this;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Long getCategory() {
        return category;
    }

    public BillingPlan category(Long category) {
        this.category = category;
        return this;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getMemberType() {
        return memberType;
    }

    public BillingPlan memberType(Long memberType) {
        this.memberType = memberType;
        return this;
    }

    public void setMemberType(Long memberType) {
        this.memberType = memberType;
    }

    public String getDescription() {
        return description;
    }

    public BillingPlan description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProrationDesc() {
        return prorationDesc;
    }

    public BillingPlan prorationDesc(String prorationDesc) {
        this.prorationDesc = prorationDesc;
        return this;
    }

    public void setProrationDesc(String prorationDesc) {
        this.prorationDesc = prorationDesc;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public BillingPlan effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Long getEffectiveStatus() {
        return effectiveStatus;
    }

    public BillingPlan effectiveStatus(Long effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
        return this;
    }

    public void setEffectiveStatus(Long effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
    }

    public String getAssociation() {
        return association;
    }

    public BillingPlan association(String association) {
        this.association = association;
        return this;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public Long getOrderType() {
        return orderType;
    }

    public BillingPlan orderType(Long orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }

    public Long getAccountingBook() {
        return accountingBook;
    }

    public BillingPlan accountingBook(Long accountingBook) {
        this.accountingBook = accountingBook;
        return this;
    }

    public void setAccountingBook(Long accountingBook) {
        this.accountingBook = accountingBook;
    }

    public Double getRates() {
        return rates;
    }

    public BillingPlan rates(Double rates) {
        this.rates = rates;
        return this;
    }

    public void setRates(Double rates) {
        this.rates = rates;
    }

    public Long getCurrency() {
        return currency;
    }

    public BillingPlan currency(Long currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getBasis() {
        return basis;
    }

    public BillingPlan basis(Long basis) {
        this.basis = basis;
        return this;
    }

    public void setBasis(Long basis) {
        this.basis = basis;
    }

    public Double getInitiationFees() {
        return initiationFees;
    }

    public BillingPlan initiationFees(Double initiationFees) {
        this.initiationFees = initiationFees;
        return this;
    }

    public void setInitiationFees(Double initiationFees) {
        this.initiationFees = initiationFees;
    }

    public String getCoupons() {
        return coupons;
    }

    public BillingPlan coupons(String coupons) {
        this.coupons = coupons;
        return this;
    }

    public void setCoupons(String coupons) {
        this.coupons = coupons;
    }

    public Boolean isProrated() {
        return prorated;
    }

    public BillingPlan prorated(Boolean prorated) {
        this.prorated = prorated;
        return this;
    }

    public void setProrated(Boolean prorated) {
        this.prorated = prorated;
    }

    public String getGlcode() {
        return glcode;
    }

    public BillingPlan glcode(String glcode) {
        this.glcode = glcode;
        return this;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public Boolean isActive() {
        return active;
    }

    public BillingPlan active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        BillingPlan billingPlan = (BillingPlan) o;
        if (billingPlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingPlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingPlan{" +
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
