import { element, by, ElementFinder } from 'protractor';

export class LookupComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-lookup div table .btn-danger'));
    title = element.all(by.css('jhi-lookup div h2#page-heading span')).first();

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

export class LookupUpdatePage {
    pageTitle = element(by.id('jhi-lookup-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    companyIdInput = element(by.id('field_companyId'));
    lookupTypesIdInput = element(by.id('field_lookupTypesId'));
    lookupCodeInput = element(by.id('field_lookupCode'));
    lookupValueInput = element(by.id('field_lookupValue'));
    flex1Input = element(by.id('field_flex1'));
    flex2Input = element(by.id('field_flex2'));
    flex3Input = element(by.id('field_flex3'));
    flex4Input = element(by.id('field_flex4'));
    versionInput = element(by.id('field_version'));
    contractSelect = element(by.id('field_contract'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCompanyIdInput(companyId) {
        await this.companyIdInput.sendKeys(companyId);
    }

    async getCompanyIdInput() {
        return this.companyIdInput.getAttribute('value');
    }

    async setLookupTypesIdInput(lookupTypesId) {
        await this.lookupTypesIdInput.sendKeys(lookupTypesId);
    }

    async getLookupTypesIdInput() {
        return this.lookupTypesIdInput.getAttribute('value');
    }

    async setLookupCodeInput(lookupCode) {
        await this.lookupCodeInput.sendKeys(lookupCode);
    }

    async getLookupCodeInput() {
        return this.lookupCodeInput.getAttribute('value');
    }

    async setLookupValueInput(lookupValue) {
        await this.lookupValueInput.sendKeys(lookupValue);
    }

    async getLookupValueInput() {
        return this.lookupValueInput.getAttribute('value');
    }

    async setFlex1Input(flex1) {
        await this.flex1Input.sendKeys(flex1);
    }

    async getFlex1Input() {
        return this.flex1Input.getAttribute('value');
    }

    async setFlex2Input(flex2) {
        await this.flex2Input.sendKeys(flex2);
    }

    async getFlex2Input() {
        return this.flex2Input.getAttribute('value');
    }

    async setFlex3Input(flex3) {
        await this.flex3Input.sendKeys(flex3);
    }

    async getFlex3Input() {
        return this.flex3Input.getAttribute('value');
    }

    async setFlex4Input(flex4) {
        await this.flex4Input.sendKeys(flex4);
    }

    async getFlex4Input() {
        return this.flex4Input.getAttribute('value');
    }

    async setVersionInput(version) {
        await this.versionInput.sendKeys(version);
    }

    async getVersionInput() {
        return this.versionInput.getAttribute('value');
    }

    async contractSelectLastOption() {
        await this.contractSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async contractSelectOption(option) {
        await this.contractSelect.sendKeys(option);
    }

    getContractSelect(): ElementFinder {
        return this.contractSelect;
    }

    async getContractSelectedOption() {
        return this.contractSelect.element(by.css('option:checked')).getText();
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

export class LookupDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-lookup-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-lookup'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
