/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NiveauUpdateComponent from '@/entities/niveau/niveau-update.vue';
import NiveauClass from '@/entities/niveau/niveau-update.component';
import NiveauService from '@/entities/niveau/niveau.service';

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
  describe('Niveau Management Update Component', () => {
    let wrapper: Wrapper<NiveauClass>;
    let comp: NiveauClass;
    let niveauServiceStub: SinonStubbedInstance<NiveauService>;

    beforeEach(() => {
      niveauServiceStub = sinon.createStubInstance<NiveauService>(NiveauService);

      wrapper = shallowMount<NiveauClass>(NiveauUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          niveauService: () => niveauServiceStub,
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
        comp.niveau = entity;
        niveauServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(niveauServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.niveau = entity;
        niveauServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(niveauServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNiveau = { id: 123 };
        niveauServiceStub.find.resolves(foundNiveau);
        niveauServiceStub.retrieve.resolves([foundNiveau]);

        // WHEN
        comp.beforeRouteEnter({ params: { niveauId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.niveau).toBe(foundNiveau);
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
