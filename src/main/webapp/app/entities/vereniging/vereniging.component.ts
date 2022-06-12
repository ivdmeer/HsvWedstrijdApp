import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVereniging } from '@/shared/model/vereniging.model';

import VerenigingService from './vereniging.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Vereniging extends Vue {
  @Inject('verenigingService') private verenigingService: () => VerenigingService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public verenigings: IVereniging[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVerenigings();
  }

  public clear(): void {
    this.retrieveAllVerenigings();
  }

  public retrieveAllVerenigings(): void {
    this.isFetching = true;
    this.verenigingService()
      .retrieve()
      .then(
        res => {
          this.verenigings = res.data;
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

  public prepareRemove(instance: IVereniging): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVereniging(): void {
    this.verenigingService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('hsvWedstrijdApp.vereniging.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVerenigings();
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
