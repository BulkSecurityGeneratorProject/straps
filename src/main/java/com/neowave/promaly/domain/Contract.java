package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Contract.
 */
@Entity
@Table(name = "contract")
@Document(indexName = "contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "contract")
    private Set<Lookup> types = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "contract_company",
               joinColumns = @JoinColumn(name = "contracts_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "companies_id", referencedColumnName = "id"))
    private Set<Company> companies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "contract_management_company",
               joinColumns = @JoinColumn(name = "contracts_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "management_companies_id", referencedColumnName = "id"))
    private Set<Company> managementCompanies = new HashSet<>();

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

    public Contract description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Contract startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Contract endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Lookup> getTypes() {
        return types;
    }

    public Contract types(Set<Lookup> lookups) {
        this.types = lookups;
        return this;
    }

    public Contract addType(Lookup lookup) {
        this.types.add(lookup);
        lookup.setContract(this);
        return this;
    }

    public Contract removeType(Lookup lookup) {
        this.types.remove(lookup);
        lookup.setContract(null);
        return this;
    }

    public void setTypes(Set<Lookup> lookups) {
        this.types = lookups;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Contract companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Contract addCompany(Company company) {
        this.companies.add(company);
        company.getContracts().add(this);
        return this;
    }

    public Contract removeCompany(Company company) {
        this.companies.remove(company);
        company.getContracts().remove(this);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Set<Company> getManagementCompanies() {
        return managementCompanies;
    }

    public Contract managementCompanies(Set<Company> companies) {
        this.managementCompanies = companies;
        return this;
    }

    public Contract addManagementCompany(Company company) {
        this.managementCompanies.add(company);
        company.getManagementCompanies().add(this);
        return this;
    }

    public Contract removeManagementCompany(Company company) {
        this.managementCompanies.remove(company);
        company.getManagementCompanies().remove(this);
        return this;
    }

    public void setManagementCompanies(Set<Company> companies) {
        this.managementCompanies = companies;
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
        Contract contract = (Contract) o;
        if (contract.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contract.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contract{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
