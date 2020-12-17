import { Moment } from 'moment';
import { IAgents } from 'app/shared/model/agents.model';
import { IValidationStep } from 'app/shared/model/validation-step.model';

export interface IConge {
  id?: number;
  idConge?: number;
  dateDebut?: Moment;
  agents?: IAgents[];
  validationStep?: IValidationStep;
}

export class Conge implements IConge {
  constructor(
    public id?: number,
    public idConge?: number,
    public dateDebut?: Moment,
    public agents?: IAgents[],
    public validationStep?: IValidationStep
  ) {}
}
