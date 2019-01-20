package com.neowave.promaly.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Mortgage entity.
 */
public class MortgageDTO implements Serializable {

    private Long id;

    private Long companyId;

    private Long propertyUnitsId;

    private Long borrower;

    private Long lender;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double amount;

    private Double interestRate;

    private Double balloonPayment;

    private Boolean refinanceOption;

    private Integer amortizationLengthYrs;

    private String version;

    private Long propertyId;

    private Long buildingId;

    private Long propertyUnitId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPropertyUnitsId() {
        return propertyUnitsId;
    }

    public void setPropertyUnitsId(Long propertyUnitsId) {
        this.propertyUnitsId = propertyUnitsId;
    }

    public Long getBorrower() {
        return borrower;
    }

    public void setBorrower(Long borrower) {
        this.borrower = borrower;
    }

    public Long getLender() {
        return lender;
    }

    public void setLender(Long lender) {
        this.lender = lender;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getBalloonPayment() {
        return balloonPayment;
    }

    public void setBalloonPayment(Double balloonPayment) {
        this.balloonPayment = balloonPayment;
    }

    public Boolean isRefinanceOption() {
        return refinanceOption;
    }

    public void setRefinanceOption(Boolean refinanceOption) {
        this.refinanceOption = refinanceOption;
    }

    public Integer getAmortizationLengthYrs() {
        return amortizationLengthYrs;
    }

    public void setAmortizationLengthYrs(Integer amortizationLengthYrs) {
        this.amortizationLengthYrs = amortizationLengthYrs;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

        MortgageDTO mortgageDTO = (MortgageDTO) o;
        if (mortgageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mortgageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MortgageDTO{" +
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
            ", property=" + getPropertyId() +
            ", building=" + getBuildingId() +
            ", propertyUnit=" + getPropertyUnitId() +
            "}";
    }
}
