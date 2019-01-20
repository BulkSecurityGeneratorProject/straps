package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RentRoll.
 */
@Entity
@Table(name = "rent_roll")
@Document(indexName = "rentroll")
public class RentRoll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "security_deposit")
    private Double securityDeposit;

    @Column(name = "bank_guarantee")
    private Double bankGuarantee;

    @Column(name = "lease_recovery_timing")
    private Double leaseRecoveryTiming;

    @ManyToOne
    @JsonIgnoreProperties("rentRolls")
    private Property property;

    @ManyToOne
    @JsonIgnoreProperties("rentRolls")
    private Building building;

    @ManyToOne
    @JsonIgnoreProperties("rentRolls")
    private PropertyUnit propertyUnit;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup inflationType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSecurityDeposit() {
        return securityDeposit;
    }

    public RentRoll securityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
        return this;
    }

    public void setSecurityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public Double getBankGuarantee() {
        return bankGuarantee;
    }

    public RentRoll bankGuarantee(Double bankGuarantee) {
        this.bankGuarantee = bankGuarantee;
        return this;
    }

    public void setBankGuarantee(Double bankGuarantee) {
        this.bankGuarantee = bankGuarantee;
    }

    public Double getLeaseRecoveryTiming() {
        return leaseRecoveryTiming;
    }

    public RentRoll leaseRecoveryTiming(Double leaseRecoveryTiming) {
        this.leaseRecoveryTiming = leaseRecoveryTiming;
        return this;
    }

    public void setLeaseRecoveryTiming(Double leaseRecoveryTiming) {
        this.leaseRecoveryTiming = leaseRecoveryTiming;
    }

    public Property getProperty() {
        return property;
    }

    public RentRoll property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Building getBuilding() {
        return building;
    }

    public RentRoll building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public PropertyUnit getPropertyUnit() {
        return propertyUnit;
    }

    public RentRoll propertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnit = propertyUnit;
        return this;
    }

    public void setPropertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnit = propertyUnit;
    }

    public Lookup getInflationType() {
        return inflationType;
    }

    public RentRoll inflationType(Lookup lookup) {
        this.inflationType = lookup;
        return this;
    }

    public void setInflationType(Lookup lookup) {
        this.inflationType = lookup;
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
        RentRoll rentRoll = (RentRoll) o;
        if (rentRoll.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rentRoll.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RentRoll{" +
            "id=" + getId() +
            ", securityDeposit=" + getSecurityDeposit() +
            ", bankGuarantee=" + getBankGuarantee() +
            ", leaseRecoveryTiming=" + getLeaseRecoveryTiming() +
            "}";
    }
}
