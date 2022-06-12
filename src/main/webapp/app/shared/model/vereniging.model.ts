import { IPersoon } from '@/shared/model/persoon.model';
import { ITeam } from '@/shared/model/team.model';

export interface IVereniging {
  id?: number;
  naam?: string;
  persoons?: IPersoon[] | null;
  teams?: ITeam[] | null;
}

export class Vereniging implements IVereniging {
  constructor(public id?: number, public naam?: string, public persoons?: IPersoon[] | null, public teams?: ITeam[] | null) {}
}
