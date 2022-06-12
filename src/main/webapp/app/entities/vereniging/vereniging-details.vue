<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="vereniging">
        <h2 class="jh-entity-heading" data-cy="verenigingDetailsHeading">
          <span v-text="$t('hsvWedstrijdApp.vereniging.detail.title')">Vereniging</span> {{ vereniging.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hsvWedstrijdApp.vereniging.naam')">Naam</span>
          </dt>
          <dd>
            <span>{{ vereniging.naam }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.vereniging.persoon')">Persoon</span>
          </dt>
          <dd>
            <div v-if="vereniging.persoon">
              <router-link :to="{ name: 'PersoonView', params: { persoonId: vereniging.persoon.id } }">{{
                vereniging.persoon.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.vereniging.team')">Team</span>
          </dt>
          <dd>
            <div v-if="vereniging.team">
              <router-link :to="{ name: 'TeamView', params: { teamId: vereniging.team.id } }">{{ vereniging.team.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="vereniging.id"
          :to="{ name: 'VerenigingEdit', params: { verenigingId: vereniging.id } }"
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

<script lang="ts" src="./vereniging-details.component.ts"></script>
