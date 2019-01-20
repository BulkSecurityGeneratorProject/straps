import { element, by, ElementFinder } from 'protractor';

export class DiscountPlanComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-discount-plan div table .btn-danger'));
    title = element.all(by.css('jhi-discount-plan div h2#page-heading span')).first();

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

export class DiscountPlanUpdatePage {
    pageTitle = element(by.id('jhi-discount-plan-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    planIdInput = element(by.id('field_planId'));
    planNameInput = element(by.id('field_planName'));
    appliedToPlanInput = element(by.id('field_appliedToPlan'));
    appliedWithPlanInput = element(by.id('field_appliedWithPlan'));
    discountRateInput = element(by.id('field_discountRate'));
    discountUnitInput = element(by.id('field_discountUnit'));
    maxDiscountAmtInput = element(by.id('field_maxDiscountAmt'));
    descriptionInput = element(by.id('field_description'));
    conditionalInput = element(by.id('field_conditional'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPlanIdInput(planId) {
        await this.planIdInput.sendKeys(planId);
    }

    async getPlanIdInput() {
        return this.planIdInput.getAttribute('value');
    }

    async setPlanNameInput(planName) {
        await this.planNameInput.sendKeys(planName);
    }

    async getPlanNameInput() {
        return this.planNameInput.getAttribute('value');
    }

    async setAppliedToPlanInput(appliedToPlan) {
        await this.appliedToPlanInput.sendKeys(appliedToPlan);
    }

    async getAppliedToPlanInput() {
        return this.appliedToPlanInput.getAttribute('value');
    }

    async setAppliedWithPlanInput(appliedWithPlan) {
        await this.appliedWithPlanInput.sendKeys(appliedWithPlan);
    }

    async getAppliedWithPlanInput() {
        return this.appliedWithPlanInput.getAttribute('value');
    }

    async setDiscountRateInput(discountRate) {
        await this.discountRateInput.sendKeys(discountRate);
    }

    async getDiscountRateInput() {
        return this.discountRateInput.getAttribute('value');
    }

    async setDiscountUnitInput(discountUnit) {
        await this.discountUnitInput.sendKeys(discountUnit);
    }

    async getDiscountUnitInput() {
        return this.discountUnitInput.getAttribute('value');
    }

    async setMaxDiscountAmtInput(maxDiscountAmt) {
        await this.maxDiscountAmtInput.sendKeys(maxDiscountAmt);
    }

    async getMaxDiscountAmtInput() {
        return this.maxDiscountAmtInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    getConditionalInput() {
        return this.conditionalInput;
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

export class DiscountPlanDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-discountPlan-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-discountPlan'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
