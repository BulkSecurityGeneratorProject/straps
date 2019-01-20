/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PropertyUnitComponentsPage, PropertyUnitDeleteDialog, PropertyUnitUpdatePage } from './property-unit.page-object';

const expect = chai.expect;

describe('PropertyUnit e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let propertyUnitUpdatePage: PropertyUnitUpdatePage;
    let propertyUnitComponentsPage: PropertyUnitComponentsPage;
    let propertyUnitDeleteDialog: PropertyUnitDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PropertyUnits', async () => {
        await navBarPage.goToEntity('property-unit');
        propertyUnitComponentsPage = new PropertyUnitComponentsPage();
        expect(await propertyUnitComponentsPage.getTitle()).to.eq('promalyV5App.propertyUnit.home.title');
    });

    it('should load create PropertyUnit page', async () => {
        await propertyUnitComponentsPage.clickOnCreateButton();
        propertyUnitUpdatePage = new PropertyUnitUpdatePage();
        expect(await propertyUnitUpdatePage.getPageTitle()).to.eq('promalyV5App.propertyUnit.home.createOrEditLabel');
        await propertyUnitUpdatePage.cancel();
    });

    it('should create and save PropertyUnits', async () => {
        const nbButtonsBeforeCreate = await propertyUnitComponentsPage.countDeleteButtons();

        await propertyUnitComponentsPage.clickOnCreateButton();
        await promise.all([
            propertyUnitUpdatePage.setUnitNoInput('unitNo'),
            propertyUnitUpdatePage.setDescriptionInput('description'),
            propertyUnitUpdatePage.setFloorsInput('5'),
            propertyUnitUpdatePage.setNetAreaInput('5'),
            propertyUnitUpdatePage.setGrossAreaInput('5'),
            propertyUnitUpdatePage.setTotalRoomsInput('5'),
            propertyUnitUpdatePage.setNoOfBrsInput('5'),
            propertyUnitUpdatePage.setNoOfFbInput('5'),
            propertyUnitUpdatePage.setNoOfHbInput('5'),
            propertyUnitUpdatePage.setPropertyMailboxNumInput('propertyMailboxNum'),
            propertyUnitUpdatePage.setPropertyParkingLotNumInput('propertyParkingLotNum'),
            propertyUnitUpdatePage.setWhoPaysHeatingInput('5'),
            propertyUnitUpdatePage.setWhoPaysElectricInput('5'),
            propertyUnitUpdatePage.setWhoPaysWaterInput('5'),
            propertyUnitUpdatePage.setLastRenovatedInput('2000-12-31'),
            propertyUnitUpdatePage.setCurrentRentPerSqftInput('5'),
            propertyUnitUpdatePage.setVersionInput('5'),
            propertyUnitUpdatePage.buildingSelectLastOption(),
            propertyUnitUpdatePage.addressSelectLastOption(),
            propertyUnitUpdatePage.usageTypeSelectLastOption(),
            propertyUnitUpdatePage.statusSelectLastOption(),
            propertyUnitUpdatePage.mortgageSelectLastOption(),
            propertyUnitUpdatePage.rentRollSelectLastOption()
            // propertyUnitUpdatePage.leaseSelectLastOption(),
            // propertyUnitUpdatePage.ownerSelectLastOption(),
        ]);
        expect(await propertyUnitUpdatePage.getUnitNoInput()).to.eq('unitNo');
        expect(await propertyUnitUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await propertyUnitUpdatePage.getFloorsInput()).to.eq('5');
        expect(await propertyUnitUpdatePage.getNetAreaInput()).to.eq('5');
        expect(await propertyUnitUpdatePage.getGrossAreaInput()).to.eq('5');
        const selectedResidential = propertyUnitUpdatePage.getResidentialInput();
        if (await selectedResidential.isSelected()) {
            await propertyUnitUpdatePage.getResidentialInput().click();
            expect(await propertyUnitUpdatePage.getResidentialInput().isSelected()).to.be.false;
        } else {
            await propertyUnitUpdatePage.getResidentialInput().click();
            expect(await propertyUnitUpdatePage.getResidentialInput().isSelected()).to.be.true;
        }
        expect(await propertyUnitUpdatePage.getTotalRoomsInput()).to.eq('5');
        expect(await propertyUnitUpdatePage.getNoOfBrsInput()).to.eq('5');
        expect(await propertyUnitUpdatePage.getNoOfFbInput()).to.eq('5');
        expect(await propertyUnitUpdatePage.getNoOfHbInput()).to.eq('5');
        expect(await propertyUnitUpdatePage.getPropertyMailboxNumInput()).to.eq('propertyMailboxNum');
        expect(await propertyUnitUpdatePage.getPropertyParkingLotNumInput()).to.eq('propertyParkingLotNum');
        const selectedGasHeating = propertyUnitUpdatePage.getGasHeatingInput();
        if (await selectedGasHeating.isSelected()) {
            await propertyUnitUpdatePage.getGasHeatingInput().click();
            expect(await propertyUnitUpdatePage.getGasHeatingInput().isSelected()).to.be.false;
        } else {
            await propertyUnitUpdatePage.getGasHeatingInput().click();
            expect(await propertyUnitUpdatePage.getGasHeatingInput().isSelected()).to.be.true;
        }
        expect(await propertyUnitUpdatePage.getWhoPaysHeatingInput()).to.eq('5');
        const selectedElectric = propertyUnitUpdatePage.getElectricInput();
        if (await selectedElectric.isSelected()) {
            await propertyUnitUpdatePage.getElectricInput().click();
            expect(await propertyUnitUpdatePage.getElectricInput().isSelected()).to.be.false;
        } else {
            await propertyUnitUpdatePage.getElectricInput().click();
            expect(await propertyUnitUpdatePage.getElectricInput().isSelected()).to.be.true;
        }
        expect(await propertyUnitUpdatePage.getWhoPaysElectricInput()).to.eq('5');
        const selectedWater = propertyUnitUpdatePage.getWaterInput();
        if (await selectedWater.isSelected()) {
            await propertyUnitUpdatePage.getWaterInput().click();
            expect(await propertyUnitUpdatePage.getWaterInput().isSelected()).to.be.false;
        } else {
            await propertyUnitUpdatePage.getWaterInput().click();
            expect(await propertyUnitUpdatePage.getWaterInput().isSelected()).to.be.true;
        }
        expect(await propertyUnitUpdatePage.getWhoPaysWaterInput()).to.eq('5');
        expect(await propertyUnitUpdatePage.getLastRenovatedInput()).to.eq('2000-12-31');
        expect(await propertyUnitUpdatePage.getCurrentRentPerSqftInput()).to.eq('5');
        expect(await propertyUnitUpdatePage.getVersionInput()).to.eq('5');
        await propertyUnitUpdatePage.save();
        expect(await propertyUnitUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await propertyUnitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PropertyUnit', async () => {
        const nbButtonsBeforeDelete = await propertyUnitComponentsPage.countDeleteButtons();
        await propertyUnitComponentsPage.clickOnLastDeleteButton();

        propertyUnitDeleteDialog = new PropertyUnitDeleteDialog();
        expect(await propertyUnitDeleteDialog.getDialogTitle()).to.eq('promalyV5App.propertyUnit.delete.question');
        await propertyUnitDeleteDialog.clickOnConfirmButton();

        expect(await propertyUnitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
