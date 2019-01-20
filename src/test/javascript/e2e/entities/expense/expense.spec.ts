/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExpenseComponentsPage, ExpenseDeleteDialog, ExpenseUpdatePage } from './expense.page-object';

const expect = chai.expect;

describe('Expense e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let expenseUpdatePage: ExpenseUpdatePage;
    let expenseComponentsPage: ExpenseComponentsPage;
    let expenseDeleteDialog: ExpenseDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Expenses', async () => {
        await navBarPage.goToEntity('expense');
        expenseComponentsPage = new ExpenseComponentsPage();
        expect(await expenseComponentsPage.getTitle()).to.eq('promalyV5App.expense.home.title');
    });

    it('should load create Expense page', async () => {
        await expenseComponentsPage.clickOnCreateButton();
        expenseUpdatePage = new ExpenseUpdatePage();
        expect(await expenseUpdatePage.getPageTitle()).to.eq('promalyV5App.expense.home.createOrEditLabel');
        await expenseUpdatePage.cancel();
    });

    it('should create and save Expenses', async () => {
        const nbButtonsBeforeCreate = await expenseComponentsPage.countDeleteButtons();

        await expenseComponentsPage.clickOnCreateButton();
        await promise.all([
            expenseUpdatePage.setAmountInput('5'),
            expenseUpdatePage.setCurrencyInput('5'),
            expenseUpdatePage.propertySelectLastOption(),
            expenseUpdatePage.buildingSelectLastOption(),
            expenseUpdatePage.propertyUnitSelectLastOption(),
            expenseUpdatePage.typeSelectLastOption()
        ]);
        expect(await expenseUpdatePage.getAmountInput()).to.eq('5');
        expect(await expenseUpdatePage.getCurrencyInput()).to.eq('5');
        await expenseUpdatePage.save();
        expect(await expenseUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await expenseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Expense', async () => {
        const nbButtonsBeforeDelete = await expenseComponentsPage.countDeleteButtons();
        await expenseComponentsPage.clickOnLastDeleteButton();

        expenseDeleteDialog = new ExpenseDeleteDialog();
        expect(await expenseDeleteDialog.getDialogTitle()).to.eq('promalyV5App.expense.delete.question');
        await expenseDeleteDialog.clickOnConfirmButton();

        expect(await expenseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
