import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOnderdeel } from '@/shared/model/onderdeel.model';
import OnderdeelService from './onderdeel.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class OnderdeelDetails extends Vue {
  @Inject('onderdeelService') private onderdeelService: () => OnderdeelService;
  @Inject('alertService') private alertService: () => AlertService;

  public onderdeel: IOnderdeel = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.onderdeelId) {
        vm.retrieveOnderdeel(to.params.onderdeelId);
      }
    });
  }

  public retrieveOnderdeel(onderdeelId) {
    this.onderdeelService()
      .find(onderdeelId)
      .then(res => {
        this.onderdeel = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
