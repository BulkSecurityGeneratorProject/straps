package com.neowave.promaly.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Building entity.
 */
public class BuildingDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate yearBuilt;

    private Long noOfloors;

    private Double buildingSize;

    private Double coveredArea;

    private Double landArea;

    private Long noOfRentalUnit;

    private Long parkingSpaces;

    private Double totalSpaceAvaliable;

    private Long totalUnitLevel;

    private Double currentRentPerSqft;

    private String description;

    private String version;

    private Long propertyId;

    private Long assetTypeId;

    private Set<LandLordDTO> owners = new HashSet<>();

    private Set<CompanyDTO> companies = new HashSet<>();

    private Set<LeaseDTO> leases = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(LocalDate yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public Long getNoOfloors() {
        return noOfloors;
    }

    public void setNoOfloors(Long noOfloors) {
        this.noOfloors = noOfloors;
    }

    public Double getBuildingSize() {
        return buildingSize;
    }

    public void setBuildingSize(Double buildingSize) {
        this.buildingSize = buildingSize;
    }

    public Double getCoveredArea() {
        return coveredArea;
    }

    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
    }

    public Double getLandArea() {
        return landArea;
    }

    public void setLandArea(Double landArea) {
        this.landArea = landArea;
    }

    public Long getNoOfRentalUnit() {
        return noOfRentalUnit;
    }

    public void setNoOfRentalUnit(Long noOfRentalUnit) {
        this.noOfRentalUnit = noOfRentalUnit;
    }

    public Long getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(Long parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public Double getTotalSpaceAvaliable() {
        return totalSpaceAvaliable;
    }

    public void setTotalSpaceAvaliable(Double totalSpaceAvaliable) {
        this.totalSpaceAvaliable = totalSpaceAvaliable;
    }

    public Long getTotalUnitLevel() {
        return totalUnitLevel;
    }

    public void setTotalUnitLevel(Long totalUnitLevel) {
        this.totalUnitLevel = totalUnitLevel;
    }

    public Double getCurrentRentPerSqft() {
        return currentRentPerSqft;
    }

    public void setCurrentRentPerSqft(Double currentRentPerSqft) {
        this.currentRentPerSqft = currentRentPerSqft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(Long lookupId) {
        this.assetTypeId = lookupId;
    }

    public Set<LandLordDTO> getOwners() {
        return owners;
    }

    public void setOwners(Set<LandLordDTO> landLords) {
        this.owners = landLords;
    }

    public Set<CompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<CompanyDTO> companies) {
        this.companies = companies;
    }

    public Set<LeaseDTO> getLeases() {
        return leases;
    }

    public void setLeases(Set<LeaseDTO> leases) {
        this.leases = leases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingDTO buildingDTO = (BuildingDTO) o;
        if (buildingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", yearBuilt='" + getYearBuilt() + "'" +
            ", noOfloors=" + getNoOfloors() +
            ", buildingSize=" + getBuildingSize() +
            ", coveredArea=" + getCoveredArea() +
            ", landArea=" + getLandArea() +
            ", noOfRentalUnit=" + getNoOfRentalUnit() +
            ", parkingSpaces=" + getParkingSpaces() +
            ", totalSpaceAvaliable=" + getTotalSpaceAvaliable() +
            ", totalUnitLevel=" + getTotalUnitLevel() +
            ", currentRentPerSqft=" + getCurrentRentPerSqft() +
            ", description='" + getDescription() + "'" +
            ", version='" + getVersion() + "'" +
            ", property=" + getPropertyId() +
            ", assetType=" + getAssetTypeId() +
            "}";
    }
}
