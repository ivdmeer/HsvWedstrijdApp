export interface INiveau {
  id?: number;
  naam?: string;
  omschrijving?: string | null;
}

export class Niveau implements INiveau {
  constructor(public id?: number, public naam?: string, public omschrijving?: string | null) {}
}
