import { IVereniging } from '@/shared/model/vereniging.model';

export interface IPersoon {
  id?: number;
  naam?: string;
  geboorteDatum?: Date;
  team?: IVereniging | null;
}

export class Persoon implements IPersoon {
  constructor(public id?: number, public naam?: string, public geboorteDatum?: Date, public team?: IVereniging | null) {}
}
