package com.neowave.promaly.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Lease entity.
 */
public class LeaseDTO implements Serializable {

    private Long id;

    private LocalDate leaseSignedDate;

    private Long landlordsId;

    private Long landlordAgent;

    private Long tenantsId;

    private Long addressId;

    private Long numOccupants;

    private Long leaseTerm;

    private LocalDate leaseStartDate;

    private LocalDate leaseEndDate;

    private Double rentAmount;

    private Long rentPeriod;

    private Double totalTermRent;

    private Double rentEscalationPerc;

    private Double proRataStartDate;

    private Double proRataRentAmount;

    private LocalDate proRataEndDate;

    private Double additionalCharges;

    private Double securityDeposit;

    private Boolean petsAllowed;

    private Long petType;

    private String petDescription;

    private Boolean water;

    private Boolean gas;

    private Boolean electric;

    private String otherUtilities;

    private Long terminationNoticePeriod;

    private Long agencyCompany;

    private Long managementCompany;

    private Long propertyId;

    private String additionalNotes;

    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLeaseSignedDate() {
        return leaseSignedDate;
    }

    public void setLeaseSignedDate(LocalDate leaseSignedDate) {
        this.leaseSignedDate = leaseSignedDate;
    }

    public Long getLandlordsId() {
        return landlordsId;
    }

    public void setLandlordsId(Long landlordsId) {
        this.landlordsId = landlordsId;
    }

    public Long getLandlordAgent() {
        return landlordAgent;
    }

    public void setLandlordAgent(Long landlordAgent) {
        this.landlordAgent = landlordAgent;
    }

    public Long getTenantsId() {
        return tenantsId;
    }

    public void setTenantsId(Long tenantsId) {
        this.tenantsId = tenantsId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getNumOccupants() {
        return numOccupants;
    }

    public void setNumOccupants(Long numOccupants) {
        this.numOccupants = numOccupants;
    }

    public Long getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(Long leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public LocalDate getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public Double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(Double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Long getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(Long rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public Double getTotalTermRent() {
        return totalTermRent;
    }

    public void setTotalTermRent(Double totalTermRent) {
        this.totalTermRent = totalTermRent;
    }

    public Double getRentEscalationPerc() {
        return rentEscalationPerc;
    }

    public void setRentEscalationPerc(Double rentEscalationPerc) {
        this.rentEscalationPerc = rentEscalationPerc;
    }

    public Double getProRataStartDate() {
        return proRataStartDate;
    }

    public void setProRataStartDate(Double proRataStartDate) {
        this.proRataStartDate = proRataStartDate;
    }

    public Double getProRataRentAmount() {
        return proRataRentAmount;
    }

    public void setProRataRentAmount(Double proRataRentAmount) {
        this.proRataRentAmount = proRataRentAmount;
    }

    public LocalDate getProRataEndDate() {
        return proRataEndDate;
    }

    public void setProRataEndDate(LocalDate proRataEndDate) {
        this.proRataEndDate = proRataEndDate;
    }

    public Double getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(Double additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public Double getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public Boolean isPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public Long getPetType() {
        return petType;
    }

    public void setPetType(Long petType) {
        this.petType = petType;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public Boolean isWater() {
        return water;
    }

    public void setWater(Boolean water) {
        this.water = water;
    }

    public Boolean isGas() {
        return gas;
    }

    public void setGas(Boolean gas) {
        this.gas = gas;
    }

    public Boolean isElectric() {
        return electric;
    }

    public void setElectric(Boolean electric) {
        this.electric = electric;
    }

    public String getOtherUtilities() {
        return otherUtilities;
    }

    public void setOtherUtilities(String otherUtilities) {
        this.otherUtilities = otherUtilities;
    }

    public Long getTerminationNoticePeriod() {
        return terminationNoticePeriod;
    }

    public void setTerminationNoticePeriod(Long terminationNoticePeriod) {
        this.terminationNoticePeriod = terminationNoticePeriod;
    }

    public Long getAgencyCompany() {
        return agencyCompany;
    }

    public void setAgencyCompany(Long agencyCompany) {
        this.agencyCompany = agencyCompany;
    }

    public Long getManagementCompany() {
        return managementCompany;
    }

    public void setManagementCompany(Long managementCompany) {
        this.managementCompany = managementCompany;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
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

        LeaseDTO leaseDTO = (LeaseDTO) o;
        if (leaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaseDTO{" +
            "id=" + getId() +
            ", leaseSignedDate='" + getLeaseSignedDate() + "'" +
            ", landlordsId=" + getLandlordsId() +
            ", landlordAgent=" + getLandlordAgent() +
            ", tenantsId=" + getTenantsId() +
            ", addressId=" + getAddressId() +
            ", numOccupants=" + getNumOccupants() +
            ", leaseTerm=" + getLeaseTerm() +
            ", leaseStartDate='" + getLeaseStartDate() + "'" +
            ", leaseEndDate='" + getLeaseEndDate() + "'" +
            ", rentAmount=" + getRentAmount() +
            ", rentPeriod=" + getRentPeriod() +
            ", totalTermRent=" + getTotalTermRent() +
            ", rentEscalationPerc=" + getRentEscalationPerc() +
            ", proRataStartDate=" + getProRataStartDate() +
            ", proRataRentAmount=" + getProRataRentAmount() +
            ", proRataEndDate='" + getProRataEndDate() + "'" +
            ", additionalCharges=" + getAdditionalCharges() +
            ", securityDeposit=" + getSecurityDeposit() +
            ", petsAllowed='" + isPetsAllowed() + "'" +
            ", petType=" + getPetType() +
            ", petDescription='" + getPetDescription() + "'" +
            ", water='" + isWater() + "'" +
            ", gas='" + isGas() + "'" +
            ", electric='" + isElectric() + "'" +
            ", otherUtilities='" + getOtherUtilities() + "'" +
            ", terminationNoticePeriod=" + getTerminationNoticePeriod() +
            ", agencyCompany=" + getAgencyCompany() +
            ", managementCompany=" + getManagementCompany() +
            ", propertyId=" + getPropertyId() +
            ", additionalNotes='" + getAdditionalNotes() + "'" +
            ", type=" + getTypeId() +
            "}";
    }
}
