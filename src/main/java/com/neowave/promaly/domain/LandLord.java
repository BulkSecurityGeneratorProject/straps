package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * landlord <-> contact (one to one)
 * landlord <-> company (one to one)
 * landlord <-> ownershipType (one to one)
 */
@ApiModel(description = "landlord <-> contact (one to one) landlord <-> company (one to one) landlord <-> ownershipType (one to one)")
@Entity
@Table(name = "land_lord")
@Document(indexName = "landlord")
public class LandLord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "percentage_ownership")
    private Double percentageOwnership;

    @Column(name = "ownership_type")
    private Long ownershipType;

    @ManyToMany(mappedBy = "owners")
    @JsonIgnore
    private Set<Property> properties = new HashSet<>();

    @ManyToMany(mappedBy = "owners")
    @JsonIgnore
    private Set<Building> buildings = new HashSet<>();

    @ManyToMany(mappedBy = "owners")
    @JsonIgnore
    private Set<PropertyUnit> propertyUnits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public LandLord contactId(Long contactId) {
        this.contactId = contactId;
        return this;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Double getPercentageOwnership() {
        return percentageOwnership;
    }

    public LandLord percentageOwnership(Double percentageOwnership) {
        this.percentageOwnership = percentageOwnership;
        return this;
    }

    public void setPercentageOwnership(Double percentageOwnership) {
        this.percentageOwnership = percentageOwnership;
    }

    public Long getOwnershipType() {
        return ownershipType;
    }

    public LandLord ownershipType(Long ownershipType) {
        this.ownershipType = ownershipType;
        return this;
    }

    public void setOwnershipType(Long ownershipType) {
        this.ownershipType = ownershipType;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public LandLord properties(Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    public LandLord addProperty(Property property) {
        this.properties.add(property);
        property.getOwners().add(this);
        return this;
    }

    public LandLord removeProperty(Property property) {
        this.properties.remove(property);
        property.getOwners().remove(this);
        return this;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public Set<Building> getBuildings() {
        return buildings;
    }

    public LandLord buildings(Set<Building> buildings) {
        this.buildings = buildings;
        return this;
    }

    public LandLord addBuilding(Building building) {
        this.buildings.add(building);
        building.getOwners().add(this);
        return this;
    }

    public LandLord removeBuilding(Building building) {
        this.buildings.remove(building);
        building.getOwners().remove(this);
        return this;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
    }

    public Set<PropertyUnit> getPropertyUnits() {
        return propertyUnits;
    }

    public LandLord propertyUnits(Set<PropertyUnit> propertyUnits) {
        this.propertyUnits = propertyUnits;
        return this;
    }

    public LandLord addPropertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnits.add(propertyUnit);
        propertyUnit.getOwners().add(this);
        return this;
    }

    public LandLord removePropertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnits.remove(propertyUnit);
        propertyUnit.getOwners().remove(this);
        return this;
    }

    public void setPropertyUnits(Set<PropertyUnit> propertyUnits) {
        this.propertyUnits = propertyUnits;
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
        LandLord landLord = (LandLord) o;
        if (landLord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), landLord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LandLord{" +
            "id=" + getId() +
            ", contactId=" + getContactId() +
            ", percentageOwnership=" + getPercentageOwnership() +
            ", ownershipType=" + getOwnershipType() +
            "}";
    }
}
