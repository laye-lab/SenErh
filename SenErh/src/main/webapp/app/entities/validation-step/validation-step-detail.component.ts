import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IValidationStep } from 'app/shared/model/validation-step.model';

@Component({
  selector: 'jhi-validation-step-detail',
  templateUrl: './validation-step-detail.component.html',
})
export class ValidationStepDetailComponent implements OnInit {
  validationStep: IValidationStep | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ validationStep }) => (this.validationStep = validationStep));
  }

  previousState(): void {
    window.history.back();
  }
}
