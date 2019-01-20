import { element, by, ElementFinder } from 'protractor';

export class BuildingComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-building div table .btn-danger'));
    title = element.all(by.css('jhi-building div h2#page-heading span')).first();

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

export class BuildingUpdatePage {
    pageTitle = element(by.id('jhi-building-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    yearBuiltInput = element(by.id('field_yearBuilt'));
    noOfloorsInput = element(by.id('field_noOfloors'));
    buildingSizeInput = element(by.id('field_buildingSize'));
    coveredAreaInput = element(by.id('field_coveredArea'));
    landAreaInput = element(by.id('field_landArea'));
    noOfRentalUnitInput = element(by.id('field_noOfRentalUnit'));
    parkingSpacesInput = element(by.id('field_parkingSpaces'));
    totalSpaceAvaliableInput = element(by.id('field_totalSpaceAvaliable'));
    totalUnitLevelInput = element(by.id('field_totalUnitLevel'));
    currentRentPerSqftInput = element(by.id('field_currentRentPerSqft'));
    descriptionInput = element(by.id('field_description'));
    versionInput = element(by.id('field_version'));
    propertySelect = element(by.id('field_property'));
    assetTypeSelect = element(by.id('field_assetType'));
    ownerSelect = element(by.id('field_owner'));
    companySelect = element(by.id('field_company'));
    leaseSelect = element(by.id('field_lease'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setYearBuiltInput(yearBuilt) {
        await this.yearBuiltInput.sendKeys(yearBuilt);
    }

    async getYearBuiltInput() {
        return this.yearBuiltInput.getAttribute('value');
    }

    async setNoOfloorsInput(noOfloors) {
        await this.noOfloorsInput.sendKeys(noOfloors);
    }

    async getNoOfloorsInput() {
        return this.noOfloorsInput.getAttribute('value');
    }

    async setBuildingSizeInput(buildingSize) {
        await this.buildingSizeInput.sendKeys(buildingSize);
    }

    async getBuildingSizeInput() {
        return this.buildingSizeInput.getAttribute('value');
    }

    async setCoveredAreaInput(coveredArea) {
        await this.coveredAreaInput.sendKeys(coveredArea);
    }

    async getCoveredAreaInput() {
        return this.coveredAreaInput.getAttribute('value');
    }

    async setLandAreaInput(landArea) {
        await this.landAreaInput.sendKeys(landArea);
    }

    async getLandAreaInput() {
        return this.landAreaInput.getAttribute('value');
    }

    async setNoOfRentalUnitInput(noOfRentalUnit) {
        await this.noOfRentalUnitInput.sendKeys(noOfRentalUnit);
    }

    async getNoOfRentalUnitInput() {
        return this.noOfRentalUnitInput.getAttribute('value');
    }

    async setParkingSpacesInput(parkingSpaces) {
        await this.parkingSpacesInput.sendKeys(parkingSpaces);
    }

    async getParkingSpacesInput() {
        return this.parkingSpacesInput.getAttribute('value');
    }

    async setTotalSpaceAvaliableInput(totalSpaceAvaliable) {
        await this.totalSpaceAvaliableInput.sendKeys(totalSpaceAvaliable);
    }

    async getTotalSpaceAvaliableInput() {
        return this.totalSpaceAvaliableInput.getAttribute('value');
    }

    async setTotalUnitLevelInput(totalUnitLevel) {
        await this.totalUnitLevelInput.sendKeys(totalUnitLevel);
    }

    async getTotalUnitLevelInput() {
        return this.totalUnitLevelInput.getAttribute('value');
    }

    async setCurrentRentPerSqftInput(currentRentPerSqft) {
        await this.currentRentPerSqftInput.sendKeys(currentRentPerSqft);
    }

    async getCurrentRentPerSqftInput() {
        return this.currentRentPerSqftInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setVersionInput(version) {
        await this.versionInput.sendKeys(version);
    }

    async getVersionInput() {
        return this.versionInput.getAttribute('value');
    }

    async propertySelectLastOption() {
        await this.propertySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async propertySelectOption(option) {
        await this.propertySelect.sendKeys(option);
    }

    getPropertySelect(): ElementFinder {
        return this.propertySelect;
    }

    async getPropertySelectedOption() {
        return this.propertySelect.element(by.css('option:checked')).getText();
    }

    async assetTypeSelectLastOption() {
        await this.assetTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async assetTypeSelectOption(option) {
        await this.assetTypeSelect.sendKeys(option);
    }

    getAssetTypeSelect(): ElementFinder {
        return this.assetTypeSelect;
    }

    async getAssetTypeSelectedOption() {
        return this.assetTypeSelect.element(by.css('option:checked')).getText();
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

    async companySelectLastOption() {
        await this.companySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async companySelectOption(option) {
        await this.companySelect.sendKeys(option);
    }

    getCompanySelect(): ElementFinder {
        return this.companySelect;
    }

    async getCompanySelectedOption() {
        return this.companySelect.element(by.css('option:checked')).getText();
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

export class BuildingDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-building-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-building'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
