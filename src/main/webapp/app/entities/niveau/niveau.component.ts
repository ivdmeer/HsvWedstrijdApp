import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { INiveau } from '@/shared/model/niveau.model';

import NiveauService from './niveau.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Niveau extends Vue {
  @Inject('niveauService') private niveauService: () => NiveauService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public niveaus: INiveau[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllNiveaus();
  }

  public clear(): void {
    this.retrieveAllNiveaus();
  }

  public retrieveAllNiveaus(): void {
    this.isFetching = true;
    this.niveauService()
      .retrieve()
      .then(
        res => {
          this.niveaus = res.data;
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

  public prepareRemove(instance: INiveau): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeNiveau(): void {
    this.niveauService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('hsvWedstrijdApp.niveau.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllNiveaus();
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
