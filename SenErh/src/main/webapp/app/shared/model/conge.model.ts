import { Moment } from 'moment';
import { IValidationStep } from 'app/shared/model/validation-step.model';
import { IAgents } from 'app/shared/model/agents.model';

export interface IConge {
  id?: number;
  idConge?: number;
  dateDebut?: Moment;
  validationStep?: IValidationStep;
  agents?: IAgents;
}

export class Conge implements IConge {
  constructor(
    public id?: number,
    public idConge?: number,
    public dateDebut?: Moment,
    public validationStep?: IValidationStep,
    public agents?: IAgents
  ) {}
}
