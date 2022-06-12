<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hsvWedstrijdApp.niveau.home.createOrEditLabel"
          data-cy="NiveauCreateUpdateHeading"
          v-text="$t('hsvWedstrijdApp.niveau.home.createOrEditLabel')"
        >
          Create or edit a Niveau
        </h2>
        <div>
          <div class="form-group" v-if="niveau.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="niveau.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.niveau.naam')" for="niveau-naam">Naam</label>
            <input
              type="text"
              class="form-control"
              name="naam"
              id="niveau-naam"
              data-cy="naam"
              :class="{ valid: !$v.niveau.naam.$invalid, invalid: $v.niveau.naam.$invalid }"
              v-model="$v.niveau.naam.$model"
              required
            />
            <div v-if="$v.niveau.naam.$anyDirty && $v.niveau.naam.$invalid">
              <small class="form-text text-danger" v-if="!$v.niveau.naam.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.niveau.omschrijving')" for="niveau-omschrijving"
              >Omschrijving</label
            >
            <input
              type="text"
              class="form-control"
              name="omschrijving"
              id="niveau-omschrijving"
              data-cy="omschrijving"
              :class="{ valid: !$v.niveau.omschrijving.$invalid, invalid: $v.niveau.omschrijving.$invalid }"
              v-model="$v.niveau.omschrijving.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.niveau.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./niveau-update.component.ts"></script>
