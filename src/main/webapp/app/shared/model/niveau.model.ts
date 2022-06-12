import { IScore } from '@/shared/model/score.model';

export interface INiveau {
  id?: number;
  naam?: string;
  omschrijving?: string | null;
  score?: IScore | null;
}

export class Niveau implements INiveau {
  constructor(public id?: number, public naam?: string, public omschrijving?: string | null, public score?: IScore | null) {}
}
