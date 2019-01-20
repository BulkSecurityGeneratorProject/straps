package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Lookup entity.
 */
public class LookupDTO implements Serializable {

    private Long id;

    private Long companyId;

    private Long lookupTypesId;

    private String lookupCode;

    private String lookupValue;

    private String flex1;

    private String flex2;

    private String flex3;

    private String flex4;

    private String version;

    private Long contractId;

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

    public Long getLookupTypesId() {
        return lookupTypesId;
    }

    public void setLookupTypesId(Long lookupTypesId) {
        this.lookupTypesId = lookupTypesId;
    }

    public String getLookupCode() {
        return lookupCode;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }

    public String getLookupValue() {
        return lookupValue;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }

    public String getFlex1() {
        return flex1;
    }

    public void setFlex1(String flex1) {
        this.flex1 = flex1;
    }

    public String getFlex2() {
        return flex2;
    }

    public void setFlex2(String flex2) {
        this.flex2 = flex2;
    }

    public String getFlex3() {
        return flex3;
    }

    public void setFlex3(String flex3) {
        this.flex3 = flex3;
    }

    public String getFlex4() {
        return flex4;
    }

    public void setFlex4(String flex4) {
        this.flex4 = flex4;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LookupDTO lookupDTO = (LookupDTO) o;
        if (lookupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lookupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LookupDTO{" +
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
            ", contract=" + getContractId() +
            "}";
    }
}
