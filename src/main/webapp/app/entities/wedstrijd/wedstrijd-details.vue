<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="wedstrijd">
        <h2 class="jh-entity-heading" data-cy="wedstrijdDetailsHeading">
          <span v-text="$t('hsvWedstrijdApp.wedstrijd.detail.title')">Wedstrijd</span> {{ wedstrijd.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hsvWedstrijdApp.wedstrijd.naam')">Naam</span>
          </dt>
          <dd>
            <span>{{ wedstrijd.naam }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.wedstrijd.omschrijving')">Omschrijving</span>
          </dt>
          <dd>
            <span>{{ wedstrijd.omschrijving }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.wedstrijd.datum')">Datum</span>
          </dt>
          <dd>
            <span>{{ wedstrijd.datum }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="wedstrijd.id"
          :to="{ name: 'WedstrijdEdit', params: { wedstrijdId: wedstrijd.id } }"
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

<script lang="ts" src="./wedstrijd-details.component.ts"></script>
