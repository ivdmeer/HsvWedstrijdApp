package nl.imvdm.hsv.contestapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import nl.imvdm.hsv.contestapp.IntegrationTest;
import nl.imvdm.hsv.contestapp.domain.Wedstrijd;
import nl.imvdm.hsv.contestapp.repository.WedstrijdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WedstrijdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WedstrijdResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/wedstrijds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WedstrijdRepository wedstrijdRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWedstrijdMockMvc;

    private Wedstrijd wedstrijd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wedstrijd createEntity(EntityManager em) {
        Wedstrijd wedstrijd = new Wedstrijd().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING).datum(DEFAULT_DATUM);
        return wedstrijd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wedstrijd createUpdatedEntity(EntityManager em) {
        Wedstrijd wedstrijd = new Wedstrijd().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).datum(UPDATED_DATUM);
        return wedstrijd;
    }

    @BeforeEach
    public void initTest() {
        wedstrijd = createEntity(em);
    }

    @Test
    @Transactional
    void createWedstrijd() throws Exception {
        int databaseSizeBeforeCreate = wedstrijdRepository.findAll().size();
        // Create the Wedstrijd
        restWedstrijdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wedstrijd)))
            .andExpect(status().isCreated());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeCreate + 1);
        Wedstrijd testWedstrijd = wedstrijdList.get(wedstrijdList.size() - 1);
        assertThat(testWedstrijd.getNaam()).isEqualTo(DEFAULT_NAAM);
        assertThat(testWedstrijd.getOmschrijving()).isEqualTo(DEFAULT_OMSCHRIJVING);
        assertThat(testWedstrijd.getDatum()).isEqualTo(DEFAULT_DATUM);
    }

    @Test
    @Transactional
    void createWedstrijdWithExistingId() throws Exception {
        // Create the Wedstrijd with an existing ID
        wedstrijd.setId(1L);

        int databaseSizeBeforeCreate = wedstrijdRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWedstrijdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wedstrijd)))
            .andExpect(status().isBadRequest());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = wedstrijdRepository.findAll().size();
        // set the field null
        wedstrijd.setNaam(null);

        // Create the Wedstrijd, which fails.

        restWedstrijdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wedstrijd)))
            .andExpect(status().isBadRequest());

        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = wedstrijdRepository.findAll().size();
        // set the field null
        wedstrijd.setDatum(null);

        // Create the Wedstrijd, which fails.

        restWedstrijdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wedstrijd)))
            .andExpect(status().isBadRequest());

        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWedstrijds() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        // Get all the wedstrijdList
        restWedstrijdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wedstrijd.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }

    @Test
    @Transactional
    void getWedstrijd() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        // Get the wedstrijd
        restWedstrijdMockMvc
            .perform(get(ENTITY_API_URL_ID, wedstrijd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wedstrijd.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWedstrijd() throws Exception {
        // Get the wedstrijd
        restWedstrijdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWedstrijd() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();

        // Update the wedstrijd
        Wedstrijd updatedWedstrijd = wedstrijdRepository.findById(wedstrijd.getId()).get();
        // Disconnect from session so that the updates on updatedWedstrijd are not directly saved in db
        em.detach(updatedWedstrijd);
        updatedWedstrijd.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).datum(UPDATED_DATUM);

        restWedstrijdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWedstrijd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWedstrijd))
            )
            .andExpect(status().isOk());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
        Wedstrijd testWedstrijd = wedstrijdList.get(wedstrijdList.size() - 1);
        assertThat(testWedstrijd.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testWedstrijd.getOmschrijving()).isEqualTo(UPDATED_OMSCHRIJVING);
        assertThat(testWedstrijd.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    void putNonExistingWedstrijd() throws Exception {
        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();
        wedstrijd.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWedstrijdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wedstrijd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wedstrijd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWedstrijd() throws Exception {
        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();
        wedstrijd.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWedstrijdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wedstrijd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWedstrijd() throws Exception {
        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();
        wedstrijd.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWedstrijdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wedstrijd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWedstrijdWithPatch() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();

        // Update the wedstrijd using partial update
        Wedstrijd partialUpdatedWedstrijd = new Wedstrijd();
        partialUpdatedWedstrijd.setId(wedstrijd.getId());

        partialUpdatedWedstrijd.datum(UPDATED_DATUM);

        restWedstrijdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWedstrijd.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWedstrijd))
            )
            .andExpect(status().isOk());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
        Wedstrijd testWedstrijd = wedstrijdList.get(wedstrijdList.size() - 1);
        assertThat(testWedstrijd.getNaam()).isEqualTo(DEFAULT_NAAM);
        assertThat(testWedstrijd.getOmschrijving()).isEqualTo(DEFAULT_OMSCHRIJVING);
        assertThat(testWedstrijd.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    void fullUpdateWedstrijdWithPatch() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();

        // Update the wedstrijd using partial update
        Wedstrijd partialUpdatedWedstrijd = new Wedstrijd();
        partialUpdatedWedstrijd.setId(wedstrijd.getId());

        partialUpdatedWedstrijd.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).datum(UPDATED_DATUM);

        restWedstrijdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWedstrijd.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWedstrijd))
            )
            .andExpect(status().isOk());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
        Wedstrijd testWedstrijd = wedstrijdList.get(wedstrijdList.size() - 1);
        assertThat(testWedstrijd.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testWedstrijd.getOmschrijving()).isEqualTo(UPDATED_OMSCHRIJVING);
        assertThat(testWedstrijd.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    void patchNonExistingWedstrijd() throws Exception {
        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();
        wedstrijd.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWedstrijdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wedstrijd.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wedstrijd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWedstrijd() throws Exception {
        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();
        wedstrijd.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWedstrijdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wedstrijd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWedstrijd() throws Exception {
        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();
        wedstrijd.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWedstrijdMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(wedstrijd))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWedstrijd() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        int databaseSizeBeforeDelete = wedstrijdRepository.findAll().size();

        // Delete the wedstrijd
        restWedstrijdMockMvc
            .perform(delete(ENTITY_API_URL_ID, wedstrijd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
