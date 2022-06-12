import { IPersoon } from '@/shared/model/persoon.model';
import { ITeam } from '@/shared/model/team.model';

export interface IVereniging {
  id?: number;
  naam?: string;
  persoon?: IPersoon | null;
  team?: ITeam | null;
}

export class Vereniging implements IVereniging {
  constructor(public id?: number, public naam?: string, public persoon?: IPersoon | null, public team?: ITeam | null) {}
}
