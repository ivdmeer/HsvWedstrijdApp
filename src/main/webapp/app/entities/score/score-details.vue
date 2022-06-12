<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="score">
        <h2 class="jh-entity-heading" data-cy="scoreDetailsHeading">
          <span v-text="$t('hsvWedstrijdApp.score.detail.title')">Score</span> {{ score.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hsvWedstrijdApp.score.punten')">Punten</span>
          </dt>
          <dd>
            <span>{{ score.punten }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.score.onderdeel')">Onderdeel</span>
          </dt>
          <dd>
            <div v-if="score.onderdeel">
              <router-link :to="{ name: 'OnderdeelView', params: { onderdeelId: score.onderdeel.id } }">{{
                score.onderdeel.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.score.niveau')">Niveau</span>
          </dt>
          <dd>
            <div v-if="score.niveau">
              <router-link :to="{ name: 'NiveauView', params: { niveauId: score.niveau.id } }">{{ score.niveau.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.score.deelnemer')">Deelnemer</span>
          </dt>
          <dd>
            <div v-if="score.deelnemer">
              <router-link :to="{ name: 'DeelnemerView', params: { deelnemerId: score.deelnemer.id } }">{{
                score.deelnemer.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="score.id" :to="{ name: 'ScoreEdit', params: { scoreId: score.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./score-details.component.ts"></script>
