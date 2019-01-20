/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoiceLineComponentsPage, InvoiceLineDeleteDialog, InvoiceLineUpdatePage } from './invoice-line.page-object';

const expect = chai.expect;

describe('InvoiceLine e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let invoiceLineUpdatePage: InvoiceLineUpdatePage;
    let invoiceLineComponentsPage: InvoiceLineComponentsPage;
    let invoiceLineDeleteDialog: InvoiceLineDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load InvoiceLines', async () => {
        await navBarPage.goToEntity('invoice-line');
        invoiceLineComponentsPage = new InvoiceLineComponentsPage();
        expect(await invoiceLineComponentsPage.getTitle()).to.eq('promalyV5App.invoiceLine.home.title');
    });

    it('should load create InvoiceLine page', async () => {
        await invoiceLineComponentsPage.clickOnCreateButton();
        invoiceLineUpdatePage = new InvoiceLineUpdatePage();
        expect(await invoiceLineUpdatePage.getPageTitle()).to.eq('promalyV5App.invoiceLine.home.createOrEditLabel');
        await invoiceLineUpdatePage.cancel();
    });

    it('should create and save InvoiceLines', async () => {
        const nbButtonsBeforeCreate = await invoiceLineComponentsPage.countDeleteButtons();

        await invoiceLineComponentsPage.clickOnCreateButton();
        await promise.all([
            invoiceLineUpdatePage.setLineNumInput('5'),
            invoiceLineUpdatePage.setInvoiceIdInput('5'),
            invoiceLineUpdatePage.setPlanIdInput('5'),
            invoiceLineUpdatePage.setCategoryIdInput('5'),
            invoiceLineUpdatePage.setDescriptionInput('description'),
            invoiceLineUpdatePage.setAmountInput('5'),
            invoiceLineUpdatePage.setCurrencyInput('5'),
            invoiceLineUpdatePage.setRateInput('5'),
            invoiceLineUpdatePage.setQuantityInput('5')
        ]);
        expect(await invoiceLineUpdatePage.getLineNumInput()).to.eq('5');
        expect(await invoiceLineUpdatePage.getInvoiceIdInput()).to.eq('5');
        expect(await invoiceLineUpdatePage.getPlanIdInput()).to.eq('5');
        expect(await invoiceLineUpdatePage.getCategoryIdInput()).to.eq('5');
        expect(await invoiceLineUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await invoiceLineUpdatePage.getAmountInput()).to.eq('5');
        expect(await invoiceLineUpdatePage.getCurrencyInput()).to.eq('5');
        expect(await invoiceLineUpdatePage.getRateInput()).to.eq('5');
        expect(await invoiceLineUpdatePage.getQuantityInput()).to.eq('5');
        await invoiceLineUpdatePage.save();
        expect(await invoiceLineUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await invoiceLineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last InvoiceLine', async () => {
        const nbButtonsBeforeDelete = await invoiceLineComponentsPage.countDeleteButtons();
        await invoiceLineComponentsPage.clickOnLastDeleteButton();

        invoiceLineDeleteDialog = new InvoiceLineDeleteDialog();
        expect(await invoiceLineDeleteDialog.getDialogTitle()).to.eq('promalyV5App.invoiceLine.delete.question');
        await invoiceLineDeleteDialog.clickOnConfirmButton();

        expect(await invoiceLineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
