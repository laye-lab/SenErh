import { Moment } from 'moment';
import { IAgents } from 'app/shared/model/agents.model';

export interface IHistoriqueConge {
  id?: number;
  dateDernierDepart?: Moment;
  dateDernierRetour?: Moment;
  agents?: IAgents;
}

export class HistoriqueConge implements IHistoriqueConge {
  constructor(public id?: number, public dateDernierDepart?: Moment, public dateDernierRetour?: Moment, public agents?: IAgents) {}
}
