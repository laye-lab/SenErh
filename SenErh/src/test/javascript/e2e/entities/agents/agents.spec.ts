import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AgentsComponentsPage, AgentsDeleteDialog, AgentsUpdatePage } from './agents.page-object';

const expect = chai.expect;

describe('Agents e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let agentsComponentsPage: AgentsComponentsPage;
  let agentsUpdatePage: AgentsUpdatePage;
  let agentsDeleteDialog: AgentsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Agents', async () => {
    await navBarPage.goToEntity('agents');
    agentsComponentsPage = new AgentsComponentsPage();
    await browser.wait(ec.visibilityOf(agentsComponentsPage.title), 5000);
    expect(await agentsComponentsPage.getTitle()).to.eq('senErhApp.agents.home.title');
    await browser.wait(ec.or(ec.visibilityOf(agentsComponentsPage.entities), ec.visibilityOf(agentsComponentsPage.noResult)), 1000);
  });

  it('should load create Agents page', async () => {
    await agentsComponentsPage.clickOnCreateButton();
    agentsUpdatePage = new AgentsUpdatePage();
    expect(await agentsUpdatePage.getPageTitle()).to.eq('senErhApp.agents.home.createOrEditLabel');
    await agentsUpdatePage.cancel();
  });

  it('should create and save Agents', async () => {
    const nbButtonsBeforeCreate = await agentsComponentsPage.countDeleteButtons();

    await agentsComponentsPage.clickOnCreateButton();

    await promise.all([
      agentsUpdatePage.setMatriceInput('matrice'),
      agentsUpdatePage.setNomInput('nom'),
      agentsUpdatePage.setEquipeInput('5'),
      agentsUpdatePage.setDirectionInput('direction'),
      agentsUpdatePage.setEtablissementInput('etablissement'),
      agentsUpdatePage.setFonctionInput('fonction'),
      agentsUpdatePage.statutSelectLastOption(),
      agentsUpdatePage.setAffectationInput('affectation'),
      agentsUpdatePage.setTauxInput('5'),
      agentsUpdatePage.congeSelectLastOption(),
    ]);

    expect(await agentsUpdatePage.getMatriceInput()).to.eq('matrice', 'Expected Matrice value to be equals to matrice');
    expect(await agentsUpdatePage.getNomInput()).to.eq('nom', 'Expected Nom value to be equals to nom');
    expect(await agentsUpdatePage.getEquipeInput()).to.eq('5', 'Expected equipe value to be equals to 5');
    expect(await agentsUpdatePage.getDirectionInput()).to.eq('direction', 'Expected Direction value to be equals to direction');
    expect(await agentsUpdatePage.getEtablissementInput()).to.eq(
      'etablissement',
      'Expected Etablissement value to be equals to etablissement'
    );
    expect(await agentsUpdatePage.getFonctionInput()).to.eq('fonction', 'Expected Fonction value to be equals to fonction');
    expect(await agentsUpdatePage.getAffectationInput()).to.eq('affectation', 'Expected Affectation value to be equals to affectation');
    expect(await agentsUpdatePage.getTauxInput()).to.eq('5', 'Expected taux value to be equals to 5');

    await agentsUpdatePage.save();
    expect(await agentsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await agentsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Agents', async () => {
    const nbButtonsBeforeDelete = await agentsComponentsPage.countDeleteButtons();
    await agentsComponentsPage.clickOnLastDeleteButton();

    agentsDeleteDialog = new AgentsDeleteDialog();
    expect(await agentsDeleteDialog.getDialogTitle()).to.eq('senErhApp.agents.delete.question');
    await agentsDeleteDialog.clickOnConfirmButton();

    expect(await agentsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
