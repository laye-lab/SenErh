import { IHistoriqueConge } from 'app/shared/model/historique-conge.model';
import { IConge } from 'app/shared/model/conge.model';
import { Statut } from 'app/shared/model/enumerations/statut.model';

export interface IAgents {
  id?: number;
  matrice?: string;
  nom?: string;
  equipe?: number;
  direction?: string;
  etablissement?: string;
  fonction?: string;
  statut?: Statut;
  affectation?: string;
  taux?: number;
  historiqueConge?: IHistoriqueConge;
  conge?: IConge;
}

export class Agents implements IAgents {
  constructor(
    public id?: number,
    public matrice?: string,
    public nom?: string,
    public equipe?: number,
    public direction?: string,
    public etablissement?: string,
    public fonction?: string,
    public statut?: Statut,
    public affectation?: string,
    public taux?: number,
    public historiqueConge?: IHistoriqueConge,
    public conge?: IConge
  ) {}
}
