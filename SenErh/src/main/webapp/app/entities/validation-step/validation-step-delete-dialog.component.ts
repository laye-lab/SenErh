import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IValidationStep } from 'app/shared/model/validation-step.model';
import { ValidationStepService } from './validation-step.service';

@Component({
  templateUrl: './validation-step-delete-dialog.component.html',
})
export class ValidationStepDeleteDialogComponent {
  validationStep?: IValidationStep;

  constructor(
    protected validationStepService: ValidationStepService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.validationStepService.delete(id).subscribe(() => {
      this.eventManager.broadcast('validationStepListModification');
      this.activeModal.close();
    });
  }
}
