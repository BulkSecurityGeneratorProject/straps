/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoiceHeaderComponentsPage, InvoiceHeaderDeleteDialog, InvoiceHeaderUpdatePage } from './invoice-header.page-object';

const expect = chai.expect;

describe('InvoiceHeader e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let invoiceHeaderUpdatePage: InvoiceHeaderUpdatePage;
    let invoiceHeaderComponentsPage: InvoiceHeaderComponentsPage;
    let invoiceHeaderDeleteDialog: InvoiceHeaderDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load InvoiceHeaders', async () => {
        await navBarPage.goToEntity('invoice-header');
        invoiceHeaderComponentsPage = new InvoiceHeaderComponentsPage();
        expect(await invoiceHeaderComponentsPage.getTitle()).to.eq('promalyV5App.invoiceHeader.home.title');
    });

    it('should load create InvoiceHeader page', async () => {
        await invoiceHeaderComponentsPage.clickOnCreateButton();
        invoiceHeaderUpdatePage = new InvoiceHeaderUpdatePage();
        expect(await invoiceHeaderUpdatePage.getPageTitle()).to.eq('promalyV5App.invoiceHeader.home.createOrEditLabel');
        await invoiceHeaderUpdatePage.cancel();
    });

    it('should create and save InvoiceHeaders', async () => {
        const nbButtonsBeforeCreate = await invoiceHeaderComponentsPage.countDeleteButtons();

        await invoiceHeaderComponentsPage.clickOnCreateButton();
        await promise.all([
            invoiceHeaderUpdatePage.setPlanIdInput('5'),
            invoiceHeaderUpdatePage.setServiceproviderIdInput('5'),
            invoiceHeaderUpdatePage.setInvoiceNumInput('5'),
            invoiceHeaderUpdatePage.setInvoiceDateInput('2000-12-31'),
            invoiceHeaderUpdatePage.setInvoiceStatusInput('5'),
            invoiceHeaderUpdatePage.setFromDateInput('2000-12-31'),
            invoiceHeaderUpdatePage.setToDateInput('2000-12-31'),
            invoiceHeaderUpdatePage.setAmountInput('5'),
            invoiceHeaderUpdatePage.setFeesInput('5'),
            invoiceHeaderUpdatePage.setCurrencyInput('5'),
            invoiceHeaderUpdatePage.setPaymentTermsInput('5'),
            invoiceHeaderUpdatePage.setPaymentStatusInput('5'),
            invoiceHeaderUpdatePage.setPaymentMethodInput('5'),
            invoiceHeaderUpdatePage.setPaymentDateInput('2000-12-31'),
            invoiceHeaderUpdatePage.setPaymentAmountInput('5'),
            invoiceHeaderUpdatePage.setPaymentRefInput('paymentRef'),
            invoiceHeaderUpdatePage.setCommentsInput('comments'),
            invoiceHeaderUpdatePage.setBillToCompanyInput('5'),
            invoiceHeaderUpdatePage.setPayorInput('payor'),
            invoiceHeaderUpdatePage.setPaymentCommentsInput('paymentComments'),
            invoiceHeaderUpdatePage.setEmailDateInput('emailDate'),
            invoiceHeaderUpdatePage.setEmailAddressInput('emailAddress'),
            invoiceHeaderUpdatePage.setKeyHashInput('keyHash')
        ]);
        expect(await invoiceHeaderUpdatePage.getPlanIdInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getServiceproviderIdInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getInvoiceNumInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getInvoiceDateInput()).to.eq('2000-12-31');
        expect(await invoiceHeaderUpdatePage.getInvoiceStatusInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getFromDateInput()).to.eq('2000-12-31');
        expect(await invoiceHeaderUpdatePage.getToDateInput()).to.eq('2000-12-31');
        expect(await invoiceHeaderUpdatePage.getAmountInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getFeesInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getCurrencyInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getPaymentTermsInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getPaymentStatusInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getPaymentMethodInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getPaymentDateInput()).to.eq('2000-12-31');
        expect(await invoiceHeaderUpdatePage.getPaymentAmountInput()).to.eq('5');
        expect(await invoiceHeaderUpdatePage.getPaymentRefInput()).to.eq('paymentRef');
        expect(await invoiceHeaderUpdatePage.getCommentsInput()).to.eq('comments');
        const selectedAdhoc = invoiceHeaderUpdatePage.getAdhocInput();
        if (await selectedAdhoc.isSelected()) {
            await invoiceHeaderUpdatePage.getAdhocInput().click();
            expect(await invoiceHeaderUpdatePage.getAdhocInput().isSelected()).to.be.false;
        } else {
            await invoiceHeaderUpdatePage.getAdhocInput().click();
            expect(await invoiceHeaderUpdatePage.getAdhocInput().isSelected()).to.be.true;
        }
        expect(await invoiceHeaderUpdatePage.getBillToCompanyInput()).to.eq('5');
        const selectedLegacy = invoiceHeaderUpdatePage.getLegacyInput();
        if (await selectedLegacy.isSelected()) {
            await invoiceHeaderUpdatePage.getLegacyInput().click();
            expect(await invoiceHeaderUpdatePage.getLegacyInput().isSelected()).to.be.false;
        } else {
            await invoiceHeaderUpdatePage.getLegacyInput().click();
            expect(await invoiceHeaderUpdatePage.getLegacyInput().isSelected()).to.be.true;
        }
        expect(await invoiceHeaderUpdatePage.getPayorInput()).to.eq('payor');
        expect(await invoiceHeaderUpdatePage.getPaymentCommentsInput()).to.eq('paymentComments');
        expect(await invoiceHeaderUpdatePage.getEmailDateInput()).to.eq('emailDate');
        expect(await invoiceHeaderUpdatePage.getEmailAddressInput()).to.eq('emailAddress');
        expect(await invoiceHeaderUpdatePage.getKeyHashInput()).to.eq('keyHash');
        await invoiceHeaderUpdatePage.save();
        expect(await invoiceHeaderUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await invoiceHeaderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last InvoiceHeader', async () => {
        const nbButtonsBeforeDelete = await invoiceHeaderComponentsPage.countDeleteButtons();
        await invoiceHeaderComponentsPage.clickOnLastDeleteButton();

        invoiceHeaderDeleteDialog = new InvoiceHeaderDeleteDialog();
        expect(await invoiceHeaderDeleteDialog.getDialogTitle()).to.eq('promalyV5App.invoiceHeader.delete.question');
        await invoiceHeaderDeleteDialog.clickOnConfirmButton();

        expect(await invoiceHeaderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
