import { element, by, ElementFinder } from 'protractor';

export class InvoiceHeaderComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-invoice-header div table .btn-danger'));
    title = element.all(by.css('jhi-invoice-header div h2#page-heading span')).first();

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

export class InvoiceHeaderUpdatePage {
    pageTitle = element(by.id('jhi-invoice-header-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    planIdInput = element(by.id('field_planId'));
    serviceproviderIdInput = element(by.id('field_serviceproviderId'));
    invoiceNumInput = element(by.id('field_invoiceNum'));
    invoiceDateInput = element(by.id('field_invoiceDate'));
    invoiceStatusInput = element(by.id('field_invoiceStatus'));
    fromDateInput = element(by.id('field_fromDate'));
    toDateInput = element(by.id('field_toDate'));
    amountInput = element(by.id('field_amount'));
    feesInput = element(by.id('field_fees'));
    currencyInput = element(by.id('field_currency'));
    paymentTermsInput = element(by.id('field_paymentTerms'));
    paymentStatusInput = element(by.id('field_paymentStatus'));
    paymentMethodInput = element(by.id('field_paymentMethod'));
    paymentDateInput = element(by.id('field_paymentDate'));
    paymentAmountInput = element(by.id('field_paymentAmount'));
    paymentRefInput = element(by.id('field_paymentRef'));
    commentsInput = element(by.id('field_comments'));
    adhocInput = element(by.id('field_adhoc'));
    billToCompanyInput = element(by.id('field_billToCompany'));
    legacyInput = element(by.id('field_legacy'));
    payorInput = element(by.id('field_payor'));
    paymentCommentsInput = element(by.id('field_paymentComments'));
    emailDateInput = element(by.id('field_emailDate'));
    emailAddressInput = element(by.id('field_emailAddress'));
    keyHashInput = element(by.id('field_keyHash'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPlanIdInput(planId) {
        await this.planIdInput.sendKeys(planId);
    }

    async getPlanIdInput() {
        return this.planIdInput.getAttribute('value');
    }

    async setServiceproviderIdInput(serviceproviderId) {
        await this.serviceproviderIdInput.sendKeys(serviceproviderId);
    }

    async getServiceproviderIdInput() {
        return this.serviceproviderIdInput.getAttribute('value');
    }

    async setInvoiceNumInput(invoiceNum) {
        await this.invoiceNumInput.sendKeys(invoiceNum);
    }

    async getInvoiceNumInput() {
        return this.invoiceNumInput.getAttribute('value');
    }

    async setInvoiceDateInput(invoiceDate) {
        await this.invoiceDateInput.sendKeys(invoiceDate);
    }

    async getInvoiceDateInput() {
        return this.invoiceDateInput.getAttribute('value');
    }

    async setInvoiceStatusInput(invoiceStatus) {
        await this.invoiceStatusInput.sendKeys(invoiceStatus);
    }

    async getInvoiceStatusInput() {
        return this.invoiceStatusInput.getAttribute('value');
    }

    async setFromDateInput(fromDate) {
        await this.fromDateInput.sendKeys(fromDate);
    }

    async getFromDateInput() {
        return this.fromDateInput.getAttribute('value');
    }

    async setToDateInput(toDate) {
        await this.toDateInput.sendKeys(toDate);
    }

    async getToDateInput() {
        return this.toDateInput.getAttribute('value');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setFeesInput(fees) {
        await this.feesInput.sendKeys(fees);
    }

    async getFeesInput() {
        return this.feesInput.getAttribute('value');
    }

    async setCurrencyInput(currency) {
        await this.currencyInput.sendKeys(currency);
    }

    async getCurrencyInput() {
        return this.currencyInput.getAttribute('value');
    }

    async setPaymentTermsInput(paymentTerms) {
        await this.paymentTermsInput.sendKeys(paymentTerms);
    }

    async getPaymentTermsInput() {
        return this.paymentTermsInput.getAttribute('value');
    }

    async setPaymentStatusInput(paymentStatus) {
        await this.paymentStatusInput.sendKeys(paymentStatus);
    }

    async getPaymentStatusInput() {
        return this.paymentStatusInput.getAttribute('value');
    }

    async setPaymentMethodInput(paymentMethod) {
        await this.paymentMethodInput.sendKeys(paymentMethod);
    }

    async getPaymentMethodInput() {
        return this.paymentMethodInput.getAttribute('value');
    }

    async setPaymentDateInput(paymentDate) {
        await this.paymentDateInput.sendKeys(paymentDate);
    }

    async getPaymentDateInput() {
        return this.paymentDateInput.getAttribute('value');
    }

    async setPaymentAmountInput(paymentAmount) {
        await this.paymentAmountInput.sendKeys(paymentAmount);
    }

    async getPaymentAmountInput() {
        return this.paymentAmountInput.getAttribute('value');
    }

    async setPaymentRefInput(paymentRef) {
        await this.paymentRefInput.sendKeys(paymentRef);
    }

    async getPaymentRefInput() {
        return this.paymentRefInput.getAttribute('value');
    }

    async setCommentsInput(comments) {
        await this.commentsInput.sendKeys(comments);
    }

    async getCommentsInput() {
        return this.commentsInput.getAttribute('value');
    }

    getAdhocInput() {
        return this.adhocInput;
    }
    async setBillToCompanyInput(billToCompany) {
        await this.billToCompanyInput.sendKeys(billToCompany);
    }

    async getBillToCompanyInput() {
        return this.billToCompanyInput.getAttribute('value');
    }

    getLegacyInput() {
        return this.legacyInput;
    }
    async setPayorInput(payor) {
        await this.payorInput.sendKeys(payor);
    }

    async getPayorInput() {
        return this.payorInput.getAttribute('value');
    }

    async setPaymentCommentsInput(paymentComments) {
        await this.paymentCommentsInput.sendKeys(paymentComments);
    }

    async getPaymentCommentsInput() {
        return this.paymentCommentsInput.getAttribute('value');
    }

    async setEmailDateInput(emailDate) {
        await this.emailDateInput.sendKeys(emailDate);
    }

    async getEmailDateInput() {
        return this.emailDateInput.getAttribute('value');
    }

    async setEmailAddressInput(emailAddress) {
        await this.emailAddressInput.sendKeys(emailAddress);
    }

    async getEmailAddressInput() {
        return this.emailAddressInput.getAttribute('value');
    }

    async setKeyHashInput(keyHash) {
        await this.keyHashInput.sendKeys(keyHash);
    }

    async getKeyHashInput() {
        return this.keyHashInput.getAttribute('value');
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

export class InvoiceHeaderDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-invoiceHeader-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-invoiceHeader'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
