package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Appliance.
 */
@Entity
@Table(name = "appliance")
@Document(indexName = "appliance")
public class Appliance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "serial_num")
    private String serialNum;

    @Column(name = "warranty_start_date")
    private LocalDate warrantyStartDate;

    @Column(name = "warranty_end_date")
    private LocalDate warrantyEndDate;

    @ManyToOne
    @JsonIgnoreProperties("appliances")
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

    public Appliance description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public Appliance serialNum(String serialNum) {
        this.serialNum = serialNum;
        return this;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public LocalDate getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public Appliance warrantyStartDate(LocalDate warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
        return this;
    }

    public void setWarrantyStartDate(LocalDate warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    public LocalDate getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public Appliance warrantyEndDate(LocalDate warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
        return this;
    }

    public void setWarrantyEndDate(LocalDate warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public PropertyUnit getPropertyUnit() {
        return propertyUnit;
    }

    public Appliance propertyUnit(PropertyUnit propertyUnit) {
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
        Appliance appliance = (Appliance) o;
        if (appliance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appliance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appliance{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", serialNum='" + getSerialNum() + "'" +
            ", warrantyStartDate='" + getWarrantyStartDate() + "'" +
            ", warrantyEndDate='" + getWarrantyEndDate() + "'" +
            "}";
    }
}
