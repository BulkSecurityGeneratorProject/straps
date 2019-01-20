import { element, by, ElementFinder } from 'protractor';

export class LookupTypeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-lookup-type div table .btn-danger'));
    title = element.all(by.css('jhi-lookup-type div h2#page-heading span')).first();

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

export class LookupTypeUpdatePage {
    pageTitle = element(by.id('jhi-lookup-type-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    companyIdInput = element(by.id('field_companyId'));
    lookupTypeCodeInput = element(by.id('field_lookupTypeCode'));
    lookupTypeValueInput = element(by.id('field_lookupTypeValue'));
    flex1DescrInput = element(by.id('field_flex1Descr'));
    flex2DescrInput = element(by.id('field_flex2Descr'));
    flex3DescrInput = element(by.id('field_flex3Descr'));
    flex4DescrInput = element(by.id('field_flex4Descr'));
    versionInput = element(by.id('field_version'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCompanyIdInput(companyId) {
        await this.companyIdInput.sendKeys(companyId);
    }

    async getCompanyIdInput() {
        return this.companyIdInput.getAttribute('value');
    }

    async setLookupTypeCodeInput(lookupTypeCode) {
        await this.lookupTypeCodeInput.sendKeys(lookupTypeCode);
    }

    async getLookupTypeCodeInput() {
        return this.lookupTypeCodeInput.getAttribute('value');
    }

    async setLookupTypeValueInput(lookupTypeValue) {
        await this.lookupTypeValueInput.sendKeys(lookupTypeValue);
    }

    async getLookupTypeValueInput() {
        return this.lookupTypeValueInput.getAttribute('value');
    }

    async setFlex1DescrInput(flex1Descr) {
        await this.flex1DescrInput.sendKeys(flex1Descr);
    }

    async getFlex1DescrInput() {
        return this.flex1DescrInput.getAttribute('value');
    }

    async setFlex2DescrInput(flex2Descr) {
        await this.flex2DescrInput.sendKeys(flex2Descr);
    }

    async getFlex2DescrInput() {
        return this.flex2DescrInput.getAttribute('value');
    }

    async setFlex3DescrInput(flex3Descr) {
        await this.flex3DescrInput.sendKeys(flex3Descr);
    }

    async getFlex3DescrInput() {
        return this.flex3DescrInput.getAttribute('value');
    }

    async setFlex4DescrInput(flex4Descr) {
        await this.flex4DescrInput.sendKeys(flex4Descr);
    }

    async getFlex4DescrInput() {
        return this.flex4DescrInput.getAttribute('value');
    }

    async setVersionInput(version) {
        await this.versionInput.sendKeys(version);
    }

    async getVersionInput() {
        return this.versionInput.getAttribute('value');
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

export class LookupTypeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-lookupType-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-lookupType'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
