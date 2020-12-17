import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgents } from 'app/shared/model/agents.model';

@Component({
  selector: 'jhi-agents-detail',
  templateUrl: './agents-detail.component.html',
})
export class AgentsDetailComponent implements OnInit {
  agents: IAgents | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agents }) => (this.agents = agents));
  }

  previousState(): void {
    window.history.back();
  }
}
