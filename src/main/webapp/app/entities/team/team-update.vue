<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hsvWedstrijdApp.team.home.createOrEditLabel"
          data-cy="TeamCreateUpdateHeading"
          v-text="$t('hsvWedstrijdApp.team.home.createOrEditLabel')"
        >
          Create or edit a Team
        </h2>
        <div>
          <div class="form-group" v-if="team.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="team.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.team.naam')" for="team-naam">Naam</label>
            <input
              type="text"
              class="form-control"
              name="naam"
              id="team-naam"
              data-cy="naam"
              :class="{ valid: !$v.team.naam.$invalid, invalid: $v.team.naam.$invalid }"
              v-model="$v.team.naam.$model"
              required
            />
            <div v-if="$v.team.naam.$anyDirty && $v.team.naam.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.naam.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.team.deelnemer')" for="team-deelnemer">Deelnemer</label>
            <select class="form-control" id="team-deelnemer" data-cy="deelnemer" name="deelnemer" v-model="team.deelnemer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="team.deelnemer && deelnemerOption.id === team.deelnemer.id ? team.deelnemer : deelnemerOption"
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
            :disabled="$v.team.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./team-update.component.ts"></script>
