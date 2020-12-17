import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAgents } from 'app/shared/model/agents.model';
import { AgentsService } from './agents.service';
import { AgentsDeleteDialogComponent } from './agents-delete-dialog.component';

@Component({
  selector: 'jhi-agents',
  templateUrl: './agents.component.html',
})
export class AgentsComponent implements OnInit, OnDestroy {
  agents?: IAgents[];
  eventSubscriber?: Subscription;

  constructor(protected agentsService: AgentsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.agentsService.query().subscribe((res: HttpResponse<IAgents[]>) => (this.agents = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAgents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAgents): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAgents(): void {
    this.eventSubscriber = this.eventManager.subscribe('agentsListModification', () => this.loadAll());
  }

  delete(agents: IAgents): void {
    const modalRef = this.modalService.open(AgentsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.agents = agents;
  }
}
