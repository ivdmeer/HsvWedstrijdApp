import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import DeelnemerService from './deelnemer/deelnemer.service';
import OnderdeelService from './onderdeel/onderdeel.service';
import NiveauService from './niveau/niveau.service';
import PersoonService from './persoon/persoon.service';
import ScoreService from './score/score.service';
import TeamService from './team/team.service';
import VerenigingService from './vereniging/vereniging.service';
import WedstrijdService from './wedstrijd/wedstrijd.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('deelnemerService') private deelnemerService = () => new DeelnemerService();
  @Provide('onderdeelService') private onderdeelService = () => new OnderdeelService();
  @Provide('niveauService') private niveauService = () => new NiveauService();
  @Provide('persoonService') private persoonService = () => new PersoonService();
  @Provide('scoreService') private scoreService = () => new ScoreService();
  @Provide('teamService') private teamService = () => new TeamService();
  @Provide('verenigingService') private verenigingService = () => new VerenigingService();
  @Provide('wedstrijdService') private wedstrijdService = () => new WedstrijdService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
