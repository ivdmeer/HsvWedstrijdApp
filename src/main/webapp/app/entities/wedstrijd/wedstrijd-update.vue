<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hsvWedstrijdApp.wedstrijd.home.createOrEditLabel"
          data-cy="WedstrijdCreateUpdateHeading"
          v-text="$t('hsvWedstrijdApp.wedstrijd.home.createOrEditLabel')"
        >
          Create or edit a Wedstrijd
        </h2>
        <div>
          <div class="form-group" v-if="wedstrijd.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="wedstrijd.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.wedstrijd.naam')" for="wedstrijd-naam">Naam</label>
            <input
              type="text"
              class="form-control"
              name="naam"
              id="wedstrijd-naam"
              data-cy="naam"
              :class="{ valid: !$v.wedstrijd.naam.$invalid, invalid: $v.wedstrijd.naam.$invalid }"
              v-model="$v.wedstrijd.naam.$model"
              required
            />
            <div v-if="$v.wedstrijd.naam.$anyDirty && $v.wedstrijd.naam.$invalid">
              <small class="form-text text-danger" v-if="!$v.wedstrijd.naam.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.wedstrijd.omschrijving')" for="wedstrijd-omschrijving"
              >Omschrijving</label
            >
            <input
              type="text"
              class="form-control"
              name="omschrijving"
              id="wedstrijd-omschrijving"
              data-cy="omschrijving"
              :class="{ valid: !$v.wedstrijd.omschrijving.$invalid, invalid: $v.wedstrijd.omschrijving.$invalid }"
              v-model="$v.wedstrijd.omschrijving.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.wedstrijd.datum')" for="wedstrijd-datum">Datum</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="wedstrijd-datum"
                  v-model="$v.wedstrijd.datum.$model"
                  name="datum"
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
                id="wedstrijd-datum"
                data-cy="datum"
                type="text"
                class="form-control"
                name="datum"
                :class="{ valid: !$v.wedstrijd.datum.$invalid, invalid: $v.wedstrijd.datum.$invalid }"
                v-model="$v.wedstrijd.datum.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.wedstrijd.datum.$anyDirty && $v.wedstrijd.datum.$invalid">
              <small class="form-text text-danger" v-if="!$v.wedstrijd.datum.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.wedstrijd.deelnemer')" for="wedstrijd-deelnemer">Deelnemer</label>
            <select class="form-control" id="wedstrijd-deelnemer" data-cy="deelnemer" name="deelnemer" v-model="wedstrijd.deelnemer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="wedstrijd.deelnemer && deelnemerOption.id === wedstrijd.deelnemer.id ? wedstrijd.deelnemer : deelnemerOption"
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
            :disabled="$v.wedstrijd.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./wedstrijd-update.component.ts"></script>
