package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Relationships:
 * propertyUnit <-> landlord (many to many)
 * propertyUnit <-> address (one to one)
 * propertyUnit <-> property unit (many to many)
 * propertyUnit <-> usage_type (one to one)
 * propertyUnit <-> status (one to one)
 * propertyUnit <-> tenants (one to many)
 * propertyUnit <-> amenities (one to many)
 * propertyUnit <-> lease (one to one)
 * propertyUnit <-> mortgage (one to many)
 */
@ApiModel(description = "Relationships: propertyUnit <-> landlord (many to many) propertyUnit <-> address (one to one) propertyUnit <-> property unit (many to many) propertyUnit <-> usage_type (one to one) propertyUnit <-> status (one to one) propertyUnit <-> tenants (one to many) propertyUnit <-> amenities (one to many) propertyUnit <-> lease (one to one) propertyUnit <-> mortgage (one to many)")
@Entity
@Table(name = "property_unit")
@Document(indexName = "propertyunit")
public class PropertyUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_no")
    private String unitNo;

    @Column(name = "description")
    private String description;

    @Column(name = "floors")
    private Long floors;

    @Column(name = "net_area")
    private Double netArea;

    @Column(name = "gross_area")
    private Double grossArea;

    @Column(name = "residential")
    private Boolean residential;

    @Column(name = "total_rooms")
    private Integer totalRooms;

    @Column(name = "no_of_brs")
    private Integer noOfBrs;

    @Column(name = "no_of_fb")
    private Integer noOfFb;

    @Column(name = "no_of_hb")
    private Integer noOfHb;

    @Column(name = "property_mailbox_num")
    private String propertyMailboxNum;

    @Column(name = "property_parking_lot_num")
    private String propertyParkingLotNum;

    @Column(name = "gas_heating")
    private Boolean gasHeating;

    @Column(name = "who_pays_heating")
    private Long whoPaysHeating;

    @Column(name = "electric")
    private Boolean electric;

    @Column(name = "who_pays_electric")
    private Long whoPaysElectric;

    @Column(name = "water")
    private Boolean water;

    @Column(name = "who_pays_water")
    private Long whoPaysWater;

    @Column(name = "last_renovated")
    private LocalDate lastRenovated;

    @Column(name = "current_rent_per_sqft")
    private Double currentRentPerSqft;

    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JsonIgnoreProperties("propertyUnits")
    private Building building;

    @OneToOne    @JoinColumn(unique = true)
    private Address address;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup usageType;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup status;

    @OneToOne    @JoinColumn(unique = true)
    private Mortgage mortgage;

    @OneToOne    @JoinColumn(unique = true)
    private RentRoll rentRoll;

    @OneToMany(mappedBy = "propertyUnit")
    private Set<IncomeProjection> projectedIncomes = new HashSet<>();
    @OneToMany(mappedBy = "propertyUnit")
    private Set<Tenant> tenants = new HashSet<>();
    @OneToMany(mappedBy = "propertyUnit")
    private Set<Amenity> amenities = new HashSet<>();
    @OneToMany(mappedBy = "propertyUnit")
    private Set<Expense> expenses = new HashSet<>();
    @OneToMany(mappedBy = "propertyUnit")
    private Set<Mortgage> mortgages = new HashSet<>();
    @OneToMany(mappedBy = "propertyUnit")
    private Set<Appliance> appliances = new HashSet<>();
    @OneToMany(mappedBy = "propertyUnit")
    private Set<RentRoll> rentRolls = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "property_unit_lease",
               joinColumns = @JoinColumn(name = "property_units_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "leases_id", referencedColumnName = "id"))
    private Set<Lease> leases = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "property_unit_owner",
               joinColumns = @JoinColumn(name = "property_units_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "owners_id", referencedColumnName = "id"))
    private Set<LandLord> owners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public PropertyUnit unitNo(String unitNo) {
        this.unitNo = unitNo;
        return this;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getDescription() {
        return description;
    }

    public PropertyUnit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFloors() {
        return floors;
    }

    public PropertyUnit floors(Long floors) {
        this.floors = floors;
        return this;
    }

    public void setFloors(Long floors) {
        this.floors = floors;
    }

    public Double getNetArea() {
        return netArea;
    }

    public PropertyUnit netArea(Double netArea) {
        this.netArea = netArea;
        return this;
    }

    public void setNetArea(Double netArea) {
        this.netArea = netArea;
    }

    public Double getGrossArea() {
        return grossArea;
    }

    public PropertyUnit grossArea(Double grossArea) {
        this.grossArea = grossArea;
        return this;
    }

    public void setGrossArea(Double grossArea) {
        this.grossArea = grossArea;
    }

    public Boolean isResidential() {
        return residential;
    }

    public PropertyUnit residential(Boolean residential) {
        this.residential = residential;
        return this;
    }

    public void setResidential(Boolean residential) {
        this.residential = residential;
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }

    public PropertyUnit totalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
        return this;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
    }

    public Integer getNoOfBrs() {
        return noOfBrs;
    }

    public PropertyUnit noOfBrs(Integer noOfBrs) {
        this.noOfBrs = noOfBrs;
        return this;
    }

    public void setNoOfBrs(Integer noOfBrs) {
        this.noOfBrs = noOfBrs;
    }

    public Integer getNoOfFb() {
        return noOfFb;
    }

    public PropertyUnit noOfFb(Integer noOfFb) {
        this.noOfFb = noOfFb;
        return this;
    }

    public void setNoOfFb(Integer noOfFb) {
        this.noOfFb = noOfFb;
    }

    public Integer getNoOfHb() {
        return noOfHb;
    }

    public PropertyUnit noOfHb(Integer noOfHb) {
        this.noOfHb = noOfHb;
        return this;
    }

    public void setNoOfHb(Integer noOfHb) {
        this.noOfHb = noOfHb;
    }

    public String getPropertyMailboxNum() {
        return propertyMailboxNum;
    }

    public PropertyUnit propertyMailboxNum(String propertyMailboxNum) {
        this.propertyMailboxNum = propertyMailboxNum;
        return this;
    }

    public void setPropertyMailboxNum(String propertyMailboxNum) {
        this.propertyMailboxNum = propertyMailboxNum;
    }

    public String getPropertyParkingLotNum() {
        return propertyParkingLotNum;
    }

    public PropertyUnit propertyParkingLotNum(String propertyParkingLotNum) {
        this.propertyParkingLotNum = propertyParkingLotNum;
        return this;
    }

    public void setPropertyParkingLotNum(String propertyParkingLotNum) {
        this.propertyParkingLotNum = propertyParkingLotNum;
    }

    public Boolean isGasHeating() {
        return gasHeating;
    }

    public PropertyUnit gasHeating(Boolean gasHeating) {
        this.gasHeating = gasHeating;
        return this;
    }

    public void setGasHeating(Boolean gasHeating) {
        this.gasHeating = gasHeating;
    }

    public Long getWhoPaysHeating() {
        return whoPaysHeating;
    }

    public PropertyUnit whoPaysHeating(Long whoPaysHeating) {
        this.whoPaysHeating = whoPaysHeating;
        return this;
    }

    public void setWhoPaysHeating(Long whoPaysHeating) {
        this.whoPaysHeating = whoPaysHeating;
    }

    public Boolean isElectric() {
        return electric;
    }

    public PropertyUnit electric(Boolean electric) {
        this.electric = electric;
        return this;
    }

    public void setElectric(Boolean electric) {
        this.electric = electric;
    }

    public Long getWhoPaysElectric() {
        return whoPaysElectric;
    }

    public PropertyUnit whoPaysElectric(Long whoPaysElectric) {
        this.whoPaysElectric = whoPaysElectric;
        return this;
    }

    public void setWhoPaysElectric(Long whoPaysElectric) {
        this.whoPaysElectric = whoPaysElectric;
    }

    public Boolean isWater() {
        return water;
    }

    public PropertyUnit water(Boolean water) {
        this.water = water;
        return this;
    }

    public void setWater(Boolean water) {
        this.water = water;
    }

    public Long getWhoPaysWater() {
        return whoPaysWater;
    }

    public PropertyUnit whoPaysWater(Long whoPaysWater) {
        this.whoPaysWater = whoPaysWater;
        return this;
    }

    public void setWhoPaysWater(Long whoPaysWater) {
        this.whoPaysWater = whoPaysWater;
    }

    public LocalDate getLastRenovated() {
        return lastRenovated;
    }

    public PropertyUnit lastRenovated(LocalDate lastRenovated) {
        this.lastRenovated = lastRenovated;
        return this;
    }

    public void setLastRenovated(LocalDate lastRenovated) {
        this.lastRenovated = lastRenovated;
    }

    public Double getCurrentRentPerSqft() {
        return currentRentPerSqft;
    }

    public PropertyUnit currentRentPerSqft(Double currentRentPerSqft) {
        this.currentRentPerSqft = currentRentPerSqft;
        return this;
    }

    public void setCurrentRentPerSqft(Double currentRentPerSqft) {
        this.currentRentPerSqft = currentRentPerSqft;
    }

    public Long getVersion() {
        return version;
    }

    public PropertyUnit version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Building getBuilding() {
        return building;
    }

    public PropertyUnit building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Address getAddress() {
        return address;
    }

    public PropertyUnit address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Lookup getUsageType() {
        return usageType;
    }

    public PropertyUnit usageType(Lookup lookup) {
        this.usageType = lookup;
        return this;
    }

    public void setUsageType(Lookup lookup) {
        this.usageType = lookup;
    }

    public Lookup getStatus() {
        return status;
    }

    public PropertyUnit status(Lookup lookup) {
        this.status = lookup;
        return this;
    }

    public void setStatus(Lookup lookup) {
        this.status = lookup;
    }

    public Mortgage getMortgage() {
        return mortgage;
    }

    public PropertyUnit mortgage(Mortgage mortgage) {
        this.mortgage = mortgage;
        return this;
    }

    public void setMortgage(Mortgage mortgage) {
        this.mortgage = mortgage;
    }

    public RentRoll getRentRoll() {
        return rentRoll;
    }

    public PropertyUnit rentRoll(RentRoll rentRoll) {
        this.rentRoll = rentRoll;
        return this;
    }

    public void setRentRoll(RentRoll rentRoll) {
        this.rentRoll = rentRoll;
    }

    public Set<IncomeProjection> getProjectedIncomes() {
        return projectedIncomes;
    }

    public PropertyUnit projectedIncomes(Set<IncomeProjection> incomeProjections) {
        this.projectedIncomes = incomeProjections;
        return this;
    }

    public PropertyUnit addProjectedIncomes(IncomeProjection incomeProjection) {
        this.projectedIncomes.add(incomeProjection);
        incomeProjection.setPropertyUnit(this);
        return this;
    }

    public PropertyUnit removeProjectedIncomes(IncomeProjection incomeProjection) {
        this.projectedIncomes.remove(incomeProjection);
        incomeProjection.setPropertyUnit(null);
        return this;
    }

    public void setProjectedIncomes(Set<IncomeProjection> incomeProjections) {
        this.projectedIncomes = incomeProjections;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public PropertyUnit tenants(Set<Tenant> tenants) {
        this.tenants = tenants;
        return this;
    }

    public PropertyUnit addTenants(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.setPropertyUnit(this);
        return this;
    }

    public PropertyUnit removeTenants(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.setPropertyUnit(null);
        return this;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public PropertyUnit amenities(Set<Amenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    public PropertyUnit addAmenities(Amenity amenity) {
        this.amenities.add(amenity);
        amenity.setPropertyUnit(this);
        return this;
    }

    public PropertyUnit removeAmenities(Amenity amenity) {
        this.amenities.remove(amenity);
        amenity.setPropertyUnit(null);
        return this;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public PropertyUnit expenses(Set<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }

    public PropertyUnit addExpenses(Expense expense) {
        this.expenses.add(expense);
        expense.setPropertyUnit(this);
        return this;
    }

    public PropertyUnit removeExpenses(Expense expense) {
        this.expenses.remove(expense);
        expense.setPropertyUnit(null);
        return this;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public Set<Mortgage> getMortgages() {
        return mortgages;
    }

    public PropertyUnit mortgages(Set<Mortgage> mortgages) {
        this.mortgages = mortgages;
        return this;
    }

    public PropertyUnit addMortgages(Mortgage mortgage) {
        this.mortgages.add(mortgage);
        mortgage.setPropertyUnit(this);
        return this;
    }

    public PropertyUnit removeMortgages(Mortgage mortgage) {
        this.mortgages.remove(mortgage);
        mortgage.setPropertyUnit(null);
        return this;
    }

    public void setMortgages(Set<Mortgage> mortgages) {
        this.mortgages = mortgages;
    }

    public Set<Appliance> getAppliances() {
        return appliances;
    }

    public PropertyUnit appliances(Set<Appliance> appliances) {
        this.appliances = appliances;
        return this;
    }

    public PropertyUnit addAppliances(Appliance appliance) {
        this.appliances.add(appliance);
        appliance.setPropertyUnit(this);
        return this;
    }

    public PropertyUnit removeAppliances(Appliance appliance) {
        this.appliances.remove(appliance);
        appliance.setPropertyUnit(null);
        return this;
    }

    public void setAppliances(Set<Appliance> appliances) {
        this.appliances = appliances;
    }

    public Set<RentRoll> getRentRolls() {
        return rentRolls;
    }

    public PropertyUnit rentRolls(Set<RentRoll> rentRolls) {
        this.rentRolls = rentRolls;
        return this;
    }

    public PropertyUnit addRentRoll(RentRoll rentRoll) {
        this.rentRolls.add(rentRoll);
        rentRoll.setPropertyUnit(this);
        return this;
    }

    public PropertyUnit removeRentRoll(RentRoll rentRoll) {
        this.rentRolls.remove(rentRoll);
        rentRoll.setPropertyUnit(null);
        return this;
    }

    public void setRentRolls(Set<RentRoll> rentRolls) {
        this.rentRolls = rentRolls;
    }

    public Set<Lease> getLeases() {
        return leases;
    }

    public PropertyUnit leases(Set<Lease> leases) {
        this.leases = leases;
        return this;
    }

    public PropertyUnit addLease(Lease lease) {
        this.leases.add(lease);
        lease.getPropertyUnits().add(this);
        return this;
    }

    public PropertyUnit removeLease(Lease lease) {
        this.leases.remove(lease);
        lease.getPropertyUnits().remove(this);
        return this;
    }

    public void setLeases(Set<Lease> leases) {
        this.leases = leases;
    }

    public Set<LandLord> getOwners() {
        return owners;
    }

    public PropertyUnit owners(Set<LandLord> landLords) {
        this.owners = landLords;
        return this;
    }

    public PropertyUnit addOwner(LandLord landLord) {
        this.owners.add(landLord);
        landLord.getPropertyUnits().add(this);
        return this;
    }

    public PropertyUnit removeOwner(LandLord landLord) {
        this.owners.remove(landLord);
        landLord.getPropertyUnits().remove(this);
        return this;
    }

    public void setOwners(Set<LandLord> landLords) {
        this.owners = landLords;
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
        PropertyUnit propertyUnit = (PropertyUnit) o;
        if (propertyUnit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertyUnit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertyUnit{" +
            "id=" + getId() +
            ", unitNo='" + getUnitNo() + "'" +
            ", description='" + getDescription() + "'" +
            ", floors=" + getFloors() +
            ", netArea=" + getNetArea() +
            ", grossArea=" + getGrossArea() +
            ", residential='" + isResidential() + "'" +
            ", totalRooms=" + getTotalRooms() +
            ", noOfBrs=" + getNoOfBrs() +
            ", noOfFb=" + getNoOfFb() +
            ", noOfHb=" + getNoOfHb() +
            ", propertyMailboxNum='" + getPropertyMailboxNum() + "'" +
            ", propertyParkingLotNum='" + getPropertyParkingLotNum() + "'" +
            ", gasHeating='" + isGasHeating() + "'" +
            ", whoPaysHeating=" + getWhoPaysHeating() +
            ", electric='" + isElectric() + "'" +
            ", whoPaysElectric=" + getWhoPaysElectric() +
            ", water='" + isWater() + "'" +
            ", whoPaysWater=" + getWhoPaysWater() +
            ", lastRenovated='" + getLastRenovated() + "'" +
            ", currentRentPerSqft=" + getCurrentRentPerSqft() +
            ", version=" + getVersion() +
            "}";
    }
}
