import { IWedstrijd } from '@/shared/model/wedstrijd.model';
import { IPersoon } from '@/shared/model/persoon.model';
import { ITeam } from '@/shared/model/team.model';
import { IScore } from '@/shared/model/score.model';

export interface IDeelnemer {
  id?: number;
  nummer?: string;
  wedstrijds?: IWedstrijd[] | null;
  persoons?: IPersoon[] | null;
  teams?: ITeam[] | null;
  score?: IScore | null;
}

export class Deelnemer implements IDeelnemer {
  constructor(
    public id?: number,
    public nummer?: string,
    public wedstrijds?: IWedstrijd[] | null,
    public persoons?: IPersoon[] | null,
    public teams?: ITeam[] | null,
    public score?: IScore | null
  ) {}
}
