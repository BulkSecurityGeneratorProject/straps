import { element, by, ElementFinder } from 'protractor';

export class ContactComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-contact div table .btn-danger'));
    title = element.all(by.css('jhi-contact div h2#page-heading span')).first();

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

export class ContactUpdatePage {
    pageTitle = element(by.id('jhi-contact-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    firstNameInput = element(by.id('field_firstName'));
    lastNameInput = element(by.id('field_lastName'));
    emailInput = element(by.id('field_email'));
    phonePrimaryInput = element(by.id('field_phonePrimary'));
    phoneSecondaryInput = element(by.id('field_phoneSecondary'));
    contactTypeInput = element(by.id('field_contactType'));
    versionInput = element(by.id('field_version'));
    companySelect = element(by.id('field_company'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setFirstNameInput(firstName) {
        await this.firstNameInput.sendKeys(firstName);
    }

    async getFirstNameInput() {
        return this.firstNameInput.getAttribute('value');
    }

    async setLastNameInput(lastName) {
        await this.lastNameInput.sendKeys(lastName);
    }

    async getLastNameInput() {
        return this.lastNameInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setPhonePrimaryInput(phonePrimary) {
        await this.phonePrimaryInput.sendKeys(phonePrimary);
    }

    async getPhonePrimaryInput() {
        return this.phonePrimaryInput.getAttribute('value');
    }

    async setPhoneSecondaryInput(phoneSecondary) {
        await this.phoneSecondaryInput.sendKeys(phoneSecondary);
    }

    async getPhoneSecondaryInput() {
        return this.phoneSecondaryInput.getAttribute('value');
    }

    async setContactTypeInput(contactType) {
        await this.contactTypeInput.sendKeys(contactType);
    }

    async getContactTypeInput() {
        return this.contactTypeInput.getAttribute('value');
    }

    async setVersionInput(version) {
        await this.versionInput.sendKeys(version);
    }

    async getVersionInput() {
        return this.versionInput.getAttribute('value');
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

export class ContactDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-contact-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-contact'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
