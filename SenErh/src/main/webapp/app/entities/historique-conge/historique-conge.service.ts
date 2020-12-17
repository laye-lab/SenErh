import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
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
    const copy = this.convertDateFromClient(historiqueConge);
    return this.http
      .post<IHistoriqueConge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(historiqueConge: IHistoriqueConge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historiqueConge);
    return this.http
      .put<IHistoriqueConge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHistoriqueConge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistoriqueConge[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(historiqueConge: IHistoriqueConge): IHistoriqueConge {
    const copy: IHistoriqueConge = Object.assign({}, historiqueConge, {
      dateDernierDepart:
        historiqueConge.dateDernierDepart && historiqueConge.dateDernierDepart.isValid()
          ? historiqueConge.dateDernierDepart.format(DATE_FORMAT)
          : undefined,
      dateDernierRetour:
        historiqueConge.dateDernierRetour && historiqueConge.dateDernierRetour.isValid()
          ? historiqueConge.dateDernierRetour.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDernierDepart = res.body.dateDernierDepart ? moment(res.body.dateDernierDepart) : undefined;
      res.body.dateDernierRetour = res.body.dateDernierRetour ? moment(res.body.dateDernierRetour) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((historiqueConge: IHistoriqueConge) => {
        historiqueConge.dateDernierDepart = historiqueConge.dateDernierDepart ? moment(historiqueConge.dateDernierDepart) : undefined;
        historiqueConge.dateDernierRetour = historiqueConge.dateDernierRetour ? moment(historiqueConge.dateDernierRetour) : undefined;
      });
    }
    return res;
  }
}
