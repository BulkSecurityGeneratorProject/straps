import { element, by, ElementFinder } from 'protractor';

export class LeaseComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-lease div table .btn-danger'));
    title = element.all(by.css('jhi-lease div h2#page-heading span')).first();

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

export class LeaseUpdatePage {
    pageTitle = element(by.id('jhi-lease-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    leaseSignedDateInput = element(by.id('field_leaseSignedDate'));
    landlordsIdInput = element(by.id('field_landlordsId'));
    landlordAgentInput = element(by.id('field_landlordAgent'));
    tenantsIdInput = element(by.id('field_tenantsId'));
    addressIdInput = element(by.id('field_addressId'));
    numOccupantsInput = element(by.id('field_numOccupants'));
    leaseTermInput = element(by.id('field_leaseTerm'));
    leaseStartDateInput = element(by.id('field_leaseStartDate'));
    leaseEndDateInput = element(by.id('field_leaseEndDate'));
    rentAmountInput = element(by.id('field_rentAmount'));
    rentPeriodInput = element(by.id('field_rentPeriod'));
    totalTermRentInput = element(by.id('field_totalTermRent'));
    rentEscalationPercInput = element(by.id('field_rentEscalationPerc'));
    proRataStartDateInput = element(by.id('field_proRataStartDate'));
    proRataRentAmountInput = element(by.id('field_proRataRentAmount'));
    proRataEndDateInput = element(by.id('field_proRataEndDate'));
    additionalChargesInput = element(by.id('field_additionalCharges'));
    securityDepositInput = element(by.id('field_securityDeposit'));
    petsAllowedInput = element(by.id('field_petsAllowed'));
    petTypeInput = element(by.id('field_petType'));
    petDescriptionInput = element(by.id('field_petDescription'));
    waterInput = element(by.id('field_water'));
    gasInput = element(by.id('field_gas'));
    electricInput = element(by.id('field_electric'));
    otherUtilitiesInput = element(by.id('field_otherUtilities'));
    terminationNoticePeriodInput = element(by.id('field_terminationNoticePeriod'));
    agencyCompanyInput = element(by.id('field_agencyCompany'));
    managementCompanyInput = element(by.id('field_managementCompany'));
    propertyIdInput = element(by.id('field_propertyId'));
    additionalNotesInput = element(by.id('field_additionalNotes'));
    typeSelect = element(by.id('field_type'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLeaseSignedDateInput(leaseSignedDate) {
        await this.leaseSignedDateInput.sendKeys(leaseSignedDate);
    }

    async getLeaseSignedDateInput() {
        return this.leaseSignedDateInput.getAttribute('value');
    }

    async setLandlordsIdInput(landlordsId) {
        await this.landlordsIdInput.sendKeys(landlordsId);
    }

    async getLandlordsIdInput() {
        return this.landlordsIdInput.getAttribute('value');
    }

    async setLandlordAgentInput(landlordAgent) {
        await this.landlordAgentInput.sendKeys(landlordAgent);
    }

    async getLandlordAgentInput() {
        return this.landlordAgentInput.getAttribute('value');
    }

    async setTenantsIdInput(tenantsId) {
        await this.tenantsIdInput.sendKeys(tenantsId);
    }

    async getTenantsIdInput() {
        return this.tenantsIdInput.getAttribute('value');
    }

    async setAddressIdInput(addressId) {
        await this.addressIdInput.sendKeys(addressId);
    }

    async getAddressIdInput() {
        return this.addressIdInput.getAttribute('value');
    }

    async setNumOccupantsInput(numOccupants) {
        await this.numOccupantsInput.sendKeys(numOccupants);
    }

    async getNumOccupantsInput() {
        return this.numOccupantsInput.getAttribute('value');
    }

    async setLeaseTermInput(leaseTerm) {
        await this.leaseTermInput.sendKeys(leaseTerm);
    }

    async getLeaseTermInput() {
        return this.leaseTermInput.getAttribute('value');
    }

    async setLeaseStartDateInput(leaseStartDate) {
        await this.leaseStartDateInput.sendKeys(leaseStartDate);
    }

    async getLeaseStartDateInput() {
        return this.leaseStartDateInput.getAttribute('value');
    }

    async setLeaseEndDateInput(leaseEndDate) {
        await this.leaseEndDateInput.sendKeys(leaseEndDate);
    }

    async getLeaseEndDateInput() {
        return this.leaseEndDateInput.getAttribute('value');
    }

    async setRentAmountInput(rentAmount) {
        await this.rentAmountInput.sendKeys(rentAmount);
    }

    async getRentAmountInput() {
        return this.rentAmountInput.getAttribute('value');
    }

    async setRentPeriodInput(rentPeriod) {
        await this.rentPeriodInput.sendKeys(rentPeriod);
    }

    async getRentPeriodInput() {
        return this.rentPeriodInput.getAttribute('value');
    }

    async setTotalTermRentInput(totalTermRent) {
        await this.totalTermRentInput.sendKeys(totalTermRent);
    }

    async getTotalTermRentInput() {
        return this.totalTermRentInput.getAttribute('value');
    }

    async setRentEscalationPercInput(rentEscalationPerc) {
        await this.rentEscalationPercInput.sendKeys(rentEscalationPerc);
    }

    async getRentEscalationPercInput() {
        return this.rentEscalationPercInput.getAttribute('value');
    }

    async setProRataStartDateInput(proRataStartDate) {
        await this.proRataStartDateInput.sendKeys(proRataStartDate);
    }

    async getProRataStartDateInput() {
        return this.proRataStartDateInput.getAttribute('value');
    }

    async setProRataRentAmountInput(proRataRentAmount) {
        await this.proRataRentAmountInput.sendKeys(proRataRentAmount);
    }

    async getProRataRentAmountInput() {
        return this.proRataRentAmountInput.getAttribute('value');
    }

    async setProRataEndDateInput(proRataEndDate) {
        await this.proRataEndDateInput.sendKeys(proRataEndDate);
    }

    async getProRataEndDateInput() {
        return this.proRataEndDateInput.getAttribute('value');
    }

    async setAdditionalChargesInput(additionalCharges) {
        await this.additionalChargesInput.sendKeys(additionalCharges);
    }

    async getAdditionalChargesInput() {
        return this.additionalChargesInput.getAttribute('value');
    }

    async setSecurityDepositInput(securityDeposit) {
        await this.securityDepositInput.sendKeys(securityDeposit);
    }

    async getSecurityDepositInput() {
        return this.securityDepositInput.getAttribute('value');
    }

    getPetsAllowedInput() {
        return this.petsAllowedInput;
    }
    async setPetTypeInput(petType) {
        await this.petTypeInput.sendKeys(petType);
    }

    async getPetTypeInput() {
        return this.petTypeInput.getAttribute('value');
    }

    async setPetDescriptionInput(petDescription) {
        await this.petDescriptionInput.sendKeys(petDescription);
    }

    async getPetDescriptionInput() {
        return this.petDescriptionInput.getAttribute('value');
    }

    getWaterInput() {
        return this.waterInput;
    }
    getGasInput() {
        return this.gasInput;
    }
    getElectricInput() {
        return this.electricInput;
    }
    async setOtherUtilitiesInput(otherUtilities) {
        await this.otherUtilitiesInput.sendKeys(otherUtilities);
    }

    async getOtherUtilitiesInput() {
        return this.otherUtilitiesInput.getAttribute('value');
    }

    async setTerminationNoticePeriodInput(terminationNoticePeriod) {
        await this.terminationNoticePeriodInput.sendKeys(terminationNoticePeriod);
    }

    async getTerminationNoticePeriodInput() {
        return this.terminationNoticePeriodInput.getAttribute('value');
    }

    async setAgencyCompanyInput(agencyCompany) {
        await this.agencyCompanyInput.sendKeys(agencyCompany);
    }

    async getAgencyCompanyInput() {
        return this.agencyCompanyInput.getAttribute('value');
    }

    async setManagementCompanyInput(managementCompany) {
        await this.managementCompanyInput.sendKeys(managementCompany);
    }

    async getManagementCompanyInput() {
        return this.managementCompanyInput.getAttribute('value');
    }

    async setPropertyIdInput(propertyId) {
        await this.propertyIdInput.sendKeys(propertyId);
    }

    async getPropertyIdInput() {
        return this.propertyIdInput.getAttribute('value');
    }

    async setAdditionalNotesInput(additionalNotes) {
        await this.additionalNotesInput.sendKeys(additionalNotes);
    }

    async getAdditionalNotesInput() {
        return this.additionalNotesInput.getAttribute('value');
    }

    async typeSelectLastOption() {
        await this.typeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async typeSelectOption(option) {
        await this.typeSelect.sendKeys(option);
    }

    getTypeSelect(): ElementFinder {
        return this.typeSelect;
    }

    async getTypeSelectedOption() {
        return this.typeSelect.element(by.css('option:checked')).getText();
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

export class LeaseDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-lease-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-lease'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
