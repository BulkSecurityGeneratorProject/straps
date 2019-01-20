package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Document(indexName = "property")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "gross_area")
    private Double grossArea;

    @Column(name = "net_area")
    private Double netArea;

    @ManyToOne
    @JsonIgnoreProperties("properties")
    private Portfolio portfolio;

    @OneToOne    @JoinColumn(unique = true)
    private Address address;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup usageType;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup status;

    @OneToOne    @JoinColumn(unique = true)
    private Lease lease;

    @OneToMany(mappedBy = "property")
    private Set<IncomeProjection> projectedIncomes = new HashSet<>();
    @OneToMany(mappedBy = "property")
    private Set<Amenity> amenities = new HashSet<>();
    @OneToMany(mappedBy = "property")
    private Set<Expense> expenses = new HashSet<>();
    @OneToMany(mappedBy = "property")
    private Set<Building> buildings = new HashSet<>();
    @OneToMany(mappedBy = "property")
    private Set<Mortgage> mortgages = new HashSet<>();
    @OneToMany(mappedBy = "property")
    private Set<RentRoll> rentRolls = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "property_owner",
               joinColumns = @JoinColumn(name = "properties_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "owners_id", referencedColumnName = "id"))
    private Set<LandLord> owners = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "property_company",
               joinColumns = @JoinColumn(name = "properties_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "companies_id", referencedColumnName = "id"))
    private Set<Company> companies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Property description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGrossArea() {
        return grossArea;
    }

    public Property grossArea(Double grossArea) {
        this.grossArea = grossArea;
        return this;
    }

    public void setGrossArea(Double grossArea) {
        this.grossArea = grossArea;
    }

    public Double getNetArea() {
        return netArea;
    }

    public Property netArea(Double netArea) {
        this.netArea = netArea;
        return this;
    }

    public void setNetArea(Double netArea) {
        this.netArea = netArea;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public Property portfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
        return this;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Address getAddress() {
        return address;
    }

    public Property address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Lookup getUsageType() {
        return usageType;
    }

    public Property usageType(Lookup lookup) {
        this.usageType = lookup;
        return this;
    }

    public void setUsageType(Lookup lookup) {
        this.usageType = lookup;
    }

    public Lookup getStatus() {
        return status;
    }

    public Property status(Lookup lookup) {
        this.status = lookup;
        return this;
    }

    public void setStatus(Lookup lookup) {
        this.status = lookup;
    }

    public Lease getLease() {
        return lease;
    }

    public Property lease(Lease lease) {
        this.lease = lease;
        return this;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public Set<IncomeProjection> getProjectedIncomes() {
        return projectedIncomes;
    }

    public Property projectedIncomes(Set<IncomeProjection> incomeProjections) {
        this.projectedIncomes = incomeProjections;
        return this;
    }

    public Property addProjectedIncomes(IncomeProjection incomeProjection) {
        this.projectedIncomes.add(incomeProjection);
        incomeProjection.setProperty(this);
        return this;
    }

    public Property removeProjectedIncomes(IncomeProjection incomeProjection) {
        this.projectedIncomes.remove(incomeProjection);
        incomeProjection.setProperty(null);
        return this;
    }

    public void setProjectedIncomes(Set<IncomeProjection> incomeProjections) {
        this.projectedIncomes = incomeProjections;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public Property amenities(Set<Amenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    public Property addAmenities(Amenity amenity) {
        this.amenities.add(amenity);
        amenity.setProperty(this);
        return this;
    }

    public Property removeAmenities(Amenity amenity) {
        this.amenities.remove(amenity);
        amenity.setProperty(null);
        return this;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public Property expenses(Set<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }

    public Property addExpenses(Expense expense) {
        this.expenses.add(expense);
        expense.setProperty(this);
        return this;
    }

    public Property removeExpenses(Expense expense) {
        this.expenses.remove(expense);
        expense.setProperty(null);
        return this;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public Set<Building> getBuildings() {
        return buildings;
    }

    public Property buildings(Set<Building> buildings) {
        this.buildings = buildings;
        return this;
    }

    public Property addBuildings(Building building) {
        this.buildings.add(building);
        building.setProperty(this);
        return this;
    }

    public Property removeBuildings(Building building) {
        this.buildings.remove(building);
        building.setProperty(null);
        return this;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
    }

    public Set<Mortgage> getMortgages() {
        return mortgages;
    }

    public Property mortgages(Set<Mortgage> mortgages) {
        this.mortgages = mortgages;
        return this;
    }

    public Property addMortgages(Mortgage mortgage) {
        this.mortgages.add(mortgage);
        mortgage.setProperty(this);
        return this;
    }

    public Property removeMortgages(Mortgage mortgage) {
        this.mortgages.remove(mortgage);
        mortgage.setProperty(null);
        return this;
    }

    public void setMortgages(Set<Mortgage> mortgages) {
        this.mortgages = mortgages;
    }

    public Set<RentRoll> getRentRolls() {
        return rentRolls;
    }

    public Property rentRolls(Set<RentRoll> rentRolls) {
        this.rentRolls = rentRolls;
        return this;
    }

    public Property addRentRoll(RentRoll rentRoll) {
        this.rentRolls.add(rentRoll);
        rentRoll.setProperty(this);
        return this;
    }

    public Property removeRentRoll(RentRoll rentRoll) {
        this.rentRolls.remove(rentRoll);
        rentRoll.setProperty(null);
        return this;
    }

    public void setRentRolls(Set<RentRoll> rentRolls) {
        this.rentRolls = rentRolls;
    }

    public Set<LandLord> getOwners() {
        return owners;
    }

    public Property owners(Set<LandLord> landLords) {
        this.owners = landLords;
        return this;
    }

    public Property addOwner(LandLord landLord) {
        this.owners.add(landLord);
        landLord.getProperties().add(this);
        return this;
    }

    public Property removeOwner(LandLord landLord) {
        this.owners.remove(landLord);
        landLord.getProperties().remove(this);
        return this;
    }

    public void setOwners(Set<LandLord> landLords) {
        this.owners = landLords;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Property companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Property addCompany(Company company) {
        this.companies.add(company);
        company.getProperties().add(this);
        return this;
    }

    public Property removeCompany(Company company) {
        this.companies.remove(company);
        company.getProperties().remove(this);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
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
        Property property = (Property) o;
        if (property.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), property.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Property{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", grossArea=" + getGrossArea() +
            ", netArea=" + getNetArea() +
            "}";
    }
}
