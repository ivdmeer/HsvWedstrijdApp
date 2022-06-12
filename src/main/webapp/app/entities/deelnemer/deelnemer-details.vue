<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="deelnemer">
        <h2 class="jh-entity-heading" data-cy="deelnemerDetailsHeading">
          <span v-text="$t('hsvWedstrijdApp.deelnemer.detail.title')">Deelnemer</span> {{ deelnemer.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hsvWedstrijdApp.deelnemer.nummer')">Nummer</span>
          </dt>
          <dd>
            <span>{{ deelnemer.nummer }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.deelnemer.wedstrijd')">Wedstrijd</span>
          </dt>
          <dd>
            <div v-if="deelnemer.wedstrijd">
              <router-link :to="{ name: 'WedstrijdView', params: { wedstrijdId: deelnemer.wedstrijd.id } }">{{
                deelnemer.wedstrijd.naam
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.deelnemer.persoon')">Persoon</span>
          </dt>
          <dd>
            <div v-if="deelnemer.persoon">
              <router-link :to="{ name: 'PersoonView', params: { persoonId: deelnemer.persoon.id } }">{{
                deelnemer.persoon.naam
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.deelnemer.team')">Team</span>
          </dt>
          <dd>
            <div v-if="deelnemer.team">
              <router-link :to="{ name: 'TeamView', params: { teamId: deelnemer.team.id } }">{{ deelnemer.team.naam }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="deelnemer.id"
          :to="{ name: 'DeelnemerEdit', params: { deelnemerId: deelnemer.id } }"
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

<script lang="ts" src="./deelnemer-details.component.ts"></script>
