import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAgents, Agents } from 'app/shared/model/agents.model';
import { AgentsService } from './agents.service';
import { AgentsComponent } from './agents.component';
import { AgentsDetailComponent } from './agents-detail.component';
import { AgentsUpdateComponent } from './agents-update.component';

@Injectable({ providedIn: 'root' })
export class AgentsResolve implements Resolve<IAgents> {
  constructor(private service: AgentsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAgents> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((agents: HttpResponse<Agents>) => {
          if (agents.body) {
            return of(agents.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Agents());
  }
}

export const agentsRoute: Routes = [
  {
    path: '',
    component: AgentsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.agents.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgentsDetailComponent,
    resolve: {
      agents: AgentsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.agents.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgentsUpdateComponent,
    resolve: {
      agents: AgentsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.agents.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgentsUpdateComponent,
    resolve: {
      agents: AgentsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'senErhApp.agents.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
