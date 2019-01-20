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
 * Company <-> Address (one to many)
 * Company <-> Contacts (one to many)
 */
@ApiModel(description = "Company <-> Address (one to many) Company <-> Contacts (one to many)")
@Entity
@Table(name = "company")
@Document(indexName = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "version")
    private String version;

    @OneToOne    @JoinColumn(unique = true)
    private Lookup type;

    @OneToMany(mappedBy = "company")
    private Set<Address> addresses = new HashSet<>();
    @OneToMany(mappedBy = "company")
    private Set<Contact> contacts = new HashSet<>();
    @OneToMany(mappedBy = "company")
    private Set<CompanyCapability> capabilities = new HashSet<>();
    @ManyToMany(mappedBy = "companies")
    @JsonIgnore
    private Set<Property> properties = new HashSet<>();

    @ManyToMany(mappedBy = "companies")
    @JsonIgnore
    private Set<Building> buildings = new HashSet<>();

    @ManyToMany(mappedBy = "companies")
    @JsonIgnore
    private Set<Contract> contracts = new HashSet<>();

    @ManyToMany(mappedBy = "managementCompanies")
    @JsonIgnore
    private Set<Contract> managementCompanies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public Company version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Lookup getType() {
        return type;
    }

    public Company type(Lookup lookup) {
        this.type = lookup;
        return this;
    }

    public void setType(Lookup lookup) {
        this.type = lookup;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Company addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Company addAddresses(Address address) {
        this.addresses.add(address);
        address.setCompany(this);
        return this;
    }

    public Company removeAddresses(Address address) {
        this.addresses.remove(address);
        address.setCompany(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Company contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Company addContacts(Contact contact) {
        this.contacts.add(contact);
        contact.setCompany(this);
        return this;
    }

    public Company removeContacts(Contact contact) {
        this.contacts.remove(contact);
        contact.setCompany(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<CompanyCapability> getCapabilities() {
        return capabilities;
    }

    public Company capabilities(Set<CompanyCapability> companyCapabilities) {
        this.capabilities = companyCapabilities;
        return this;
    }

    public Company addCapabilities(CompanyCapability companyCapability) {
        this.capabilities.add(companyCapability);
        companyCapability.setCompany(this);
        return this;
    }

    public Company removeCapabilities(CompanyCapability companyCapability) {
        this.capabilities.remove(companyCapability);
        companyCapability.setCompany(null);
        return this;
    }

    public void setCapabilities(Set<CompanyCapability> companyCapabilities) {
        this.capabilities = companyCapabilities;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public Company properties(Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    public Company addProperty(Property property) {
        this.properties.add(property);
        property.getCompanies().add(this);
        return this;
    }

    public Company removeProperty(Property property) {
        this.properties.remove(property);
        property.getCompanies().remove(this);
        return this;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public Set<Building> getBuildings() {
        return buildings;
    }

    public Company buildings(Set<Building> buildings) {
        this.buildings = buildings;
        return this;
    }

    public Company addBuilding(Building building) {
        this.buildings.add(building);
        building.getCompanies().add(this);
        return this;
    }

    public Company removeBuilding(Building building) {
        this.buildings.remove(building);
        building.getCompanies().remove(this);
        return this;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public Company contracts(Set<Contract> contracts) {
        this.contracts = contracts;
        return this;
    }

    public Company addContracts(Contract contract) {
        this.contracts.add(contract);
        contract.getCompanies().add(this);
        return this;
    }

    public Company removeContracts(Contract contract) {
        this.contracts.remove(contract);
        contract.getCompanies().remove(this);
        return this;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public Set<Contract> getManagementCompanies() {
        return managementCompanies;
    }

    public Company managementCompanies(Set<Contract> contracts) {
        this.managementCompanies = contracts;
        return this;
    }

    public Company addManagementCompany(Contract contract) {
        this.managementCompanies.add(contract);
        contract.getManagementCompanies().add(this);
        return this;
    }

    public Company removeManagementCompany(Contract contract) {
        this.managementCompanies.remove(contract);
        contract.getManagementCompanies().remove(this);
        return this;
    }

    public void setManagementCompanies(Set<Contract> contracts) {
        this.managementCompanies = contracts;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
