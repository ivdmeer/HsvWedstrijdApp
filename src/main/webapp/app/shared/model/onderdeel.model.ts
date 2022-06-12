import { IScore } from '@/shared/model/score.model';

export interface IOnderdeel {
  id?: number;
  naam?: string;
  omschrijving?: string | null;
  score?: IScore | null;
}

export class Onderdeel implements IOnderdeel {
  constructor(public id?: number, public naam?: string, public omschrijving?: string | null, public score?: IScore | null) {}
}
