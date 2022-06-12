import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Deelnemer = () => import('@/entities/deelnemer/deelnemer.vue');
// prettier-ignore
const DeelnemerUpdate = () => import('@/entities/deelnemer/deelnemer-update.vue');
// prettier-ignore
const DeelnemerDetails = () => import('@/entities/deelnemer/deelnemer-details.vue');
// prettier-ignore
const Onderdeel = () => import('@/entities/onderdeel/onderdeel.vue');
// prettier-ignore
const OnderdeelUpdate = () => import('@/entities/onderdeel/onderdeel-update.vue');
// prettier-ignore
const OnderdeelDetails = () => import('@/entities/onderdeel/onderdeel-details.vue');
// prettier-ignore
const Niveau = () => import('@/entities/niveau/niveau.vue');
// prettier-ignore
const NiveauUpdate = () => import('@/entities/niveau/niveau-update.vue');
// prettier-ignore
const NiveauDetails = () => import('@/entities/niveau/niveau-details.vue');
// prettier-ignore
const Persoon = () => import('@/entities/persoon/persoon.vue');
// prettier-ignore
const PersoonUpdate = () => import('@/entities/persoon/persoon-update.vue');
// prettier-ignore
const PersoonDetails = () => import('@/entities/persoon/persoon-details.vue');
// prettier-ignore
const Score = () => import('@/entities/score/score.vue');
// prettier-ignore
const ScoreUpdate = () => import('@/entities/score/score-update.vue');
// prettier-ignore
const ScoreDetails = () => import('@/entities/score/score-details.vue');
// prettier-ignore
const Team = () => import('@/entities/team/team.vue');
// prettier-ignore
const TeamUpdate = () => import('@/entities/team/team-update.vue');
// prettier-ignore
const TeamDetails = () => import('@/entities/team/team-details.vue');
// prettier-ignore
const Vereniging = () => import('@/entities/vereniging/vereniging.vue');
// prettier-ignore
const VerenigingUpdate = () => import('@/entities/vereniging/vereniging-update.vue');
// prettier-ignore
const VerenigingDetails = () => import('@/entities/vereniging/vereniging-details.vue');
// prettier-ignore
const Wedstrijd = () => import('@/entities/wedstrijd/wedstrijd.vue');
// prettier-ignore
const WedstrijdUpdate = () => import('@/entities/wedstrijd/wedstrijd-update.vue');
// prettier-ignore
const WedstrijdDetails = () => import('@/entities/wedstrijd/wedstrijd-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'deelnemer',
      name: 'Deelnemer',
      component: Deelnemer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'deelnemer/new',
      name: 'DeelnemerCreate',
      component: DeelnemerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'deelnemer/:deelnemerId/edit',
      name: 'DeelnemerEdit',
      component: DeelnemerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'deelnemer/:deelnemerId/view',
      name: 'DeelnemerView',
      component: DeelnemerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'onderdeel',
      name: 'Onderdeel',
      component: Onderdeel,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'onderdeel/new',
      name: 'OnderdeelCreate',
      component: OnderdeelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'onderdeel/:onderdeelId/edit',
      name: 'OnderdeelEdit',
      component: OnderdeelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'onderdeel/:onderdeelId/view',
      name: 'OnderdeelView',
      component: OnderdeelDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'niveau',
      name: 'Niveau',
      component: Niveau,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'niveau/new',
      name: 'NiveauCreate',
      component: NiveauUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'niveau/:niveauId/edit',
      name: 'NiveauEdit',
      component: NiveauUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'niveau/:niveauId/view',
      name: 'NiveauView',
      component: NiveauDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persoon',
      name: 'Persoon',
      component: Persoon,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persoon/new',
      name: 'PersoonCreate',
      component: PersoonUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persoon/:persoonId/edit',
      name: 'PersoonEdit',
      component: PersoonUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'persoon/:persoonId/view',
      name: 'PersoonView',
      component: PersoonDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score',
      name: 'Score',
      component: Score,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score/new',
      name: 'ScoreCreate',
      component: ScoreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score/:scoreId/edit',
      name: 'ScoreEdit',
      component: ScoreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'score/:scoreId/view',
      name: 'ScoreView',
      component: ScoreDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team',
      name: 'Team',
      component: Team,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team/new',
      name: 'TeamCreate',
      component: TeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team/:teamId/edit',
      name: 'TeamEdit',
      component: TeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team/:teamId/view',
      name: 'TeamView',
      component: TeamDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vereniging',
      name: 'Vereniging',
      component: Vereniging,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vereniging/new',
      name: 'VerenigingCreate',
      component: VerenigingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vereniging/:verenigingId/edit',
      name: 'VerenigingEdit',
      component: VerenigingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'vereniging/:verenigingId/view',
      name: 'VerenigingView',
      component: VerenigingDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'wedstrijd',
      name: 'Wedstrijd',
      component: Wedstrijd,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'wedstrijd/new',
      name: 'WedstrijdCreate',
      component: WedstrijdUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'wedstrijd/:wedstrijdId/edit',
      name: 'WedstrijdEdit',
      component: WedstrijdUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'wedstrijd/:wedstrijdId/view',
      name: 'WedstrijdView',
      component: WedstrijdDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
