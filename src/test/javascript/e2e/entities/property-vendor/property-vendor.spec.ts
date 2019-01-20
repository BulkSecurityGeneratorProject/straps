/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PropertyVendorComponentsPage, PropertyVendorDeleteDialog, PropertyVendorUpdatePage } from './property-vendor.page-object';

const expect = chai.expect;

describe('PropertyVendor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let propertyVendorUpdatePage: PropertyVendorUpdatePage;
    let propertyVendorComponentsPage: PropertyVendorComponentsPage;
    let propertyVendorDeleteDialog: PropertyVendorDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PropertyVendors', async () => {
        await navBarPage.goToEntity('property-vendor');
        propertyVendorComponentsPage = new PropertyVendorComponentsPage();
        expect(await propertyVendorComponentsPage.getTitle()).to.eq('promalyV5App.propertyVendor.home.title');
    });

    it('should load create PropertyVendor page', async () => {
        await propertyVendorComponentsPage.clickOnCreateButton();
        propertyVendorUpdatePage = new PropertyVendorUpdatePage();
        expect(await propertyVendorUpdatePage.getPageTitle()).to.eq('promalyV5App.propertyVendor.home.createOrEditLabel');
        await propertyVendorUpdatePage.cancel();
    });

    it('should create and save PropertyVendors', async () => {
        const nbButtonsBeforeCreate = await propertyVendorComponentsPage.countDeleteButtons();

        await propertyVendorComponentsPage.clickOnCreateButton();
        await promise.all([
            propertyVendorUpdatePage.setCompanyIdInput('5'),
            propertyVendorUpdatePage.setRankingInput('5'),
            propertyVendorUpdatePage.setStartDateInput('2000-12-31'),
            propertyVendorUpdatePage.setEndDateInput('2000-12-31'),
            propertyVendorUpdatePage.setVersionInput('version')
        ]);
        expect(await propertyVendorUpdatePage.getCompanyIdInput()).to.eq('5');
        expect(await propertyVendorUpdatePage.getRankingInput()).to.eq('5');
        expect(await propertyVendorUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await propertyVendorUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        expect(await propertyVendorUpdatePage.getVersionInput()).to.eq('version');
        await propertyVendorUpdatePage.save();
        expect(await propertyVendorUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await propertyVendorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PropertyVendor', async () => {
        const nbButtonsBeforeDelete = await propertyVendorComponentsPage.countDeleteButtons();
        await propertyVendorComponentsPage.clickOnLastDeleteButton();

        propertyVendorDeleteDialog = new PropertyVendorDeleteDialog();
        expect(await propertyVendorDeleteDialog.getDialogTitle()).to.eq('promalyV5App.propertyVendor.delete.question');
        await propertyVendorDeleteDialog.clickOnConfirmButton();

        expect(await propertyVendorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
