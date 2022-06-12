<template>
  <div>
    <h2 id="page-heading" data-cy="VerenigingHeading">
      <span v-text="$t('hsvWedstrijdApp.vereniging.home.title')" id="vereniging-heading">Verenigings</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('hsvWedstrijdApp.vereniging.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'VerenigingCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-vereniging"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('hsvWedstrijdApp.vereniging.home.createLabel')"> Create a new Vereniging </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && verenigings && verenigings.length === 0">
      <span v-text="$t('hsvWedstrijdApp.vereniging.home.notFound')">No verenigings found</span>
    </div>
    <div class="table-responsive" v-if="verenigings && verenigings.length > 0">
      <table class="table table-striped" aria-describedby="verenigings">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('hsvWedstrijdApp.vereniging.naam')">Naam</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="vereniging in verenigings" :key="vereniging.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VerenigingView', params: { verenigingId: vereniging.id } }">{{ vereniging.id }}</router-link>
            </td>
            <td>{{ vereniging.naam }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VerenigingView', params: { verenigingId: vereniging.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VerenigingEdit', params: { verenigingId: vereniging.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(vereniging)"
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
        ><span id="hsvWedstrijdApp.vereniging.delete.question" data-cy="verenigingDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-vereniging-heading" v-text="$t('hsvWedstrijdApp.vereniging.delete.question', { id: removeId })">
          Are you sure you want to delete this Vereniging?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-vereniging"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeVereniging()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./vereniging.component.ts"></script>
