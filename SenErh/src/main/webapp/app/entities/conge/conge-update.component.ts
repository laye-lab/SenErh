import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IConge, Conge } from 'app/shared/model/conge.model';
import { CongeService } from './conge.service';
import { IValidationStep } from 'app/shared/model/validation-step.model';
import { ValidationStepService } from 'app/entities/validation-step/validation-step.service';
import { IAgents } from 'app/shared/model/agents.model';
import { AgentsService } from 'app/entities/agents/agents.service';

type SelectableEntity = IValidationStep | IAgents;

@Component({
  selector: 'jhi-conge-update',
  templateUrl: './conge-update.component.html',
})
export class CongeUpdateComponent implements OnInit {
  isSaving = false;
  validationsteps: IValidationStep[] = [];
  agents: IAgents[] = [];

  editForm = this.fb.group({
    id: [],
    idConge: [null, [Validators.required]],
    dateDebut: [null, [Validators.required]],
    validationStep: [],
    agents: [],
  });

  constructor(
    protected congeService: CongeService,
    protected validationStepService: ValidationStepService,
    protected agentsService: AgentsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conge }) => {
      this.updateForm(conge);

      this.validationStepService
        .query({ filter: 'conge-is-null' })
        .pipe(
          map((res: HttpResponse<IValidationStep[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IValidationStep[]) => {
          if (!conge.validationStep || !conge.validationStep.id) {
            this.validationsteps = resBody;
          } else {
            this.validationStepService
              .find(conge.validationStep.id)
              .pipe(
                map((subRes: HttpResponse<IValidationStep>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IValidationStep[]) => (this.validationsteps = concatRes));
          }
        });

      this.agentsService.query().subscribe((res: HttpResponse<IAgents[]>) => (this.agents = res.body || []));
    });
  }

  updateForm(conge: IConge): void {
    this.editForm.patchValue({
      id: conge.id,
      idConge: conge.idConge,
      dateDebut: conge.dateDebut,
      validationStep: conge.validationStep,
      agents: conge.agents,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conge = this.createFromForm();
    if (conge.id !== undefined) {
      this.subscribeToSaveResponse(this.congeService.update(conge));
    } else {
      this.subscribeToSaveResponse(this.congeService.create(conge));
    }
  }

  private createFromForm(): IConge {
    return {
      ...new Conge(),
      id: this.editForm.get(['id'])!.value,
      idConge: this.editForm.get(['idConge'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      validationStep: this.editForm.get(['validationStep'])!.value,
      agents: this.editForm.get(['agents'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConge>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
