package com.neowave.promaly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LookupType.
 */
@Entity
@Table(name = "lookup_type")
@Document(indexName = "lookuptype")
public class LookupType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "lookup_type_code")
    private String lookupTypeCode;

    @Column(name = "lookup_type_value")
    private String lookupTypeValue;

    @Column(name = "flex_1_descr")
    private String flex1Descr;

    @Column(name = "flex_2_descr")
    private String flex2Descr;

    @Column(name = "flex_3_descr")
    private String flex3Descr;

    @Column(name = "flex_4_descr")
    private String flex4Descr;

    @Column(name = "version")
    private String version;

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

    public LookupType companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getLookupTypeCode() {
        return lookupTypeCode;
    }

    public LookupType lookupTypeCode(String lookupTypeCode) {
        this.lookupTypeCode = lookupTypeCode;
        return this;
    }

    public void setLookupTypeCode(String lookupTypeCode) {
        this.lookupTypeCode = lookupTypeCode;
    }

    public String getLookupTypeValue() {
        return lookupTypeValue;
    }

    public LookupType lookupTypeValue(String lookupTypeValue) {
        this.lookupTypeValue = lookupTypeValue;
        return this;
    }

    public void setLookupTypeValue(String lookupTypeValue) {
        this.lookupTypeValue = lookupTypeValue;
    }

    public String getFlex1Descr() {
        return flex1Descr;
    }

    public LookupType flex1Descr(String flex1Descr) {
        this.flex1Descr = flex1Descr;
        return this;
    }

    public void setFlex1Descr(String flex1Descr) {
        this.flex1Descr = flex1Descr;
    }

    public String getFlex2Descr() {
        return flex2Descr;
    }

    public LookupType flex2Descr(String flex2Descr) {
        this.flex2Descr = flex2Descr;
        return this;
    }

    public void setFlex2Descr(String flex2Descr) {
        this.flex2Descr = flex2Descr;
    }

    public String getFlex3Descr() {
        return flex3Descr;
    }

    public LookupType flex3Descr(String flex3Descr) {
        this.flex3Descr = flex3Descr;
        return this;
    }

    public void setFlex3Descr(String flex3Descr) {
        this.flex3Descr = flex3Descr;
    }

    public String getFlex4Descr() {
        return flex4Descr;
    }

    public LookupType flex4Descr(String flex4Descr) {
        this.flex4Descr = flex4Descr;
        return this;
    }

    public void setFlex4Descr(String flex4Descr) {
        this.flex4Descr = flex4Descr;
    }

    public String getVersion() {
        return version;
    }

    public LookupType version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
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
        LookupType lookupType = (LookupType) o;
        if (lookupType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lookupType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LookupType{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", lookupTypeCode='" + getLookupTypeCode() + "'" +
            ", lookupTypeValue='" + getLookupTypeValue() + "'" +
            ", flex1Descr='" + getFlex1Descr() + "'" +
            ", flex2Descr='" + getFlex2Descr() + "'" +
            ", flex3Descr='" + getFlex3Descr() + "'" +
            ", flex4Descr='" + getFlex4Descr() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
