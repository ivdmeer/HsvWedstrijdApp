/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PersoonComponent from '@/entities/persoon/persoon.vue';
import PersoonClass from '@/entities/persoon/persoon.component';
import PersoonService from '@/entities/persoon/persoon.service';
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
  describe('Persoon Management Component', () => {
    let wrapper: Wrapper<PersoonClass>;
    let comp: PersoonClass;
    let persoonServiceStub: SinonStubbedInstance<PersoonService>;

    beforeEach(() => {
      persoonServiceStub = sinon.createStubInstance<PersoonService>(PersoonService);
      persoonServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PersoonClass>(PersoonComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          persoonService: () => persoonServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      persoonServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPersoons();
      await comp.$nextTick();

      // THEN
      expect(persoonServiceStub.retrieve.called).toBeTruthy();
      expect(comp.persoons[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      persoonServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(persoonServiceStub.retrieve.callCount).toEqual(1);

      comp.removePersoon();
      await comp.$nextTick();

      // THEN
      expect(persoonServiceStub.delete.called).toBeTruthy();
      expect(persoonServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
