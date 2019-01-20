package com.neowave.promaly.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Lookup.
 */
@Entity
@Table(name = "lookup")
@Document(indexName = "lookup")
public class Lookup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "lookup_types_id")
    private Long lookupTypesId;

    @Column(name = "lookup_code")
    private String lookupCode;

    @Column(name = "lookup_value")
    private String lookupValue;

    @Column(name = "flex_1")
    private String flex1;

    @Column(name = "flex_2")
    private String flex2;

    @Column(name = "flex_3")
    private String flex3;

    @Column(name = "flex_4")
    private String flex4;

    @Column(name = "version")
    private String version;

    @ManyToOne
    @JsonIgnoreProperties("types")
    private Contract contract;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Lookup companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getLookupTypesId() {
        return lookupTypesId;
    }

    public Lookup lookupTypesId(Long lookupTypesId) {
        this.lookupTypesId = lookupTypesId;
        return this;
    }

    public void setLookupTypesId(Long lookupTypesId) {
        this.lookupTypesId = lookupTypesId;
    }

    public String getLookupCode() {
        return lookupCode;
    }

    public Lookup lookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
        return this;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }

    public String getLookupValue() {
        return lookupValue;
    }

    public Lookup lookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
        return this;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }

    public String getFlex1() {
        return flex1;
    }

    public Lookup flex1(String flex1) {
        this.flex1 = flex1;
        return this;
    }

    public void setFlex1(String flex1) {
        this.flex1 = flex1;
    }

    public String getFlex2() {
        return flex2;
    }

    public Lookup flex2(String flex2) {
        this.flex2 = flex2;
        return this;
    }

    public void setFlex2(String flex2) {
        this.flex2 = flex2;
    }

    public String getFlex3() {
        return flex3;
    }

    public Lookup flex3(String flex3) {
        this.flex3 = flex3;
        return this;
    }

    public void setFlex3(String flex3) {
        this.flex3 = flex3;
    }

    public String getFlex4() {
        return flex4;
    }

    public Lookup flex4(String flex4) {
        this.flex4 = flex4;
        return this;
    }

    public void setFlex4(String flex4) {
        this.flex4 = flex4;
    }

    public String getVersion() {
        return version;
    }

    public Lookup version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Contract getContract() {
        return contract;
    }

    public Lookup contract(Contract contract) {
        this.contract = contract;
        return this;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
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
        Lookup lookup = (Lookup) o;
        if (lookup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lookup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lookup{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", lookupTypesId=" + getLookupTypesId() +
            ", lookupCode='" + getLookupCode() + "'" +
            ", lookupValue='" + getLookupValue() + "'" +
            ", flex1='" + getFlex1() + "'" +
            ", flex2='" + getFlex2() + "'" +
            ", flex3='" + getFlex3() + "'" +
            ", flex4='" + getFlex4() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
