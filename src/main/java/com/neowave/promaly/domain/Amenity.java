package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Amenity.
 */
@Entity
@Table(name = "amenity")
@Document(indexName = "amenity")
public class Amenity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("amenities")
    private Property property;

    @ManyToOne
    @JsonIgnoreProperties("amenities")
    private Building building;

    @ManyToOne
    @JsonIgnoreProperties("amenities")
    private PropertyUnit propertyUnit;

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

    public Amenity description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Property getProperty() {
        return property;
    }

    public Amenity property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Building getBuilding() {
        return building;
    }

    public Amenity building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public PropertyUnit getPropertyUnit() {
        return propertyUnit;
    }

    public Amenity propertyUnit(PropertyUnit propertyUnit) {
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
        Amenity amenity = (Amenity) o;
        if (amenity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amenity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Amenity{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
