import { IVereniging } from '@/shared/model/vereniging.model';

export interface ITeam {
  id?: number;
  naam?: string;
  vereniging?: IVereniging | null;
}

export class Team implements ITeam {
  constructor(public id?: number, public naam?: string, public vereniging?: IVereniging | null) {}
}
