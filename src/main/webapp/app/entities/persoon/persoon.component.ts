import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPersoon } from '@/shared/model/persoon.model';

import PersoonService from './persoon.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Persoon extends Vue {
  @Inject('persoonService') private persoonService: () => PersoonService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public persoons: IPersoon[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPersoons();
  }

  public clear(): void {
    this.retrieveAllPersoons();
  }

  public retrieveAllPersoons(): void {
    this.isFetching = true;
    this.persoonService()
      .retrieve()
      .then(
        res => {
          this.persoons = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IPersoon): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePersoon(): void {
    this.persoonService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('hsvWedstrijdApp.persoon.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPersoons();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
