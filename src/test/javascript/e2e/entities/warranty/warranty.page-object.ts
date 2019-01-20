import { element, by, ElementFinder } from 'protractor';

export class WarrantyComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-warranty div table .btn-danger'));
    title = element.all(by.css('jhi-warranty div h2#page-heading span')).first();

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

export class WarrantyUpdatePage {
    pageTitle = element(by.id('jhi-warranty-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    serialNumInput = element(by.id('field_serialNum'));
    warrantyStartDateInput = element(by.id('field_warrantyStartDate'));
    warrantyEndDateInput = element(by.id('field_warrantyEndDate'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setSerialNumInput(serialNum) {
        await this.serialNumInput.sendKeys(serialNum);
    }

    async getSerialNumInput() {
        return this.serialNumInput.getAttribute('value');
    }

    async setWarrantyStartDateInput(warrantyStartDate) {
        await this.warrantyStartDateInput.sendKeys(warrantyStartDate);
    }

    async getWarrantyStartDateInput() {
        return this.warrantyStartDateInput.getAttribute('value');
    }

    async setWarrantyEndDateInput(warrantyEndDate) {
        await this.warrantyEndDateInput.sendKeys(warrantyEndDate);
    }

    async getWarrantyEndDateInput() {
        return this.warrantyEndDateInput.getAttribute('value');
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

export class WarrantyDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-warranty-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-warranty'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
