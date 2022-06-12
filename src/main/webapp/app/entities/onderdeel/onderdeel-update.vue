<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hsvWedstrijdApp.onderdeel.home.createOrEditLabel"
          data-cy="OnderdeelCreateUpdateHeading"
          v-text="$t('hsvWedstrijdApp.onderdeel.home.createOrEditLabel')"
        >
          Create or edit a Onderdeel
        </h2>
        <div>
          <div class="form-group" v-if="onderdeel.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="onderdeel.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.onderdeel.naam')" for="onderdeel-naam">Naam</label>
            <input
              type="text"
              class="form-control"
              name="naam"
              id="onderdeel-naam"
              data-cy="naam"
              :class="{ valid: !$v.onderdeel.naam.$invalid, invalid: $v.onderdeel.naam.$invalid }"
              v-model="$v.onderdeel.naam.$model"
              required
            />
            <div v-if="$v.onderdeel.naam.$anyDirty && $v.onderdeel.naam.$invalid">
              <small class="form-text text-danger" v-if="!$v.onderdeel.naam.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.onderdeel.omschrijving')" for="onderdeel-omschrijving"
              >Omschrijving</label
            >
            <input
              type="text"
              class="form-control"
              name="omschrijving"
              id="onderdeel-omschrijving"
              data-cy="omschrijving"
              :class="{ valid: !$v.onderdeel.omschrijving.$invalid, invalid: $v.onderdeel.omschrijving.$invalid }"
              v-model="$v.onderdeel.omschrijving.$model"
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
            :disabled="$v.onderdeel.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./onderdeel-update.component.ts"></script>
