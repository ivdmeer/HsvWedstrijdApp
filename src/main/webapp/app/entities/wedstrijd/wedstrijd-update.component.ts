import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import DeelnemerService from '@/entities/deelnemer/deelnemer.service';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

import { IWedstrijd, Wedstrijd } from '@/shared/model/wedstrijd.model';
import WedstrijdService from './wedstrijd.service';

const validations: any = {
  wedstrijd: {
    naam: {
      required,
    },
    omschrijving: {},
    datum: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class WedstrijdUpdate extends Vue {
  @Inject('wedstrijdService') private wedstrijdService: () => WedstrijdService;
  @Inject('alertService') private alertService: () => AlertService;

  public wedstrijd: IWedstrijd = new Wedstrijd();

  @Inject('deelnemerService') private deelnemerService: () => DeelnemerService;

  public deelnemers: IDeelnemer[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.wedstrijdId) {
        vm.retrieveWedstrijd(to.params.wedstrijdId);
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
    if (this.wedstrijd.id) {
      this.wedstrijdService()
        .update(this.wedstrijd)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.wedstrijd.updated', { param: param.id });
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
      this.wedstrijdService()
        .create(this.wedstrijd)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.wedstrijd.created', { param: param.id });
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

  public retrieveWedstrijd(wedstrijdId): void {
    this.wedstrijdService()
      .find(wedstrijdId)
      .then(res => {
        this.wedstrijd = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.deelnemerService()
      .retrieve()
      .then(res => {
        this.deelnemers = res.data;
      });
  }
}
