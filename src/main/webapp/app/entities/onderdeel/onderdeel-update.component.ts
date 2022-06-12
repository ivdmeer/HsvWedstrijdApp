import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IOnderdeel, Onderdeel } from '@/shared/model/onderdeel.model';
import OnderdeelService from './onderdeel.service';

const validations: any = {
  onderdeel: {
    naam: {
      required,
    },
    omschrijving: {},
  },
};

@Component({
  validations,
})
export default class OnderdeelUpdate extends Vue {
  @Inject('onderdeelService') private onderdeelService: () => OnderdeelService;
  @Inject('alertService') private alertService: () => AlertService;

  public onderdeel: IOnderdeel = new Onderdeel();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.onderdeelId) {
        vm.retrieveOnderdeel(to.params.onderdeelId);
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
    if (this.onderdeel.id) {
      this.onderdeelService()
        .update(this.onderdeel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.onderdeel.updated', { param: param.id });
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
      this.onderdeelService()
        .create(this.onderdeel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hsvWedstrijdApp.onderdeel.created', { param: param.id });
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

  public retrieveOnderdeel(onderdeelId): void {
    this.onderdeelService()
      .find(onderdeelId)
      .then(res => {
        this.onderdeel = res;
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
