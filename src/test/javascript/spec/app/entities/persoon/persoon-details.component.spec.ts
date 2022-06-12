/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PersoonDetailComponent from '@/entities/persoon/persoon-details.vue';
import PersoonClass from '@/entities/persoon/persoon-details.component';
import PersoonService from '@/entities/persoon/persoon.service';
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
  describe('Persoon Management Detail Component', () => {
    let wrapper: Wrapper<PersoonClass>;
    let comp: PersoonClass;
    let persoonServiceStub: SinonStubbedInstance<PersoonService>;

    beforeEach(() => {
      persoonServiceStub = sinon.createStubInstance<PersoonService>(PersoonService);

      wrapper = shallowMount<PersoonClass>(PersoonDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { persoonService: () => persoonServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPersoon = { id: 123 };
        persoonServiceStub.find.resolves(foundPersoon);

        // WHEN
        comp.retrievePersoon(123);
        await comp.$nextTick();

        // THEN
        expect(comp.persoon).toBe(foundPersoon);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPersoon = { id: 123 };
        persoonServiceStub.find.resolves(foundPersoon);

        // WHEN
        comp.beforeRouteEnter({ params: { persoonId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.persoon).toBe(foundPersoon);
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
