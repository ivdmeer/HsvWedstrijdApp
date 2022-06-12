<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hsvWedstrijdApp.vereniging.home.createOrEditLabel"
          data-cy="VerenigingCreateUpdateHeading"
          v-text="$t('hsvWedstrijdApp.vereniging.home.createOrEditLabel')"
        >
          Create or edit a Vereniging
        </h2>
        <div>
          <div class="form-group" v-if="vereniging.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="vereniging.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.vereniging.naam')" for="vereniging-naam">Naam</label>
            <input
              type="text"
              class="form-control"
              name="naam"
              id="vereniging-naam"
              data-cy="naam"
              :class="{ valid: !$v.vereniging.naam.$invalid, invalid: $v.vereniging.naam.$invalid }"
              v-model="$v.vereniging.naam.$model"
              required
            />
            <div v-if="$v.vereniging.naam.$anyDirty && $v.vereniging.naam.$invalid">
              <small class="form-text text-danger" v-if="!$v.vereniging.naam.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.vereniging.persoon')" for="vereniging-persoon">Persoon</label>
            <select class="form-control" id="vereniging-persoon" data-cy="persoon" name="persoon" v-model="vereniging.persoon">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="vereniging.persoon && persoonOption.id === vereniging.persoon.id ? vereniging.persoon : persoonOption"
                v-for="persoonOption in persoons"
                :key="persoonOption.id"
              >
                {{ persoonOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.vereniging.team')" for="vereniging-team">Team</label>
            <select class="form-control" id="vereniging-team" data-cy="team" name="team" v-model="vereniging.team">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="vereniging.team && teamOption.id === vereniging.team.id ? vereniging.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
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
            :disabled="$v.vereniging.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./vereniging-update.component.ts"></script>
