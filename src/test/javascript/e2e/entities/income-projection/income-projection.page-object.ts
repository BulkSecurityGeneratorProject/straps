import { element, by, ElementFinder } from 'protractor';

export class IncomeProjectionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-income-projection div table .btn-danger'));
    title = element.all(by.css('jhi-income-projection div h2#page-heading span')).first();

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

export class IncomeProjectionUpdatePage {
    pageTitle = element(by.id('jhi-income-projection-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    periodInput = element(by.id('field_period'));
    projectedValueInput = element(by.id('field_projectedValue'));
    portfolioSelect = element(by.id('field_portfolio'));
    propertySelect = element(by.id('field_property'));
    buildingSelect = element(by.id('field_building'));
    propertyUnitSelect = element(by.id('field_propertyUnit'));
    assetLevelTypeSelect = element(by.id('field_assetLevelType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPeriodInput(period) {
        await this.periodInput.sendKeys(period);
    }

    async getPeriodInput() {
        return this.periodInput.getAttribute('value');
    }

    async setProjectedValueInput(projectedValue) {
        await this.projectedValueInput.sendKeys(projectedValue);
    }

    async getProjectedValueInput() {
        return this.projectedValueInput.getAttribute('value');
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

    async assetLevelTypeSelectLastOption() {
        await this.assetLevelTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async assetLevelTypeSelectOption(option) {
        await this.assetLevelTypeSelect.sendKeys(option);
    }

    getAssetLevelTypeSelect(): ElementFinder {
        return this.assetLevelTypeSelect;
    }

    async getAssetLevelTypeSelectedOption() {
        return this.assetLevelTypeSelect.element(by.css('option:checked')).getText();
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

export class IncomeProjectionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-incomeProjection-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-incomeProjection'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
