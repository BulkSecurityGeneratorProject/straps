/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RentRollComponentsPage, RentRollDeleteDialog, RentRollUpdatePage } from './rent-roll.page-object';

const expect = chai.expect;

describe('RentRoll e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let rentRollUpdatePage: RentRollUpdatePage;
    let rentRollComponentsPage: RentRollComponentsPage;
    let rentRollDeleteDialog: RentRollDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load RentRolls', async () => {
        await navBarPage.goToEntity('rent-roll');
        rentRollComponentsPage = new RentRollComponentsPage();
        expect(await rentRollComponentsPage.getTitle()).to.eq('promalyV5App.rentRoll.home.title');
    });

    it('should load create RentRoll page', async () => {
        await rentRollComponentsPage.clickOnCreateButton();
        rentRollUpdatePage = new RentRollUpdatePage();
        expect(await rentRollUpdatePage.getPageTitle()).to.eq('promalyV5App.rentRoll.home.createOrEditLabel');
        await rentRollUpdatePage.cancel();
    });

    it('should create and save RentRolls', async () => {
        const nbButtonsBeforeCreate = await rentRollComponentsPage.countDeleteButtons();

        await rentRollComponentsPage.clickOnCreateButton();
        await promise.all([
            rentRollUpdatePage.setSecurityDepositInput('5'),
            rentRollUpdatePage.setBankGuaranteeInput('5'),
            rentRollUpdatePage.setLeaseRecoveryTimingInput('5'),
            rentRollUpdatePage.propertySelectLastOption(),
            rentRollUpdatePage.buildingSelectLastOption(),
            rentRollUpdatePage.propertyUnitSelectLastOption(),
            rentRollUpdatePage.inflationTypeSelectLastOption()
        ]);
        expect(await rentRollUpdatePage.getSecurityDepositInput()).to.eq('5');
        expect(await rentRollUpdatePage.getBankGuaranteeInput()).to.eq('5');
        expect(await rentRollUpdatePage.getLeaseRecoveryTimingInput()).to.eq('5');
        await rentRollUpdatePage.save();
        expect(await rentRollUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await rentRollComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last RentRoll', async () => {
        const nbButtonsBeforeDelete = await rentRollComponentsPage.countDeleteButtons();
        await rentRollComponentsPage.clickOnLastDeleteButton();

        rentRollDeleteDialog = new RentRollDeleteDialog();
        expect(await rentRollDeleteDialog.getDialogTitle()).to.eq('promalyV5App.rentRoll.delete.question');
        await rentRollDeleteDialog.clickOnConfirmButton();

        expect(await rentRollComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
