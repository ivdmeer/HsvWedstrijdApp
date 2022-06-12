<template>
  <div>
    <b-dropdown-item to="/deelnemer">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.deelnemer')">Deelnemer</span>
    </b-dropdown-item>
    <b-dropdown-item to="/onderdeel">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.onderdeel')">Onderdeel</span>
    </b-dropdown-item>
    <b-dropdown-item to="/niveau">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.niveau')">Niveau</span>
    </b-dropdown-item>
    <b-dropdown-item to="/persoon">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.persoon')">Persoon</span>
    </b-dropdown-item>
    <b-dropdown-item to="/score">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.score')">Score</span>
    </b-dropdown-item>
    <b-dropdown-item to="/team">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.team')">Team</span>
    </b-dropdown-item>
    <b-dropdown-item to="/vereniging">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.vereniging')">Vereniging</span>
    </b-dropdown-item>
    <b-dropdown-item to="/wedstrijd">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.wedstrijd')">Wedstrijd</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
