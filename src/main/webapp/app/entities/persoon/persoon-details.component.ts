import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPersoon } from '@/shared/model/persoon.model';
import PersoonService from './persoon.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PersoonDetails extends Vue {
  @Inject('persoonService') private persoonService: () => PersoonService;
  @Inject('alertService') private alertService: () => AlertService;

  public persoon: IPersoon = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.persoonId) {
        vm.retrievePersoon(to.params.persoonId);
      }
    });
  }

  public retrievePersoon(persoonId) {
    this.persoonService()
      .find(persoonId)
      .then(res => {
        this.persoon = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
