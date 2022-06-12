<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="team">
        <h2 class="jh-entity-heading" data-cy="teamDetailsHeading">
          <span v-text="$t('hsvWedstrijdApp.team.detail.title')">Team</span> {{ team.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hsvWedstrijdApp.team.naam')">Naam</span>
          </dt>
          <dd>
            <span>{{ team.naam }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.team.deelnemer')">Deelnemer</span>
          </dt>
          <dd>
            <div v-if="team.deelnemer">
              <router-link :to="{ name: 'DeelnemerView', params: { deelnemerId: team.deelnemer.id } }">{{ team.deelnemer.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="team.id" :to="{ name: 'TeamEdit', params: { teamId: team.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./team-details.component.ts"></script>
