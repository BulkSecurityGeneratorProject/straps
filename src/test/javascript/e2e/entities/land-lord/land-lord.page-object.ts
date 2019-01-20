import { element, by, ElementFinder } from 'protractor';

export class LandLordComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-land-lord div table .btn-danger'));
    title = element.all(by.css('jhi-land-lord div h2#page-heading span')).first();

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

export class LandLordUpdatePage {
    pageTitle = element(by.id('jhi-land-lord-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    contactIdInput = element(by.id('field_contactId'));
    percentageOwnershipInput = element(by.id('field_percentageOwnership'));
    ownershipTypeInput = element(by.id('field_ownershipType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setContactIdInput(contactId) {
        await this.contactIdInput.sendKeys(contactId);
    }

    async getContactIdInput() {
        return this.contactIdInput.getAttribute('value');
    }

    async setPercentageOwnershipInput(percentageOwnership) {
        await this.percentageOwnershipInput.sendKeys(percentageOwnership);
    }

    async getPercentageOwnershipInput() {
        return this.percentageOwnershipInput.getAttribute('value');
    }

    async setOwnershipTypeInput(ownershipType) {
        await this.ownershipTypeInput.sendKeys(ownershipType);
    }

    async getOwnershipTypeInput() {
        return this.ownershipTypeInput.getAttribute('value');
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

export class LandLordDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-landLord-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-landLord'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
