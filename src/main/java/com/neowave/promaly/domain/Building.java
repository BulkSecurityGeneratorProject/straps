package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Building.
 */
@Entity
@Table(name = "building")
@Document(indexName = "building")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "year_built")
    private LocalDate yearBuilt;

    @Column(name = "no_ofloors")
    private Long noOfloors;

    @Column(name = "building_size")
    private Double buildingSize;

    @Column(name = "covered_area")
    private Double coveredArea;

    @Column(name = "land_area")
    private Double landArea;

    @Column(name = "no_of_rental_unit")
    private Long noOfRentalUnit;

    @Column(name = "parking_spaces")
    private Long parkingSpaces;

    @Column(name = "total_space_avaliable")
    private Double totalSpaceAvaliable;

    @Column(name = "total_unit_level")
    private Long totalUnitLevel;

    @Column(name = "current_rent_per_sqft")
    private Double currentRentPerSqft;

    @Column(name = "description")
    private String description;

    @Column(name = "version")
    private String version;

    @ManyToOne
    @JsonIgnoreProperties("buildings")
    private Property property;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup assetType;

    @OneToMany(mappedBy = "building")
    private Set<IncomeProjection> projectedIncomes = new HashSet<>();
    @OneToMany(mappedBy = "building")
    private Set<PropertyUnit> propertyUnits = new HashSet<>();
    @OneToMany(mappedBy = "building")
    private Set<Amenity> amenities = new HashSet<>();
    @OneToMany(mappedBy = "building")
    private Set<Expense> expenses = new HashSet<>();
    @OneToMany(mappedBy = "building")
    private Set<Mortgage> mortgages = new HashSet<>();
    @OneToMany(mappedBy = "building")
    private Set<RentRoll> rentRolls = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "building_owner",
               joinColumns = @JoinColumn(name = "buildings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "owners_id", referencedColumnName = "id"))
    private Set<LandLord> owners = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "building_company",
               joinColumns = @JoinColumn(name = "buildings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "companies_id", referencedColumnName = "id"))
    private Set<Company> companies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "building_lease",
               joinColumns = @JoinColumn(name = "buildings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "leases_id", referencedColumnName = "id"))
    private Set<Lease> leases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Building name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getYearBuilt() {
        return yearBuilt;
    }

    public Building yearBuilt(LocalDate yearBuilt) {
        this.yearBuilt = yearBuilt;
        return this;
    }

    public void setYearBuilt(LocalDate yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public Long getNoOfloors() {
        return noOfloors;
    }

    public Building noOfloors(Long noOfloors) {
        this.noOfloors = noOfloors;
        return this;
    }

    public void setNoOfloors(Long noOfloors) {
        this.noOfloors = noOfloors;
    }

    public Double getBuildingSize() {
        return buildingSize;
    }

    public Building buildingSize(Double buildingSize) {
        this.buildingSize = buildingSize;
        return this;
    }

    public void setBuildingSize(Double buildingSize) {
        this.buildingSize = buildingSize;
    }

    public Double getCoveredArea() {
        return coveredArea;
    }

    public Building coveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
        return this;
    }

    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
    }

    public Double getLandArea() {
        return landArea;
    }

    public Building landArea(Double landArea) {
        this.landArea = landArea;
        return this;
    }

    public void setLandArea(Double landArea) {
        this.landArea = landArea;
    }

    public Long getNoOfRentalUnit() {
        return noOfRentalUnit;
    }

    public Building noOfRentalUnit(Long noOfRentalUnit) {
        this.noOfRentalUnit = noOfRentalUnit;
        return this;
    }

    public void setNoOfRentalUnit(Long noOfRentalUnit) {
        this.noOfRentalUnit = noOfRentalUnit;
    }

    public Long getParkingSpaces() {
        return parkingSpaces;
    }

    public Building parkingSpaces(Long parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
        return this;
    }

    public void setParkingSpaces(Long parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public Double getTotalSpaceAvaliable() {
        return totalSpaceAvaliable;
    }

    public Building totalSpaceAvaliable(Double totalSpaceAvaliable) {
        this.totalSpaceAvaliable = totalSpaceAvaliable;
        return this;
    }

    public void setTotalSpaceAvaliable(Double totalSpaceAvaliable) {
        this.totalSpaceAvaliable = totalSpaceAvaliable;
    }

    public Long getTotalUnitLevel() {
        return totalUnitLevel;
    }

    public Building totalUnitLevel(Long totalUnitLevel) {
        this.totalUnitLevel = totalUnitLevel;
        return this;
    }

    public void setTotalUnitLevel(Long totalUnitLevel) {
        this.totalUnitLevel = totalUnitLevel;
    }

    public Double getCurrentRentPerSqft() {
        return currentRentPerSqft;
    }

    public Building currentRentPerSqft(Double currentRentPerSqft) {
        this.currentRentPerSqft = currentRentPerSqft;
        return this;
    }

    public void setCurrentRentPerSqft(Double currentRentPerSqft) {
        this.currentRentPerSqft = currentRentPerSqft;
    }

    public String getDescription() {
        return description;
    }

    public Building description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public Building version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Property getProperty() {
        return property;
    }

    public Building property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Lookup getAssetType() {
        return assetType;
    }

    public Building assetType(Lookup lookup) {
        this.assetType = lookup;
        return this;
    }

    public void setAssetType(Lookup lookup) {
        this.assetType = lookup;
    }

    public Set<IncomeProjection> getProjectedIncomes() {
        return projectedIncomes;
    }

    public Building projectedIncomes(Set<IncomeProjection> incomeProjections) {
        this.projectedIncomes = incomeProjections;
        return this;
    }

    public Building addProjectedIncomes(IncomeProjection incomeProjection) {
        this.projectedIncomes.add(incomeProjection);
        incomeProjection.setBuilding(this);
        return this;
    }

    public Building removeProjectedIncomes(IncomeProjection incomeProjection) {
        this.projectedIncomes.remove(incomeProjection);
        incomeProjection.setBuilding(null);
        return this;
    }

    public void setProjectedIncomes(Set<IncomeProjection> incomeProjections) {
        this.projectedIncomes = incomeProjections;
    }

    public Set<PropertyUnit> getPropertyUnits() {
        return propertyUnits;
    }

    public Building propertyUnits(Set<PropertyUnit> propertyUnits) {
        this.propertyUnits = propertyUnits;
        return this;
    }

    public Building addPropertyUnits(PropertyUnit propertyUnit) {
        this.propertyUnits.add(propertyUnit);
        propertyUnit.setBuilding(this);
        return this;
    }

    public Building removePropertyUnits(PropertyUnit propertyUnit) {
        this.propertyUnits.remove(propertyUnit);
        propertyUnit.setBuilding(null);
        return this;
    }

    public void setPropertyUnits(Set<PropertyUnit> propertyUnits) {
        this.propertyUnits = propertyUnits;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public Building amenities(Set<Amenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    public Building addAmenities(Amenity amenity) {
        this.amenities.add(amenity);
        amenity.setBuilding(this);
        return this;
    }

    public Building removeAmenities(Amenity amenity) {
        this.amenities.remove(amenity);
        amenity.setBuilding(null);
        return this;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public Building expenses(Set<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }

    public Building addExpenses(Expense expense) {
        this.expenses.add(expense);
        expense.setBuilding(this);
        return this;
    }

    public Building removeExpenses(Expense expense) {
        this.expenses.remove(expense);
        expense.setBuilding(null);
        return this;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public Set<Mortgage> getMortgages() {
        return mortgages;
    }

    public Building mortgages(Set<Mortgage> mortgages) {
        this.mortgages = mortgages;
        return this;
    }

    public Building addMortgages(Mortgage mortgage) {
        this.mortgages.add(mortgage);
        mortgage.setBuilding(this);
        return this;
    }

    public Building removeMortgages(Mortgage mortgage) {
        this.mortgages.remove(mortgage);
        mortgage.setBuilding(null);
        return this;
    }

    public void setMortgages(Set<Mortgage> mortgages) {
        this.mortgages = mortgages;
    }

    public Set<RentRoll> getRentRolls() {
        return rentRolls;
    }

    public Building rentRolls(Set<RentRoll> rentRolls) {
        this.rentRolls = rentRolls;
        return this;
    }

    public Building addRentRoll(RentRoll rentRoll) {
        this.rentRolls.add(rentRoll);
        rentRoll.setBuilding(this);
        return this;
    }

    public Building removeRentRoll(RentRoll rentRoll) {
        this.rentRolls.remove(rentRoll);
        rentRoll.setBuilding(null);
        return this;
    }

    public void setRentRolls(Set<RentRoll> rentRolls) {
        this.rentRolls = rentRolls;
    }

    public Set<LandLord> getOwners() {
        return owners;
    }

    public Building owners(Set<LandLord> landLords) {
        this.owners = landLords;
        return this;
    }

    public Building addOwner(LandLord landLord) {
        this.owners.add(landLord);
        landLord.getBuildings().add(this);
        return this;
    }

    public Building removeOwner(LandLord landLord) {
        this.owners.remove(landLord);
        landLord.getBuildings().remove(this);
        return this;
    }

    public void setOwners(Set<LandLord> landLords) {
        this.owners = landLords;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Building companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Building addCompany(Company company) {
        this.companies.add(company);
        company.getBuildings().add(this);
        return this;
    }

    public Building removeCompany(Company company) {
        this.companies.remove(company);
        company.getBuildings().remove(this);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Set<Lease> getLeases() {
        return leases;
    }

    public Building leases(Set<Lease> leases) {
        this.leases = leases;
        return this;
    }

    public Building addLease(Lease lease) {
        this.leases.add(lease);
        lease.getBuildings().add(this);
        return this;
    }

    public Building removeLease(Lease lease) {
        this.leases.remove(lease);
        lease.getBuildings().remove(this);
        return this;
    }

    public void setLeases(Set<Lease> leases) {
        this.leases = leases;
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
        Building building = (Building) o;
        if (building.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), building.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Building{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", yearBuilt='" + getYearBuilt() + "'" +
            ", noOfloors=" + getNoOfloors() +
            ", buildingSize=" + getBuildingSize() +
            ", coveredArea=" + getCoveredArea() +
            ", landArea=" + getLandArea() +
            ", noOfRentalUnit=" + getNoOfRentalUnit() +
            ", parkingSpaces=" + getParkingSpaces() +
            ", totalSpaceAvaliable=" + getTotalSpaceAvaliable() +
            ", totalUnitLevel=" + getTotalUnitLevel() +
            ", currentRentPerSqft=" + getCurrentRentPerSqft() +
            ", description='" + getDescription() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
