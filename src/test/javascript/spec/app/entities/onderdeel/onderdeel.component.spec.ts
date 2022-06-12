/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import OnderdeelComponent from '@/entities/onderdeel/onderdeel.vue';
import OnderdeelClass from '@/entities/onderdeel/onderdeel.component';
import OnderdeelService from '@/entities/onderdeel/onderdeel.service';
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
  describe('Onderdeel Management Component', () => {
    let wrapper: Wrapper<OnderdeelClass>;
    let comp: OnderdeelClass;
    let onderdeelServiceStub: SinonStubbedInstance<OnderdeelService>;

    beforeEach(() => {
      onderdeelServiceStub = sinon.createStubInstance<OnderdeelService>(OnderdeelService);
      onderdeelServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OnderdeelClass>(OnderdeelComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          onderdeelService: () => onderdeelServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      onderdeelServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOnderdeels();
      await comp.$nextTick();

      // THEN
      expect(onderdeelServiceStub.retrieve.called).toBeTruthy();
      expect(comp.onderdeels[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      onderdeelServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(onderdeelServiceStub.retrieve.callCount).toEqual(1);

      comp.removeOnderdeel();
      await comp.$nextTick();

      // THEN
      expect(onderdeelServiceStub.delete.called).toBeTruthy();
      expect(onderdeelServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
