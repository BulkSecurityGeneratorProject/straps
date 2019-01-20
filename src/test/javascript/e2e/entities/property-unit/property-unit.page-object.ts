import { element, by, ElementFinder } from 'protractor';

export class PropertyUnitComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-property-unit div table .btn-danger'));
    title = element.all(by.css('jhi-property-unit div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PropertyUnitUpdatePage {
    pageTitle = element(by.id('jhi-property-unit-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    unitNoInput = element(by.id('field_unitNo'));
    descriptionInput = element(by.id('field_description'));
    floorsInput = element(by.id('field_floors'));
    netAreaInput = element(by.id('field_netArea'));
    grossAreaInput = element(by.id('field_grossArea'));
    residentialInput = element(by.id('field_residential'));
    totalRoomsInput = element(by.id('field_totalRooms'));
    noOfBrsInput = element(by.id('field_noOfBrs'));
    noOfFbInput = element(by.id('field_noOfFb'));
    noOfHbInput = element(by.id('field_noOfHb'));
    propertyMailboxNumInput = element(by.id('field_propertyMailboxNum'));
    propertyParkingLotNumInput = element(by.id('field_propertyParkingLotNum'));
    gasHeatingInput = element(by.id('field_gasHeating'));
    whoPaysHeatingInput = element(by.id('field_whoPaysHeating'));
    electricInput = element(by.id('field_electric'));
    whoPaysElectricInput = element(by.id('field_whoPaysElectric'));
    waterInput = element(by.id('field_water'));
    whoPaysWaterInput = element(by.id('field_whoPaysWater'));
    lastRenovatedInput = element(by.id('field_lastRenovated'));
    currentRentPerSqftInput = element(by.id('field_currentRentPerSqft'));
    versionInput = element(by.id('field_version'));
    buildingSelect = element(by.id('field_building'));
    addressSelect = element(by.id('field_address'));
    usageTypeSelect = element(by.id('field_usageType'));
    statusSelect = element(by.id('field_status'));
    mortgageSelect = element(by.id('field_mortgage'));
    rentRollSelect = element(by.id('field_rentRoll'));
    leaseSelect = element(by.id('field_lease'));
    ownerSelect = element(by.id('field_owner'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setUnitNoInput(unitNo) {
        await this.unitNoInput.sendKeys(unitNo);
    }

    async getUnitNoInput() {
        return this.unitNoInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setFloorsInput(floors) {
        await this.floorsInput.sendKeys(floors);
    }

    async getFloorsInput() {
        return this.floorsInput.getAttribute('value');
    }

    async setNetAreaInput(netArea) {
        await this.netAreaInput.sendKeys(netArea);
    }

    async getNetAreaInput() {
        return this.netAreaInput.getAttribute('value');
    }

    async setGrossAreaInput(grossArea) {
        await this.grossAreaInput.sendKeys(grossArea);
    }

    async getGrossAreaInput() {
        return this.grossAreaInput.getAttribute('value');
    }

    getResidentialInput() {
        return this.residentialInput;
    }
    async setTotalRoomsInput(totalRooms) {
        await this.totalRoomsInput.sendKeys(totalRooms);
    }

    async getTotalRoomsInput() {
        return this.totalRoomsInput.getAttribute('value');
    }

    async setNoOfBrsInput(noOfBrs) {
        await this.noOfBrsInput.sendKeys(noOfBrs);
    }

    async getNoOfBrsInput() {
        return this.noOfBrsInput.getAttribute('value');
    }

    async setNoOfFbInput(noOfFb) {
        await this.noOfFbInput.sendKeys(noOfFb);
    }

    async getNoOfFbInput() {
        return this.noOfFbInput.getAttribute('value');
    }

    async setNoOfHbInput(noOfHb) {
        await this.noOfHbInput.sendKeys(noOfHb);
    }

    async getNoOfHbInput() {
        return this.noOfHbInput.getAttribute('value');
    }

    async setPropertyMailboxNumInput(propertyMailboxNum) {
        await this.propertyMailboxNumInput.sendKeys(propertyMailboxNum);
    }

    async getPropertyMailboxNumInput() {
        return this.propertyMailboxNumInput.getAttribute('value');
    }

    async setPropertyParkingLotNumInput(propertyParkingLotNum) {
        await this.propertyParkingLotNumInput.sendKeys(propertyParkingLotNum);
    }

    async getPropertyParkingLotNumInput() {
        return this.propertyParkingLotNumInput.getAttribute('value');
    }

    getGasHeatingInput() {
        return this.gasHeatingInput;
    }
    async setWhoPaysHeatingInput(whoPaysHeating) {
        await this.whoPaysHeatingInput.sendKeys(whoPaysHeating);
    }

    async getWhoPaysHeatingInput() {
        return this.whoPaysHeatingInput.getAttribute('value');
    }

    getElectricInput() {
        return this.electricInput;
    }
    async setWhoPaysElectricInput(whoPaysElectric) {
        await this.whoPaysElectricInput.sendKeys(whoPaysElectric);
    }

    async getWhoPaysElectricInput() {
        return this.whoPaysElectricInput.getAttribute('value');
    }

    getWaterInput() {
        return this.waterInput;
    }
    async setWhoPaysWaterInput(whoPaysWater) {
        await this.whoPaysWaterInput.sendKeys(whoPaysWater);
    }

    async getWhoPaysWaterInput() {
        return this.whoPaysWaterInput.getAttribute('value');
    }

    async setLastRenovatedInput(lastRenovated) {
        await this.lastRenovatedInput.sendKeys(lastRenovated);
    }

    async getLastRenovatedInput() {
        return this.lastRenovatedInput.getAttribute('value');
    }

    async setCurrentRentPerSqftInput(currentRentPerSqft) {
        await this.currentRentPerSqftInput.sendKeys(currentRentPerSqft);
    }

    async getCurrentRentPerSqftInput() {
        return this.currentRentPerSqftInput.getAttribute('value');
    }

    async setVersionInput(version) {
        await this.versionInput.sendKeys(version);
    }

    async getVersionInput() {
        return this.versionInput.getAttribute('value');
    }

    async buildingSelectLastOption() {
        await this.buildingSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async buildingSelectOption(option) {
        await this.buildingSelect.sendKeys(option);
    }

    getBuildingSelect(): ElementFinder {
        return this.buildingSelect;
    }

    async getBuildingSelectedOption() {
        return this.buildingSelect.element(by.css('option:checked')).getText();
    }

    async addressSelectLastOption() {
        await this.addressSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async addressSelectOption(option) {
        await this.addressSelect.sendKeys(option);
    }

    getAddressSelect(): ElementFinder {
        return this.addressSelect;
    }

    async getAddressSelectedOption() {
        return this.addressSelect.element(by.css('option:checked')).getText();
    }

    async usageTypeSelectLastOption() {
        await this.usageTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async usageTypeSelectOption(option) {
        await this.usageTypeSelect.sendKeys(option);
    }

    getUsageTypeSelect(): ElementFinder {
        return this.usageTypeSelect;
    }

    async getUsageTypeSelectedOption() {
        return this.usageTypeSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async statusSelectOption(option) {
        await this.statusSelect.sendKeys(option);
    }

    getStatusSelect(): ElementFinder {
        return this.statusSelect;
    }

    async getStatusSelectedOption() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async mortgageSelectLastOption() {
        await this.mortgageSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async mortgageSelectOption(option) {
        await this.mortgageSelect.sendKeys(option);
    }

    getMortgageSelect(): ElementFinder {
        return this.mortgageSelect;
    }

    async getMortgageSelectedOption() {
        return this.mortgageSelect.element(by.css('option:checked')).getText();
    }

    async rentRollSelectLastOption() {
        await this.rentRollSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async rentRollSelectOption(option) {
        await this.rentRollSelect.sendKeys(option);
    }

    getRentRollSelect(): ElementFinder {
        return this.rentRollSelect;
    }

    async getRentRollSelectedOption() {
        return this.rentRollSelect.element(by.css('option:checked')).getText();
    }

    async leaseSelectLastOption() {
        await this.leaseSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async leaseSelectOption(option) {
        await this.leaseSelect.sendKeys(option);
    }

    getLeaseSelect(): ElementFinder {
        return this.leaseSelect;
    }

    async getLeaseSelectedOption() {
        return this.leaseSelect.element(by.css('option:checked')).getText();
    }

    async ownerSelectLastOption() {
        await this.ownerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async ownerSelectOption(option) {
        await this.ownerSelect.sendKeys(option);
    }

    getOwnerSelect(): ElementFinder {
        return this.ownerSelect;
    }

    async getOwnerSelectedOption() {
        return this.ownerSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class PropertyUnitDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-propertyUnit-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-propertyUnit'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
