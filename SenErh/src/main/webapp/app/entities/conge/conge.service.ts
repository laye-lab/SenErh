import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConge } from 'app/shared/model/conge.model';

type EntityResponseType = HttpResponse<IConge>;
type EntityArrayResponseType = HttpResponse<IConge[]>;

@Injectable({ providedIn: 'root' })
export class CongeService {
  public resourceUrl = SERVER_API_URL + 'api/conges';

  constructor(protected http: HttpClient) {}

  create(conge: IConge): Observable<EntityResponseType> {
    return this.http.post<IConge>(this.resourceUrl, conge, { observe: 'response' });
  }

  update(conge: IConge): Observable<EntityResponseType> {
    return this.http.put<IConge>(this.resourceUrl, conge, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
