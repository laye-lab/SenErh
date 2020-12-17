import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHistoriqueConge } from 'app/shared/model/historique-conge.model';

type EntityResponseType = HttpResponse<IHistoriqueConge>;
type EntityArrayResponseType = HttpResponse<IHistoriqueConge[]>;

@Injectable({ providedIn: 'root' })
export class HistoriqueCongeService {
  public resourceUrl = SERVER_API_URL + 'api/historique-conges';

  constructor(protected http: HttpClient) {}

  create(historiqueConge: IHistoriqueConge): Observable<EntityResponseType> {
    return this.http.post<IHistoriqueConge>(this.resourceUrl, historiqueConge, { observe: 'response' });
  }

  update(historiqueConge: IHistoriqueConge): Observable<EntityResponseType> {
    return this.http.put<IHistoriqueConge>(this.resourceUrl, historiqueConge, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHistoriqueConge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHistoriqueConge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
