<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="onderdeel">
        <h2 class="jh-entity-heading" data-cy="onderdeelDetailsHeading">
          <span v-text="$t('hsvWedstrijdApp.onderdeel.detail.title')">Onderdeel</span> {{ onderdeel.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hsvWedstrijdApp.onderdeel.naam')">Naam</span>
          </dt>
          <dd>
            <span>{{ onderdeel.naam }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.onderdeel.omschrijving')">Omschrijving</span>
          </dt>
          <dd>
            <span>{{ onderdeel.omschrijving }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.onderdeel.score')">Score</span>
          </dt>
          <dd>
            <div v-if="onderdeel.score">
              <router-link :to="{ name: 'ScoreView', params: { scoreId: onderdeel.score.id } }">{{ onderdeel.score.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="onderdeel.id"
          :to="{ name: 'OnderdeelEdit', params: { onderdeelId: onderdeel.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./onderdeel-details.component.ts"></script>
