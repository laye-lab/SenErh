import { element, by, ElementFinder } from 'protractor';

export class HistoriqueCongeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-historique-conge div table .btn-danger'));
  title = element.all(by.css('jhi-historique-conge div h2#page-heading span')).first();
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

export class HistoriqueCongeUpdatePage {
  pageTitle = element(by.id('jhi-historique-conge-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  dateDernierDepartInput = element(by.id('field_dateDernierDepart'));
  dateDernierRetourInput = element(by.id('field_dateDernierRetour'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDateDernierDepartInput(dateDernierDepart: string): Promise<void> {
    await this.dateDernierDepartInput.sendKeys(dateDernierDepart);
  }

  async getDateDernierDepartInput(): Promise<string> {
    return await this.dateDernierDepartInput.getAttribute('value');
  }

  async setDateDernierRetourInput(dateDernierRetour: string): Promise<void> {
    await this.dateDernierRetourInput.sendKeys(dateDernierRetour);
  }

  async getDateDernierRetourInput(): Promise<string> {
    return await this.dateDernierRetourInput.getAttribute('value');
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

export class HistoriqueCongeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-historiqueConge-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-historiqueConge'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
