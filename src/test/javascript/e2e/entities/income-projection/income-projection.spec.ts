/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IncomeProjectionComponentsPage, IncomeProjectionDeleteDialog, IncomeProjectionUpdatePage } from './income-projection.page-object';

const expect = chai.expect;

describe('IncomeProjection e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let incomeProjectionUpdatePage: IncomeProjectionUpdatePage;
    let incomeProjectionComponentsPage: IncomeProjectionComponentsPage;
    let incomeProjectionDeleteDialog: IncomeProjectionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load IncomeProjections', async () => {
        await navBarPage.goToEntity('income-projection');
        incomeProjectionComponentsPage = new IncomeProjectionComponentsPage();
        expect(await incomeProjectionComponentsPage.getTitle()).to.eq('promalyV5App.incomeProjection.home.title');
    });

    it('should load create IncomeProjection page', async () => {
        await incomeProjectionComponentsPage.clickOnCreateButton();
        incomeProjectionUpdatePage = new IncomeProjectionUpdatePage();
        expect(await incomeProjectionUpdatePage.getPageTitle()).to.eq('promalyV5App.incomeProjection.home.createOrEditLabel');
        await incomeProjectionUpdatePage.cancel();
    });

    it('should create and save IncomeProjections', async () => {
        const nbButtonsBeforeCreate = await incomeProjectionComponentsPage.countDeleteButtons();

        await incomeProjectionComponentsPage.clickOnCreateButton();
        await promise.all([
            incomeProjectionUpdatePage.setPeriodInput('5'),
            incomeProjectionUpdatePage.setProjectedValueInput('5'),
            incomeProjectionUpdatePage.portfolioSelectLastOption(),
            incomeProjectionUpdatePage.propertySelectLastOption(),
            incomeProjectionUpdatePage.buildingSelectLastOption(),
            incomeProjectionUpdatePage.propertyUnitSelectLastOption(),
            incomeProjectionUpdatePage.assetLevelTypeSelectLastOption()
        ]);
        expect(await incomeProjectionUpdatePage.getPeriodInput()).to.eq('5');
        expect(await incomeProjectionUpdatePage.getProjectedValueInput()).to.eq('5');
        await incomeProjectionUpdatePage.save();
        expect(await incomeProjectionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await incomeProjectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last IncomeProjection', async () => {
        const nbButtonsBeforeDelete = await incomeProjectionComponentsPage.countDeleteButtons();
        await incomeProjectionComponentsPage.clickOnLastDeleteButton();

        incomeProjectionDeleteDialog = new IncomeProjectionDeleteDialog();
        expect(await incomeProjectionDeleteDialog.getDialogTitle()).to.eq('promalyV5App.incomeProjection.delete.question');
        await incomeProjectionDeleteDialog.clickOnConfirmButton();

        expect(await incomeProjectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
