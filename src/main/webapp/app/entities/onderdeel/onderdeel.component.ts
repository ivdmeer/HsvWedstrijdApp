import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IOnderdeel } from '@/shared/model/onderdeel.model';

import OnderdeelService from './onderdeel.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Onderdeel extends Vue {
  @Inject('onderdeelService') private onderdeelService: () => OnderdeelService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public onderdeels: IOnderdeel[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllOnderdeels();
  }

  public clear(): void {
    this.retrieveAllOnderdeels();
  }

  public retrieveAllOnderdeels(): void {
    this.isFetching = true;
    this.onderdeelService()
      .retrieve()
      .then(
        res => {
          this.onderdeels = res.data;
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

  public prepareRemove(instance: IOnderdeel): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeOnderdeel(): void {
    this.onderdeelService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('hsvWedstrijdApp.onderdeel.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllOnderdeels();
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
