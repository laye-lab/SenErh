import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConge } from 'app/shared/model/conge.model';
import { CongeService } from './conge.service';
import { CongeDeleteDialogComponent } from './conge-delete-dialog.component';

@Component({
  selector: 'jhi-conge',
  templateUrl: './conge.component.html',
})
export class CongeComponent implements OnInit, OnDestroy {
  conges?: IConge[];
  eventSubscriber?: Subscription;

  constructor(protected congeService: CongeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.congeService.query().subscribe((res: HttpResponse<IConge[]>) => (this.conges = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConges();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConge): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConges(): void {
    this.eventSubscriber = this.eventManager.subscribe('congeListModification', () => this.loadAll());
  }

  delete(conge: IConge): void {
    const modalRef = this.modalService.open(CongeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conge = conge;
  }
}
