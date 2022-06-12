import { IDeelnemer } from '@/shared/model/deelnemer.model';

export interface IWedstrijd {
  id?: number;
  naam?: string;
  omschrijving?: string | null;
  datum?: Date;
  deelnemers?: IDeelnemer[] | null;
}

export class Wedstrijd implements IWedstrijd {
  constructor(
    public id?: number,
    public naam?: string,
    public omschrijving?: string | null,
    public datum?: Date,
    public deelnemers?: IDeelnemer[] | null
  ) {}
}
