import { element, by, ElementFinder } from 'protractor';

export class InvoiceLineComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-invoice-line div table .btn-danger'));
    title = element.all(by.css('jhi-invoice-line div h2#page-heading span')).first();

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

export class InvoiceLineUpdatePage {
    pageTitle = element(by.id('jhi-invoice-line-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    lineNumInput = element(by.id('field_lineNum'));
    invoiceIdInput = element(by.id('field_invoiceId'));
    planIdInput = element(by.id('field_planId'));
    categoryIdInput = element(by.id('field_categoryId'));
    descriptionInput = element(by.id('field_description'));
    amountInput = element(by.id('field_amount'));
    currencyInput = element(by.id('field_currency'));
    rateInput = element(by.id('field_rate'));
    quantityInput = element(by.id('field_quantity'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLineNumInput(lineNum) {
        await this.lineNumInput.sendKeys(lineNum);
    }

    async getLineNumInput() {
        return this.lineNumInput.getAttribute('value');
    }

    async setInvoiceIdInput(invoiceId) {
        await this.invoiceIdInput.sendKeys(invoiceId);
    }

    async getInvoiceIdInput() {
        return this.invoiceIdInput.getAttribute('value');
    }

    async setPlanIdInput(planId) {
        await this.planIdInput.sendKeys(planId);
    }

    async getPlanIdInput() {
        return this.planIdInput.getAttribute('value');
    }

    async setCategoryIdInput(categoryId) {
        await this.categoryIdInput.sendKeys(categoryId);
    }

    async getCategoryIdInput() {
        return this.categoryIdInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
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

    async setRateInput(rate) {
        await this.rateInput.sendKeys(rate);
    }

    async getRateInput() {
        return this.rateInput.getAttribute('value');
    }

    async setQuantityInput(quantity) {
        await this.quantityInput.sendKeys(quantity);
    }

    async getQuantityInput() {
        return this.quantityInput.getAttribute('value');
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

export class InvoiceLineDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-invoiceLine-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-invoiceLine'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
