import { element, by, ElementFinder } from 'protractor';

export class CompanyCapabilityComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-company-capability div table .btn-danger'));
    title = element.all(by.css('jhi-company-capability div h2#page-heading span')).first();

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

export class CompanyCapabilityUpdatePage {
    pageTitle = element(by.id('jhi-company-capability-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    capabilityIdInput = element(by.id('field_capabilityId'));
    licenseInput = element(by.id('field_license'));
    certficationInput = element(by.id('field_certfication'));
    companySelect = element(by.id('field_company'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCapabilityIdInput(capabilityId) {
        await this.capabilityIdInput.sendKeys(capabilityId);
    }

    async getCapabilityIdInput() {
        return this.capabilityIdInput.getAttribute('value');
    }

    async setLicenseInput(license) {
        await this.licenseInput.sendKeys(license);
    }

    async getLicenseInput() {
        return this.licenseInput.getAttribute('value');
    }

    async setCertficationInput(certfication) {
        await this.certficationInput.sendKeys(certfication);
    }

    async getCertficationInput() {
        return this.certficationInput.getAttribute('value');
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

export class CompanyCapabilityDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-companyCapability-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-companyCapability'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
