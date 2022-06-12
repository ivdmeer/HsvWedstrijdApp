/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NiveauComponent from '@/entities/niveau/niveau.vue';
import NiveauClass from '@/entities/niveau/niveau.component';
import NiveauService from '@/entities/niveau/niveau.service';
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
  describe('Niveau Management Component', () => {
    let wrapper: Wrapper<NiveauClass>;
    let comp: NiveauClass;
    let niveauServiceStub: SinonStubbedInstance<NiveauService>;

    beforeEach(() => {
      niveauServiceStub = sinon.createStubInstance<NiveauService>(NiveauService);
      niveauServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<NiveauClass>(NiveauComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          niveauService: () => niveauServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      niveauServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllNiveaus();
      await comp.$nextTick();

      // THEN
      expect(niveauServiceStub.retrieve.called).toBeTruthy();
      expect(comp.niveaus[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      niveauServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(niveauServiceStub.retrieve.callCount).toEqual(1);

      comp.removeNiveau();
      await comp.$nextTick();

      // THEN
      expect(niveauServiceStub.delete.called).toBeTruthy();
      expect(niveauServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
