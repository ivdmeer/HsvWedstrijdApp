import { IVereniging } from '@/shared/model/vereniging.model';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

export interface IPersoon {
  id?: number;
  naam?: string;
  geboorteDatum?: Date;
  verenigings?: IVereniging[] | null;
  deelnemer?: IDeelnemer | null;
}

export class Persoon implements IPersoon {
  constructor(
    public id?: number,
    public naam?: string,
    public geboorteDatum?: Date,
    public verenigings?: IVereniging[] | null,
    public deelnemer?: IDeelnemer | null
  ) {}
}
