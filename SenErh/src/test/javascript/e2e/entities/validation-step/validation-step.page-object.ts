import { element, by, ElementFinder } from 'protractor';

export class ValidationStepComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-validation-step div table .btn-danger'));
  title = element.all(by.css('jhi-validation-step div h2#page-heading span')).first();
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

export class ValidationStepUpdatePage {
  pageTitle = element(by.id('jhi-validation-step-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  stepInput = element(by.id('field_step'));

  congeSelect = element(by.id('field_conge'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setStepInput(step: string): Promise<void> {
    await this.stepInput.sendKeys(step);
  }

  async getStepInput(): Promise<string> {
    return await this.stepInput.getAttribute('value');
  }

  async congeSelectLastOption(): Promise<void> {
    await this.congeSelect.all(by.tagName('option')).last().click();
  }

  async congeSelectOption(option: string): Promise<void> {
    await this.congeSelect.sendKeys(option);
  }

  getCongeSelect(): ElementFinder {
    return this.congeSelect;
  }

  async getCongeSelectedOption(): Promise<string> {
    return await this.congeSelect.element(by.css('option:checked')).getText();
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

export class ValidationStepDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-validationStep-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-validationStep'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
