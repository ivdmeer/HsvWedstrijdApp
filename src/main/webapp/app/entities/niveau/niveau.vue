<template>
  <div>
    <h2 id="page-heading" data-cy="NiveauHeading">
      <span v-text="$t('hsvWedstrijdApp.niveau.home.title')" id="niveau-heading">Niveaus</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('hsvWedstrijdApp.niveau.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'NiveauCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-niveau"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('hsvWedstrijdApp.niveau.home.createLabel')"> Create a new Niveau </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && niveaus && niveaus.length === 0">
      <span v-text="$t('hsvWedstrijdApp.niveau.home.notFound')">No niveaus found</span>
    </div>
    <div class="table-responsive" v-if="niveaus && niveaus.length > 0">
      <table class="table table-striped" aria-describedby="niveaus">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('hsvWedstrijdApp.niveau.naam')">Naam</span></th>
            <th scope="row"><span v-text="$t('hsvWedstrijdApp.niveau.omschrijving')">Omschrijving</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="niveau in niveaus" :key="niveau.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'NiveauView', params: { niveauId: niveau.id } }">{{ niveau.id }}</router-link>
            </td>
            <td>{{ niveau.naam }}</td>
            <td>{{ niveau.omschrijving }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'NiveauView', params: { niveauId: niveau.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'NiveauEdit', params: { niveauId: niveau.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(niveau)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="hsvWedstrijdApp.niveau.delete.question" data-cy="niveauDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-niveau-heading" v-text="$t('hsvWedstrijdApp.niveau.delete.question', { id: removeId })">
          Are you sure you want to delete this Niveau?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-niveau"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeNiveau()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./niveau.component.ts"></script>
