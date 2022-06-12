import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IVereniging, Vereniging } from '@/shared/model/vereniging.model';
import VerenigingService from './vereniging.service';

const validations: any = {
  vereniging: {
    naam: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class VerenigingUpdate extends Vue {
  @Inject('verenigingService') private verenigingService: () => VerenigingService;
  @Inject('alertService') private alertService: () => AlertService;

  public vereniging: IVereniging = new Vereniging();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.verenigingId) {
        vm.retrieveVereniging(to.params.verenigingId);
      }
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
    if (this.vereniging.id) {
      this.verenigingService()
        .update(this.vereniging)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.vereniging.updated', { param: param.id });
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
      this.verenigingService()
        .create(this.vereniging)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.vereniging.created', { param: param.id });
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

  public retrieveVereniging(verenigingId): void {
    this.verenigingService()
      .find(verenigingId)
      .then(res => {
        this.vereniging = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
