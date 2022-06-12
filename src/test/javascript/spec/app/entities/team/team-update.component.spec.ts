/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TeamUpdateComponent from '@/entities/team/team-update.vue';
import TeamClass from '@/entities/team/team-update.component';
import TeamService from '@/entities/team/team.service';

import VerenigingService from '@/entities/vereniging/vereniging.service';

import DeelnemerService from '@/entities/deelnemer/deelnemer.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Team Management Update Component', () => {
    let wrapper: Wrapper<TeamClass>;
    let comp: TeamClass;
    let teamServiceStub: SinonStubbedInstance<TeamService>;

    beforeEach(() => {
      teamServiceStub = sinon.createStubInstance<TeamService>(TeamService);

      wrapper = shallowMount<TeamClass>(TeamUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          teamService: () => teamServiceStub,
          alertService: () => new AlertService(),

          verenigingService: () =>
            sinon.createStubInstance<VerenigingService>(VerenigingService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          deelnemerService: () =>
            sinon.createStubInstance<DeelnemerService>(DeelnemerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.team = entity;
        teamServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.team = entity;
        teamServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeam = { id: 123 };
        teamServiceStub.find.resolves(foundTeam);
        teamServiceStub.retrieve.resolves([foundTeam]);

        // WHEN
        comp.beforeRouteEnter({ params: { teamId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.team).toBe(foundTeam);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
