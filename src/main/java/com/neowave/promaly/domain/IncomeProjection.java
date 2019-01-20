package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A IncomeProjection.
 */
@Entity
@Table(name = "income_projection")
@Document(indexName = "incomeprojection")
public class IncomeProjection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period")
    private Integer period;

    @Column(name = "projected_value")
    private Double projectedValue;

    @ManyToOne
    @JsonIgnoreProperties("projectedIncomes")
    private Portfolio portfolio;

    @ManyToOne
    @JsonIgnoreProperties("projectedIncomes")
    private Property property;

    @ManyToOne
    @JsonIgnoreProperties("projectedIncomes")
    private Building building;

    @ManyToOne
    @JsonIgnoreProperties("projectedIncomes")
    private PropertyUnit propertyUnit;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup assetLevelType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriod() {
        return period;
    }

    public IncomeProjection period(Integer period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getProjectedValue() {
        return projectedValue;
    }

    public IncomeProjection projectedValue(Double projectedValue) {
        this.projectedValue = projectedValue;
        return this;
    }

    public void setProjectedValue(Double projectedValue) {
        this.projectedValue = projectedValue;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public IncomeProjection portfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
        return this;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Property getProperty() {
        return property;
    }

    public IncomeProjection property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Building getBuilding() {
        return building;
    }

    public IncomeProjection building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public PropertyUnit getPropertyUnit() {
        return propertyUnit;
    }

    public IncomeProjection propertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnit = propertyUnit;
        return this;
    }

    public void setPropertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnit = propertyUnit;
    }

    public Lookup getAssetLevelType() {
        return assetLevelType;
    }

    public IncomeProjection assetLevelType(Lookup lookup) {
        this.assetLevelType = lookup;
        return this;
    }

    public void setAssetLevelType(Lookup lookup) {
        this.assetLevelType = lookup;
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
        IncomeProjection incomeProjection = (IncomeProjection) o;
        if (incomeProjection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incomeProjection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncomeProjection{" +
            "id=" + getId() +
            ", period=" + getPeriod() +
            ", projectedValue=" + getProjectedValue() +
            "}";
    }
}
