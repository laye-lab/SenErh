import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CongeComponentsPage, CongeDeleteDialog, CongeUpdatePage } from './conge.page-object';

const expect = chai.expect;

describe('Conge e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let congeComponentsPage: CongeComponentsPage;
  let congeUpdatePage: CongeUpdatePage;
  let congeDeleteDialog: CongeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Conges', async () => {
    await navBarPage.goToEntity('conge');
    congeComponentsPage = new CongeComponentsPage();
    await browser.wait(ec.visibilityOf(congeComponentsPage.title), 5000);
    expect(await congeComponentsPage.getTitle()).to.eq('senErhApp.conge.home.title');
    await browser.wait(ec.or(ec.visibilityOf(congeComponentsPage.entities), ec.visibilityOf(congeComponentsPage.noResult)), 1000);
  });

  it('should load create Conge page', async () => {
    await congeComponentsPage.clickOnCreateButton();
    congeUpdatePage = new CongeUpdatePage();
    expect(await congeUpdatePage.getPageTitle()).to.eq('senErhApp.conge.home.createOrEditLabel');
    await congeUpdatePage.cancel();
  });

  it('should create and save Conges', async () => {
    const nbButtonsBeforeCreate = await congeComponentsPage.countDeleteButtons();

    await congeComponentsPage.clickOnCreateButton();

    await promise.all([
      congeUpdatePage.setIdCongeInput('5'),
      congeUpdatePage.setDateDebutInput('dateDebut'),
      congeUpdatePage.validationStepSelectLastOption(),
      congeUpdatePage.agentsSelectLastOption(),
    ]);

    expect(await congeUpdatePage.getIdCongeInput()).to.eq('5', 'Expected idConge value to be equals to 5');
    expect(await congeUpdatePage.getDateDebutInput()).to.eq('dateDebut', 'Expected DateDebut value to be equals to dateDebut');

    await congeUpdatePage.save();
    expect(await congeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await congeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Conge', async () => {
    const nbButtonsBeforeDelete = await congeComponentsPage.countDeleteButtons();
    await congeComponentsPage.clickOnLastDeleteButton();

    congeDeleteDialog = new CongeDeleteDialog();
    expect(await congeDeleteDialog.getDialogTitle()).to.eq('senErhApp.conge.delete.question');
    await congeDeleteDialog.clickOnConfirmButton();

    expect(await congeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
