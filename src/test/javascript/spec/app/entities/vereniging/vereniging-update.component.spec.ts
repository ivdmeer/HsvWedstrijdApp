/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import VerenigingUpdateComponent from '@/entities/vereniging/vereniging-update.vue';
import VerenigingClass from '@/entities/vereniging/vereniging-update.component';
import VerenigingService from '@/entities/vereniging/vereniging.service';

import PersoonService from '@/entities/persoon/persoon.service';

import TeamService from '@/entities/team/team.service';
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
  describe('Vereniging Management Update Component', () => {
    let wrapper: Wrapper<VerenigingClass>;
    let comp: VerenigingClass;
    let verenigingServiceStub: SinonStubbedInstance<VerenigingService>;

    beforeEach(() => {
      verenigingServiceStub = sinon.createStubInstance<VerenigingService>(VerenigingService);

      wrapper = shallowMount<VerenigingClass>(VerenigingUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          verenigingService: () => verenigingServiceStub,
          alertService: () => new AlertService(),

          persoonService: () =>
            sinon.createStubInstance<PersoonService>(PersoonService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
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
        comp.vereniging = entity;
        verenigingServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(verenigingServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.vereniging = entity;
        verenigingServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(verenigingServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVereniging = { id: 123 };
        verenigingServiceStub.find.resolves(foundVereniging);
        verenigingServiceStub.retrieve.resolves([foundVereniging]);

        // WHEN
        comp.beforeRouteEnter({ params: { verenigingId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.vereniging).toBe(foundVereniging);
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
