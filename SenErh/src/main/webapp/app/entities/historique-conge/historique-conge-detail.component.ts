import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoriqueConge } from 'app/shared/model/historique-conge.model';

@Component({
  selector: 'jhi-historique-conge-detail',
  templateUrl: './historique-conge-detail.component.html',
})
export class HistoriqueCongeDetailComponent implements OnInit {
  historiqueConge: IHistoriqueConge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historiqueConge }) => (this.historiqueConge = historiqueConge));
  }

  previousState(): void {
    window.history.back();
  }
}
