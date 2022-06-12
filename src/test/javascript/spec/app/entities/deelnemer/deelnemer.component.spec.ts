/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DeelnemerComponent from '@/entities/deelnemer/deelnemer.vue';
import DeelnemerClass from '@/entities/deelnemer/deelnemer.component';
import DeelnemerService from '@/entities/deelnemer/deelnemer.service';
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
  describe('Deelnemer Management Component', () => {
    let wrapper: Wrapper<DeelnemerClass>;
    let comp: DeelnemerClass;
    let deelnemerServiceStub: SinonStubbedInstance<DeelnemerService>;

    beforeEach(() => {
      deelnemerServiceStub = sinon.createStubInstance<DeelnemerService>(DeelnemerService);
      deelnemerServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DeelnemerClass>(DeelnemerComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          deelnemerService: () => deelnemerServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      deelnemerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDeelnemers();
      await comp.$nextTick();

      // THEN
      expect(deelnemerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.deelnemers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      deelnemerServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(deelnemerServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDeelnemer();
      await comp.$nextTick();

      // THEN
      expect(deelnemerServiceStub.delete.called).toBeTruthy();
      expect(deelnemerServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
