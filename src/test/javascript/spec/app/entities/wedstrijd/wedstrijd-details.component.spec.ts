/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import WedstrijdDetailComponent from '@/entities/wedstrijd/wedstrijd-details.vue';
import WedstrijdClass from '@/entities/wedstrijd/wedstrijd-details.component';
import WedstrijdService from '@/entities/wedstrijd/wedstrijd.service';
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
  describe('Wedstrijd Management Detail Component', () => {
    let wrapper: Wrapper<WedstrijdClass>;
    let comp: WedstrijdClass;
    let wedstrijdServiceStub: SinonStubbedInstance<WedstrijdService>;

    beforeEach(() => {
      wedstrijdServiceStub = sinon.createStubInstance<WedstrijdService>(WedstrijdService);

      wrapper = shallowMount<WedstrijdClass>(WedstrijdDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { wedstrijdService: () => wedstrijdServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundWedstrijd = { id: 123 };
        wedstrijdServiceStub.find.resolves(foundWedstrijd);

        // WHEN
        comp.retrieveWedstrijd(123);
        await comp.$nextTick();

        // THEN
        expect(comp.wedstrijd).toBe(foundWedstrijd);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundWedstrijd = { id: 123 };
        wedstrijdServiceStub.find.resolves(foundWedstrijd);

        // WHEN
        comp.beforeRouteEnter({ params: { wedstrijdId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.wedstrijd).toBe(foundWedstrijd);
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
