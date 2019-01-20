import { element, by, ElementFinder } from 'protractor';

export class DocumentStoreComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-document-store div table .btn-danger'));
    title = element.all(by.css('jhi-document-store div h2#page-heading span')).first();

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

export class DocumentStoreUpdatePage {
    pageTitle = element(by.id('jhi-document-store-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    entityIdInput = element(by.id('field_entityId'));
    entityNameInput = element(by.id('field_entityName'));
    pathInput = element(by.id('field_path'));
    categoryInput = element(by.id('field_category'));
    subCategoryInput = element(by.id('field_subCategory'));
    versionInput = element(by.id('field_version'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setEntityIdInput(entityId) {
        await this.entityIdInput.sendKeys(entityId);
    }

    async getEntityIdInput() {
        return this.entityIdInput.getAttribute('value');
    }

    async setEntityNameInput(entityName) {
        await this.entityNameInput.sendKeys(entityName);
    }

    async getEntityNameInput() {
        return this.entityNameInput.getAttribute('value');
    }

    async setPathInput(path) {
        await this.pathInput.sendKeys(path);
    }

    async getPathInput() {
        return this.pathInput.getAttribute('value');
    }

    async setCategoryInput(category) {
        await this.categoryInput.sendKeys(category);
    }

    async getCategoryInput() {
        return this.categoryInput.getAttribute('value');
    }

    async setSubCategoryInput(subCategory) {
        await this.subCategoryInput.sendKeys(subCategory);
    }

    async getSubCategoryInput() {
        return this.subCategoryInput.getAttribute('value');
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

export class DocumentStoreDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-documentStore-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-documentStore'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
