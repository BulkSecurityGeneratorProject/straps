/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ApplianceComponentsPage, ApplianceDeleteDialog, ApplianceUpdatePage } from './appliance.page-object';

const expect = chai.expect;

describe('Appliance e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let applianceUpdatePage: ApplianceUpdatePage;
    let applianceComponentsPage: ApplianceComponentsPage;
    let applianceDeleteDialog: ApplianceDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Appliances', async () => {
        await navBarPage.goToEntity('appliance');
        applianceComponentsPage = new ApplianceComponentsPage();
        expect(await applianceComponentsPage.getTitle()).to.eq('promalyV5App.appliance.home.title');
    });

    it('should load create Appliance page', async () => {
        await applianceComponentsPage.clickOnCreateButton();
        applianceUpdatePage = new ApplianceUpdatePage();
        expect(await applianceUpdatePage.getPageTitle()).to.eq('promalyV5App.appliance.home.createOrEditLabel');
        await applianceUpdatePage.cancel();
    });

    it('should create and save Appliances', async () => {
        const nbButtonsBeforeCreate = await applianceComponentsPage.countDeleteButtons();

        await applianceComponentsPage.clickOnCreateButton();
        await promise.all([
            applianceUpdatePage.setDescriptionInput('description'),
            applianceUpdatePage.setSerialNumInput('serialNum'),
            applianceUpdatePage.setWarrantyStartDateInput('2000-12-31'),
            applianceUpdatePage.setWarrantyEndDateInput('2000-12-31'),
            applianceUpdatePage.propertyUnitSelectLastOption()
        ]);
        expect(await applianceUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await applianceUpdatePage.getSerialNumInput()).to.eq('serialNum');
        expect(await applianceUpdatePage.getWarrantyStartDateInput()).to.eq('2000-12-31');
        expect(await applianceUpdatePage.getWarrantyEndDateInput()).to.eq('2000-12-31');
        await applianceUpdatePage.save();
        expect(await applianceUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await applianceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Appliance', async () => {
        const nbButtonsBeforeDelete = await applianceComponentsPage.countDeleteButtons();
        await applianceComponentsPage.clickOnLastDeleteButton();

        applianceDeleteDialog = new ApplianceDeleteDialog();
        expect(await applianceDeleteDialog.getDialogTitle()).to.eq('promalyV5App.appliance.delete.question');
        await applianceDeleteDialog.clickOnConfirmButton();

        expect(await applianceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
