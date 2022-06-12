<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hsvWedstrijdApp.score.home.createOrEditLabel"
          data-cy="ScoreCreateUpdateHeading"
          v-text="$t('hsvWedstrijdApp.score.home.createOrEditLabel')"
        >
          Create or edit a Score
        </h2>
        <div>
          <div class="form-group" v-if="score.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="score.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hsvWedstrijdApp.score.punten')" for="score-punten">Punten</label>
            <input
              type="number"
              class="form-control"
              name="punten"
              id="score-punten"
              data-cy="punten"
              :class="{ valid: !$v.score.punten.$invalid, invalid: $v.score.punten.$invalid }"
              v-model.number="$v.score.punten.$model"
              required
            />
            <div v-if="$v.score.punten.$anyDirty && $v.score.punten.$invalid">
              <small class="form-text text-danger" v-if="!$v.score.punten.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.score.punten.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
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
            :disabled="$v.score.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./score-update.component.ts"></script>
