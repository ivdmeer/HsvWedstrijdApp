export interface IWedstrijd {
  id?: number;
  naam?: string;
  omschrijving?: string | null;
  datum?: Date;
}

export class Wedstrijd implements IWedstrijd {
  constructor(public id?: number, public naam?: string, public omschrijving?: string | null, public datum?: Date) {}
}
