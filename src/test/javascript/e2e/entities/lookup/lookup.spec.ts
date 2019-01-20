/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LookupComponentsPage, LookupDeleteDialog, LookupUpdatePage } from './lookup.page-object';

const expect = chai.expect;

describe('Lookup e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let lookupUpdatePage: LookupUpdatePage;
    let lookupComponentsPage: LookupComponentsPage;
    let lookupDeleteDialog: LookupDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Lookups', async () => {
        await navBarPage.goToEntity('lookup');
        lookupComponentsPage = new LookupComponentsPage();
        expect(await lookupComponentsPage.getTitle()).to.eq('promalyV5App.lookup.home.title');
    });

    it('should load create Lookup page', async () => {
        await lookupComponentsPage.clickOnCreateButton();
        lookupUpdatePage = new LookupUpdatePage();
        expect(await lookupUpdatePage.getPageTitle()).to.eq('promalyV5App.lookup.home.createOrEditLabel');
        await lookupUpdatePage.cancel();
    });

    it('should create and save Lookups', async () => {
        const nbButtonsBeforeCreate = await lookupComponentsPage.countDeleteButtons();

        await lookupComponentsPage.clickOnCreateButton();
        await promise.all([
            lookupUpdatePage.setCompanyIdInput('5'),
            lookupUpdatePage.setLookupTypesIdInput('5'),
            lookupUpdatePage.setLookupCodeInput('lookupCode'),
            lookupUpdatePage.setLookupValueInput('lookupValue'),
            lookupUpdatePage.setFlex1Input('flex1'),
            lookupUpdatePage.setFlex2Input('flex2'),
            lookupUpdatePage.setFlex3Input('flex3'),
            lookupUpdatePage.setFlex4Input('flex4'),
            lookupUpdatePage.setVersionInput('version'),
            lookupUpdatePage.contractSelectLastOption()
        ]);
        expect(await lookupUpdatePage.getCompanyIdInput()).to.eq('5');
        expect(await lookupUpdatePage.getLookupTypesIdInput()).to.eq('5');
        expect(await lookupUpdatePage.getLookupCodeInput()).to.eq('lookupCode');
        expect(await lookupUpdatePage.getLookupValueInput()).to.eq('lookupValue');
        expect(await lookupUpdatePage.getFlex1Input()).to.eq('flex1');
        expect(await lookupUpdatePage.getFlex2Input()).to.eq('flex2');
        expect(await lookupUpdatePage.getFlex3Input()).to.eq('flex3');
        expect(await lookupUpdatePage.getFlex4Input()).to.eq('flex4');
        expect(await lookupUpdatePage.getVersionInput()).to.eq('version');
        await lookupUpdatePage.save();
        expect(await lookupUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await lookupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Lookup', async () => {
        const nbButtonsBeforeDelete = await lookupComponentsPage.countDeleteButtons();
        await lookupComponentsPage.clickOnLastDeleteButton();

        lookupDeleteDialog = new LookupDeleteDialog();
        expect(await lookupDeleteDialog.getDialogTitle()).to.eq('promalyV5App.lookup.delete.question');
        await lookupDeleteDialog.clickOnConfirmButton();

        expect(await lookupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
