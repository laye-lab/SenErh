import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IValidationStep, ValidationStep } from 'app/shared/model/validation-step.model';
import { ValidationStepService } from './validation-step.service';
import { ValidationStepComponent } from './validation-step.component';
import { ValidationStepDetailComponent } from './validation-step-detail.component';
import { ValidationStepUpdateComponent } from './validation-step-update.component';

@Injectable({ providedIn: 'root' })
export class ValidationStepResolve implements Resolve<IValidationStep> {
  constructor(private service: ValidationStepService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IValidationStep> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((validationStep: HttpResponse<ValidationStep>) => {
          if (validationStep.body) {
            return of(validationStep.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ValidationStep());
  }
}

export const validationStepRoute: Routes = [
  {
    path: '',
    component: ValidationStepComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.validationStep.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ValidationStepDetailComponent,
    resolve: {
      validationStep: ValidationStepResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.validationStep.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ValidationStepUpdateComponent,
    resolve: {
      validationStep: ValidationStepResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.validationStep.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ValidationStepUpdateComponent,
    resolve: {
      validationStep: ValidationStepResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.validationStep.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
