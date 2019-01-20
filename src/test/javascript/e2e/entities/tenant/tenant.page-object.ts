import { element, by, ElementFinder } from 'protractor';

export class TenantComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-tenant div table .btn-danger'));
    title = element.all(by.css('jhi-tenant div h2#page-heading span')).first();

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

export class TenantUpdatePage {
    pageTitle = element(by.id('jhi-tenant-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    leaseIdInput = element(by.id('field_leaseId'));
    contactInput = element(by.id('field_contact'));
    propertyUnitSelect = element(by.id('field_propertyUnit'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLeaseIdInput(leaseId) {
        await this.leaseIdInput.sendKeys(leaseId);
    }

    async getLeaseIdInput() {
        return this.leaseIdInput.getAttribute('value');
    }

    async setContactInput(contact) {
        await this.contactInput.sendKeys(contact);
    }

    async getContactInput() {
        return this.contactInput.getAttribute('value');
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

export class TenantDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-tenant-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-tenant'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
