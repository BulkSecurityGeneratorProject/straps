/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ServiceRequestComponentsPage, ServiceRequestDeleteDialog, ServiceRequestUpdatePage } from './service-request.page-object';

const expect = chai.expect;

describe('ServiceRequest e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let serviceRequestUpdatePage: ServiceRequestUpdatePage;
    let serviceRequestComponentsPage: ServiceRequestComponentsPage;
    let serviceRequestDeleteDialog: ServiceRequestDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ServiceRequests', async () => {
        await navBarPage.goToEntity('service-request');
        serviceRequestComponentsPage = new ServiceRequestComponentsPage();
        expect(await serviceRequestComponentsPage.getTitle()).to.eq('promalyV5App.serviceRequest.home.title');
    });

    it('should load create ServiceRequest page', async () => {
        await serviceRequestComponentsPage.clickOnCreateButton();
        serviceRequestUpdatePage = new ServiceRequestUpdatePage();
        expect(await serviceRequestUpdatePage.getPageTitle()).to.eq('promalyV5App.serviceRequest.home.createOrEditLabel');
        await serviceRequestUpdatePage.cancel();
    });

    it('should create and save ServiceRequests', async () => {
        const nbButtonsBeforeCreate = await serviceRequestComponentsPage.countDeleteButtons();

        await serviceRequestComponentsPage.clickOnCreateButton();
        await promise.all([
            serviceRequestUpdatePage.setCompanyIdInput('5'),
            serviceRequestUpdatePage.setPropertyUnitsIdInput('5'),
            serviceRequestUpdatePage.setPropertiesIdInput('5'),
            serviceRequestUpdatePage.setPropertyVendorsIdInput('5'),
            serviceRequestUpdatePage.setCategoryInput('5'),
            serviceRequestUpdatePage.setSubCategoryInput('5'),
            serviceRequestUpdatePage.setUrgencyInput('5'),
            serviceRequestUpdatePage.setUnitIdInput('5'),
            serviceRequestUpdatePage.setPropertyIdInput('5'),
            serviceRequestUpdatePage.setRequestDateTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            serviceRequestUpdatePage.setDescriptionInput('description'),
            serviceRequestUpdatePage.setAssignmentStatusInput('5'),
            serviceRequestUpdatePage.setVersionInput('version')
        ]);
        expect(await serviceRequestUpdatePage.getCompanyIdInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getPropertyUnitsIdInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getPropertiesIdInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getPropertyVendorsIdInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getCategoryInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getSubCategoryInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getUrgencyInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getUnitIdInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getPropertyIdInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getRequestDateTimeInput()).to.contain('2001-01-01T02:30');
        expect(await serviceRequestUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await serviceRequestUpdatePage.getAssignmentStatusInput()).to.eq('5');
        expect(await serviceRequestUpdatePage.getVersionInput()).to.eq('version');
        await serviceRequestUpdatePage.save();
        expect(await serviceRequestUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await serviceRequestComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ServiceRequest', async () => {
        const nbButtonsBeforeDelete = await serviceRequestComponentsPage.countDeleteButtons();
        await serviceRequestComponentsPage.clickOnLastDeleteButton();

        serviceRequestDeleteDialog = new ServiceRequestDeleteDialog();
        expect(await serviceRequestDeleteDialog.getDialogTitle()).to.eq('promalyV5App.serviceRequest.delete.question');
        await serviceRequestDeleteDialog.clickOnConfirmButton();

        expect(await serviceRequestComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
