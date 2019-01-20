/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WarrantyComponentsPage, WarrantyDeleteDialog, WarrantyUpdatePage } from './warranty.page-object';

const expect = chai.expect;

describe('Warranty e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let warrantyUpdatePage: WarrantyUpdatePage;
    let warrantyComponentsPage: WarrantyComponentsPage;
    let warrantyDeleteDialog: WarrantyDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Warranties', async () => {
        await navBarPage.goToEntity('warranty');
        warrantyComponentsPage = new WarrantyComponentsPage();
        expect(await warrantyComponentsPage.getTitle()).to.eq('promalyV5App.warranty.home.title');
    });

    it('should load create Warranty page', async () => {
        await warrantyComponentsPage.clickOnCreateButton();
        warrantyUpdatePage = new WarrantyUpdatePage();
        expect(await warrantyUpdatePage.getPageTitle()).to.eq('promalyV5App.warranty.home.createOrEditLabel');
        await warrantyUpdatePage.cancel();
    });

    it('should create and save Warranties', async () => {
        const nbButtonsBeforeCreate = await warrantyComponentsPage.countDeleteButtons();

        await warrantyComponentsPage.clickOnCreateButton();
        await promise.all([
            warrantyUpdatePage.setDescriptionInput('description'),
            warrantyUpdatePage.setSerialNumInput('serialNum'),
            warrantyUpdatePage.setWarrantyStartDateInput('2000-12-31'),
            warrantyUpdatePage.setWarrantyEndDateInput('2000-12-31')
        ]);
        expect(await warrantyUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await warrantyUpdatePage.getSerialNumInput()).to.eq('serialNum');
        expect(await warrantyUpdatePage.getWarrantyStartDateInput()).to.eq('2000-12-31');
        expect(await warrantyUpdatePage.getWarrantyEndDateInput()).to.eq('2000-12-31');
        await warrantyUpdatePage.save();
        expect(await warrantyUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await warrantyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Warranty', async () => {
        const nbButtonsBeforeDelete = await warrantyComponentsPage.countDeleteButtons();
        await warrantyComponentsPage.clickOnLastDeleteButton();

        warrantyDeleteDialog = new WarrantyDeleteDialog();
        expect(await warrantyDeleteDialog.getDialogTitle()).to.eq('promalyV5App.warranty.delete.question');
        await warrantyDeleteDialog.clickOnConfirmButton();

        expect(await warrantyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
