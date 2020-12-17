import { element, by, ElementFinder } from 'protractor';

export class CongeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-conge div table .btn-danger'));
  title = element.all(by.css('jhi-conge div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class CongeUpdatePage {
  pageTitle = element(by.id('jhi-conge-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idCongeInput = element(by.id('field_idConge'));
  dateDebutInput = element(by.id('field_dateDebut'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdCongeInput(idConge: string): Promise<void> {
    await this.idCongeInput.sendKeys(idConge);
  }

  async getIdCongeInput(): Promise<string> {
    return await this.idCongeInput.getAttribute('value');
  }

  async setDateDebutInput(dateDebut: string): Promise<void> {
    await this.dateDebutInput.sendKeys(dateDebut);
  }

  async getDateDebutInput(): Promise<string> {
    return await this.dateDebutInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CongeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-conge-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-conge'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
