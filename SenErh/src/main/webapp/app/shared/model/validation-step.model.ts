export interface IValidationStep {
  id?: number;
  step?: number;
}

export class ValidationStep implements IValidationStep {
  constructor(public id?: number, public step?: number) {}
}
