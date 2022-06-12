export interface IVereniging {
  id?: number;
  naam?: string;
}

export class Vereniging implements IVereniging {
  constructor(public id?: number, public naam?: string) {}
}
