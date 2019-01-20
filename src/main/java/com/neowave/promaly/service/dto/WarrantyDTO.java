package com.neowave.promaly.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Warranty entity.
 */
public class WarrantyDTO implements Serializable {

    private Long id;

    private String description;

    private String serialNum;

    private LocalDate warrantyStartDate;

    private LocalDate warrantyEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public LocalDate getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(LocalDate warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    public LocalDate getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(LocalDate warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WarrantyDTO warrantyDTO = (WarrantyDTO) o;
        if (warrantyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), warrantyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WarrantyDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", serialNum='" + getSerialNum() + "'" +
            ", warrantyStartDate='" + getWarrantyStartDate() + "'" +
            ", warrantyEndDate='" + getWarrantyEndDate() + "'" +
            "}";
    }
}
