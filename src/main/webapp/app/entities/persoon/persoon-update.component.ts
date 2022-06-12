import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import VerenigingService from '@/entities/vereniging/vereniging.service';
import { IVereniging } from '@/shared/model/vereniging.model';

import DeelnemerService from '@/entities/deelnemer/deelnemer.service';
import { IDeelnemer } from '@/shared/model/deelnemer.model';

import { IPersoon, Persoon } from '@/shared/model/persoon.model';
import PersoonService from './persoon.service';

const validations: any = {
  persoon: {
    naam: {
      required,
    },
    geboorteDatum: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class PersoonUpdate extends Vue {
  @Inject('persoonService') private persoonService: () => PersoonService;
  @Inject('alertService') private alertService: () => AlertService;

  public persoon: IPersoon = new Persoon();

  @Inject('verenigingService') private verenigingService: () => VerenigingService;

  public verenigings: IVereniging[] = [];

  @Inject('deelnemerService') private deelnemerService: () => DeelnemerService;

  public deelnemers: IDeelnemer[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.persoonId) {
        vm.retrievePersoon(to.params.persoonId);
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
    if (this.persoon.id) {
      this.persoonService()
        .update(this.persoon)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.persoon.updated', { param: param.id });
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
      this.persoonService()
        .create(this.persoon)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.persoon.created', { param: param.id });
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

  public retrievePersoon(persoonId): void {
    this.persoonService()
      .find(persoonId)
      .then(res => {
        this.persoon = res;
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
