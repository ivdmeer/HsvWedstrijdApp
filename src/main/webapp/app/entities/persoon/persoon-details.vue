<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="persoon">
        <h2 class="jh-entity-heading" data-cy="persoonDetailsHeading">
          <span v-text="$t('hsvWedstrijdApp.persoon.detail.title')">Persoon</span> {{ persoon.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hsvWedstrijdApp.persoon.naam')">Naam</span>
          </dt>
          <dd>
            <span>{{ persoon.naam }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.persoon.geboorteDatum')">Geboorte Datum</span>
          </dt>
          <dd>
            <span>{{ persoon.geboorteDatum }}</span>
          </dd>
          <dt>
            <span v-text="$t('hsvWedstrijdApp.persoon.vereniging')">Vereniging</span>
          </dt>
          <dd>
            <div v-if="persoon.vereniging">
              <router-link :to="{ name: 'VerenigingView', params: { verenigingId: persoon.vereniging.id } }">{{
                persoon.vereniging.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="persoon.id" :to="{ name: 'PersoonEdit', params: { persoonId: persoon.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./persoon-details.component.ts"></script>
