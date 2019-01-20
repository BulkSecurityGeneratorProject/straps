import { element, by, ElementFinder } from 'protractor';

export class NotesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-notes div table .btn-danger'));
    title = element.all(by.css('jhi-notes div h2#page-heading span')).first();

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

export class NotesUpdatePage {
    pageTitle = element(by.id('jhi-notes-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    noteInput = element(by.id('field_note'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNoteInput(note) {
        await this.noteInput.sendKeys(note);
    }

    async getNoteInput() {
        return this.noteInput.getAttribute('value');
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

export class NotesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-notes-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-notes'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
