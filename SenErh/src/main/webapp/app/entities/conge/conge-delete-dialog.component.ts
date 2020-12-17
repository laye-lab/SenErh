import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConge } from 'app/shared/model/conge.model';
import { CongeService } from './conge.service';

@Component({
  templateUrl: './conge-delete-dialog.component.html',
})
export class CongeDeleteDialogComponent {
  conge?: IConge;

  constructor(protected congeService: CongeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.congeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('congeListModification');
      this.activeModal.close();
    });
  }
}
