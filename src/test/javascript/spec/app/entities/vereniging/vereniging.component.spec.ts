/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import VerenigingComponent from '@/entities/vereniging/vereniging.vue';
import VerenigingClass from '@/entities/vereniging/vereniging.component';
import VerenigingService from '@/entities/vereniging/vereniging.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Vereniging Management Component', () => {
    let wrapper: Wrapper<VerenigingClass>;
    let comp: VerenigingClass;
    let verenigingServiceStub: SinonStubbedInstance<VerenigingService>;

    beforeEach(() => {
      verenigingServiceStub = sinon.createStubInstance<VerenigingService>(VerenigingService);
      verenigingServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VerenigingClass>(VerenigingComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          verenigingService: () => verenigingServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      verenigingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllVerenigings();
      await comp.$nextTick();

      // THEN
      expect(verenigingServiceStub.retrieve.called).toBeTruthy();
      expect(comp.verenigings[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      verenigingServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(verenigingServiceStub.retrieve.callCount).toEqual(1);

      comp.removeVereniging();
      await comp.$nextTick();

      // THEN
      expect(verenigingServiceStub.delete.called).toBeTruthy();
      expect(verenigingServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
