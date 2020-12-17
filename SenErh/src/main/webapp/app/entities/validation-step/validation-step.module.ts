import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenErhSharedModule } from 'app/shared/shared.module';
import { ValidationStepComponent } from './validation-step.component';
import { ValidationStepDetailComponent } from './validation-step-detail.component';
import { ValidationStepUpdateComponent } from './validation-step-update.component';
import { ValidationStepDeleteDialogComponent } from './validation-step-delete-dialog.component';
import { validationStepRoute } from './validation-step.route';

@NgModule({
  imports: [SenErhSharedModule, RouterModule.forChild(validationStepRoute)],
  declarations: [
    ValidationStepComponent,
    ValidationStepDetailComponent,
    ValidationStepUpdateComponent,
    ValidationStepDeleteDialogComponent,
  ],
  entryComponents: [ValidationStepDeleteDialogComponent],
})
export class SenErhValidationStepModule {}
