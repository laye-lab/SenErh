import { element, by, ElementFinder } from 'protractor';

export class AgentsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-agents div table .btn-danger'));
  title = element.all(by.css('jhi-agents div h2#page-heading span')).first();
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

export class AgentsUpdatePage {
  pageTitle = element(by.id('jhi-agents-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomInput = element(by.id('field_nom'));
  equipeInput = element(by.id('field_equipe'));
  directionInput = element(by.id('field_direction'));
  etablissementInput = element(by.id('field_etablissement'));
  fonctionInput = element(by.id('field_fonction'));
  statutSelect = element(by.id('field_statut'));
  affectationInput = element(by.id('field_affectation'));
  tauxInput = element(by.id('field_taux'));

  historiqueCongeSelect = element(by.id('field_historiqueConge'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomInput(nom: string): Promise<void> {
    await this.nomInput.sendKeys(nom);
  }

  async getNomInput(): Promise<string> {
    return await this.nomInput.getAttribute('value');
  }

  async setEquipeInput(equipe: string): Promise<void> {
    await this.equipeInput.sendKeys(equipe);
  }

  async getEquipeInput(): Promise<string> {
    return await this.equipeInput.getAttribute('value');
  }

  async setDirectionInput(direction: string): Promise<void> {
    await this.directionInput.sendKeys(direction);
  }

  async getDirectionInput(): Promise<string> {
    return await this.directionInput.getAttribute('value');
  }

  async setEtablissementInput(etablissement: string): Promise<void> {
    await this.etablissementInput.sendKeys(etablissement);
  }

  async getEtablissementInput(): Promise<string> {
    return await this.etablissementInput.getAttribute('value');
  }

  async setFonctionInput(fonction: string): Promise<void> {
    await this.fonctionInput.sendKeys(fonction);
  }

  async getFonctionInput(): Promise<string> {
    return await this.fonctionInput.getAttribute('value');
  }

  async setStatutSelect(statut: string): Promise<void> {
    await this.statutSelect.sendKeys(statut);
  }

  async getStatutSelect(): Promise<string> {
    return await this.statutSelect.element(by.css('option:checked')).getText();
  }

  async statutSelectLastOption(): Promise<void> {
    await this.statutSelect.all(by.tagName('option')).last().click();
  }

  async setAffectationInput(affectation: string): Promise<void> {
    await this.affectationInput.sendKeys(affectation);
  }

  async getAffectationInput(): Promise<string> {
    return await this.affectationInput.getAttribute('value');
  }

  async setTauxInput(taux: string): Promise<void> {
    await this.tauxInput.sendKeys(taux);
  }

  async getTauxInput(): Promise<string> {
    return await this.tauxInput.getAttribute('value');
  }

  async historiqueCongeSelectLastOption(): Promise<void> {
    await this.historiqueCongeSelect.all(by.tagName('option')).last().click();
  }

  async historiqueCongeSelectOption(option: string): Promise<void> {
    await this.historiqueCongeSelect.sendKeys(option);
  }

  getHistoriqueCongeSelect(): ElementFinder {
    return this.historiqueCongeSelect;
  }

  async getHistoriqueCongeSelectedOption(): Promise<string> {
    return await this.historiqueCongeSelect.element(by.css('option:checked')).getText();
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

export class AgentsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-agents-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-agents'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
