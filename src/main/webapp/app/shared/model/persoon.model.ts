import { IVereniging } from '@/shared/model/vereniging.model';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

export interface IPersoon {
  id?: number;
  naam?: string;
  geboorteDatum?: Date;
  vereniging?: IVereniging | null;
  deelnemers?: IDeelnemer[] | null;
}

export class Persoon implements IPersoon {
  constructor(
    public id?: number,
    public naam?: string,
    public geboorteDatum?: Date,
    public vereniging?: IVereniging | null,
    public deelnemers?: IDeelnemer[] | null
  ) {}
}
