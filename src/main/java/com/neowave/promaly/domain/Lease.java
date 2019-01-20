package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lease.
 */
@Entity
@Table(name = "lease")
@Document(indexName = "lease")
public class Lease implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lease_signed_date")
    private LocalDate leaseSignedDate;

    @Column(name = "landlords_id")
    private Long landlordsId;

    @Column(name = "landlord_agent")
    private Long landlordAgent;

    @Column(name = "tenants_id")
    private Long tenantsId;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "num_occupants")
    private Long numOccupants;

    @Column(name = "lease_term")
    private Long leaseTerm;

    @Column(name = "lease_start_date")
    private LocalDate leaseStartDate;

    @Column(name = "lease_end_date")
    private LocalDate leaseEndDate;

    @Column(name = "rent_amount")
    private Double rentAmount;

    @Column(name = "rent_period")
    private Long rentPeriod;

    @Column(name = "total_term_rent")
    private Double totalTermRent;

    @Column(name = "rent_escalation_perc")
    private Double rentEscalationPerc;

    @Column(name = "pro_rata_start_date")
    private Double proRataStartDate;

    @Column(name = "pro_rata_rent_amount")
    private Double proRataRentAmount;

    @Column(name = "pro_rata_end_date")
    private LocalDate proRataEndDate;

    @Column(name = "additional_charges")
    private Double additionalCharges;

    @Column(name = "security_deposit")
    private Double securityDeposit;

    @Column(name = "pets_allowed")
    private Boolean petsAllowed;

    @Column(name = "pet_type")
    private Long petType;

    @Column(name = "pet_description")
    private String petDescription;

    @Column(name = "water")
    private Boolean water;

    @Column(name = "gas")
    private Boolean gas;

    @Column(name = "electric")
    private Boolean electric;

    @Column(name = "other_utilities")
    private String otherUtilities;

    @Column(name = "termination_notice_period")
    private Long terminationNoticePeriod;

    @Column(name = "agency_company")
    private Long agencyCompany;

    @Column(name = "management_company")
    private Long managementCompany;

    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup type;

    @ManyToMany(mappedBy = "leases")
    @JsonIgnore
    private Set<PropertyUnit> propertyUnits = new HashSet<>();

    @ManyToMany(mappedBy = "leases")
    @JsonIgnore
    private Set<Building> buildings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLeaseSignedDate() {
        return leaseSignedDate;
    }

    public Lease leaseSignedDate(LocalDate leaseSignedDate) {
        this.leaseSignedDate = leaseSignedDate;
        return this;
    }

    public void setLeaseSignedDate(LocalDate leaseSignedDate) {
        this.leaseSignedDate = leaseSignedDate;
    }

    public Long getLandlordsId() {
        return landlordsId;
    }

    public Lease landlordsId(Long landlordsId) {
        this.landlordsId = landlordsId;
        return this;
    }

    public void setLandlordsId(Long landlordsId) {
        this.landlordsId = landlordsId;
    }

    public Long getLandlordAgent() {
        return landlordAgent;
    }

    public Lease landlordAgent(Long landlordAgent) {
        this.landlordAgent = landlordAgent;
        return this;
    }

    public void setLandlordAgent(Long landlordAgent) {
        this.landlordAgent = landlordAgent;
    }

    public Long getTenantsId() {
        return tenantsId;
    }

    public Lease tenantsId(Long tenantsId) {
        this.tenantsId = tenantsId;
        return this;
    }

    public void setTenantsId(Long tenantsId) {
        this.tenantsId = tenantsId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public Lease addressId(Long addressId) {
        this.addressId = addressId;
        return this;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getNumOccupants() {
        return numOccupants;
    }

    public Lease numOccupants(Long numOccupants) {
        this.numOccupants = numOccupants;
        return this;
    }

    public void setNumOccupants(Long numOccupants) {
        this.numOccupants = numOccupants;
    }

    public Long getLeaseTerm() {
        return leaseTerm;
    }

    public Lease leaseTerm(Long leaseTerm) {
        this.leaseTerm = leaseTerm;
        return this;
    }

    public void setLeaseTerm(Long leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }

    public Lease leaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
        return this;
    }

    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public LocalDate getLeaseEndDate() {
        return leaseEndDate;
    }

    public Lease leaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
        return this;
    }

    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public Double getRentAmount() {
        return rentAmount;
    }

    public Lease rentAmount(Double rentAmount) {
        this.rentAmount = rentAmount;
        return this;
    }

    public void setRentAmount(Double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Long getRentPeriod() {
        return rentPeriod;
    }

    public Lease rentPeriod(Long rentPeriod) {
        this.rentPeriod = rentPeriod;
        return this;
    }

    public void setRentPeriod(Long rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public Double getTotalTermRent() {
        return totalTermRent;
    }

    public Lease totalTermRent(Double totalTermRent) {
        this.totalTermRent = totalTermRent;
        return this;
    }

    public void setTotalTermRent(Double totalTermRent) {
        this.totalTermRent = totalTermRent;
    }

    public Double getRentEscalationPerc() {
        return rentEscalationPerc;
    }

    public Lease rentEscalationPerc(Double rentEscalationPerc) {
        this.rentEscalationPerc = rentEscalationPerc;
        return this;
    }

    public void setRentEscalationPerc(Double rentEscalationPerc) {
        this.rentEscalationPerc = rentEscalationPerc;
    }

    public Double getProRataStartDate() {
        return proRataStartDate;
    }

    public Lease proRataStartDate(Double proRataStartDate) {
        this.proRataStartDate = proRataStartDate;
        return this;
    }

    public void setProRataStartDate(Double proRataStartDate) {
        this.proRataStartDate = proRataStartDate;
    }

    public Double getProRataRentAmount() {
        return proRataRentAmount;
    }

    public Lease proRataRentAmount(Double proRataRentAmount) {
        this.proRataRentAmount = proRataRentAmount;
        return this;
    }

    public void setProRataRentAmount(Double proRataRentAmount) {
        this.proRataRentAmount = proRataRentAmount;
    }

    public LocalDate getProRataEndDate() {
        return proRataEndDate;
    }

    public Lease proRataEndDate(LocalDate proRataEndDate) {
        this.proRataEndDate = proRataEndDate;
        return this;
    }

    public void setProRataEndDate(LocalDate proRataEndDate) {
        this.proRataEndDate = proRataEndDate;
    }

    public Double getAdditionalCharges() {
        return additionalCharges;
    }

    public Lease additionalCharges(Double additionalCharges) {
        this.additionalCharges = additionalCharges;
        return this;
    }

    public void setAdditionalCharges(Double additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public Double getSecurityDeposit() {
        return securityDeposit;
    }

    public Lease securityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
        return this;
    }

    public void setSecurityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public Boolean isPetsAllowed() {
        return petsAllowed;
    }

    public Lease petsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
        return this;
    }

    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public Long getPetType() {
        return petType;
    }

    public Lease petType(Long petType) {
        this.petType = petType;
        return this;
    }

    public void setPetType(Long petType) {
        this.petType = petType;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public Lease petDescription(String petDescription) {
        this.petDescription = petDescription;
        return this;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public Boolean isWater() {
        return water;
    }

    public Lease water(Boolean water) {
        this.water = water;
        return this;
    }

    public void setWater(Boolean water) {
        this.water = water;
    }

    public Boolean isGas() {
        return gas;
    }

    public Lease gas(Boolean gas) {
        this.gas = gas;
        return this;
    }

    public void setGas(Boolean gas) {
        this.gas = gas;
    }

    public Boolean isElectric() {
        return electric;
    }

    public Lease electric(Boolean electric) {
        this.electric = electric;
        return this;
    }

    public void setElectric(Boolean electric) {
        this.electric = electric;
    }

    public String getOtherUtilities() {
        return otherUtilities;
    }

    public Lease otherUtilities(String otherUtilities) {
        this.otherUtilities = otherUtilities;
        return this;
    }

    public void setOtherUtilities(String otherUtilities) {
        this.otherUtilities = otherUtilities;
    }

    public Long getTerminationNoticePeriod() {
        return terminationNoticePeriod;
    }

    public Lease terminationNoticePeriod(Long terminationNoticePeriod) {
        this.terminationNoticePeriod = terminationNoticePeriod;
        return this;
    }

    public void setTerminationNoticePeriod(Long terminationNoticePeriod) {
        this.terminationNoticePeriod = terminationNoticePeriod;
    }

    public Long getAgencyCompany() {
        return agencyCompany;
    }

    public Lease agencyCompany(Long agencyCompany) {
        this.agencyCompany = agencyCompany;
        return this;
    }

    public void setAgencyCompany(Long agencyCompany) {
        this.agencyCompany = agencyCompany;
    }

    public Long getManagementCompany() {
        return managementCompany;
    }

    public Lease managementCompany(Long managementCompany) {
        this.managementCompany = managementCompany;
        return this;
    }

    public void setManagementCompany(Long managementCompany) {
        this.managementCompany = managementCompany;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public Lease propertyId(Long propertyId) {
        this.propertyId = propertyId;
        return this;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public Lease additionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
        return this;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public Lookup getType() {
        return type;
    }

    public Lease type(Lookup lookup) {
        this.type = lookup;
        return this;
    }

    public void setType(Lookup lookup) {
        this.type = lookup;
    }

    public Set<PropertyUnit> getPropertyUnits() {
        return propertyUnits;
    }

    public Lease propertyUnits(Set<PropertyUnit> propertyUnits) {
        this.propertyUnits = propertyUnits;
        return this;
    }

    public Lease addPropertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnits.add(propertyUnit);
        propertyUnit.getLeases().add(this);
        return this;
    }

    public Lease removePropertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnits.remove(propertyUnit);
        propertyUnit.getLeases().remove(this);
        return this;
    }

    public void setPropertyUnits(Set<PropertyUnit> propertyUnits) {
        this.propertyUnits = propertyUnits;
    }

    public Set<Building> getBuildings() {
        return buildings;
    }

    public Lease buildings(Set<Building> buildings) {
        this.buildings = buildings;
        return this;
    }

    public Lease addBuilding(Building building) {
        this.buildings.add(building);
        building.getLeases().add(this);
        return this;
    }

    public Lease removeBuilding(Building building) {
        this.buildings.remove(building);
        building.getLeases().remove(this);
        return this;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
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
        Lease lease = (Lease) o;
        if (lease.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lease.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lease{" +
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
            "}";
    }
}
