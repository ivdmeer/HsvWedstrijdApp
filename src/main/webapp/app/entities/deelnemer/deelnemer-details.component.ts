import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDeelnemer } from '@/shared/model/deelnemer.model';
import DeelnemerService from './deelnemer.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DeelnemerDetails extends Vue {
  @Inject('deelnemerService') private deelnemerService: () => DeelnemerService;
  @Inject('alertService') private alertService: () => AlertService;

  public deelnemer: IDeelnemer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.deelnemerId) {
        vm.retrieveDeelnemer(to.params.deelnemerId);
      }
    });
  }

  public retrieveDeelnemer(deelnemerId) {
    this.deelnemerService()
      .find(deelnemerId)
      .then(res => {
        this.deelnemer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
