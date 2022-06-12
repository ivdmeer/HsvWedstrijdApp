import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVereniging } from '@/shared/model/vereniging.model';
import VerenigingService from './vereniging.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class VerenigingDetails extends Vue {
  @Inject('verenigingService') private verenigingService: () => VerenigingService;
  @Inject('alertService') private alertService: () => AlertService;

  public vereniging: IVereniging = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.verenigingId) {
        vm.retrieveVereniging(to.params.verenigingId);
      }
    });
  }

  public retrieveVereniging(verenigingId) {
    this.verenigingService()
      .find(verenigingId)
      .then(res => {
        this.vereniging = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
