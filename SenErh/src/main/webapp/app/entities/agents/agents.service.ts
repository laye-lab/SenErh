import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAgents } from 'app/shared/model/agents.model';

type EntityResponseType = HttpResponse<IAgents>;
type EntityArrayResponseType = HttpResponse<IAgents[]>;

@Injectable({ providedIn: 'root' })
export class AgentsService {
  public resourceUrl = SERVER_API_URL + 'api/agents';

  constructor(protected http: HttpClient) {}

  create(agents: IAgents): Observable<EntityResponseType> {
    return this.http.post<IAgents>(this.resourceUrl, agents, { observe: 'response' });
  }

  update(agents: IAgents): Observable<EntityResponseType> {
    return this.http.put<IAgents>(this.resourceUrl, agents, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgents>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgents[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
