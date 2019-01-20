/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LeaseComponentsPage, LeaseDeleteDialog, LeaseUpdatePage } from './lease.page-object';

const expect = chai.expect;

describe('Lease e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let leaseUpdatePage: LeaseUpdatePage;
    let leaseComponentsPage: LeaseComponentsPage;
    let leaseDeleteDialog: LeaseDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Leases', async () => {
        await navBarPage.goToEntity('lease');
        leaseComponentsPage = new LeaseComponentsPage();
        expect(await leaseComponentsPage.getTitle()).to.eq('promalyV5App.lease.home.title');
    });

    it('should load create Lease page', async () => {
        await leaseComponentsPage.clickOnCreateButton();
        leaseUpdatePage = new LeaseUpdatePage();
        expect(await leaseUpdatePage.getPageTitle()).to.eq('promalyV5App.lease.home.createOrEditLabel');
        await leaseUpdatePage.cancel();
    });

    it('should create and save Leases', async () => {
        const nbButtonsBeforeCreate = await leaseComponentsPage.countDeleteButtons();

        await leaseComponentsPage.clickOnCreateButton();
        await promise.all([
            leaseUpdatePage.setLeaseSignedDateInput('2000-12-31'),
            leaseUpdatePage.setLandlordsIdInput('5'),
            leaseUpdatePage.setLandlordAgentInput('5'),
            leaseUpdatePage.setTenantsIdInput('5'),
            leaseUpdatePage.setAddressIdInput('5'),
            leaseUpdatePage.setNumOccupantsInput('5'),
            leaseUpdatePage.setLeaseTermInput('5'),
            leaseUpdatePage.setLeaseStartDateInput('2000-12-31'),
            leaseUpdatePage.setLeaseEndDateInput('2000-12-31'),
            leaseUpdatePage.setRentAmountInput('5'),
            leaseUpdatePage.setRentPeriodInput('5'),
            leaseUpdatePage.setTotalTermRentInput('5'),
            leaseUpdatePage.setRentEscalationPercInput('5'),
            leaseUpdatePage.setProRataStartDateInput('5'),
            leaseUpdatePage.setProRataRentAmountInput('5'),
            leaseUpdatePage.setProRataEndDateInput('2000-12-31'),
            leaseUpdatePage.setAdditionalChargesInput('5'),
            leaseUpdatePage.setSecurityDepositInput('5'),
            leaseUpdatePage.setPetTypeInput('5'),
            leaseUpdatePage.setPetDescriptionInput('petDescription'),
            leaseUpdatePage.setOtherUtilitiesInput('otherUtilities'),
            leaseUpdatePage.setTerminationNoticePeriodInput('5'),
            leaseUpdatePage.setAgencyCompanyInput('5'),
            leaseUpdatePage.setManagementCompanyInput('5'),
            leaseUpdatePage.setPropertyIdInput('5'),
            leaseUpdatePage.setAdditionalNotesInput('additionalNotes'),
            leaseUpdatePage.typeSelectLastOption()
        ]);
        expect(await leaseUpdatePage.getLeaseSignedDateInput()).to.eq('2000-12-31');
        expect(await leaseUpdatePage.getLandlordsIdInput()).to.eq('5');
        expect(await leaseUpdatePage.getLandlordAgentInput()).to.eq('5');
        expect(await leaseUpdatePage.getTenantsIdInput()).to.eq('5');
        expect(await leaseUpdatePage.getAddressIdInput()).to.eq('5');
        expect(await leaseUpdatePage.getNumOccupantsInput()).to.eq('5');
        expect(await leaseUpdatePage.getLeaseTermInput()).to.eq('5');
        expect(await leaseUpdatePage.getLeaseStartDateInput()).to.eq('2000-12-31');
        expect(await leaseUpdatePage.getLeaseEndDateInput()).to.eq('2000-12-31');
        expect(await leaseUpdatePage.getRentAmountInput()).to.eq('5');
        expect(await leaseUpdatePage.getRentPeriodInput()).to.eq('5');
        expect(await leaseUpdatePage.getTotalTermRentInput()).to.eq('5');
        expect(await leaseUpdatePage.getRentEscalationPercInput()).to.eq('5');
        expect(await leaseUpdatePage.getProRataStartDateInput()).to.eq('5');
        expect(await leaseUpdatePage.getProRataRentAmountInput()).to.eq('5');
        expect(await leaseUpdatePage.getProRataEndDateInput()).to.eq('2000-12-31');
        expect(await leaseUpdatePage.getAdditionalChargesInput()).to.eq('5');
        expect(await leaseUpdatePage.getSecurityDepositInput()).to.eq('5');
        const selectedPetsAllowed = leaseUpdatePage.getPetsAllowedInput();
        if (await selectedPetsAllowed.isSelected()) {
            await leaseUpdatePage.getPetsAllowedInput().click();
            expect(await leaseUpdatePage.getPetsAllowedInput().isSelected()).to.be.false;
        } else {
            await leaseUpdatePage.getPetsAllowedInput().click();
            expect(await leaseUpdatePage.getPetsAllowedInput().isSelected()).to.be.true;
        }
        expect(await leaseUpdatePage.getPetTypeInput()).to.eq('5');
        expect(await leaseUpdatePage.getPetDescriptionInput()).to.eq('petDescription');
        const selectedWater = leaseUpdatePage.getWaterInput();
        if (await selectedWater.isSelected()) {
            await leaseUpdatePage.getWaterInput().click();
            expect(await leaseUpdatePage.getWaterInput().isSelected()).to.be.false;
        } else {
            await leaseUpdatePage.getWaterInput().click();
            expect(await leaseUpdatePage.getWaterInput().isSelected()).to.be.true;
        }
        const selectedGas = leaseUpdatePage.getGasInput();
        if (await selectedGas.isSelected()) {
            await leaseUpdatePage.getGasInput().click();
            expect(await leaseUpdatePage.getGasInput().isSelected()).to.be.false;
        } else {
            await leaseUpdatePage.getGasInput().click();
            expect(await leaseUpdatePage.getGasInput().isSelected()).to.be.true;
        }
        const selectedElectric = leaseUpdatePage.getElectricInput();
        if (await selectedElectric.isSelected()) {
            await leaseUpdatePage.getElectricInput().click();
            expect(await leaseUpdatePage.getElectricInput().isSelected()).to.be.false;
        } else {
            await leaseUpdatePage.getElectricInput().click();
            expect(await leaseUpdatePage.getElectricInput().isSelected()).to.be.true;
        }
        expect(await leaseUpdatePage.getOtherUtilitiesInput()).to.eq('otherUtilities');
        expect(await leaseUpdatePage.getTerminationNoticePeriodInput()).to.eq('5');
        expect(await leaseUpdatePage.getAgencyCompanyInput()).to.eq('5');
        expect(await leaseUpdatePage.getManagementCompanyInput()).to.eq('5');
        expect(await leaseUpdatePage.getPropertyIdInput()).to.eq('5');
        expect(await leaseUpdatePage.getAdditionalNotesInput()).to.eq('additionalNotes');
        await leaseUpdatePage.save();
        expect(await leaseUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await leaseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Lease', async () => {
        const nbButtonsBeforeDelete = await leaseComponentsPage.countDeleteButtons();
        await leaseComponentsPage.clickOnLastDeleteButton();

        leaseDeleteDialog = new LeaseDeleteDialog();
        expect(await leaseDeleteDialog.getDialogTitle()).to.eq('promalyV5App.lease.delete.question');
        await leaseDeleteDialog.clickOnConfirmButton();

        expect(await leaseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
