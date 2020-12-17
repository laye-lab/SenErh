import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHistoriqueConge, HistoriqueConge } from 'app/shared/model/historique-conge.model';
import { HistoriqueCongeService } from './historique-conge.service';
import { HistoriqueCongeComponent } from './historique-conge.component';
import { HistoriqueCongeDetailComponent } from './historique-conge-detail.component';
import { HistoriqueCongeUpdateComponent } from './historique-conge-update.component';

@Injectable({ providedIn: 'root' })
export class HistoriqueCongeResolve implements Resolve<IHistoriqueConge> {
  constructor(private service: HistoriqueCongeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistoriqueConge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((historiqueConge: HttpResponse<HistoriqueConge>) => {
          if (historiqueConge.body) {
            return of(historiqueConge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HistoriqueConge());
  }
}

export const historiqueCongeRoute: Routes = [
  {
    path: '',
    component: HistoriqueCongeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.historiqueConge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HistoriqueCongeDetailComponent,
    resolve: {
      historiqueConge: HistoriqueCongeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.historiqueConge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HistoriqueCongeUpdateComponent,
    resolve: {
      historiqueConge: HistoriqueCongeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.historiqueConge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HistoriqueCongeUpdateComponent,
    resolve: {
      historiqueConge: HistoriqueCongeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.historiqueConge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
