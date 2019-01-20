package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Mortgage.
 */
@Entity
@Table(name = "mortgage")
@Document(indexName = "mortgage")
public class Mortgage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "property_units_id")
    private Long propertyUnitsId;

    @Column(name = "borrower")
    private Long borrower;

    @Column(name = "lender")
    private Long lender;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "balloon_payment")
    private Double balloonPayment;

    @Column(name = "refinance_option")
    private Boolean refinanceOption;

    @Column(name = "amortization_length_yrs")
    private Integer amortizationLengthYrs;

    @Column(name = "version")
    private String version;

    @ManyToOne
    @JsonIgnoreProperties("mortgages")
    private Property property;

    @ManyToOne
    @JsonIgnoreProperties("mortgages")
    private Building building;

    @ManyToOne
    @JsonIgnoreProperties("mortgages")
    private PropertyUnit propertyUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Mortgage companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPropertyUnitsId() {
        return propertyUnitsId;
    }

    public Mortgage propertyUnitsId(Long propertyUnitsId) {
        this.propertyUnitsId = propertyUnitsId;
        return this;
    }

    public void setPropertyUnitsId(Long propertyUnitsId) {
        this.propertyUnitsId = propertyUnitsId;
    }

    public Long getBorrower() {
        return borrower;
    }

    public Mortgage borrower(Long borrower) {
        this.borrower = borrower;
        return this;
    }

    public void setBorrower(Long borrower) {
        this.borrower = borrower;
    }

    public Long getLender() {
        return lender;
    }

    public Mortgage lender(Long lender) {
        this.lender = lender;
        return this;
    }

    public void setLender(Long lender) {
        this.lender = lender;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Mortgage startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Mortgage endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getAmount() {
        return amount;
    }

    public Mortgage amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public Mortgage interestRate(Double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getBalloonPayment() {
        return balloonPayment;
    }

    public Mortgage balloonPayment(Double balloonPayment) {
        this.balloonPayment = balloonPayment;
        return this;
    }

    public void setBalloonPayment(Double balloonPayment) {
        this.balloonPayment = balloonPayment;
    }

    public Boolean isRefinanceOption() {
        return refinanceOption;
    }

    public Mortgage refinanceOption(Boolean refinanceOption) {
        this.refinanceOption = refinanceOption;
        return this;
    }

    public void setRefinanceOption(Boolean refinanceOption) {
        this.refinanceOption = refinanceOption;
    }

    public Integer getAmortizationLengthYrs() {
        return amortizationLengthYrs;
    }

    public Mortgage amortizationLengthYrs(Integer amortizationLengthYrs) {
        this.amortizationLengthYrs = amortizationLengthYrs;
        return this;
    }

    public void setAmortizationLengthYrs(Integer amortizationLengthYrs) {
        this.amortizationLengthYrs = amortizationLengthYrs;
    }

    public String getVersion() {
        return version;
    }

    public Mortgage version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Property getProperty() {
        return property;
    }

    public Mortgage property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Building getBuilding() {
        return building;
    }

    public Mortgage building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public PropertyUnit getPropertyUnit() {
        return propertyUnit;
    }

    public Mortgage propertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnit = propertyUnit;
        return this;
    }

    public void setPropertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnit = propertyUnit;
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
        Mortgage mortgage = (Mortgage) o;
        if (mortgage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mortgage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mortgage{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", propertyUnitsId=" + getPropertyUnitsId() +
            ", borrower=" + getBorrower() +
            ", lender=" + getLender() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", amount=" + getAmount() +
            ", interestRate=" + getInterestRate() +
            ", balloonPayment=" + getBalloonPayment() +
            ", refinanceOption='" + isRefinanceOption() + "'" +
            ", amortizationLengthYrs=" + getAmortizationLengthYrs() +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
