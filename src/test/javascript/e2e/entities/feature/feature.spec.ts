/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FeatureComponentsPage, FeatureDeleteDialog, FeatureUpdatePage } from './feature.page-object';

const expect = chai.expect;

describe('Feature e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let featureUpdatePage: FeatureUpdatePage;
    let featureComponentsPage: FeatureComponentsPage;
    let featureDeleteDialog: FeatureDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Features', async () => {
        await navBarPage.goToEntity('feature');
        featureComponentsPage = new FeatureComponentsPage();
        expect(await featureComponentsPage.getTitle()).to.eq('promalyV5App.feature.home.title');
    });

    it('should load create Feature page', async () => {
        await featureComponentsPage.clickOnCreateButton();
        featureUpdatePage = new FeatureUpdatePage();
        expect(await featureUpdatePage.getPageTitle()).to.eq('promalyV5App.feature.home.createOrEditLabel');
        await featureUpdatePage.cancel();
    });

    it('should create and save Features', async () => {
        const nbButtonsBeforeCreate = await featureComponentsPage.countDeleteButtons();

        await featureComponentsPage.clickOnCreateButton();
        await promise.all([featureUpdatePage.setDescriptionInput('description'), featureUpdatePage.typeSelectLastOption()]);
        expect(await featureUpdatePage.getDescriptionInput()).to.eq('description');
        await featureUpdatePage.save();
        expect(await featureUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await featureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Feature', async () => {
        const nbButtonsBeforeDelete = await featureComponentsPage.countDeleteButtons();
        await featureComponentsPage.clickOnLastDeleteButton();

        featureDeleteDialog = new FeatureDeleteDialog();
        expect(await featureDeleteDialog.getDialogTitle()).to.eq('promalyV5App.feature.delete.question');
        await featureDeleteDialog.clickOnConfirmButton();

        expect(await featureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
