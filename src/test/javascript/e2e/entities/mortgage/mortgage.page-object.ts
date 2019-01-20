import { element, by, ElementFinder } from 'protractor';

export class MortgageComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-mortgage div table .btn-danger'));
    title = element.all(by.css('jhi-mortgage div h2#page-heading span')).first();

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

export class MortgageUpdatePage {
    pageTitle = element(by.id('jhi-mortgage-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    companyIdInput = element(by.id('field_companyId'));
    propertyUnitsIdInput = element(by.id('field_propertyUnitsId'));
    borrowerInput = element(by.id('field_borrower'));
    lenderInput = element(by.id('field_lender'));
    startDateInput = element(by.id('field_startDate'));
    endDateInput = element(by.id('field_endDate'));
    amountInput = element(by.id('field_amount'));
    interestRateInput = element(by.id('field_interestRate'));
    balloonPaymentInput = element(by.id('field_balloonPayment'));
    refinanceOptionInput = element(by.id('field_refinanceOption'));
    amortizationLengthYrsInput = element(by.id('field_amortizationLengthYrs'));
    versionInput = element(by.id('field_version'));
    propertySelect = element(by.id('field_property'));
    buildingSelect = element(by.id('field_building'));
    propertyUnitSelect = element(by.id('field_propertyUnit'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCompanyIdInput(companyId) {
        await this.companyIdInput.sendKeys(companyId);
    }

    async getCompanyIdInput() {
        return this.companyIdInput.getAttribute('value');
    }

    async setPropertyUnitsIdInput(propertyUnitsId) {
        await this.propertyUnitsIdInput.sendKeys(propertyUnitsId);
    }

    async getPropertyUnitsIdInput() {
        return this.propertyUnitsIdInput.getAttribute('value');
    }

    async setBorrowerInput(borrower) {
        await this.borrowerInput.sendKeys(borrower);
    }

    async getBorrowerInput() {
        return this.borrowerInput.getAttribute('value');
    }

    async setLenderInput(lender) {
        await this.lenderInput.sendKeys(lender);
    }

    async getLenderInput() {
        return this.lenderInput.getAttribute('value');
    }

    async setStartDateInput(startDate) {
        await this.startDateInput.sendKeys(startDate);
    }

    async getStartDateInput() {
        return this.startDateInput.getAttribute('value');
    }

    async setEndDateInput(endDate) {
        await this.endDateInput.sendKeys(endDate);
    }

    async getEndDateInput() {
        return this.endDateInput.getAttribute('value');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setInterestRateInput(interestRate) {
        await this.interestRateInput.sendKeys(interestRate);
    }

    async getInterestRateInput() {
        return this.interestRateInput.getAttribute('value');
    }

    async setBalloonPaymentInput(balloonPayment) {
        await this.balloonPaymentInput.sendKeys(balloonPayment);
    }

    async getBalloonPaymentInput() {
        return this.balloonPaymentInput.getAttribute('value');
    }

    getRefinanceOptionInput() {
        return this.refinanceOptionInput;
    }
    async setAmortizationLengthYrsInput(amortizationLengthYrs) {
        await this.amortizationLengthYrsInput.sendKeys(amortizationLengthYrs);
    }

    async getAmortizationLengthYrsInput() {
        return this.amortizationLengthYrsInput.getAttribute('value');
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

    async propertyUnitSelectLastOption() {
        await this.propertyUnitSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async propertyUnitSelectOption(option) {
        await this.propertyUnitSelect.sendKeys(option);
    }

    getPropertyUnitSelect(): ElementFinder {
        return this.propertyUnitSelect;
    }

    async getPropertyUnitSelectedOption() {
        return this.propertyUnitSelect.element(by.css('option:checked')).getText();
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

export class MortgageDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-mortgage-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-mortgage'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
