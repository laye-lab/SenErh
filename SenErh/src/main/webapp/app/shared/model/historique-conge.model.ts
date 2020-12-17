import { Moment } from 'moment';
import { IAgents } from 'app/shared/model/agents.model';

export interface IHistoriqueConge {
  id?: number;
  dateDernierDepart?: Moment;
  dateDernierRetour?: Moment;
  idAgent?: IAgents;
}

export class HistoriqueConge implements IHistoriqueConge {
  constructor(public id?: number, public dateDernierDepart?: Moment, public dateDernierRetour?: Moment, public idAgent?: IAgents) {}
}
