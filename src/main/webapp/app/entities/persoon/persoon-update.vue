<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hsvWedstrijdApp.persoon.home.createOrEditLabel"
          data-cy="PersoonCreateUpdateHeading"
          v-text="$t('hsvWedstrijdApp.persoon.home.createOrEditLabel')"
        >
          Create or edit a Persoon
        </h2>
        <div>
          <div class="form-group" v-if="persoon.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="persoon.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.persoon.naam')" for="persoon-naam">Naam</label>
            <input
              type="text"
              class="form-control"
              name="naam"
              id="persoon-naam"
              data-cy="naam"
              :class="{ valid: !$v.persoon.naam.$invalid, invalid: $v.persoon.naam.$invalid }"
              v-model="$v.persoon.naam.$model"
              required
            />
            <div v-if="$v.persoon.naam.$anyDirty && $v.persoon.naam.$invalid">
              <small class="form-text text-danger" v-if="!$v.persoon.naam.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.persoon.geboorteDatum')" for="persoon-geboorteDatum"
              >Geboorte Datum</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="persoon-geboorteDatum"
                  v-model="$v.persoon.geboorteDatum.$model"
                  name="geboorteDatum"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="persoon-geboorteDatum"
                data-cy="geboorteDatum"
                type="text"
                class="form-control"
                name="geboorteDatum"
                :class="{ valid: !$v.persoon.geboorteDatum.$invalid, invalid: $v.persoon.geboorteDatum.$invalid }"
                v-model="$v.persoon.geboorteDatum.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.persoon.geboorteDatum.$anyDirty && $v.persoon.geboorteDatum.$invalid">
              <small class="form-text text-danger" v-if="!$v.persoon.geboorteDatum.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.persoon.deelnemer')" for="persoon-deelnemer">Deelnemer</label>
            <select class="form-control" id="persoon-deelnemer" data-cy="deelnemer" name="deelnemer" v-model="persoon.deelnemer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="persoon.deelnemer && deelnemerOption.id === persoon.deelnemer.id ? persoon.deelnemer : deelnemerOption"
                v-for="deelnemerOption in deelnemers"
                :key="deelnemerOption.id"
              >
                {{ deelnemerOption.id }}
              </option>
            </select>
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
            :disabled="$v.persoon.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./persoon-update.component.ts"></script>
