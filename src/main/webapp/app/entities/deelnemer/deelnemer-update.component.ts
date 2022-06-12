import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import WedstrijdService from '@/entities/wedstrijd/wedstrijd.service';
import { IWedstrijd } from '@/shared/model/wedstrijd.model';

import PersoonService from '@/entities/persoon/persoon.service';
import { IPersoon } from '@/shared/model/persoon.model';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import { IDeelnemer, Deelnemer } from '@/shared/model/deelnemer.model';
import DeelnemerService from './deelnemer.service';

const validations: any = {
  deelnemer: {
    nummer: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class DeelnemerUpdate extends Vue {
  @Inject('deelnemerService') private deelnemerService: () => DeelnemerService;
  @Inject('alertService') private alertService: () => AlertService;

  public deelnemer: IDeelnemer = new Deelnemer();

  @Inject('wedstrijdService') private wedstrijdService: () => WedstrijdService;

  public wedstrijds: IWedstrijd[] = [];

  @Inject('persoonService') private persoonService: () => PersoonService;

  public persoons: IPersoon[] = [];

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.deelnemerId) {
        vm.retrieveDeelnemer(to.params.deelnemerId);
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
    if (this.deelnemer.id) {
      this.deelnemerService()
        .update(this.deelnemer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.deelnemer.updated', { param: param.id });
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
      this.deelnemerService()
        .create(this.deelnemer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.deelnemer.created', { param: param.id });
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

  public retrieveDeelnemer(deelnemerId): void {
    this.deelnemerService()
      .find(deelnemerId)
      .then(res => {
        this.deelnemer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.wedstrijdService()
      .retrieve()
      .then(res => {
        this.wedstrijds = res.data;
      });
    this.persoonService()
      .retrieve()
      .then(res => {
        this.persoons = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
  }
}
