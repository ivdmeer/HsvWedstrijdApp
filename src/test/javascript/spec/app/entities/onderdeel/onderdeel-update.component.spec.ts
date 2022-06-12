/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import OnderdeelUpdateComponent from '@/entities/onderdeel/onderdeel-update.vue';
import OnderdeelClass from '@/entities/onderdeel/onderdeel-update.component';
import OnderdeelService from '@/entities/onderdeel/onderdeel.service';

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
  describe('Onderdeel Management Update Component', () => {
    let wrapper: Wrapper<OnderdeelClass>;
    let comp: OnderdeelClass;
    let onderdeelServiceStub: SinonStubbedInstance<OnderdeelService>;

    beforeEach(() => {
      onderdeelServiceStub = sinon.createStubInstance<OnderdeelService>(OnderdeelService);

      wrapper = shallowMount<OnderdeelClass>(OnderdeelUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          onderdeelService: () => onderdeelServiceStub,
          alertService: () => new AlertService(),

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
        comp.onderdeel = entity;
        onderdeelServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(onderdeelServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.onderdeel = entity;
        onderdeelServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(onderdeelServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOnderdeel = { id: 123 };
        onderdeelServiceStub.find.resolves(foundOnderdeel);
        onderdeelServiceStub.retrieve.resolves([foundOnderdeel]);

        // WHEN
        comp.beforeRouteEnter({ params: { onderdeelId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.onderdeel).toBe(foundOnderdeel);
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
