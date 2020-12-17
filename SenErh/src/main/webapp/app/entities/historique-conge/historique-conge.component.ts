import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHistoriqueConge } from 'app/shared/model/historique-conge.model';
import { HistoriqueCongeService } from './historique-conge.service';
import { HistoriqueCongeDeleteDialogComponent } from './historique-conge-delete-dialog.component';

@Component({
  selector: 'jhi-historique-conge',
  templateUrl: './historique-conge.component.html',
})
export class HistoriqueCongeComponent implements OnInit, OnDestroy {
  historiqueConges?: IHistoriqueConge[];
  eventSubscriber?: Subscription;

  constructor(
    protected historiqueCongeService: HistoriqueCongeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.historiqueCongeService.query().subscribe((res: HttpResponse<IHistoriqueConge[]>) => (this.historiqueConges = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHistoriqueConges();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHistoriqueConge): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHistoriqueConges(): void {
    this.eventSubscriber = this.eventManager.subscribe('historiqueCongeListModification', () => this.loadAll());
  }

  delete(historiqueConge: IHistoriqueConge): void {
    const modalRef = this.modalService.open(HistoriqueCongeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.historiqueConge = historiqueConge;
  }
}
