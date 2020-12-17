import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { HistoriqueCongeComponentsPage, HistoriqueCongeDeleteDialog, HistoriqueCongeUpdatePage } from './historique-conge.page-object';

const expect = chai.expect;

describe('HistoriqueConge e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let historiqueCongeComponentsPage: HistoriqueCongeComponentsPage;
  let historiqueCongeUpdatePage: HistoriqueCongeUpdatePage;
  let historiqueCongeDeleteDialog: HistoriqueCongeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load HistoriqueConges', async () => {
    await navBarPage.goToEntity('historique-conge');
    historiqueCongeComponentsPage = new HistoriqueCongeComponentsPage();
    await browser.wait(ec.visibilityOf(historiqueCongeComponentsPage.title), 5000);
    expect(await historiqueCongeComponentsPage.getTitle()).to.eq('senErhApp.historiqueConge.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(historiqueCongeComponentsPage.entities), ec.visibilityOf(historiqueCongeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create HistoriqueConge page', async () => {
    await historiqueCongeComponentsPage.clickOnCreateButton();
    historiqueCongeUpdatePage = new HistoriqueCongeUpdatePage();
    expect(await historiqueCongeUpdatePage.getPageTitle()).to.eq('senErhApp.historiqueConge.home.createOrEditLabel');
    await historiqueCongeUpdatePage.cancel();
  });

  it('should create and save HistoriqueConges', async () => {
    const nbButtonsBeforeCreate = await historiqueCongeComponentsPage.countDeleteButtons();

    await historiqueCongeComponentsPage.clickOnCreateButton();

    await promise.all([
      historiqueCongeUpdatePage.setDateDernierDepartInput('2000-12-31'),
      historiqueCongeUpdatePage.setDateDernierRetourInput('2000-12-31'),
    ]);

    expect(await historiqueCongeUpdatePage.getDateDernierDepartInput()).to.eq(
      '2000-12-31',
      'Expected dateDernierDepart value to be equals to 2000-12-31'
    );
    expect(await historiqueCongeUpdatePage.getDateDernierRetourInput()).to.eq(
      '2000-12-31',
      'Expected dateDernierRetour value to be equals to 2000-12-31'
    );

    await historiqueCongeUpdatePage.save();
    expect(await historiqueCongeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await historiqueCongeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last HistoriqueConge', async () => {
    const nbButtonsBeforeDelete = await historiqueCongeComponentsPage.countDeleteButtons();
    await historiqueCongeComponentsPage.clickOnLastDeleteButton();

    historiqueCongeDeleteDialog = new HistoriqueCongeDeleteDialog();
    expect(await historiqueCongeDeleteDialog.getDialogTitle()).to.eq('senErhApp.historiqueConge.delete.question');
    await historiqueCongeDeleteDialog.clickOnConfirmButton();

    expect(await historiqueCongeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
