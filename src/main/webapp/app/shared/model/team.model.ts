import { IVereniging } from '@/shared/model/vereniging.model';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

export interface ITeam {
  id?: number;
  naam?: string;
  vereniging?: IVereniging | null;
  deelnemers?: IDeelnemer[] | null;
}

export class Team implements ITeam {
  constructor(public id?: number, public naam?: string, public vereniging?: IVereniging | null, public deelnemers?: IDeelnemer[] | null) {}
}
