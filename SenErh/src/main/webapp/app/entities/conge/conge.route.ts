import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConge, Conge } from 'app/shared/model/conge.model';
import { CongeService } from './conge.service';
import { CongeComponent } from './conge.component';
import { CongeDetailComponent } from './conge-detail.component';
import { CongeUpdateComponent } from './conge-update.component';

@Injectable({ providedIn: 'root' })
export class CongeResolve implements Resolve<IConge> {
  constructor(private service: CongeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conge: HttpResponse<Conge>) => {
          if (conge.body) {
            return of(conge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Conge());
  }
}

export const congeRoute: Routes = [
  {
    path: '',
    component: CongeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.conge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CongeDetailComponent,
    resolve: {
      conge: CongeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.conge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CongeUpdateComponent,
    resolve: {
      conge: CongeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.conge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CongeUpdateComponent,
    resolve: {
      conge: CongeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.conge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
