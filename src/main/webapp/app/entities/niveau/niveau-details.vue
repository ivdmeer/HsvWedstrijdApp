<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="niveau">
        <h2 class="jh-entity-heading" data-cy="niveauDetailsHeading">
          <span v-text="$t('hsvWedstrijdApp.niveau.detail.title')">Niveau</span> {{ niveau.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hsvWedstrijdApp.niveau.naam')">Naam</span>
          </dt>
          <dd>
            <span>{{ niveau.naam }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.niveau.omschrijving')">Omschrijving</span>
          </dt>
          <dd>
            <span>{{ niveau.omschrijving }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.niveau.score')">Score</span>
          </dt>
          <dd>
            <div v-if="niveau.score">
              <router-link :to="{ name: 'ScoreView', params: { scoreId: niveau.score.id } }">{{ niveau.score.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="niveau.id" :to="{ name: 'NiveauEdit', params: { niveauId: niveau.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./niveau-details.component.ts"></script>
