package nl.imvdm.hsv.contestapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import nl.imvdm.hsv.contestapp.IntegrationTest;
import nl.imvdm.hsv.contestapp.domain.Persoon;
import nl.imvdm.hsv.contestapp.repository.PersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersoonResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PersoonResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GEBOORTE_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GEBOORTE_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/persoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersoonRepository persoonRepository;

    @Mock
    private PersoonRepository persoonRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersoonMockMvc;

    private Persoon persoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persoon createEntity(EntityManager em) {
        Persoon persoon = new Persoon().naam(DEFAULT_NAAM).geboorteDatum(DEFAULT_GEBOORTE_DATUM);
        return persoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persoon createUpdatedEntity(EntityManager em) {
        Persoon persoon = new Persoon().naam(UPDATED_NAAM).geboorteDatum(UPDATED_GEBOORTE_DATUM);
        return persoon;
    }

    @BeforeEach
    public void initTest() {
        persoon = createEntity(em);
    }

    @Test
    @Transactional
    void createPersoon() throws Exception {
        int databaseSizeBeforeCreate = persoonRepository.findAll().size();
        // Create the Persoon
        restPersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(persoon)))
            .andExpect(status().isCreated());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeCreate + 1);
        Persoon testPersoon = persoonList.get(persoonList.size() - 1);
        assertThat(testPersoon.getNaam()).isEqualTo(DEFAULT_NAAM);
        assertThat(testPersoon.getGeboorteDatum()).isEqualTo(DEFAULT_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void createPersoonWithExistingId() throws Exception {
        // Create the Persoon with an existing ID
        persoon.setId(1L);

        int databaseSizeBeforeCreate = persoonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(persoon)))
            .andExpect(status().isBadRequest());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = persoonRepository.findAll().size();
        // set the field null
        persoon.setNaam(null);

        // Create the Persoon, which fails.

        restPersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(persoon)))
            .andExpect(status().isBadRequest());

        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGeboorteDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = persoonRepository.findAll().size();
        // set the field null
        persoon.setGeboorteDatum(null);

        // Create the Persoon, which fails.

        restPersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(persoon)))
            .andExpect(status().isBadRequest());

        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersoons() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

        // Get all the persoonList
        restPersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].geboorteDatum").value(hasItem(DEFAULT_GEBOORTE_DATUM.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPersoonsWithEagerRelationshipsIsEnabled() throws Exception {
        when(persoonRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersoonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(persoonRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPersoonsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(persoonRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersoonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(persoonRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPersoon() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

        // Get the persoon
        restPersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, persoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(persoon.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.geboorteDatum").value(DEFAULT_GEBOORTE_DATUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPersoon() throws Exception {
        // Get the persoon
        restPersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersoon() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

        int databaseSizeBeforeUpdate = persoonRepository.findAll().size();

        // Update the persoon
        Persoon updatedPersoon = persoonRepository.findById(persoon.getId()).get();
        // Disconnect from session so that the updates on updatedPersoon are not directly saved in db
        em.detach(updatedPersoon);
        updatedPersoon.naam(UPDATED_NAAM).geboorteDatum(UPDATED_GEBOORTE_DATUM);

        restPersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersoon))
            )
            .andExpect(status().isOk());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeUpdate);
        Persoon testPersoon = persoonList.get(persoonList.size() - 1);
        assertThat(testPersoon.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testPersoon.getGeboorteDatum()).isEqualTo(UPDATED_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void putNonExistingPersoon() throws Exception {
        int databaseSizeBeforeUpdate = persoonRepository.findAll().size();
        persoon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, persoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersoon() throws Exception {
        int databaseSizeBeforeUpdate = persoonRepository.findAll().size();
        persoon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersoon() throws Exception {
        int databaseSizeBeforeUpdate = persoonRepository.findAll().size();
        persoon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersoonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(persoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersoonWithPatch() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

        int databaseSizeBeforeUpdate = persoonRepository.findAll().size();

        // Update the persoon using partial update
        Persoon partialUpdatedPersoon = new Persoon();
        partialUpdatedPersoon.setId(persoon.getId());

        partialUpdatedPersoon.geboorteDatum(UPDATED_GEBOORTE_DATUM);

        restPersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersoon))
            )
            .andExpect(status().isOk());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeUpdate);
        Persoon testPersoon = persoonList.get(persoonList.size() - 1);
        assertThat(testPersoon.getNaam()).isEqualTo(DEFAULT_NAAM);
        assertThat(testPersoon.getGeboorteDatum()).isEqualTo(UPDATED_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void fullUpdatePersoonWithPatch() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

        int databaseSizeBeforeUpdate = persoonRepository.findAll().size();

        // Update the persoon using partial update
        Persoon partialUpdatedPersoon = new Persoon();
        partialUpdatedPersoon.setId(persoon.getId());

        partialUpdatedPersoon.naam(UPDATED_NAAM).geboorteDatum(UPDATED_GEBOORTE_DATUM);

        restPersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersoon))
            )
            .andExpect(status().isOk());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeUpdate);
        Persoon testPersoon = persoonList.get(persoonList.size() - 1);
        assertThat(testPersoon.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testPersoon.getGeboorteDatum()).isEqualTo(UPDATED_GEBOORTE_DATUM);
    }

    @Test
    @Transactional
    void patchNonExistingPersoon() throws Exception {
        int databaseSizeBeforeUpdate = persoonRepository.findAll().size();
        persoon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, persoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersoon() throws Exception {
        int databaseSizeBeforeUpdate = persoonRepository.findAll().size();
        persoon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersoon() throws Exception {
        int databaseSizeBeforeUpdate = persoonRepository.findAll().size();
        persoon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersoonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(persoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Persoon in the database
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersoon() throws Exception {
        // Initialize the database
        persoonRepository.saveAndFlush(persoon);

        int databaseSizeBeforeDelete = persoonRepository.findAll().size();

        // Delete the persoon
        restPersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, persoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Persoon> persoonList = persoonRepository.findAll();
        assertThat(persoonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
