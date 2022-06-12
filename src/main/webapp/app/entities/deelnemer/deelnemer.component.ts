import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

import DeelnemerService from './deelnemer.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Deelnemer extends Vue {
  @Inject('deelnemerService') private deelnemerService: () => DeelnemerService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public deelnemers: IDeelnemer[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDeelnemers();
  }

  public clear(): void {
    this.retrieveAllDeelnemers();
  }

  public retrieveAllDeelnemers(): void {
    this.isFetching = true;
    this.deelnemerService()
      .retrieve()
      .then(
        res => {
          this.deelnemers = res.data;
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

  public prepareRemove(instance: IDeelnemer): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDeelnemer(): void {
    this.deelnemerService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('hsvWedstrijdApp.deelnemer.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDeelnemers();
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
