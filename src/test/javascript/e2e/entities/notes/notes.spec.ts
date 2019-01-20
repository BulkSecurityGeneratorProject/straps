/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NotesComponentsPage, NotesDeleteDialog, NotesUpdatePage } from './notes.page-object';

const expect = chai.expect;

describe('Notes e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let notesUpdatePage: NotesUpdatePage;
    let notesComponentsPage: NotesComponentsPage;
    let notesDeleteDialog: NotesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Notes', async () => {
        await navBarPage.goToEntity('notes');
        notesComponentsPage = new NotesComponentsPage();
        expect(await notesComponentsPage.getTitle()).to.eq('promalyV5App.notes.home.title');
    });

    it('should load create Notes page', async () => {
        await notesComponentsPage.clickOnCreateButton();
        notesUpdatePage = new NotesUpdatePage();
        expect(await notesUpdatePage.getPageTitle()).to.eq('promalyV5App.notes.home.createOrEditLabel');
        await notesUpdatePage.cancel();
    });

    it('should create and save Notes', async () => {
        const nbButtonsBeforeCreate = await notesComponentsPage.countDeleteButtons();

        await notesComponentsPage.clickOnCreateButton();
        await promise.all([notesUpdatePage.setNoteInput('note')]);
        expect(await notesUpdatePage.getNoteInput()).to.eq('note');
        await notesUpdatePage.save();
        expect(await notesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await notesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Notes', async () => {
        const nbButtonsBeforeDelete = await notesComponentsPage.countDeleteButtons();
        await notesComponentsPage.clickOnLastDeleteButton();

        notesDeleteDialog = new NotesDeleteDialog();
        expect(await notesDeleteDialog.getDialogTitle()).to.eq('promalyV5App.notes.delete.question');
        await notesDeleteDialog.clickOnConfirmButton();

        expect(await notesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
