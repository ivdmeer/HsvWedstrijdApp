import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITeam } from '@/shared/model/team.model';

import TeamService from './team.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Team extends Vue {
  @Inject('teamService') private teamService: () => TeamService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public teams: ITeam[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTeams();
  }

  public clear(): void {
    this.retrieveAllTeams();
  }

  public retrieveAllTeams(): void {
    this.isFetching = true;
    this.teamService()
      .retrieve()
      .then(
        res => {
          this.teams = res.data;
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

  public prepareRemove(instance: ITeam): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTeam(): void {
    this.teamService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('hsvWedstrijdApp.team.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTeams();
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
