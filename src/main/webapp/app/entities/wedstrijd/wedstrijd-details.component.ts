import { Component, Vue, Inject } from 'vue-property-decorator';

import { IWedstrijd } from '@/shared/model/wedstrijd.model';
import WedstrijdService from './wedstrijd.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class WedstrijdDetails extends Vue {
  @Inject('wedstrijdService') private wedstrijdService: () => WedstrijdService;
  @Inject('alertService') private alertService: () => AlertService;

  public wedstrijd: IWedstrijd = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.wedstrijdId) {
        vm.retrieveWedstrijd(to.params.wedstrijdId);
      }
    });
  }

  public retrieveWedstrijd(wedstrijdId) {
    this.wedstrijdService()
      .find(wedstrijdId)
      .then(res => {
        this.wedstrijd = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
