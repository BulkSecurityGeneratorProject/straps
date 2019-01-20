/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MortgageComponentsPage, MortgageDeleteDialog, MortgageUpdatePage } from './mortgage.page-object';

const expect = chai.expect;

describe('Mortgage e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let mortgageUpdatePage: MortgageUpdatePage;
    let mortgageComponentsPage: MortgageComponentsPage;
    let mortgageDeleteDialog: MortgageDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Mortgages', async () => {
        await navBarPage.goToEntity('mortgage');
        mortgageComponentsPage = new MortgageComponentsPage();
        expect(await mortgageComponentsPage.getTitle()).to.eq('promalyV5App.mortgage.home.title');
    });

    it('should load create Mortgage page', async () => {
        await mortgageComponentsPage.clickOnCreateButton();
        mortgageUpdatePage = new MortgageUpdatePage();
        expect(await mortgageUpdatePage.getPageTitle()).to.eq('promalyV5App.mortgage.home.createOrEditLabel');
        await mortgageUpdatePage.cancel();
    });

    it('should create and save Mortgages', async () => {
        const nbButtonsBeforeCreate = await mortgageComponentsPage.countDeleteButtons();

        await mortgageComponentsPage.clickOnCreateButton();
        await promise.all([
            mortgageUpdatePage.setCompanyIdInput('5'),
            mortgageUpdatePage.setPropertyUnitsIdInput('5'),
            mortgageUpdatePage.setBorrowerInput('5'),
            mortgageUpdatePage.setLenderInput('5'),
            mortgageUpdatePage.setStartDateInput('2000-12-31'),
            mortgageUpdatePage.setEndDateInput('2000-12-31'),
            mortgageUpdatePage.setAmountInput('5'),
            mortgageUpdatePage.setInterestRateInput('5'),
            mortgageUpdatePage.setBalloonPaymentInput('5'),
            mortgageUpdatePage.setAmortizationLengthYrsInput('5'),
            mortgageUpdatePage.setVersionInput('version'),
            mortgageUpdatePage.propertySelectLastOption(),
            mortgageUpdatePage.buildingSelectLastOption(),
            mortgageUpdatePage.propertyUnitSelectLastOption()
        ]);
        expect(await mortgageUpdatePage.getCompanyIdInput()).to.eq('5');
        expect(await mortgageUpdatePage.getPropertyUnitsIdInput()).to.eq('5');
        expect(await mortgageUpdatePage.getBorrowerInput()).to.eq('5');
        expect(await mortgageUpdatePage.getLenderInput()).to.eq('5');
        expect(await mortgageUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await mortgageUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        expect(await mortgageUpdatePage.getAmountInput()).to.eq('5');
        expect(await mortgageUpdatePage.getInterestRateInput()).to.eq('5');
        expect(await mortgageUpdatePage.getBalloonPaymentInput()).to.eq('5');
        const selectedRefinanceOption = mortgageUpdatePage.getRefinanceOptionInput();
        if (await selectedRefinanceOption.isSelected()) {
            await mortgageUpdatePage.getRefinanceOptionInput().click();
            expect(await mortgageUpdatePage.getRefinanceOptionInput().isSelected()).to.be.false;
        } else {
            await mortgageUpdatePage.getRefinanceOptionInput().click();
            expect(await mortgageUpdatePage.getRefinanceOptionInput().isSelected()).to.be.true;
        }
        expect(await mortgageUpdatePage.getAmortizationLengthYrsInput()).to.eq('5');
        expect(await mortgageUpdatePage.getVersionInput()).to.eq('version');
        await mortgageUpdatePage.save();
        expect(await mortgageUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await mortgageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Mortgage', async () => {
        const nbButtonsBeforeDelete = await mortgageComponentsPage.countDeleteButtons();
        await mortgageComponentsPage.clickOnLastDeleteButton();

        mortgageDeleteDialog = new MortgageDeleteDialog();
        expect(await mortgageDeleteDialog.getDialogTitle()).to.eq('promalyV5App.mortgage.delete.question');
        await mortgageDeleteDialog.clickOnConfirmButton();

        expect(await mortgageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
