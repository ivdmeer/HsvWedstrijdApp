import { IVereniging } from '@/shared/model/vereniging.model';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

export interface ITeam {
  id?: number;
  naam?: string;
  verenigings?: IVereniging[] | null;
  deelnemer?: IDeelnemer | null;
}

export class Team implements ITeam {
  constructor(public id?: number, public naam?: string, public verenigings?: IVereniging[] | null, public deelnemer?: IDeelnemer | null) {}
}
