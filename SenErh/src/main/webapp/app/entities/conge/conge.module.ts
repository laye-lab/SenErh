import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenErhSharedModule } from 'app/shared/shared.module';
import { CongeComponent } from './conge.component';
import { CongeDetailComponent } from './conge-detail.component';
import { CongeUpdateComponent } from './conge-update.component';
import { CongeDeleteDialogComponent } from './conge-delete-dialog.component';
import { congeRoute } from './conge.route';

@NgModule({
  imports: [SenErhSharedModule, RouterModule.forChild(congeRoute)],
  declarations: [CongeComponent, CongeDetailComponent, CongeUpdateComponent, CongeDeleteDialogComponent],
  entryComponents: [CongeDeleteDialogComponent],
})
export class SenErhCongeModule {}
