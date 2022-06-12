import { Component, Vue, Inject } from 'vue-property-decorator';

import { decimal, required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import OnderdeelService from '@/entities/onderdeel/onderdeel.service';
import { IOnderdeel } from '@/shared/model/onderdeel.model';

import NiveauService from '@/entities/niveau/niveau.service';
import { INiveau } from '@/shared/model/niveau.model';

import DeelnemerService from '@/entities/deelnemer/deelnemer.service';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

import { IScore, Score } from '@/shared/model/score.model';
import ScoreService from './score.service';

const validations: any = {
  score: {
    punten: {
      required,
      decimal,
    },
  },
};

@Component({
  validations,
})
export default class ScoreUpdate extends Vue {
  @Inject('scoreService') private scoreService: () => ScoreService;
  @Inject('alertService') private alertService: () => AlertService;

  public score: IScore = new Score();

  @Inject('onderdeelService') private onderdeelService: () => OnderdeelService;

  public onderdeels: IOnderdeel[] = [];

  @Inject('niveauService') private niveauService: () => NiveauService;

  public niveaus: INiveau[] = [];

  @Inject('deelnemerService') private deelnemerService: () => DeelnemerService;

  public deelnemers: IDeelnemer[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.scoreId) {
        vm.retrieveScore(to.params.scoreId);
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
    if (this.score.id) {
      this.scoreService()
        .update(this.score)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.score.updated', { param: param.id });
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
      this.scoreService()
        .create(this.score)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.score.created', { param: param.id });
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

  public retrieveScore(scoreId): void {
    this.scoreService()
      .find(scoreId)
      .then(res => {
        this.score = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.onderdeelService()
      .retrieve()
      .then(res => {
        this.onderdeels = res.data;
      });
    this.niveauService()
      .retrieve()
      .then(res => {
        this.niveaus = res.data;
      });
    this.deelnemerService()
      .retrieve()
      .then(res => {
        this.deelnemers = res.data;
      });
  }
}
