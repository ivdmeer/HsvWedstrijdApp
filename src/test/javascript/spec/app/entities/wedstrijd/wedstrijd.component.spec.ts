/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import WedstrijdComponent from '@/entities/wedstrijd/wedstrijd.vue';
import WedstrijdClass from '@/entities/wedstrijd/wedstrijd.component';
import WedstrijdService from '@/entities/wedstrijd/wedstrijd.service';
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
  describe('Wedstrijd Management Component', () => {
    let wrapper: Wrapper<WedstrijdClass>;
    let comp: WedstrijdClass;
    let wedstrijdServiceStub: SinonStubbedInstance<WedstrijdService>;

    beforeEach(() => {
      wedstrijdServiceStub = sinon.createStubInstance<WedstrijdService>(WedstrijdService);
      wedstrijdServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<WedstrijdClass>(WedstrijdComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          wedstrijdService: () => wedstrijdServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      wedstrijdServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllWedstrijds();
      await comp.$nextTick();

      // THEN
      expect(wedstrijdServiceStub.retrieve.called).toBeTruthy();
      expect(comp.wedstrijds[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      wedstrijdServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(wedstrijdServiceStub.retrieve.callCount).toEqual(1);

      comp.removeWedstrijd();
      await comp.$nextTick();

      // THEN
      expect(wedstrijdServiceStub.delete.called).toBeTruthy();
      expect(wedstrijdServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
