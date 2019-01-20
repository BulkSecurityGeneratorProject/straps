package com.neowave.promaly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A InvoiceHeader.
 */
@Entity
@Table(name = "invoice_header")
@Document(indexName = "invoiceheader")
public class InvoiceHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "serviceprovider_id")
    private Long serviceproviderId;

    @Column(name = "invoice_num")
    private Long invoiceNum;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "invoice_status")
    private Long invoiceStatus;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "fees")
    private Double fees;

    @Column(name = "currency")
    private Long currency;

    @Column(name = "payment_terms")
    private Long paymentTerms;

    @Column(name = "payment_status")
    private Long paymentStatus;

    @Column(name = "payment_method")
    private Long paymentMethod;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "payment_ref")
    private String paymentRef;

    @Column(name = "comments")
    private String comments;

    @Column(name = "adhoc")
    private Boolean adhoc;

    @Column(name = "bill_to_company")
    private Long billToCompany;

    @Column(name = "legacy")
    private Boolean legacy;

    @Column(name = "payor")
    private String payor;

    @Column(name = "payment_comments")
    private String paymentComments;

    @Column(name = "email_date")
    private String emailDate;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "key_hash")
    private String keyHash;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public InvoiceHeader planId(Long planId) {
        this.planId = planId;
        return this;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getServiceproviderId() {
        return serviceproviderId;
    }

    public InvoiceHeader serviceproviderId(Long serviceproviderId) {
        this.serviceproviderId = serviceproviderId;
        return this;
    }

    public void setServiceproviderId(Long serviceproviderId) {
        this.serviceproviderId = serviceproviderId;
    }

    public Long getInvoiceNum() {
        return invoiceNum;
    }

    public InvoiceHeader invoiceNum(Long invoiceNum) {
        this.invoiceNum = invoiceNum;
        return this;
    }

    public void setInvoiceNum(Long invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public InvoiceHeader invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getInvoiceStatus() {
        return invoiceStatus;
    }

    public InvoiceHeader invoiceStatus(Long invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
        return this;
    }

    public void setInvoiceStatus(Long invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public InvoiceHeader fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public InvoiceHeader toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Double getAmount() {
        return amount;
    }

    public InvoiceHeader amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFees() {
        return fees;
    }

    public InvoiceHeader fees(Double fees) {
        this.fees = fees;
        return this;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public Long getCurrency() {
        return currency;
    }

    public InvoiceHeader currency(Long currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getPaymentTerms() {
        return paymentTerms;
    }

    public InvoiceHeader paymentTerms(Long paymentTerms) {
        this.paymentTerms = paymentTerms;
        return this;
    }

    public void setPaymentTerms(Long paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public Long getPaymentStatus() {
        return paymentStatus;
    }

    public InvoiceHeader paymentStatus(Long paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(Long paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public InvoiceHeader paymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public InvoiceHeader paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public InvoiceHeader paymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentRef() {
        return paymentRef;
    }

    public InvoiceHeader paymentRef(String paymentRef) {
        this.paymentRef = paymentRef;
        return this;
    }

    public void setPaymentRef(String paymentRef) {
        this.paymentRef = paymentRef;
    }

    public String getComments() {
        return comments;
    }

    public InvoiceHeader comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean isAdhoc() {
        return adhoc;
    }

    public InvoiceHeader adhoc(Boolean adhoc) {
        this.adhoc = adhoc;
        return this;
    }

    public void setAdhoc(Boolean adhoc) {
        this.adhoc = adhoc;
    }

    public Long getBillToCompany() {
        return billToCompany;
    }

    public InvoiceHeader billToCompany(Long billToCompany) {
        this.billToCompany = billToCompany;
        return this;
    }

    public void setBillToCompany(Long billToCompany) {
        this.billToCompany = billToCompany;
    }

    public Boolean isLegacy() {
        return legacy;
    }

    public InvoiceHeader legacy(Boolean legacy) {
        this.legacy = legacy;
        return this;
    }

    public void setLegacy(Boolean legacy) {
        this.legacy = legacy;
    }

    public String getPayor() {
        return payor;
    }

    public InvoiceHeader payor(String payor) {
        this.payor = payor;
        return this;
    }

    public void setPayor(String payor) {
        this.payor = payor;
    }

    public String getPaymentComments() {
        return paymentComments;
    }

    public InvoiceHeader paymentComments(String paymentComments) {
        this.paymentComments = paymentComments;
        return this;
    }

    public void setPaymentComments(String paymentComments) {
        this.paymentComments = paymentComments;
    }

    public String getEmailDate() {
        return emailDate;
    }

    public InvoiceHeader emailDate(String emailDate) {
        this.emailDate = emailDate;
        return this;
    }

    public void setEmailDate(String emailDate) {
        this.emailDate = emailDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public InvoiceHeader emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public InvoiceHeader keyHash(String keyHash) {
        this.keyHash = keyHash;
        return this;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
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
        InvoiceHeader invoiceHeader = (InvoiceHeader) o;
        if (invoiceHeader.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceHeader.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" +
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
