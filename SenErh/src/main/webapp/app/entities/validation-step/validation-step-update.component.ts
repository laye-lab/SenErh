import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IValidationStep, ValidationStep } from 'app/shared/model/validation-step.model';
import { ValidationStepService } from './validation-step.service';

@Component({
  selector: 'jhi-validation-step-update',
  templateUrl: './validation-step-update.component.html',
})
export class ValidationStepUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    step: [null, [Validators.required]],
  });

  constructor(protected validationStepService: ValidationStepService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ validationStep }) => {
      this.updateForm(validationStep);
    });
  }

  updateForm(validationStep: IValidationStep): void {
    this.editForm.patchValue({
      id: validationStep.id,
      step: validationStep.step,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const validationStep = this.createFromForm();
    if (validationStep.id !== undefined) {
      this.subscribeToSaveResponse(this.validationStepService.update(validationStep));
    } else {
      this.subscribeToSaveResponse(this.validationStepService.create(validationStep));
    }
  }

  private createFromForm(): IValidationStep {
    return {
      ...new ValidationStep(),
      id: this.editForm.get(['id'])!.value,
      step: this.editForm.get(['step'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IValidationStep>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
