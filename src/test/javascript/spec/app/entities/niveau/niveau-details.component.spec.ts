/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import NiveauDetailComponent from '@/entities/niveau/niveau-details.vue';
import NiveauClass from '@/entities/niveau/niveau-details.component';
import NiveauService from '@/entities/niveau/niveau.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Niveau Management Detail Component', () => {
    let wrapper: Wrapper<NiveauClass>;
    let comp: NiveauClass;
    let niveauServiceStub: SinonStubbedInstance<NiveauService>;

    beforeEach(() => {
      niveauServiceStub = sinon.createStubInstance<NiveauService>(NiveauService);

      wrapper = shallowMount<NiveauClass>(NiveauDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { niveauService: () => niveauServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundNiveau = { id: 123 };
        niveauServiceStub.find.resolves(foundNiveau);

        // WHEN
        comp.retrieveNiveau(123);
        await comp.$nextTick();

        // THEN
        expect(comp.niveau).toBe(foundNiveau);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNiveau = { id: 123 };
        niveauServiceStub.find.resolves(foundNiveau);

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
