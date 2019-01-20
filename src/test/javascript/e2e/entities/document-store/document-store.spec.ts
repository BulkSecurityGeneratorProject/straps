/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DocumentStoreComponentsPage, DocumentStoreDeleteDialog, DocumentStoreUpdatePage } from './document-store.page-object';

const expect = chai.expect;

describe('DocumentStore e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let documentStoreUpdatePage: DocumentStoreUpdatePage;
    let documentStoreComponentsPage: DocumentStoreComponentsPage;
    let documentStoreDeleteDialog: DocumentStoreDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load DocumentStores', async () => {
        await navBarPage.goToEntity('document-store');
        documentStoreComponentsPage = new DocumentStoreComponentsPage();
        expect(await documentStoreComponentsPage.getTitle()).to.eq('promalyV5App.documentStore.home.title');
    });

    it('should load create DocumentStore page', async () => {
        await documentStoreComponentsPage.clickOnCreateButton();
        documentStoreUpdatePage = new DocumentStoreUpdatePage();
        expect(await documentStoreUpdatePage.getPageTitle()).to.eq('promalyV5App.documentStore.home.createOrEditLabel');
        await documentStoreUpdatePage.cancel();
    });

    it('should create and save DocumentStores', async () => {
        const nbButtonsBeforeCreate = await documentStoreComponentsPage.countDeleteButtons();

        await documentStoreComponentsPage.clickOnCreateButton();
        await promise.all([
            documentStoreUpdatePage.setEntityIdInput('5'),
            documentStoreUpdatePage.setEntityNameInput('entityName'),
            documentStoreUpdatePage.setPathInput('path'),
            documentStoreUpdatePage.setCategoryInput('5'),
            documentStoreUpdatePage.setSubCategoryInput('5'),
            documentStoreUpdatePage.setVersionInput('version')
        ]);
        expect(await documentStoreUpdatePage.getEntityIdInput()).to.eq('5');
        expect(await documentStoreUpdatePage.getEntityNameInput()).to.eq('entityName');
        expect(await documentStoreUpdatePage.getPathInput()).to.eq('path');
        expect(await documentStoreUpdatePage.getCategoryInput()).to.eq('5');
        expect(await documentStoreUpdatePage.getSubCategoryInput()).to.eq('5');
        expect(await documentStoreUpdatePage.getVersionInput()).to.eq('version');
        await documentStoreUpdatePage.save();
        expect(await documentStoreUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await documentStoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last DocumentStore', async () => {
        const nbButtonsBeforeDelete = await documentStoreComponentsPage.countDeleteButtons();
        await documentStoreComponentsPage.clickOnLastDeleteButton();

        documentStoreDeleteDialog = new DocumentStoreDeleteDialog();
        expect(await documentStoreDeleteDialog.getDialogTitle()).to.eq('promalyV5App.documentStore.delete.question');
        await documentStoreDeleteDialog.clickOnConfirmButton();

        expect(await documentStoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
