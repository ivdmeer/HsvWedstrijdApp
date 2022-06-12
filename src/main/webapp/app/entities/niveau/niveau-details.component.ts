import { Component, Vue, Inject } from 'vue-property-decorator';

import { INiveau } from '@/shared/model/niveau.model';
import NiveauService from './niveau.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class NiveauDetails extends Vue {
  @Inject('niveauService') private niveauService: () => NiveauService;
  @Inject('alertService') private alertService: () => AlertService;

  public niveau: INiveau = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.niveauId) {
        vm.retrieveNiveau(to.params.niveauId);
      }
    });
  }

  public retrieveNiveau(niveauId) {
    this.niveauService()
      .find(niveauId)
      .then(res => {
        this.niveau = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
