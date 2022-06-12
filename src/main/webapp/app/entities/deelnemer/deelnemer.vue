<template>
  <div>
    <h2 id="page-heading" data-cy="DeelnemerHeading">
      <span v-text="$t('hsvWedstrijdApp.deelnemer.home.title')" id="deelnemer-heading">Deelnemers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('hsvWedstrijdApp.deelnemer.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DeelnemerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-deelnemer"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('hsvWedstrijdApp.deelnemer.home.createLabel')"> Create a new Deelnemer </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && deelnemers && deelnemers.length === 0">
      <span v-text="$t('hsvWedstrijdApp.deelnemer.home.notFound')">No deelnemers found</span>
    </div>
    <div class="table-responsive" v-if="deelnemers && deelnemers.length > 0">
      <table class="table table-striped" aria-describedby="deelnemers">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('hsvWedstrijdApp.deelnemer.nummer')">Nummer</span></th>
            <th scope="row"><span v-text="$t('hsvWedstrijdApp.deelnemer.wedstrijd')">Wedstrijd</span></th>
            <th scope="row"><span v-text="$t('hsvWedstrijdApp.deelnemer.persoon')">Persoon</span></th>
            <th scope="row"><span v-text="$t('hsvWedstrijdApp.deelnemer.team')">Team</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="deelnemer in deelnemers" :key="deelnemer.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DeelnemerView', params: { deelnemerId: deelnemer.id } }">{{ deelnemer.id }}</router-link>
            </td>
            <td>{{ deelnemer.nummer }}</td>
            <td>
              <div v-if="deelnemer.wedstrijd">
                <router-link :to="{ name: 'WedstrijdView', params: { wedstrijdId: deelnemer.wedstrijd.id } }">{{
                  deelnemer.wedstrijd.naam
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="deelnemer.persoon">
                <router-link :to="{ name: 'PersoonView', params: { persoonId: deelnemer.persoon.id } }">{{
                  deelnemer.persoon.naam
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="deelnemer.team">
                <router-link :to="{ name: 'TeamView', params: { teamId: deelnemer.team.id } }">{{ deelnemer.team.naam }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DeelnemerView', params: { deelnemerId: deelnemer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DeelnemerEdit', params: { deelnemerId: deelnemer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(deelnemer)"
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
        ><span id="hsvWedstrijdApp.deelnemer.delete.question" data-cy="deelnemerDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-deelnemer-heading" v-text="$t('hsvWedstrijdApp.deelnemer.delete.question', { id: removeId })">
          Are you sure you want to delete this Deelnemer?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-deelnemer"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDeelnemer()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./deelnemer.component.ts"></script>
