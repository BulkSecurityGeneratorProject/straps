/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContractComponentsPage, ContractDeleteDialog, ContractUpdatePage } from './contract.page-object';

const expect = chai.expect;

describe('Contract e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contractUpdatePage: ContractUpdatePage;
    let contractComponentsPage: ContractComponentsPage;
    let contractDeleteDialog: ContractDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Contracts', async () => {
        await navBarPage.goToEntity('contract');
        contractComponentsPage = new ContractComponentsPage();
        expect(await contractComponentsPage.getTitle()).to.eq('promalyV5App.contract.home.title');
    });

    it('should load create Contract page', async () => {
        await contractComponentsPage.clickOnCreateButton();
        contractUpdatePage = new ContractUpdatePage();
        expect(await contractUpdatePage.getPageTitle()).to.eq('promalyV5App.contract.home.createOrEditLabel');
        await contractUpdatePage.cancel();
    });

    it('should create and save Contracts', async () => {
        const nbButtonsBeforeCreate = await contractComponentsPage.countDeleteButtons();

        await contractComponentsPage.clickOnCreateButton();
        await promise.all([
            contractUpdatePage.setDescriptionInput('description'),
            contractUpdatePage.setStartDateInput('2000-12-31'),
            contractUpdatePage.setEndDateInput('2000-12-31')
            // contractUpdatePage.companySelectLastOption(),
            // contractUpdatePage.managementCompanySelectLastOption(),
        ]);
        expect(await contractUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await contractUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await contractUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        await contractUpdatePage.save();
        expect(await contractUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contractComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Contract', async () => {
        const nbButtonsBeforeDelete = await contractComponentsPage.countDeleteButtons();
        await contractComponentsPage.clickOnLastDeleteButton();

        contractDeleteDialog = new ContractDeleteDialog();
        expect(await contractDeleteDialog.getDialogTitle()).to.eq('promalyV5App.contract.delete.question');
        await contractDeleteDialog.clickOnConfirmButton();

        expect(await contractComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
