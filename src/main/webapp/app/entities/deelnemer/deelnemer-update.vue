<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hsvWedstrijdApp.deelnemer.home.createOrEditLabel"
          data-cy="DeelnemerCreateUpdateHeading"
          v-text="$t('hsvWedstrijdApp.deelnemer.home.createOrEditLabel')"
        >
          Create or edit a Deelnemer
        </h2>
        <div>
          <div class="form-group" v-if="deelnemer.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="deelnemer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.deelnemer.nummer')" for="deelnemer-nummer">Nummer</label>
            <input
              type="text"
              class="form-control"
              name="nummer"
              id="deelnemer-nummer"
              data-cy="nummer"
              :class="{ valid: !$v.deelnemer.nummer.$invalid, invalid: $v.deelnemer.nummer.$invalid }"
              v-model="$v.deelnemer.nummer.$model"
              required
            />
            <div v-if="$v.deelnemer.nummer.$anyDirty && $v.deelnemer.nummer.$invalid">
              <small class="form-text text-danger" v-if="!$v.deelnemer.nummer.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.deelnemer.wedstrijd')" for="deelnemer-wedstrijd">Wedstrijd</label>
            <select class="form-control" id="deelnemer-wedstrijd" data-cy="wedstrijd" name="wedstrijd" v-model="deelnemer.wedstrijd">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="deelnemer.wedstrijd && wedstrijdOption.id === deelnemer.wedstrijd.id ? deelnemer.wedstrijd : wedstrijdOption"
                v-for="wedstrijdOption in wedstrijds"
                :key="wedstrijdOption.id"
              >
                {{ wedstrijdOption.naam }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.deelnemer.persoon')" for="deelnemer-persoon">Persoon</label>
            <select class="form-control" id="deelnemer-persoon" data-cy="persoon" name="persoon" v-model="deelnemer.persoon">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="deelnemer.persoon && persoonOption.id === deelnemer.persoon.id ? deelnemer.persoon : persoonOption"
                v-for="persoonOption in persoons"
                :key="persoonOption.id"
              >
                {{ persoonOption.naam }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.deelnemer.team')" for="deelnemer-team">Team</label>
            <select class="form-control" id="deelnemer-team" data-cy="team" name="team" v-model="deelnemer.team">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="deelnemer.team && teamOption.id === deelnemer.team.id ? deelnemer.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.naam }}
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
            :disabled="$v.deelnemer.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./deelnemer-update.component.ts"></script>
