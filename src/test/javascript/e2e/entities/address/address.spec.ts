/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AddressComponentsPage, AddressDeleteDialog, AddressUpdatePage } from './address.page-object';

const expect = chai.expect;

describe('Address e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let addressUpdatePage: AddressUpdatePage;
    let addressComponentsPage: AddressComponentsPage;
    let addressDeleteDialog: AddressDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Addresses', async () => {
        await navBarPage.goToEntity('address');
        addressComponentsPage = new AddressComponentsPage();
        expect(await addressComponentsPage.getTitle()).to.eq('promalyV5App.address.home.title');
    });

    it('should load create Address page', async () => {
        await addressComponentsPage.clickOnCreateButton();
        addressUpdatePage = new AddressUpdatePage();
        expect(await addressUpdatePage.getPageTitle()).to.eq('promalyV5App.address.home.createOrEditLabel');
        await addressUpdatePage.cancel();
    });

    it('should create and save Addresses', async () => {
        const nbButtonsBeforeCreate = await addressComponentsPage.countDeleteButtons();

        await addressComponentsPage.clickOnCreateButton();
        await promise.all([
            addressUpdatePage.setLocationIdInput('5'),
            addressUpdatePage.setLocationTypeIdInput('5'),
            addressUpdatePage.setAddressTypeIdInput('5'),
            addressUpdatePage.setDescriptionInput('description'),
            addressUpdatePage.setAddressline1Input('addressline1'),
            addressUpdatePage.setAddressline2Input('addressline2'),
            addressUpdatePage.setAddressline3Input('addressline3'),
            addressUpdatePage.setCityInput('city'),
            addressUpdatePage.setStateInput('state'),
            addressUpdatePage.setPostalCodeInput('postalCode'),
            addressUpdatePage.setCountryCodeInput('countryCode'),
            addressUpdatePage.setCountryInput('country'),
            addressUpdatePage.setPhoneNumberInput('phoneNumber'),
            addressUpdatePage.setTollFreeNumberInput('tollFreeNumber'),
            addressUpdatePage.setAfterHoursNumberInput('afterHoursNumber'),
            addressUpdatePage.setFaxNumberInput('faxNumber'),
            addressUpdatePage.setEmailAddressInput('emailAddress'),
            addressUpdatePage.setPhonePrefixInput('phonePrefix'),
            addressUpdatePage.setGeocodeInput('geocode'),
            addressUpdatePage.setVersionInput('version'),
            addressUpdatePage.companySelectLastOption()
        ]);
        expect(await addressUpdatePage.getLocationIdInput()).to.eq('5');
        expect(await addressUpdatePage.getLocationTypeIdInput()).to.eq('5');
        expect(await addressUpdatePage.getAddressTypeIdInput()).to.eq('5');
        expect(await addressUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await addressUpdatePage.getAddressline1Input()).to.eq('addressline1');
        expect(await addressUpdatePage.getAddressline2Input()).to.eq('addressline2');
        expect(await addressUpdatePage.getAddressline3Input()).to.eq('addressline3');
        expect(await addressUpdatePage.getCityInput()).to.eq('city');
        expect(await addressUpdatePage.getStateInput()).to.eq('state');
        expect(await addressUpdatePage.getPostalCodeInput()).to.eq('postalCode');
        expect(await addressUpdatePage.getCountryCodeInput()).to.eq('countryCode');
        expect(await addressUpdatePage.getCountryInput()).to.eq('country');
        expect(await addressUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber');
        expect(await addressUpdatePage.getTollFreeNumberInput()).to.eq('tollFreeNumber');
        expect(await addressUpdatePage.getAfterHoursNumberInput()).to.eq('afterHoursNumber');
        expect(await addressUpdatePage.getFaxNumberInput()).to.eq('faxNumber');
        expect(await addressUpdatePage.getEmailAddressInput()).to.eq('emailAddress');
        expect(await addressUpdatePage.getPhonePrefixInput()).to.eq('phonePrefix');
        expect(await addressUpdatePage.getGeocodeInput()).to.eq('geocode');
        expect(await addressUpdatePage.getVersionInput()).to.eq('version');
        await addressUpdatePage.save();
        expect(await addressUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await addressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Address', async () => {
        const nbButtonsBeforeDelete = await addressComponentsPage.countDeleteButtons();
        await addressComponentsPage.clickOnLastDeleteButton();

        addressDeleteDialog = new AddressDeleteDialog();
        expect(await addressDeleteDialog.getDialogTitle()).to.eq('promalyV5App.address.delete.question');
        await addressDeleteDialog.clickOnConfirmButton();

        expect(await addressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
