import { IWedstrijd } from '@/shared/model/wedstrijd.model';
import { IPersoon } from '@/shared/model/persoon.model';
import { ITeam } from '@/shared/model/team.model';
import { IScore } from '@/shared/model/score.model';

export interface IDeelnemer {
  id?: number;
  nummer?: string;
  wedstrijd?: IWedstrijd | null;
  persoon?: IPersoon | null;
  team?: ITeam | null;
  scores?: IScore[] | null;
}

export class Deelnemer implements IDeelnemer {
  constructor(
    public id?: number,
    public nummer?: string,
    public wedstrijd?: IWedstrijd | null,
    public persoon?: IPersoon | null,
    public team?: ITeam | null,
    public scores?: IScore[] | null
  ) {}
}
