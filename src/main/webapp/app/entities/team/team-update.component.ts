import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import VerenigingService from '@/entities/vereniging/vereniging.service';
import { IVereniging } from '@/shared/model/vereniging.model';

import DeelnemerService from '@/entities/deelnemer/deelnemer.service';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

import { ITeam, Team } from '@/shared/model/team.model';
import TeamService from './team.service';

const validations: any = {
  team: {
    naam: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class TeamUpdate extends Vue {
  @Inject('teamService') private teamService: () => TeamService;
  @Inject('alertService') private alertService: () => AlertService;

  public team: ITeam = new Team();

  @Inject('verenigingService') private verenigingService: () => VerenigingService;

  public verenigings: IVereniging[] = [];

  @Inject('deelnemerService') private deelnemerService: () => DeelnemerService;

  public deelnemers: IDeelnemer[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamId) {
        vm.retrieveTeam(to.params.teamId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.team.id) {
      this.teamService()
        .update(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.team.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.teamService()
        .create(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.team.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveTeam(teamId): void {
    this.teamService()
      .find(teamId)
      .then(res => {
        this.team = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.verenigingService()
      .retrieve()
      .then(res => {
        this.verenigings = res.data;
      });
    this.deelnemerService()
      .retrieve()
      .then(res => {
        this.deelnemers = res.data;
      });
  }
}
