package com.neowave.promaly.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DocumentStore.
 */
@Entity
@Table(name = "document_store")
@Document(indexName = "documentstore")
public class DocumentStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "path")
    private String path;

    @Column(name = "category")
    private Long category;

    @Column(name = "sub_category")
    private Long subCategory;

    @Column(name = "version")
    private String version;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public DocumentStore entityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public DocumentStore entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPath() {
        return path;
    }

    public DocumentStore path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getCategory() {
        return category;
    }

    public DocumentStore category(Long category) {
        this.category = category;
        return this;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getSubCategory() {
        return subCategory;
    }

    public DocumentStore subCategory(Long subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public void setSubCategory(Long subCategory) {
        this.subCategory = subCategory;
    }

    public String getVersion() {
        return version;
    }

    public DocumentStore version(String version) {
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
        DocumentStore documentStore = (DocumentStore) o;
        if (documentStore.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentStore.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentStore{" +
            "id=" + getId() +
            ", entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", path='" + getPath() + "'" +
            ", category=" + getCategory() +
            ", subCategory=" + getSubCategory() +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
