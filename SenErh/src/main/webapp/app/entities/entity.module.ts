import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agents',
        loadChildren: () => import('./agents/agents.module').then(m => m.SenErhAgentsModule),
      },
      {
        path: 'conge',
        loadChildren: () => import('./conge/conge.module').then(m => m.SenErhCongeModule),
      },
      {
        path: 'validation-step',
        loadChildren: () => import('./validation-step/validation-step.module').then(m => m.SenErhValidationStepModule),
      },
      {
        path: 'historique-conge',
        loadChildren: () => import('./historique-conge/historique-conge.module').then(m => m.SenErhHistoriqueCongeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SenErhEntityModule {}
