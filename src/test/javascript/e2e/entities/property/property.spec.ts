/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PropertyComponentsPage, PropertyDeleteDialog, PropertyUpdatePage } from './property.page-object';

const expect = chai.expect;

describe('Property e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let propertyUpdatePage: PropertyUpdatePage;
    let propertyComponentsPage: PropertyComponentsPage;
    let propertyDeleteDialog: PropertyDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Properties', async () => {
        await navBarPage.goToEntity('property');
        propertyComponentsPage = new PropertyComponentsPage();
        expect(await propertyComponentsPage.getTitle()).to.eq('promalyV5App.property.home.title');
    });

    it('should load create Property page', async () => {
        await propertyComponentsPage.clickOnCreateButton();
        propertyUpdatePage = new PropertyUpdatePage();
        expect(await propertyUpdatePage.getPageTitle()).to.eq('promalyV5App.property.home.createOrEditLabel');
        await propertyUpdatePage.cancel();
    });

    it('should create and save Properties', async () => {
        const nbButtonsBeforeCreate = await propertyComponentsPage.countDeleteButtons();

        await propertyComponentsPage.clickOnCreateButton();
        await promise.all([
            propertyUpdatePage.setDescriptionInput('description'),
            propertyUpdatePage.setGrossAreaInput('5'),
            propertyUpdatePage.setNetAreaInput('5'),
            propertyUpdatePage.portfolioSelectLastOption(),
            propertyUpdatePage.addressSelectLastOption(),
            propertyUpdatePage.usageTypeSelectLastOption(),
            propertyUpdatePage.statusSelectLastOption(),
            propertyUpdatePage.leaseSelectLastOption()
            // propertyUpdatePage.ownerSelectLastOption(),
            // propertyUpdatePage.companySelectLastOption(),
        ]);
        expect(await propertyUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await propertyUpdatePage.getGrossAreaInput()).to.eq('5');
        expect(await propertyUpdatePage.getNetAreaInput()).to.eq('5');
        await propertyUpdatePage.save();
        expect(await propertyUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await propertyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Property', async () => {
        const nbButtonsBeforeDelete = await propertyComponentsPage.countDeleteButtons();
        await propertyComponentsPage.clickOnLastDeleteButton();

        propertyDeleteDialog = new PropertyDeleteDialog();
        expect(await propertyDeleteDialog.getDialogTitle()).to.eq('promalyV5App.property.delete.question');
        await propertyDeleteDialog.clickOnConfirmButton();

        expect(await propertyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
