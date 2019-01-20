/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PortfolioComponentsPage, PortfolioDeleteDialog, PortfolioUpdatePage } from './portfolio.page-object';

const expect = chai.expect;

describe('Portfolio e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let portfolioUpdatePage: PortfolioUpdatePage;
    let portfolioComponentsPage: PortfolioComponentsPage;
    let portfolioDeleteDialog: PortfolioDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Portfolios', async () => {
        await navBarPage.goToEntity('portfolio');
        portfolioComponentsPage = new PortfolioComponentsPage();
        expect(await portfolioComponentsPage.getTitle()).to.eq('promalyV5App.portfolio.home.title');
    });

    it('should load create Portfolio page', async () => {
        await portfolioComponentsPage.clickOnCreateButton();
        portfolioUpdatePage = new PortfolioUpdatePage();
        expect(await portfolioUpdatePage.getPageTitle()).to.eq('promalyV5App.portfolio.home.createOrEditLabel');
        await portfolioUpdatePage.cancel();
    });

    it('should create and save Portfolios', async () => {
        const nbButtonsBeforeCreate = await portfolioComponentsPage.countDeleteButtons();

        await portfolioComponentsPage.clickOnCreateButton();
        await promise.all([portfolioUpdatePage.setDescriptionInput('description')]);
        expect(await portfolioUpdatePage.getDescriptionInput()).to.eq('description');
        await portfolioUpdatePage.save();
        expect(await portfolioUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await portfolioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Portfolio', async () => {
        const nbButtonsBeforeDelete = await portfolioComponentsPage.countDeleteButtons();
        await portfolioComponentsPage.clickOnLastDeleteButton();

        portfolioDeleteDialog = new PortfolioDeleteDialog();
        expect(await portfolioDeleteDialog.getDialogTitle()).to.eq('promalyV5App.portfolio.delete.question');
        await portfolioDeleteDialog.clickOnConfirmButton();

        expect(await portfolioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
