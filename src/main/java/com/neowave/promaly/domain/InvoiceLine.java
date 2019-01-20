package com.neowave.promaly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A InvoiceLine.
 */
@Entity
@Table(name = "invoice_line")
@Document(indexName = "invoiceline")
public class InvoiceLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_num")
    private Long lineNum;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency")
    private Long currency;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "quantity")
    private Double quantity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineNum() {
        return lineNum;
    }

    public InvoiceLine lineNum(Long lineNum) {
        this.lineNum = lineNum;
        return this;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public InvoiceLine invoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getPlanId() {
        return planId;
    }

    public InvoiceLine planId(Long planId) {
        this.planId = planId;
        return this;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public InvoiceLine categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public InvoiceLine description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public InvoiceLine amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCurrency() {
        return currency;
    }

    public InvoiceLine currency(Long currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public InvoiceLine rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public InvoiceLine quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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
        InvoiceLine invoiceLine = (InvoiceLine) o;
        if (invoiceLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceLine{" +
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
