import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAgents, Agents } from 'app/shared/model/agents.model';
import { AgentsService } from './agents.service';
import { IHistoriqueConge } from 'app/shared/model/historique-conge.model';
import { HistoriqueCongeService } from 'app/entities/historique-conge/historique-conge.service';

@Component({
  selector: 'jhi-agents-update',
  templateUrl: './agents-update.component.html',
})
export class AgentsUpdateComponent implements OnInit {
  isSaving = false;
  historiqueconges: IHistoriqueConge[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    equipe: [null, [Validators.required]],
    direction: [null, [Validators.required]],
    etablissement: [null, [Validators.required]],
    fonction: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    affectation: [null, [Validators.required]],
    taux: [],
    historiqueConge: [],
  });

  constructor(
    protected agentsService: AgentsService,
    protected historiqueCongeService: HistoriqueCongeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agents }) => {
      this.updateForm(agents);

      this.historiqueCongeService
        .query({ filter: 'agents-is-null' })
        .pipe(
          map((res: HttpResponse<IHistoriqueConge[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IHistoriqueConge[]) => {
          if (!agents.historiqueConge || !agents.historiqueConge.id) {
            this.historiqueconges = resBody;
          } else {
            this.historiqueCongeService
              .find(agents.historiqueConge.id)
              .pipe(
                map((subRes: HttpResponse<IHistoriqueConge>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IHistoriqueConge[]) => (this.historiqueconges = concatRes));
          }
        });
    });
  }

  updateForm(agents: IAgents): void {
    this.editForm.patchValue({
      id: agents.id,
      nom: agents.nom,
      equipe: agents.equipe,
      direction: agents.direction,
      etablissement: agents.etablissement,
      fonction: agents.fonction,
      statut: agents.statut,
      affectation: agents.affectation,
      taux: agents.taux,
      historiqueConge: agents.historiqueConge,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agents = this.createFromForm();
    if (agents.id !== undefined) {
      this.subscribeToSaveResponse(this.agentsService.update(agents));
    } else {
      this.subscribeToSaveResponse(this.agentsService.create(agents));
    }
  }

  private createFromForm(): IAgents {
    return {
      ...new Agents(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      equipe: this.editForm.get(['equipe'])!.value,
      direction: this.editForm.get(['direction'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
      fonction: this.editForm.get(['fonction'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      affectation: this.editForm.get(['affectation'])!.value,
      taux: this.editForm.get(['taux'])!.value,
      historiqueConge: this.editForm.get(['historiqueConge'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgents>>): void {
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

  trackById(index: number, item: IHistoriqueConge): any {
    return item.id;
  }
}
