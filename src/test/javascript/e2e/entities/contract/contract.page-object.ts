import { element, by, ElementFinder } from 'protractor';

export class ContractComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-contract div table .btn-danger'));
    title = element.all(by.css('jhi-contract div h2#page-heading span')).first();

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

export class ContractUpdatePage {
    pageTitle = element(by.id('jhi-contract-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    startDateInput = element(by.id('field_startDate'));
    endDateInput = element(by.id('field_endDate'));
    companySelect = element(by.id('field_company'));
    managementCompanySelect = element(by.id('field_managementCompany'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
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

    async managementCompanySelectLastOption() {
        await this.managementCompanySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async managementCompanySelectOption(option) {
        await this.managementCompanySelect.sendKeys(option);
    }

    getManagementCompanySelect(): ElementFinder {
        return this.managementCompanySelect;
    }

    async getManagementCompanySelectedOption() {
        return this.managementCompanySelect.element(by.css('option:checked')).getText();
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

export class ContractDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-contract-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-contract'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
