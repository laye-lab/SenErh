import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHistoriqueConge, HistoriqueConge } from 'app/shared/model/historique-conge.model';
import { HistoriqueCongeService } from './historique-conge.service';

@Component({
  selector: 'jhi-historique-conge-update',
  templateUrl: './historique-conge-update.component.html',
})
export class HistoriqueCongeUpdateComponent implements OnInit {
  isSaving = false;
  dateDernierDepartDp: any;
  dateDernierRetourDp: any;

  editForm = this.fb.group({
    id: [],
    dateDernierDepart: [null, [Validators.required]],
    dateDernierRetour: [null, [Validators.required]],
  });

  constructor(
    protected historiqueCongeService: HistoriqueCongeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historiqueConge }) => {
      this.updateForm(historiqueConge);
    });
  }

  updateForm(historiqueConge: IHistoriqueConge): void {
    this.editForm.patchValue({
      id: historiqueConge.id,
      dateDernierDepart: historiqueConge.dateDernierDepart,
      dateDernierRetour: historiqueConge.dateDernierRetour,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const historiqueConge = this.createFromForm();
    if (historiqueConge.id !== undefined) {
      this.subscribeToSaveResponse(this.historiqueCongeService.update(historiqueConge));
    } else {
      this.subscribeToSaveResponse(this.historiqueCongeService.create(historiqueConge));
    }
  }

  private createFromForm(): IHistoriqueConge {
    return {
      ...new HistoriqueConge(),
      id: this.editForm.get(['id'])!.value,
      dateDernierDepart: this.editForm.get(['dateDernierDepart'])!.value,
      dateDernierRetour: this.editForm.get(['dateDernierRetour'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoriqueConge>>): void {
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
