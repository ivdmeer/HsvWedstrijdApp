import { IOnderdeel } from '@/shared/model/onderdeel.model';
import { INiveau } from '@/shared/model/niveau.model';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

export interface IScore {
  id?: number;
  punten?: number;
  onderdeels?: IOnderdeel[] | null;
  niveaus?: INiveau[] | null;
  deelnemers?: IDeelnemer[] | null;
}

export class Score implements IScore {
  constructor(
    public id?: number,
    public punten?: number,
    public onderdeels?: IOnderdeel[] | null,
    public niveaus?: INiveau[] | null,
    public deelnemers?: IDeelnemer[] | null
  ) {}
}
