import { element, by, ElementFinder } from 'protractor';

export class AmenityComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-amenity div table .btn-danger'));
    title = element.all(by.css('jhi-amenity div h2#page-heading span')).first();

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

export class AmenityUpdatePage {
    pageTitle = element(by.id('jhi-amenity-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    propertySelect = element(by.id('field_property'));
    buildingSelect = element(by.id('field_building'));
    propertyUnitSelect = element(by.id('field_propertyUnit'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
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

export class AmenityDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-amenity-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-amenity'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
