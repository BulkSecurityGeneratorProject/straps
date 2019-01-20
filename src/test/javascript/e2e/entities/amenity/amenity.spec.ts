/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AmenityComponentsPage, AmenityDeleteDialog, AmenityUpdatePage } from './amenity.page-object';

const expect = chai.expect;

describe('Amenity e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let amenityUpdatePage: AmenityUpdatePage;
    let amenityComponentsPage: AmenityComponentsPage;
    let amenityDeleteDialog: AmenityDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Amenities', async () => {
        await navBarPage.goToEntity('amenity');
        amenityComponentsPage = new AmenityComponentsPage();
        expect(await amenityComponentsPage.getTitle()).to.eq('promalyV5App.amenity.home.title');
    });

    it('should load create Amenity page', async () => {
        await amenityComponentsPage.clickOnCreateButton();
        amenityUpdatePage = new AmenityUpdatePage();
        expect(await amenityUpdatePage.getPageTitle()).to.eq('promalyV5App.amenity.home.createOrEditLabel');
        await amenityUpdatePage.cancel();
    });

    it('should create and save Amenities', async () => {
        const nbButtonsBeforeCreate = await amenityComponentsPage.countDeleteButtons();

        await amenityComponentsPage.clickOnCreateButton();
        await promise.all([
            amenityUpdatePage.setDescriptionInput('description'),
            amenityUpdatePage.propertySelectLastOption(),
            amenityUpdatePage.buildingSelectLastOption(),
            amenityUpdatePage.propertyUnitSelectLastOption()
        ]);
        expect(await amenityUpdatePage.getDescriptionInput()).to.eq('description');
        await amenityUpdatePage.save();
        expect(await amenityUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await amenityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Amenity', async () => {
        const nbButtonsBeforeDelete = await amenityComponentsPage.countDeleteButtons();
        await amenityComponentsPage.clickOnLastDeleteButton();

        amenityDeleteDialog = new AmenityDeleteDialog();
        expect(await amenityDeleteDialog.getDialogTitle()).to.eq('promalyV5App.amenity.delete.question');
        await amenityDeleteDialog.clickOnConfirmButton();

        expect(await amenityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
