import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenErhSharedModule } from 'app/shared/shared.module';
import { AgentsComponent } from './agents.component';
import { AgentsDetailComponent } from './agents-detail.component';
import { AgentsUpdateComponent } from './agents-update.component';
import { AgentsDeleteDialogComponent } from './agents-delete-dialog.component';
import { agentsRoute } from './agents.route';

@NgModule({
  imports: [SenErhSharedModule, RouterModule.forChild(agentsRoute)],
  declarations: [AgentsComponent, AgentsDetailComponent, AgentsUpdateComponent, AgentsDeleteDialogComponent],
  entryComponents: [AgentsDeleteDialogComponent],
})
export class SenErhAgentsModule {}
