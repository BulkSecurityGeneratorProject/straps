import { element, by, ElementFinder } from 'protractor';

export class BillingPlanComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-billing-plan div table .btn-danger'));
    title = element.all(by.css('jhi-billing-plan div h2#page-heading span')).first();

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

export class BillingPlanUpdatePage {
    pageTitle = element(by.id('jhi-billing-plan-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    planNameInput = element(by.id('field_planName'));
    categoryInput = element(by.id('field_category'));
    memberTypeInput = element(by.id('field_memberType'));
    descriptionInput = element(by.id('field_description'));
    prorationDescInput = element(by.id('field_prorationDesc'));
    effectiveDateInput = element(by.id('field_effectiveDate'));
    effectiveStatusInput = element(by.id('field_effectiveStatus'));
    associationInput = element(by.id('field_association'));
    orderTypeInput = element(by.id('field_orderType'));
    accountingBookInput = element(by.id('field_accountingBook'));
    ratesInput = element(by.id('field_rates'));
    currencyInput = element(by.id('field_currency'));
    basisInput = element(by.id('field_basis'));
    initiationFeesInput = element(by.id('field_initiationFees'));
    couponsInput = element(by.id('field_coupons'));
    proratedInput = element(by.id('field_prorated'));
    glcodeInput = element(by.id('field_glcode'));
    activeInput = element(by.id('field_active'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPlanNameInput(planName) {
        await this.planNameInput.sendKeys(planName);
    }

    async getPlanNameInput() {
        return this.planNameInput.getAttribute('value');
    }

    async setCategoryInput(category) {
        await this.categoryInput.sendKeys(category);
    }

    async getCategoryInput() {
        return this.categoryInput.getAttribute('value');
    }

    async setMemberTypeInput(memberType) {
        await this.memberTypeInput.sendKeys(memberType);
    }

    async getMemberTypeInput() {
        return this.memberTypeInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setProrationDescInput(prorationDesc) {
        await this.prorationDescInput.sendKeys(prorationDesc);
    }

    async getProrationDescInput() {
        return this.prorationDescInput.getAttribute('value');
    }

    async setEffectiveDateInput(effectiveDate) {
        await this.effectiveDateInput.sendKeys(effectiveDate);
    }

    async getEffectiveDateInput() {
        return this.effectiveDateInput.getAttribute('value');
    }

    async setEffectiveStatusInput(effectiveStatus) {
        await this.effectiveStatusInput.sendKeys(effectiveStatus);
    }

    async getEffectiveStatusInput() {
        return this.effectiveStatusInput.getAttribute('value');
    }

    async setAssociationInput(association) {
        await this.associationInput.sendKeys(association);
    }

    async getAssociationInput() {
        return this.associationInput.getAttribute('value');
    }

    async setOrderTypeInput(orderType) {
        await this.orderTypeInput.sendKeys(orderType);
    }

    async getOrderTypeInput() {
        return this.orderTypeInput.getAttribute('value');
    }

    async setAccountingBookInput(accountingBook) {
        await this.accountingBookInput.sendKeys(accountingBook);
    }

    async getAccountingBookInput() {
        return this.accountingBookInput.getAttribute('value');
    }

    async setRatesInput(rates) {
        await this.ratesInput.sendKeys(rates);
    }

    async getRatesInput() {
        return this.ratesInput.getAttribute('value');
    }

    async setCurrencyInput(currency) {
        await this.currencyInput.sendKeys(currency);
    }

    async getCurrencyInput() {
        return this.currencyInput.getAttribute('value');
    }

    async setBasisInput(basis) {
        await this.basisInput.sendKeys(basis);
    }

    async getBasisInput() {
        return this.basisInput.getAttribute('value');
    }

    async setInitiationFeesInput(initiationFees) {
        await this.initiationFeesInput.sendKeys(initiationFees);
    }

    async getInitiationFeesInput() {
        return this.initiationFeesInput.getAttribute('value');
    }

    async setCouponsInput(coupons) {
        await this.couponsInput.sendKeys(coupons);
    }

    async getCouponsInput() {
        return this.couponsInput.getAttribute('value');
    }

    getProratedInput() {
        return this.proratedInput;
    }
    async setGlcodeInput(glcode) {
        await this.glcodeInput.sendKeys(glcode);
    }

    async getGlcodeInput() {
        return this.glcodeInput.getAttribute('value');
    }

    getActiveInput() {
        return this.activeInput;
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

export class BillingPlanDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-billingPlan-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-billingPlan'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
