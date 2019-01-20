/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BuildingComponentsPage, BuildingDeleteDialog, BuildingUpdatePage } from './building.page-object';

const expect = chai.expect;

describe('Building e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let buildingUpdatePage: BuildingUpdatePage;
    let buildingComponentsPage: BuildingComponentsPage;
    let buildingDeleteDialog: BuildingDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Buildings', async () => {
        await navBarPage.goToEntity('building');
        buildingComponentsPage = new BuildingComponentsPage();
        expect(await buildingComponentsPage.getTitle()).to.eq('promalyV5App.building.home.title');
    });

    it('should load create Building page', async () => {
        await buildingComponentsPage.clickOnCreateButton();
        buildingUpdatePage = new BuildingUpdatePage();
        expect(await buildingUpdatePage.getPageTitle()).to.eq('promalyV5App.building.home.createOrEditLabel');
        await buildingUpdatePage.cancel();
    });

    it('should create and save Buildings', async () => {
        const nbButtonsBeforeCreate = await buildingComponentsPage.countDeleteButtons();

        await buildingComponentsPage.clickOnCreateButton();
        await promise.all([
            buildingUpdatePage.setNameInput('name'),
            buildingUpdatePage.setYearBuiltInput('2000-12-31'),
            buildingUpdatePage.setNoOfloorsInput('5'),
            buildingUpdatePage.setBuildingSizeInput('5'),
            buildingUpdatePage.setCoveredAreaInput('5'),
            buildingUpdatePage.setLandAreaInput('5'),
            buildingUpdatePage.setNoOfRentalUnitInput('5'),
            buildingUpdatePage.setParkingSpacesInput('5'),
            buildingUpdatePage.setTotalSpaceAvaliableInput('5'),
            buildingUpdatePage.setTotalUnitLevelInput('5'),
            buildingUpdatePage.setCurrentRentPerSqftInput('5'),
            buildingUpdatePage.setDescriptionInput('description'),
            buildingUpdatePage.setVersionInput('version'),
            buildingUpdatePage.propertySelectLastOption(),
            buildingUpdatePage.assetTypeSelectLastOption()
            // buildingUpdatePage.ownerSelectLastOption(),
            // buildingUpdatePage.companySelectLastOption(),
            // buildingUpdatePage.leaseSelectLastOption(),
        ]);
        expect(await buildingUpdatePage.getNameInput()).to.eq('name');
        expect(await buildingUpdatePage.getYearBuiltInput()).to.eq('2000-12-31');
        expect(await buildingUpdatePage.getNoOfloorsInput()).to.eq('5');
        expect(await buildingUpdatePage.getBuildingSizeInput()).to.eq('5');
        expect(await buildingUpdatePage.getCoveredAreaInput()).to.eq('5');
        expect(await buildingUpdatePage.getLandAreaInput()).to.eq('5');
        expect(await buildingUpdatePage.getNoOfRentalUnitInput()).to.eq('5');
        expect(await buildingUpdatePage.getParkingSpacesInput()).to.eq('5');
        expect(await buildingUpdatePage.getTotalSpaceAvaliableInput()).to.eq('5');
        expect(await buildingUpdatePage.getTotalUnitLevelInput()).to.eq('5');
        expect(await buildingUpdatePage.getCurrentRentPerSqftInput()).to.eq('5');
        expect(await buildingUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await buildingUpdatePage.getVersionInput()).to.eq('version');
        await buildingUpdatePage.save();
        expect(await buildingUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await buildingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Building', async () => {
        const nbButtonsBeforeDelete = await buildingComponentsPage.countDeleteButtons();
        await buildingComponentsPage.clickOnLastDeleteButton();

        buildingDeleteDialog = new BuildingDeleteDialog();
        expect(await buildingDeleteDialog.getDialogTitle()).to.eq('promalyV5App.building.delete.question');
        await buildingDeleteDialog.clickOnConfirmButton();

        expect(await buildingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
