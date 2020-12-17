import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ValidationStepComponentsPage, ValidationStepDeleteDialog, ValidationStepUpdatePage } from './validation-step.page-object';

const expect = chai.expect;

describe('ValidationStep e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let validationStepComponentsPage: ValidationStepComponentsPage;
  let validationStepUpdatePage: ValidationStepUpdatePage;
  let validationStepDeleteDialog: ValidationStepDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ValidationSteps', async () => {
    await navBarPage.goToEntity('validation-step');
    validationStepComponentsPage = new ValidationStepComponentsPage();
    await browser.wait(ec.visibilityOf(validationStepComponentsPage.title), 5000);
    expect(await validationStepComponentsPage.getTitle()).to.eq('senErhApp.validationStep.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(validationStepComponentsPage.entities), ec.visibilityOf(validationStepComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ValidationStep page', async () => {
    await validationStepComponentsPage.clickOnCreateButton();
    validationStepUpdatePage = new ValidationStepUpdatePage();
    expect(await validationStepUpdatePage.getPageTitle()).to.eq('senErhApp.validationStep.home.createOrEditLabel');
    await validationStepUpdatePage.cancel();
  });

  it('should create and save ValidationSteps', async () => {
    const nbButtonsBeforeCreate = await validationStepComponentsPage.countDeleteButtons();

    await validationStepComponentsPage.clickOnCreateButton();

    await promise.all([validationStepUpdatePage.setStepInput('5'), validationStepUpdatePage.congeSelectLastOption()]);

    expect(await validationStepUpdatePage.getStepInput()).to.eq('5', 'Expected step value to be equals to 5');

    await validationStepUpdatePage.save();
    expect(await validationStepUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await validationStepComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ValidationStep', async () => {
    const nbButtonsBeforeDelete = await validationStepComponentsPage.countDeleteButtons();
    await validationStepComponentsPage.clickOnLastDeleteButton();

    validationStepDeleteDialog = new ValidationStepDeleteDialog();
    expect(await validationStepDeleteDialog.getDialogTitle()).to.eq('senErhApp.validationStep.delete.question');
    await validationStepDeleteDialog.clickOnConfirmButton();

    expect(await validationStepComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
