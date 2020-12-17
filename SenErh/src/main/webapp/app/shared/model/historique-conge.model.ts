import { IAgents } from 'app/shared/model/agents.model';

export interface IHistoriqueConge {
  id?: number;
  dateDernierDepart?: number;
  dateDernierRetour?: number;
  agents?: IAgents;
}

export class HistoriqueConge implements IHistoriqueConge {
  constructor(public id?: number, public dateDernierDepart?: number, public dateDernierRetour?: number, public agents?: IAgents) {}
}
