/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OnderdeelDetailComponent from '@/entities/onderdeel/onderdeel-details.vue';
import OnderdeelClass from '@/entities/onderdeel/onderdeel-details.component';
import OnderdeelService from '@/entities/onderdeel/onderdeel.service';
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
  describe('Onderdeel Management Detail Component', () => {
    let wrapper: Wrapper<OnderdeelClass>;
    let comp: OnderdeelClass;
    let onderdeelServiceStub: SinonStubbedInstance<OnderdeelService>;

    beforeEach(() => {
      onderdeelServiceStub = sinon.createStubInstance<OnderdeelService>(OnderdeelService);

      wrapper = shallowMount<OnderdeelClass>(OnderdeelDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { onderdeelService: () => onderdeelServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOnderdeel = { id: 123 };
        onderdeelServiceStub.find.resolves(foundOnderdeel);

        // WHEN
        comp.retrieveOnderdeel(123);
        await comp.$nextTick();

        // THEN
        expect(comp.onderdeel).toBe(foundOnderdeel);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOnderdeel = { id: 123 };
        onderdeelServiceStub.find.resolves(foundOnderdeel);

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
