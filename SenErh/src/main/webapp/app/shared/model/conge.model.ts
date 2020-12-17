import { IValidationStep } from 'app/shared/model/validation-step.model';
import { IAgents } from 'app/shared/model/agents.model';

export interface IConge {
  id?: number;
  idConge?: number;
  dateDebut?: string;
  validationStep?: IValidationStep;
  agents?: IAgents;
}

export class Conge implements IConge {
  constructor(
    public id?: number,
    public idConge?: number,
    public dateDebut?: string,
    public validationStep?: IValidationStep,
    public agents?: IAgents
  ) {}
}
