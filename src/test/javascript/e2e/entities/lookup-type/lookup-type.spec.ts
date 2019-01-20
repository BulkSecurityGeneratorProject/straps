/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LookupTypeComponentsPage, LookupTypeDeleteDialog, LookupTypeUpdatePage } from './lookup-type.page-object';

const expect = chai.expect;

describe('LookupType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let lookupTypeUpdatePage: LookupTypeUpdatePage;
    let lookupTypeComponentsPage: LookupTypeComponentsPage;
    let lookupTypeDeleteDialog: LookupTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load LookupTypes', async () => {
        await navBarPage.goToEntity('lookup-type');
        lookupTypeComponentsPage = new LookupTypeComponentsPage();
        expect(await lookupTypeComponentsPage.getTitle()).to.eq('promalyV5App.lookupType.home.title');
    });

    it('should load create LookupType page', async () => {
        await lookupTypeComponentsPage.clickOnCreateButton();
        lookupTypeUpdatePage = new LookupTypeUpdatePage();
        expect(await lookupTypeUpdatePage.getPageTitle()).to.eq('promalyV5App.lookupType.home.createOrEditLabel');
        await lookupTypeUpdatePage.cancel();
    });

    it('should create and save LookupTypes', async () => {
        const nbButtonsBeforeCreate = await lookupTypeComponentsPage.countDeleteButtons();

        await lookupTypeComponentsPage.clickOnCreateButton();
        await promise.all([
            lookupTypeUpdatePage.setCompanyIdInput('5'),
            lookupTypeUpdatePage.setLookupTypeCodeInput('lookupTypeCode'),
            lookupTypeUpdatePage.setLookupTypeValueInput('lookupTypeValue'),
            lookupTypeUpdatePage.setFlex1DescrInput('flex1Descr'),
            lookupTypeUpdatePage.setFlex2DescrInput('flex2Descr'),
            lookupTypeUpdatePage.setFlex3DescrInput('flex3Descr'),
            lookupTypeUpdatePage.setFlex4DescrInput('flex4Descr'),
            lookupTypeUpdatePage.setVersionInput('version')
        ]);
        expect(await lookupTypeUpdatePage.getCompanyIdInput()).to.eq('5');
        expect(await lookupTypeUpdatePage.getLookupTypeCodeInput()).to.eq('lookupTypeCode');
        expect(await lookupTypeUpdatePage.getLookupTypeValueInput()).to.eq('lookupTypeValue');
        expect(await lookupTypeUpdatePage.getFlex1DescrInput()).to.eq('flex1Descr');
        expect(await lookupTypeUpdatePage.getFlex2DescrInput()).to.eq('flex2Descr');
        expect(await lookupTypeUpdatePage.getFlex3DescrInput()).to.eq('flex3Descr');
        expect(await lookupTypeUpdatePage.getFlex4DescrInput()).to.eq('flex4Descr');
        expect(await lookupTypeUpdatePage.getVersionInput()).to.eq('version');
        await lookupTypeUpdatePage.save();
        expect(await lookupTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await lookupTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last LookupType', async () => {
        const nbButtonsBeforeDelete = await lookupTypeComponentsPage.countDeleteButtons();
        await lookupTypeComponentsPage.clickOnLastDeleteButton();

        lookupTypeDeleteDialog = new LookupTypeDeleteDialog();
        expect(await lookupTypeDeleteDialog.getDialogTitle()).to.eq('promalyV5App.lookupType.delete.question');
        await lookupTypeDeleteDialog.clickOnConfirmButton();

        expect(await lookupTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
