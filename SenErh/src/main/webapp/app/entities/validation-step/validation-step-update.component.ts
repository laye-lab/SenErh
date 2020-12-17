import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IValidationStep, ValidationStep } from 'app/shared/model/validation-step.model';
import { ValidationStepService } from './validation-step.service';
import { IConge } from 'app/shared/model/conge.model';
import { CongeService } from 'app/entities/conge/conge.service';

@Component({
  selector: 'jhi-validation-step-update',
  templateUrl: './validation-step-update.component.html',
})
export class ValidationStepUpdateComponent implements OnInit {
  isSaving = false;
  conges: IConge[] = [];

  editForm = this.fb.group({
    id: [],
    step: [null, [Validators.required]],
    conge: [],
  });

  constructor(
    protected validationStepService: ValidationStepService,
    protected congeService: CongeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ validationStep }) => {
      this.updateForm(validationStep);

      this.congeService
        .query({ filter: 'validationstep-is-null' })
        .pipe(
          map((res: HttpResponse<IConge[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IConge[]) => {
          if (!validationStep.conge || !validationStep.conge.id) {
            this.conges = resBody;
          } else {
            this.congeService
              .find(validationStep.conge.id)
              .pipe(
                map((subRes: HttpResponse<IConge>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IConge[]) => (this.conges = concatRes));
          }
        });
    });
  }

  updateForm(validationStep: IValidationStep): void {
    this.editForm.patchValue({
      id: validationStep.id,
      step: validationStep.step,
      conge: validationStep.conge,
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
      conge: this.editForm.get(['conge'])!.value,
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

  trackById(index: number, item: IConge): any {
    return item.id;
  }
}
