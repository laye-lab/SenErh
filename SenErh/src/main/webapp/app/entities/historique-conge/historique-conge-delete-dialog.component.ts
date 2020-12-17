import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistoriqueConge } from 'app/shared/model/historique-conge.model';
import { HistoriqueCongeService } from './historique-conge.service';

@Component({
  templateUrl: './historique-conge-delete-dialog.component.html',
})
export class HistoriqueCongeDeleteDialogComponent {
  historiqueConge?: IHistoriqueConge;

  constructor(
    protected historiqueCongeService: HistoriqueCongeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.historiqueCongeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('historiqueCongeListModification');
      this.activeModal.close();
    });
  }
}
