package com.neowave.promaly.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PropertyUnit entity.
 */
public class PropertyUnitDTO implements Serializable {

    private Long id;

    private String unitNo;

    private String description;

    private Long floors;

    private Double netArea;

    private Double grossArea;

    private Boolean residential;

    private Integer totalRooms;

    private Integer noOfBrs;

    private Integer noOfFb;

    private Integer noOfHb;

    private String propertyMailboxNum;

    private String propertyParkingLotNum;

    private Boolean gasHeating;

    private Long whoPaysHeating;

    private Boolean electric;

    private Long whoPaysElectric;

    private Boolean water;

    private Long whoPaysWater;

    private LocalDate lastRenovated;

    private Double currentRentPerSqft;

    private Long version;

    private Long buildingId;

    private Long addressId;

    private Long usageTypeId;

    private Long statusId;

    private Long mortgageId;

    private Long rentRollId;

    private Set<LeaseDTO> leases = new HashSet<>();

    private Set<LandLordDTO> owners = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFloors() {
        return floors;
    }

    public void setFloors(Long floors) {
        this.floors = floors;
    }

    public Double getNetArea() {
        return netArea;
    }

    public void setNetArea(Double netArea) {
        this.netArea = netArea;
    }

    public Double getGrossArea() {
        return grossArea;
    }

    public void setGrossArea(Double grossArea) {
        this.grossArea = grossArea;
    }

    public Boolean isResidential() {
        return residential;
    }

    public void setResidential(Boolean residential) {
        this.residential = residential;
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
    }

    public Integer getNoOfBrs() {
        return noOfBrs;
    }

    public void setNoOfBrs(Integer noOfBrs) {
        this.noOfBrs = noOfBrs;
    }

    public Integer getNoOfFb() {
        return noOfFb;
    }

    public void setNoOfFb(Integer noOfFb) {
        this.noOfFb = noOfFb;
    }

    public Integer getNoOfHb() {
        return noOfHb;
    }

    public void setNoOfHb(Integer noOfHb) {
        this.noOfHb = noOfHb;
    }

    public String getPropertyMailboxNum() {
        return propertyMailboxNum;
    }

    public void setPropertyMailboxNum(String propertyMailboxNum) {
        this.propertyMailboxNum = propertyMailboxNum;
    }

    public String getPropertyParkingLotNum() {
        return propertyParkingLotNum;
    }

    public void setPropertyParkingLotNum(String propertyParkingLotNum) {
        this.propertyParkingLotNum = propertyParkingLotNum;
    }

    public Boolean isGasHeating() {
        return gasHeating;
    }

    public void setGasHeating(Boolean gasHeating) {
        this.gasHeating = gasHeating;
    }

    public Long getWhoPaysHeating() {
        return whoPaysHeating;
    }

    public void setWhoPaysHeating(Long whoPaysHeating) {
        this.whoPaysHeating = whoPaysHeating;
    }

    public Boolean isElectric() {
        return electric;
    }

    public void setElectric(Boolean electric) {
        this.electric = electric;
    }

    public Long getWhoPaysElectric() {
        return whoPaysElectric;
    }

    public void setWhoPaysElectric(Long whoPaysElectric) {
        this.whoPaysElectric = whoPaysElectric;
    }

    public Boolean isWater() {
        return water;
    }

    public void setWater(Boolean water) {
        this.water = water;
    }

    public Long getWhoPaysWater() {
        return whoPaysWater;
    }

    public void setWhoPaysWater(Long whoPaysWater) {
        this.whoPaysWater = whoPaysWater;
    }

    public LocalDate getLastRenovated() {
        return lastRenovated;
    }

    public void setLastRenovated(LocalDate lastRenovated) {
        this.lastRenovated = lastRenovated;
    }

    public Double getCurrentRentPerSqft() {
        return currentRentPerSqft;
    }

    public void setCurrentRentPerSqft(Double currentRentPerSqft) {
        this.currentRentPerSqft = currentRentPerSqft;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getUsageTypeId() {
        return usageTypeId;
    }

    public void setUsageTypeId(Long lookupId) {
        this.usageTypeId = lookupId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long lookupId) {
        this.statusId = lookupId;
    }

    public Long getMortgageId() {
        return mortgageId;
    }

    public void setMortgageId(Long mortgageId) {
        this.mortgageId = mortgageId;
    }

    public Long getRentRollId() {
        return rentRollId;
    }

    public void setRentRollId(Long rentRollId) {
        this.rentRollId = rentRollId;
    }

    public Set<LeaseDTO> getLeases() {
        return leases;
    }

    public void setLeases(Set<LeaseDTO> leases) {
        this.leases = leases;
    }

    public Set<LandLordDTO> getOwners() {
        return owners;
    }

    public void setOwners(Set<LandLordDTO> landLords) {
        this.owners = landLords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropertyUnitDTO propertyUnitDTO = (PropertyUnitDTO) o;
        if (propertyUnitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertyUnitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertyUnitDTO{" +
            "id=" + getId() +
            ", unitNo='" + getUnitNo() + "'" +
            ", description='" + getDescription() + "'" +
            ", floors=" + getFloors() +
            ", netArea=" + getNetArea() +
            ", grossArea=" + getGrossArea() +
            ", residential='" + isResidential() + "'" +
            ", totalRooms=" + getTotalRooms() +
            ", noOfBrs=" + getNoOfBrs() +
            ", noOfFb=" + getNoOfFb() +
            ", noOfHb=" + getNoOfHb() +
            ", propertyMailboxNum='" + getPropertyMailboxNum() + "'" +
            ", propertyParkingLotNum='" + getPropertyParkingLotNum() + "'" +
            ", gasHeating='" + isGasHeating() + "'" +
            ", whoPaysHeating=" + getWhoPaysHeating() +
            ", electric='" + isElectric() + "'" +
            ", whoPaysElectric=" + getWhoPaysElectric() +
            ", water='" + isWater() + "'" +
            ", whoPaysWater=" + getWhoPaysWater() +
            ", lastRenovated='" + getLastRenovated() + "'" +
            ", currentRentPerSqft=" + getCurrentRentPerSqft() +
            ", version=" + getVersion() +
            ", building=" + getBuildingId() +
            ", address=" + getAddressId() +
            ", usageType=" + getUsageTypeId() +
            ", status=" + getStatusId() +
            ", mortgage=" + getMortgageId() +
            ", rentRoll=" + getRentRollId() +
            "}";
    }
}
