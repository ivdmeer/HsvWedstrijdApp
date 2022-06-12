export interface IOnderdeel {
  id?: number;
  naam?: string;
  omschrijving?: string | null;
}

export class Onderdeel implements IOnderdeel {
  constructor(public id?: number, public naam?: string, public omschrijving?: string | null) {}
}
