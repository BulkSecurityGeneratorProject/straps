package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Expense.
 */
@Entity
@Table(name = "expense")
@Document(indexName = "expense")
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency")
    private Long currency;

    @ManyToOne
    @JsonIgnoreProperties("expenses")
    private Property property;

    @ManyToOne
    @JsonIgnoreProperties("expenses")
    private Building building;

    @ManyToOne
    @JsonIgnoreProperties("expenses")
    private PropertyUnit propertyUnit;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Expense amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCurrency() {
        return currency;
    }

    public Expense currency(Long currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Property getProperty() {
        return property;
    }

    public Expense property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Building getBuilding() {
        return building;
    }

    public Expense building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public PropertyUnit getPropertyUnit() {
        return propertyUnit;
    }

    public Expense propertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnit = propertyUnit;
        return this;
    }

    public void setPropertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnit = propertyUnit;
    }

    public Lookup getType() {
        return type;
    }

    public Expense type(Lookup lookup) {
        this.type = lookup;
        return this;
    }

    public void setType(Lookup lookup) {
        this.type = lookup;
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
        Expense expense = (Expense) o;
        if (expense.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expense.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Expense{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", currency=" + getCurrency() +
            "}";
    }
}
