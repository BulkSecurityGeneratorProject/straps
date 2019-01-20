import { element, by, ElementFinder } from 'protractor';

export class PropertyComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-property div table .btn-danger'));
    title = element.all(by.css('jhi-property div h2#page-heading span')).first();

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

export class PropertyUpdatePage {
    pageTitle = element(by.id('jhi-property-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    grossAreaInput = element(by.id('field_grossArea'));
    netAreaInput = element(by.id('field_netArea'));
    portfolioSelect = element(by.id('field_portfolio'));
    addressSelect = element(by.id('field_address'));
    usageTypeSelect = element(by.id('field_usageType'));
    statusSelect = element(by.id('field_status'));
    leaseSelect = element(by.id('field_lease'));
    ownerSelect = element(by.id('field_owner'));
    companySelect = element(by.id('field_company'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setGrossAreaInput(grossArea) {
        await this.grossAreaInput.sendKeys(grossArea);
    }

    async getGrossAreaInput() {
        return this.grossAreaInput.getAttribute('value');
    }

    async setNetAreaInput(netArea) {
        await this.netAreaInput.sendKeys(netArea);
    }

    async getNetAreaInput() {
        return this.netAreaInput.getAttribute('value');
    }

    async portfolioSelectLastOption() {
        await this.portfolioSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async portfolioSelectOption(option) {
        await this.portfolioSelect.sendKeys(option);
    }

    getPortfolioSelect(): ElementFinder {
        return this.portfolioSelect;
    }

    async getPortfolioSelectedOption() {
        return this.portfolioSelect.element(by.css('option:checked')).getText();
    }

    async addressSelectLastOption() {
        await this.addressSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async addressSelectOption(option) {
        await this.addressSelect.sendKeys(option);
    }

    getAddressSelect(): ElementFinder {
        return this.addressSelect;
    }

    async getAddressSelectedOption() {
        return this.addressSelect.element(by.css('option:checked')).getText();
    }

    async usageTypeSelectLastOption() {
        await this.usageTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async usageTypeSelectOption(option) {
        await this.usageTypeSelect.sendKeys(option);
    }

    getUsageTypeSelect(): ElementFinder {
        return this.usageTypeSelect;
    }

    async getUsageTypeSelectedOption() {
        return this.usageTypeSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async statusSelectOption(option) {
        await this.statusSelect.sendKeys(option);
    }

    getStatusSelect(): ElementFinder {
        return this.statusSelect;
    }

    async getStatusSelectedOption() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async leaseSelectLastOption() {
        await this.leaseSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async leaseSelectOption(option) {
        await this.leaseSelect.sendKeys(option);
    }

    getLeaseSelect(): ElementFinder {
        return this.leaseSelect;
    }

    async getLeaseSelectedOption() {
        return this.leaseSelect.element(by.css('option:checked')).getText();
    }

    async ownerSelectLastOption() {
        await this.ownerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async ownerSelectOption(option) {
        await this.ownerSelect.sendKeys(option);
    }

    getOwnerSelect(): ElementFinder {
        return this.ownerSelect;
    }

    async getOwnerSelectedOption() {
        return this.ownerSelect.element(by.css('option:checked')).getText();
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

export class PropertyDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-property-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-property'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
