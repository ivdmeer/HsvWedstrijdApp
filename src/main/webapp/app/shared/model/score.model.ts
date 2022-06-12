import { IOnderdeel } from '@/shared/model/onderdeel.model';
import { INiveau } from '@/shared/model/niveau.model';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

export interface IScore {
  id?: number;
  punten?: number;
  onderdeel?: IOnderdeel | null;
  niveau?: INiveau | null;
  deelnemer?: IDeelnemer | null;
}

export class Score implements IScore {
  constructor(
    public id?: number,
    public punten?: number,
    public onderdeel?: IOnderdeel | null,
    public niveau?: INiveau | null,
    public deelnemer?: IDeelnemer | null
  ) {}
}
