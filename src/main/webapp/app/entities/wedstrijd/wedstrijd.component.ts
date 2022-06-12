import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IWedstrijd } from '@/shared/model/wedstrijd.model';

import WedstrijdService from './wedstrijd.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Wedstrijd extends Vue {
  @Inject('wedstrijdService') private wedstrijdService: () => WedstrijdService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public wedstrijds: IWedstrijd[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllWedstrijds();
  }

  public clear(): void {
    this.retrieveAllWedstrijds();
  }

  public retrieveAllWedstrijds(): void {
    this.isFetching = true;
    this.wedstrijdService()
      .retrieve()
      .then(
        res => {
          this.wedstrijds = res.data;
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

  public prepareRemove(instance: IWedstrijd): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeWedstrijd(): void {
    this.wedstrijdService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('hsvWedstrijdApp.wedstrijd.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllWedstrijds();
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
