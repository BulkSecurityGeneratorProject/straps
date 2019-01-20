package com.neowave.promaly.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DocumentStore entity.
 */
public class DocumentStoreDTO implements Serializable {

    private Long id;

    private Long entityId;

    private String entityName;

    private String path;

    private Long category;

    private Long subCategory;

    private String version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Long subCategory) {
        this.subCategory = subCategory;
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

        DocumentStoreDTO documentStoreDTO = (DocumentStoreDTO) o;
        if (documentStoreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentStoreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentStoreDTO{" +
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
