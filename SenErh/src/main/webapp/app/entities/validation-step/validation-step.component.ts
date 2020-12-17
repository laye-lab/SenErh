import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IValidationStep } from 'app/shared/model/validation-step.model';
import { ValidationStepService } from './validation-step.service';
import { ValidationStepDeleteDialogComponent } from './validation-step-delete-dialog.component';

@Component({
  selector: 'jhi-validation-step',
  templateUrl: './validation-step.component.html',
})
export class ValidationStepComponent implements OnInit, OnDestroy {
  validationSteps?: IValidationStep[];
  eventSubscriber?: Subscription;

  constructor(
    protected validationStepService: ValidationStepService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.validationStepService.query().subscribe((res: HttpResponse<IValidationStep[]>) => (this.validationSteps = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInValidationSteps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IValidationStep): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInValidationSteps(): void {
    this.eventSubscriber = this.eventManager.subscribe('validationStepListModification', () => this.loadAll());
  }

  delete(validationStep: IValidationStep): void {
    const modalRef = this.modalService.open(ValidationStepDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.validationStep = validationStep;
  }
}
