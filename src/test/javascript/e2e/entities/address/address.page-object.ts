import { element, by, ElementFinder } from 'protractor';

export class AddressComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-address div table .btn-danger'));
    title = element.all(by.css('jhi-address div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AddressUpdatePage {
    pageTitle = element(by.id('jhi-address-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    locationIdInput = element(by.id('field_locationId'));
    locationTypeIdInput = element(by.id('field_locationTypeId'));
    addressTypeIdInput = element(by.id('field_addressTypeId'));
    descriptionInput = element(by.id('field_description'));
    addressline1Input = element(by.id('field_addressline1'));
    addressline2Input = element(by.id('field_addressline2'));
    addressline3Input = element(by.id('field_addressline3'));
    cityInput = element(by.id('field_city'));
    stateInput = element(by.id('field_state'));
    postalCodeInput = element(by.id('field_postalCode'));
    countryCodeInput = element(by.id('field_countryCode'));
    countryInput = element(by.id('field_country'));
    phoneNumberInput = element(by.id('field_phoneNumber'));
    tollFreeNumberInput = element(by.id('field_tollFreeNumber'));
    afterHoursNumberInput = element(by.id('field_afterHoursNumber'));
    faxNumberInput = element(by.id('field_faxNumber'));
    emailAddressInput = element(by.id('field_emailAddress'));
    phonePrefixInput = element(by.id('field_phonePrefix'));
    geocodeInput = element(by.id('field_geocode'));
    versionInput = element(by.id('field_version'));
    companySelect = element(by.id('field_company'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLocationIdInput(locationId) {
        await this.locationIdInput.sendKeys(locationId);
    }

    async getLocationIdInput() {
        return this.locationIdInput.getAttribute('value');
    }

    async setLocationTypeIdInput(locationTypeId) {
        await this.locationTypeIdInput.sendKeys(locationTypeId);
    }

    async getLocationTypeIdInput() {
        return this.locationTypeIdInput.getAttribute('value');
    }

    async setAddressTypeIdInput(addressTypeId) {
        await this.addressTypeIdInput.sendKeys(addressTypeId);
    }

    async getAddressTypeIdInput() {
        return this.addressTypeIdInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setAddressline1Input(addressline1) {
        await this.addressline1Input.sendKeys(addressline1);
    }

    async getAddressline1Input() {
        return this.addressline1Input.getAttribute('value');
    }

    async setAddressline2Input(addressline2) {
        await this.addressline2Input.sendKeys(addressline2);
    }

    async getAddressline2Input() {
        return this.addressline2Input.getAttribute('value');
    }

    async setAddressline3Input(addressline3) {
        await this.addressline3Input.sendKeys(addressline3);
    }

    async getAddressline3Input() {
        return this.addressline3Input.getAttribute('value');
    }

    async setCityInput(city) {
        await this.cityInput.sendKeys(city);
    }

    async getCityInput() {
        return this.cityInput.getAttribute('value');
    }

    async setStateInput(state) {
        await this.stateInput.sendKeys(state);
    }

    async getStateInput() {
        return this.stateInput.getAttribute('value');
    }

    async setPostalCodeInput(postalCode) {
        await this.postalCodeInput.sendKeys(postalCode);
    }

    async getPostalCodeInput() {
        return this.postalCodeInput.getAttribute('value');
    }

    async setCountryCodeInput(countryCode) {
        await this.countryCodeInput.sendKeys(countryCode);
    }

    async getCountryCodeInput() {
        return this.countryCodeInput.getAttribute('value');
    }

    async setCountryInput(country) {
        await this.countryInput.sendKeys(country);
    }

    async getCountryInput() {
        return this.countryInput.getAttribute('value');
    }

    async setPhoneNumberInput(phoneNumber) {
        await this.phoneNumberInput.sendKeys(phoneNumber);
    }

    async getPhoneNumberInput() {
        return this.phoneNumberInput.getAttribute('value');
    }

    async setTollFreeNumberInput(tollFreeNumber) {
        await this.tollFreeNumberInput.sendKeys(tollFreeNumber);
    }

    async getTollFreeNumberInput() {
        return this.tollFreeNumberInput.getAttribute('value');
    }

    async setAfterHoursNumberInput(afterHoursNumber) {
        await this.afterHoursNumberInput.sendKeys(afterHoursNumber);
    }

    async getAfterHoursNumberInput() {
        return this.afterHoursNumberInput.getAttribute('value');
    }

    async setFaxNumberInput(faxNumber) {
        await this.faxNumberInput.sendKeys(faxNumber);
    }

    async getFaxNumberInput() {
        return this.faxNumberInput.getAttribute('value');
    }

    async setEmailAddressInput(emailAddress) {
        await this.emailAddressInput.sendKeys(emailAddress);
    }

    async getEmailAddressInput() {
        return this.emailAddressInput.getAttribute('value');
    }

    async setPhonePrefixInput(phonePrefix) {
        await this.phonePrefixInput.sendKeys(phonePrefix);
    }

    async getPhonePrefixInput() {
        return this.phonePrefixInput.getAttribute('value');
    }

    async setGeocodeInput(geocode) {
        await this.geocodeInput.sendKeys(geocode);
    }

    async getGeocodeInput() {
        return this.geocodeInput.getAttribute('value');
    }

    async setVersionInput(version) {
        await this.versionInput.sendKeys(version);
    }

    async getVersionInput() {
        return this.versionInput.getAttribute('value');
    }

    async companySelectLastOption() {
        await this.companySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async companySelectOption(option) {
        await this.companySelect.sendKeys(option);
    }

    getCompanySelect(): ElementFinder {
        return this.companySelect;
    }

    async getCompanySelectedOption() {
        return this.companySelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class AddressDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-address-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-address'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
