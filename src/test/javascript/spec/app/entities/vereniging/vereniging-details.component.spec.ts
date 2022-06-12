/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VerenigingDetailComponent from '@/entities/vereniging/vereniging-details.vue';
import VerenigingClass from '@/entities/vereniging/vereniging-details.component';
import VerenigingService from '@/entities/vereniging/vereniging.service';
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
  describe('Vereniging Management Detail Component', () => {
    let wrapper: Wrapper<VerenigingClass>;
    let comp: VerenigingClass;
    let verenigingServiceStub: SinonStubbedInstance<VerenigingService>;

    beforeEach(() => {
      verenigingServiceStub = sinon.createStubInstance<VerenigingService>(VerenigingService);

      wrapper = shallowMount<VerenigingClass>(VerenigingDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { verenigingService: () => verenigingServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVereniging = { id: 123 };
        verenigingServiceStub.find.resolves(foundVereniging);

        // WHEN
        comp.retrieveVereniging(123);
        await comp.$nextTick();

        // THEN
        expect(comp.vereniging).toBe(foundVereniging);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVereniging = { id: 123 };
        verenigingServiceStub.find.resolves(foundVereniging);

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
