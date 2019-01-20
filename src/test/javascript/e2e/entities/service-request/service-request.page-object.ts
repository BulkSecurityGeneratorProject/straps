import { element, by, ElementFinder } from 'protractor';

export class ServiceRequestComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-service-request div table .btn-danger'));
    title = element.all(by.css('jhi-service-request div h2#page-heading span')).first();

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

export class ServiceRequestUpdatePage {
    pageTitle = element(by.id('jhi-service-request-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    companyIdInput = element(by.id('field_companyId'));
    propertyUnitsIdInput = element(by.id('field_propertyUnitsId'));
    propertiesIdInput = element(by.id('field_propertiesId'));
    propertyVendorsIdInput = element(by.id('field_propertyVendorsId'));
    categoryInput = element(by.id('field_category'));
    subCategoryInput = element(by.id('field_subCategory'));
    urgencyInput = element(by.id('field_urgency'));
    unitIdInput = element(by.id('field_unitId'));
    propertyIdInput = element(by.id('field_propertyId'));
    requestDateTimeInput = element(by.id('field_requestDateTime'));
    descriptionInput = element(by.id('field_description'));
    assignmentStatusInput = element(by.id('field_assignmentStatus'));
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

    async setPropertyUnitsIdInput(propertyUnitsId) {
        await this.propertyUnitsIdInput.sendKeys(propertyUnitsId);
    }

    async getPropertyUnitsIdInput() {
        return this.propertyUnitsIdInput.getAttribute('value');
    }

    async setPropertiesIdInput(propertiesId) {
        await this.propertiesIdInput.sendKeys(propertiesId);
    }

    async getPropertiesIdInput() {
        return this.propertiesIdInput.getAttribute('value');
    }

    async setPropertyVendorsIdInput(propertyVendorsId) {
        await this.propertyVendorsIdInput.sendKeys(propertyVendorsId);
    }

    async getPropertyVendorsIdInput() {
        return this.propertyVendorsIdInput.getAttribute('value');
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

    async setUrgencyInput(urgency) {
        await this.urgencyInput.sendKeys(urgency);
    }

    async getUrgencyInput() {
        return this.urgencyInput.getAttribute('value');
    }

    async setUnitIdInput(unitId) {
        await this.unitIdInput.sendKeys(unitId);
    }

    async getUnitIdInput() {
        return this.unitIdInput.getAttribute('value');
    }

    async setPropertyIdInput(propertyId) {
        await this.propertyIdInput.sendKeys(propertyId);
    }

    async getPropertyIdInput() {
        return this.propertyIdInput.getAttribute('value');
    }

    async setRequestDateTimeInput(requestDateTime) {
        await this.requestDateTimeInput.sendKeys(requestDateTime);
    }

    async getRequestDateTimeInput() {
        return this.requestDateTimeInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setAssignmentStatusInput(assignmentStatus) {
        await this.assignmentStatusInput.sendKeys(assignmentStatus);
    }

    async getAssignmentStatusInput() {
        return this.assignmentStatusInput.getAttribute('value');
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

export class ServiceRequestDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-serviceRequest-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-serviceRequest'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
