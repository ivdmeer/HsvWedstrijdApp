/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DeelnemerUpdateComponent from '@/entities/deelnemer/deelnemer-update.vue';
import DeelnemerClass from '@/entities/deelnemer/deelnemer-update.component';
import DeelnemerService from '@/entities/deelnemer/deelnemer.service';

import WedstrijdService from '@/entities/wedstrijd/wedstrijd.service';

import PersoonService from '@/entities/persoon/persoon.service';

import TeamService from '@/entities/team/team.service';

import ScoreService from '@/entities/score/score.service';
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
  describe('Deelnemer Management Update Component', () => {
    let wrapper: Wrapper<DeelnemerClass>;
    let comp: DeelnemerClass;
    let deelnemerServiceStub: SinonStubbedInstance<DeelnemerService>;

    beforeEach(() => {
      deelnemerServiceStub = sinon.createStubInstance<DeelnemerService>(DeelnemerService);

      wrapper = shallowMount<DeelnemerClass>(DeelnemerUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          deelnemerService: () => deelnemerServiceStub,
          alertService: () => new AlertService(),

          wedstrijdService: () =>
            sinon.createStubInstance<WedstrijdService>(WedstrijdService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          persoonService: () =>
            sinon.createStubInstance<PersoonService>(PersoonService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          scoreService: () =>
            sinon.createStubInstance<ScoreService>(ScoreService, {
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
        comp.deelnemer = entity;
        deelnemerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(deelnemerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.deelnemer = entity;
        deelnemerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(deelnemerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDeelnemer = { id: 123 };
        deelnemerServiceStub.find.resolves(foundDeelnemer);
        deelnemerServiceStub.retrieve.resolves([foundDeelnemer]);

        // WHEN
        comp.beforeRouteEnter({ params: { deelnemerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.deelnemer).toBe(foundDeelnemer);
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
