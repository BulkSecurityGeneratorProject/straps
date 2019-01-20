/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactComponentsPage, ContactDeleteDialog, ContactUpdatePage } from './contact.page-object';

const expect = chai.expect;

describe('Contact e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contactUpdatePage: ContactUpdatePage;
    let contactComponentsPage: ContactComponentsPage;
    let contactDeleteDialog: ContactDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Contacts', async () => {
        await navBarPage.goToEntity('contact');
        contactComponentsPage = new ContactComponentsPage();
        expect(await contactComponentsPage.getTitle()).to.eq('promalyV5App.contact.home.title');
    });

    it('should load create Contact page', async () => {
        await contactComponentsPage.clickOnCreateButton();
        contactUpdatePage = new ContactUpdatePage();
        expect(await contactUpdatePage.getPageTitle()).to.eq('promalyV5App.contact.home.createOrEditLabel');
        await contactUpdatePage.cancel();
    });

    it('should create and save Contacts', async () => {
        const nbButtonsBeforeCreate = await contactComponentsPage.countDeleteButtons();

        await contactComponentsPage.clickOnCreateButton();
        await promise.all([
            contactUpdatePage.setFirstNameInput('firstName'),
            contactUpdatePage.setLastNameInput('lastName'),
            contactUpdatePage.setEmailInput('email'),
            contactUpdatePage.setPhonePrimaryInput('phonePrimary'),
            contactUpdatePage.setPhoneSecondaryInput('phoneSecondary'),
            contactUpdatePage.setContactTypeInput('5'),
            contactUpdatePage.setVersionInput('version'),
            contactUpdatePage.companySelectLastOption()
        ]);
        expect(await contactUpdatePage.getFirstNameInput()).to.eq('firstName');
        expect(await contactUpdatePage.getLastNameInput()).to.eq('lastName');
        expect(await contactUpdatePage.getEmailInput()).to.eq('email');
        expect(await contactUpdatePage.getPhonePrimaryInput()).to.eq('phonePrimary');
        expect(await contactUpdatePage.getPhoneSecondaryInput()).to.eq('phoneSecondary');
        expect(await contactUpdatePage.getContactTypeInput()).to.eq('5');
        expect(await contactUpdatePage.getVersionInput()).to.eq('version');
        await contactUpdatePage.save();
        expect(await contactUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Contact', async () => {
        const nbButtonsBeforeDelete = await contactComponentsPage.countDeleteButtons();
        await contactComponentsPage.clickOnLastDeleteButton();

        contactDeleteDialog = new ContactDeleteDialog();
        expect(await contactDeleteDialog.getDialogTitle()).to.eq('promalyV5App.contact.delete.question');
        await contactDeleteDialog.clickOnConfirmButton();

        expect(await contactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
