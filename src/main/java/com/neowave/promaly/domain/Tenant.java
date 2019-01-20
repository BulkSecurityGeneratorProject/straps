package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tenant.
 */
@Entity
@Table(name = "tenant")
@Document(indexName = "tenant")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lease_id")
    private Long leaseId;

    @Column(name = "contact")
    private Long contact;

    @ManyToOne
    @JsonIgnoreProperties("tenants")
    private PropertyUnit propertyUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public Tenant leaseId(Long leaseId) {
        this.leaseId = leaseId;
        return this;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public Long getContact() {
        return contact;
    }

    public Tenant contact(Long contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public PropertyUnit getPropertyUnit() {
        return propertyUnit;
    }

    public Tenant propertyUnit(PropertyUnit propertyUnit) {
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
        Tenant tenant = (Tenant) o;
        if (tenant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tenant{" +
            "id=" + getId() +
            ", leaseId=" + getLeaseId() +
            ", contact=" + getContact() +
            "}";
    }
}
