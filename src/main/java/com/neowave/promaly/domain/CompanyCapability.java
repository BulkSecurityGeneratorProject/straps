package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CompanyCapability.
 */
@Entity
@Table(name = "company_capability")
@Document(indexName = "companycapability")
public class CompanyCapability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capability_id")
    private Long capabilityId;

    @Column(name = "license")
    private Long license;

    @Column(name = "certfication")
    private Long certfication;

    @ManyToOne
    @JsonIgnoreProperties("capabilities")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCapabilityId() {
        return capabilityId;
    }

    public CompanyCapability capabilityId(Long capabilityId) {
        this.capabilityId = capabilityId;
        return this;
    }

    public void setCapabilityId(Long capabilityId) {
        this.capabilityId = capabilityId;
    }

    public Long getLicense() {
        return license;
    }

    public CompanyCapability license(Long license) {
        this.license = license;
        return this;
    }

    public void setLicense(Long license) {
        this.license = license;
    }

    public Long getCertfication() {
        return certfication;
    }

    public CompanyCapability certfication(Long certfication) {
        this.certfication = certfication;
        return this;
    }

    public void setCertfication(Long certfication) {
        this.certfication = certfication;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyCapability company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        CompanyCapability companyCapability = (CompanyCapability) o;
        if (companyCapability.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyCapability.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyCapability{" +
            "id=" + getId() +
            ", capabilityId=" + getCapabilityId() +
            ", license=" + getLicense() +
            ", certfication=" + getCertfication() +
            "}";
    }
}
