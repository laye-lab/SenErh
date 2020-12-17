import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenErhSharedModule } from 'app/shared/shared.module';
import { HistoriqueCongeComponent } from './historique-conge.component';
import { HistoriqueCongeDetailComponent } from './historique-conge-detail.component';
import { HistoriqueCongeUpdateComponent } from './historique-conge-update.component';
import { HistoriqueCongeDeleteDialogComponent } from './historique-conge-delete-dialog.component';
import { historiqueCongeRoute } from './historique-conge.route';

@NgModule({
  imports: [SenErhSharedModule, RouterModule.forChild(historiqueCongeRoute)],
  declarations: [
    HistoriqueCongeComponent,
    HistoriqueCongeDetailComponent,
    HistoriqueCongeUpdateComponent,
    HistoriqueCongeDeleteDialogComponent,
  ],
  entryComponents: [HistoriqueCongeDeleteDialogComponent],
})
export class SenErhHistoriqueCongeModule {}
