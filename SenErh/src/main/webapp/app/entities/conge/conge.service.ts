import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
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
    const copy = this.convertDateFromClient(conge);
    return this.http
      .post<IConge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(conge: IConge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conge);
    return this.http
      .put<IConge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConge[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(conge: IConge): IConge {
    const copy: IConge = Object.assign({}, conge, {
      dateDebut: conge.dateDebut && conge.dateDebut.isValid() ? conge.dateDebut.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? moment(res.body.dateDebut) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((conge: IConge) => {
        conge.dateDebut = conge.dateDebut ? moment(conge.dateDebut) : undefined;
      });
    }
    return res;
  }
}
