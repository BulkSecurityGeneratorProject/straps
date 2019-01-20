/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LandLordComponentsPage, LandLordDeleteDialog, LandLordUpdatePage } from './land-lord.page-object';

const expect = chai.expect;

describe('LandLord e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let landLordUpdatePage: LandLordUpdatePage;
    let landLordComponentsPage: LandLordComponentsPage;
    let landLordDeleteDialog: LandLordDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load LandLords', async () => {
        await navBarPage.goToEntity('land-lord');
        landLordComponentsPage = new LandLordComponentsPage();
        expect(await landLordComponentsPage.getTitle()).to.eq('promalyV5App.landLord.home.title');
    });

    it('should load create LandLord page', async () => {
        await landLordComponentsPage.clickOnCreateButton();
        landLordUpdatePage = new LandLordUpdatePage();
        expect(await landLordUpdatePage.getPageTitle()).to.eq('promalyV5App.landLord.home.createOrEditLabel');
        await landLordUpdatePage.cancel();
    });

    it('should create and save LandLords', async () => {
        const nbButtonsBeforeCreate = await landLordComponentsPage.countDeleteButtons();

        await landLordComponentsPage.clickOnCreateButton();
        await promise.all([
            landLordUpdatePage.setContactIdInput('5'),
            landLordUpdatePage.setPercentageOwnershipInput('5'),
            landLordUpdatePage.setOwnershipTypeInput('5')
        ]);
        expect(await landLordUpdatePage.getContactIdInput()).to.eq('5');
        expect(await landLordUpdatePage.getPercentageOwnershipInput()).to.eq('5');
        expect(await landLordUpdatePage.getOwnershipTypeInput()).to.eq('5');
        await landLordUpdatePage.save();
        expect(await landLordUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await landLordComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last LandLord', async () => {
        const nbButtonsBeforeDelete = await landLordComponentsPage.countDeleteButtons();
        await landLordComponentsPage.clickOnLastDeleteButton();

        landLordDeleteDialog = new LandLordDeleteDialog();
        expect(await landLordDeleteDialog.getDialogTitle()).to.eq('promalyV5App.landLord.delete.question');
        await landLordDeleteDialog.clickOnConfirmButton();

        expect(await landLordComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
