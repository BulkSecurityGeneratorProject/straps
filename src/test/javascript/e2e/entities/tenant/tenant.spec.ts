/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TenantComponentsPage, TenantDeleteDialog, TenantUpdatePage } from './tenant.page-object';

const expect = chai.expect;

describe('Tenant e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tenantUpdatePage: TenantUpdatePage;
    let tenantComponentsPage: TenantComponentsPage;
    let tenantDeleteDialog: TenantDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Tenants', async () => {
        await navBarPage.goToEntity('tenant');
        tenantComponentsPage = new TenantComponentsPage();
        expect(await tenantComponentsPage.getTitle()).to.eq('promalyV5App.tenant.home.title');
    });

    it('should load create Tenant page', async () => {
        await tenantComponentsPage.clickOnCreateButton();
        tenantUpdatePage = new TenantUpdatePage();
        expect(await tenantUpdatePage.getPageTitle()).to.eq('promalyV5App.tenant.home.createOrEditLabel');
        await tenantUpdatePage.cancel();
    });

    it('should create and save Tenants', async () => {
        const nbButtonsBeforeCreate = await tenantComponentsPage.countDeleteButtons();

        await tenantComponentsPage.clickOnCreateButton();
        await promise.all([
            tenantUpdatePage.setLeaseIdInput('5'),
            tenantUpdatePage.setContactInput('5'),
            tenantUpdatePage.propertyUnitSelectLastOption()
        ]);
        expect(await tenantUpdatePage.getLeaseIdInput()).to.eq('5');
        expect(await tenantUpdatePage.getContactInput()).to.eq('5');
        await tenantUpdatePage.save();
        expect(await tenantUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tenantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Tenant', async () => {
        const nbButtonsBeforeDelete = await tenantComponentsPage.countDeleteButtons();
        await tenantComponentsPage.clickOnLastDeleteButton();

        tenantDeleteDialog = new TenantDeleteDialog();
        expect(await tenantDeleteDialog.getDialogTitle()).to.eq('promalyV5App.tenant.delete.question');
        await tenantDeleteDialog.clickOnConfirmButton();

        expect(await tenantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
