import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IValidationStep } from 'app/shared/model/validation-step.model';

type EntityResponseType = HttpResponse<IValidationStep>;
type EntityArrayResponseType = HttpResponse<IValidationStep[]>;

@Injectable({ providedIn: 'root' })
export class ValidationStepService {
  public resourceUrl = SERVER_API_URL + 'api/validation-steps';

  constructor(protected http: HttpClient) {}

  create(validationStep: IValidationStep): Observable<EntityResponseType> {
    return this.http.post<IValidationStep>(this.resourceUrl, validationStep, { observe: 'response' });
  }

  update(validationStep: IValidationStep): Observable<EntityResponseType> {
    return this.http.put<IValidationStep>(this.resourceUrl, validationStep, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IValidationStep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IValidationStep[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
