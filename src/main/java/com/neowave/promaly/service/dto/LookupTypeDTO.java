package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LookupType entity.
 */
public class LookupTypeDTO implements Serializable {

    private Long id;

    private Long companyId;

    private String lookupTypeCode;

    private String lookupTypeValue;

    private String flex1Descr;

    private String flex2Descr;

    private String flex3Descr;

    private String flex4Descr;

    private String version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getLookupTypeCode() {
        return lookupTypeCode;
    }

    public void setLookupTypeCode(String lookupTypeCode) {
        this.lookupTypeCode = lookupTypeCode;
    }

    public String getLookupTypeValue() {
        return lookupTypeValue;
    }

    public void setLookupTypeValue(String lookupTypeValue) {
        this.lookupTypeValue = lookupTypeValue;
    }

    public String getFlex1Descr() {
        return flex1Descr;
    }

    public void setFlex1Descr(String flex1Descr) {
        this.flex1Descr = flex1Descr;
    }

    public String getFlex2Descr() {
        return flex2Descr;
    }

    public void setFlex2Descr(String flex2Descr) {
        this.flex2Descr = flex2Descr;
    }

    public String getFlex3Descr() {
        return flex3Descr;
    }

    public void setFlex3Descr(String flex3Descr) {
        this.flex3Descr = flex3Descr;
    }

    public String getFlex4Descr() {
        return flex4Descr;
    }

    public void setFlex4Descr(String flex4Descr) {
        this.flex4Descr = flex4Descr;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LookupTypeDTO lookupTypeDTO = (LookupTypeDTO) o;
        if (lookupTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lookupTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LookupTypeDTO{" +
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
