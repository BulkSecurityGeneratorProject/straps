package com.neowave.promaly.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InvoiceHeader entity.
 */
public class InvoiceHeaderDTO implements Serializable {

    private Long id;

    private Long planId;

    private Long serviceproviderId;

    private Long invoiceNum;

    private LocalDate invoiceDate;

    private Long invoiceStatus;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Double amount;

    private Double fees;

    private Long currency;

    private Long paymentTerms;

    private Long paymentStatus;

    private Long paymentMethod;

    private LocalDate paymentDate;

    private Double paymentAmount;

    private String paymentRef;

    private String comments;

    private Boolean adhoc;

    private Long billToCompany;

    private Boolean legacy;

    private String payor;

    private String paymentComments;

    private String emailDate;

    private String emailAddress;

    private String keyHash;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getServiceproviderId() {
        return serviceproviderId;
    }

    public void setServiceproviderId(Long serviceproviderId) {
        this.serviceproviderId = serviceproviderId;
    }

    public Long getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(Long invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Long invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(Long paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public Long getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Long paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentRef() {
        return paymentRef;
    }

    public void setPaymentRef(String paymentRef) {
        this.paymentRef = paymentRef;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean isAdhoc() {
        return adhoc;
    }

    public void setAdhoc(Boolean adhoc) {
        this.adhoc = adhoc;
    }

    public Long getBillToCompany() {
        return billToCompany;
    }

    public void setBillToCompany(Long billToCompany) {
        this.billToCompany = billToCompany;
    }

    public Boolean isLegacy() {
        return legacy;
    }

    public void setLegacy(Boolean legacy) {
        this.legacy = legacy;
    }

    public String getPayor() {
        return payor;
    }

    public void setPayor(String payor) {
        this.payor = payor;
    }

    public String getPaymentComments() {
        return paymentComments;
    }

    public void setPaymentComments(String paymentComments) {
        this.paymentComments = paymentComments;
    }

    public String getEmailDate() {
        return emailDate;
    }

    public void setEmailDate(String emailDate) {
        this.emailDate = emailDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvoiceHeaderDTO invoiceHeaderDTO = (InvoiceHeaderDTO) o;
        if (invoiceHeaderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceHeaderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceHeaderDTO{" +
            "id=" + getId() +
            ", planId=" + getPlanId() +
            ", serviceproviderId=" + getServiceproviderId() +
            ", invoiceNum=" + getInvoiceNum() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", invoiceStatus=" + getInvoiceStatus() +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", amount=" + getAmount() +
            ", fees=" + getFees() +
            ", currency=" + getCurrency() +
            ", paymentTerms=" + getPaymentTerms() +
            ", paymentStatus=" + getPaymentStatus() +
            ", paymentMethod=" + getPaymentMethod() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", paymentRef='" + getPaymentRef() + "'" +
            ", comments='" + getComments() + "'" +
            ", adhoc='" + isAdhoc() + "'" +
            ", billToCompany=" + getBillToCompany() +
            ", legacy='" + isLegacy() + "'" +
            ", payor='" + getPayor() + "'" +
            ", paymentComments='" + getPaymentComments() + "'" +
            ", emailDate='" + getEmailDate() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", keyHash='" + getKeyHash() + "'" +
            "}";
    }
}
