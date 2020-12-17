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

  validationStepSelect = element(by.id('field_validationStep'));
  agentsSelect = element(by.id('field_agents'));

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

  async validationStepSelectLastOption(): Promise<void> {
    await this.validationStepSelect.all(by.tagName('option')).last().click();
  }

  async validationStepSelectOption(option: string): Promise<void> {
    await this.validationStepSelect.sendKeys(option);
  }

  getValidationStepSelect(): ElementFinder {
    return this.validationStepSelect;
  }

  async getValidationStepSelectedOption(): Promise<string> {
    return await this.validationStepSelect.element(by.css('option:checked')).getText();
  }

  async agentsSelectLastOption(): Promise<void> {
    await this.agentsSelect.all(by.tagName('option')).last().click();
  }

  async agentsSelectOption(option: string): Promise<void> {
    await this.agentsSelect.sendKeys(option);
  }

  getAgentsSelect(): ElementFinder {
    return this.agentsSelect;
  }

  async getAgentsSelectedOption(): Promise<string> {
    return await this.agentsSelect.element(by.css('option:checked')).getText();
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
