import { element, by, ElementFinder } from 'protractor';

export class ExpenseComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-expense div table .btn-danger'));
    title = element.all(by.css('jhi-expense div h2#page-heading span')).first();

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

export class ExpenseUpdatePage {
    pageTitle = element(by.id('jhi-expense-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    amountInput = element(by.id('field_amount'));
    currencyInput = element(by.id('field_currency'));
    propertySelect = element(by.id('field_property'));
    buildingSelect = element(by.id('field_building'));
    propertyUnitSelect = element(by.id('field_propertyUnit'));
    typeSelect = element(by.id('field_type'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setCurrencyInput(currency) {
        await this.currencyInput.sendKeys(currency);
    }

    async getCurrencyInput() {
        return this.currencyInput.getAttribute('value');
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

    async typeSelectLastOption() {
        await this.typeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async typeSelectOption(option) {
        await this.typeSelect.sendKeys(option);
    }

    getTypeSelect(): ElementFinder {
        return this.typeSelect;
    }

    async getTypeSelectedOption() {
        return this.typeSelect.element(by.css('option:checked')).getText();
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

export class ExpenseDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-expense-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-expense'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
