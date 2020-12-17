import { IConge } from 'app/shared/model/conge.model';

export interface IValidationStep {
  id?: number;
  step?: number;
  conge?: IConge;
}

export class ValidationStep implements IValidationStep {
  constructor(public id?: number, public step?: number, public conge?: IConge) {}
}
