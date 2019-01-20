/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    CompanyCapabilityComponentsPage,
    CompanyCapabilityDeleteDialog,
    CompanyCapabilityUpdatePage
} from './company-capability.page-object';

const expect = chai.expect;

describe('CompanyCapability e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let companyCapabilityUpdatePage: CompanyCapabilityUpdatePage;
    let companyCapabilityComponentsPage: CompanyCapabilityComponentsPage;
    let companyCapabilityDeleteDialog: CompanyCapabilityDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load CompanyCapabilities', async () => {
        await navBarPage.goToEntity('company-capability');
        companyCapabilityComponentsPage = new CompanyCapabilityComponentsPage();
        expect(await companyCapabilityComponentsPage.getTitle()).to.eq('promalyV5App.companyCapability.home.title');
    });

    it('should load create CompanyCapability page', async () => {
        await companyCapabilityComponentsPage.clickOnCreateButton();
        companyCapabilityUpdatePage = new CompanyCapabilityUpdatePage();
        expect(await companyCapabilityUpdatePage.getPageTitle()).to.eq('promalyV5App.companyCapability.home.createOrEditLabel');
        await companyCapabilityUpdatePage.cancel();
    });

    it('should create and save CompanyCapabilities', async () => {
        const nbButtonsBeforeCreate = await companyCapabilityComponentsPage.countDeleteButtons();

        await companyCapabilityComponentsPage.clickOnCreateButton();
        await promise.all([
            companyCapabilityUpdatePage.setCapabilityIdInput('5'),
            companyCapabilityUpdatePage.setLicenseInput('5'),
            companyCapabilityUpdatePage.setCertficationInput('5'),
            companyCapabilityUpdatePage.companySelectLastOption()
        ]);
        expect(await companyCapabilityUpdatePage.getCapabilityIdInput()).to.eq('5');
        expect(await companyCapabilityUpdatePage.getLicenseInput()).to.eq('5');
        expect(await companyCapabilityUpdatePage.getCertficationInput()).to.eq('5');
        await companyCapabilityUpdatePage.save();
        expect(await companyCapabilityUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await companyCapabilityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last CompanyCapability', async () => {
        const nbButtonsBeforeDelete = await companyCapabilityComponentsPage.countDeleteButtons();
        await companyCapabilityComponentsPage.clickOnLastDeleteButton();

        companyCapabilityDeleteDialog = new CompanyCapabilityDeleteDialog();
        expect(await companyCapabilityDeleteDialog.getDialogTitle()).to.eq('promalyV5App.companyCapability.delete.question');
        await companyCapabilityDeleteDialog.clickOnConfirmButton();

        expect(await companyCapabilityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
