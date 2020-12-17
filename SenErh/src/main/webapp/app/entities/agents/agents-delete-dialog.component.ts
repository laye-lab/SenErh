import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgents } from 'app/shared/model/agents.model';
import { AgentsService } from './agents.service';

@Component({
  templateUrl: './agents-delete-dialog.component.html',
})
export class AgentsDeleteDialogComponent {
  agents?: IAgents;

  constructor(protected agentsService: AgentsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agentsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('agentsListModification');
      this.activeModal.close();
    });
  }
}
