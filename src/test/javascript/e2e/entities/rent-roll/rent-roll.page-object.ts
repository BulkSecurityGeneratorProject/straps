import { element, by, ElementFinder } from 'protractor';

export class RentRollComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-rent-roll div table .btn-danger'));
    title = element.all(by.css('jhi-rent-roll div h2#page-heading span')).first();

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

export class RentRollUpdatePage {
    pageTitle = element(by.id('jhi-rent-roll-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    securityDepositInput = element(by.id('field_securityDeposit'));
    bankGuaranteeInput = element(by.id('field_bankGuarantee'));
    leaseRecoveryTimingInput = element(by.id('field_leaseRecoveryTiming'));
    propertySelect = element(by.id('field_property'));
    buildingSelect = element(by.id('field_building'));
    propertyUnitSelect = element(by.id('field_propertyUnit'));
    inflationTypeSelect = element(by.id('field_inflationType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setSecurityDepositInput(securityDeposit) {
        await this.securityDepositInput.sendKeys(securityDeposit);
    }

    async getSecurityDepositInput() {
        return this.securityDepositInput.getAttribute('value');
    }

    async setBankGuaranteeInput(bankGuarantee) {
        await this.bankGuaranteeInput.sendKeys(bankGuarantee);
    }

    async getBankGuaranteeInput() {
        return this.bankGuaranteeInput.getAttribute('value');
    }

    async setLeaseRecoveryTimingInput(leaseRecoveryTiming) {
        await this.leaseRecoveryTimingInput.sendKeys(leaseRecoveryTiming);
    }

    async getLeaseRecoveryTimingInput() {
        return this.leaseRecoveryTimingInput.getAttribute('value');
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

    async inflationTypeSelectLastOption() {
        await this.inflationTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async inflationTypeSelectOption(option) {
        await this.inflationTypeSelect.sendKeys(option);
    }

    getInflationTypeSelect(): ElementFinder {
        return this.inflationTypeSelect;
    }

    async getInflationTypeSelectedOption() {
        return this.inflationTypeSelect.element(by.css('option:checked')).getText();
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

export class RentRollDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-rentRoll-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-rentRoll'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
