package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InvoiceLine entity.
 */
public class InvoiceLineDTO implements Serializable {

    private Long id;

    private Long lineNum;

    private Long invoiceId;

    private Long planId;

    private Long categoryId;

    private String description;

    private Double amount;

    private Long currency;

    private Double rate;

    private Double quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineNum() {
        return lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvoiceLineDTO invoiceLineDTO = (InvoiceLineDTO) o;
        if (invoiceLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceLineDTO{" +
            "id=" + getId() +
            ", lineNum=" + getLineNum() +
            ", invoiceId=" + getInvoiceId() +
            ", planId=" + getPlanId() +
            ", categoryId=" + getCategoryId() +
            ", description='" + getDescription() + "'" +
            ", amount=" + getAmount() +
            ", currency=" + getCurrency() +
            ", rate=" + getRate() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
