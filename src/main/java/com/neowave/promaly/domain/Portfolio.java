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
 * Relationships:
 * property <-> landlord (many to many)
 * property <-> address (one to one)
 * property <-> property unit (one to many)
 * property <-> usage_type (one to one)
 * property <-> status (one to one)
 * property <-> tenants (one to many)
 * property <-> amenities (one to many)
 * property <-> lease (one to one)
 * property <-> mortgage (one to one)
 * property <-> company (many to many)
 */
@ApiModel(description = "Relationships: property <-> landlord (many to many) property <-> address (one to one) property <-> property unit (one to many) property <-> usage_type (one to one) property <-> status (one to one) property <-> tenants (one to many) property <-> amenities (one to many) property <-> lease (one to one) property <-> mortgage (one to one) property <-> company (many to many)")
@Entity
@Table(name = "portfolio")
@Document(indexName = "portfolio")
public class Portfolio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "portfolio")
    private Set<IncomeProjection> projectedIncomes = new HashSet<>();
    @OneToMany(mappedBy = "portfolio")
    private Set<Property> properties = new HashSet<>();
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

    public Portfolio description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<IncomeProjection> getProjectedIncomes() {
        return projectedIncomes;
    }

    public Portfolio projectedIncomes(Set<IncomeProjection> incomeProjections) {
        this.projectedIncomes = incomeProjections;
        return this;
    }

    public Portfolio addProjectedIncomes(IncomeProjection incomeProjection) {
        this.projectedIncomes.add(incomeProjection);
        incomeProjection.setPortfolio(this);
        return this;
    }

    public Portfolio removeProjectedIncomes(IncomeProjection incomeProjection) {
        this.projectedIncomes.remove(incomeProjection);
        incomeProjection.setPortfolio(null);
        return this;
    }

    public void setProjectedIncomes(Set<IncomeProjection> incomeProjections) {
        this.projectedIncomes = incomeProjections;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public Portfolio properties(Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    public Portfolio addProperties(Property property) {
        this.properties.add(property);
        property.setPortfolio(this);
        return this;
    }

    public Portfolio removeProperties(Property property) {
        this.properties.remove(property);
        property.setPortfolio(null);
        return this;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
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
        Portfolio portfolio = (Portfolio) o;
        if (portfolio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), portfolio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Portfolio{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
