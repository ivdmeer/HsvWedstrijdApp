package nl.imvdm.hsv.contestapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import nl.imvdm.hsv.contestapp.IntegrationTest;
import nl.imvdm.hsv.contestapp.domain.Onderdeel;
import nl.imvdm.hsv.contestapp.repository.OnderdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnderdeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnderdeelResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/onderdeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OnderdeelRepository onderdeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnderdeelMockMvc;

    private Onderdeel onderdeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderdeel createEntity(EntityManager em) {
        Onderdeel onderdeel = new Onderdeel().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return onderdeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onderdeel createUpdatedEntity(EntityManager em) {
        Onderdeel onderdeel = new Onderdeel().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return onderdeel;
    }

    @BeforeEach
    public void initTest() {
        onderdeel = createEntity(em);
    }

    @Test
    @Transactional
    void createOnderdeel() throws Exception {
        int databaseSizeBeforeCreate = onderdeelRepository.findAll().size();
        // Create the Onderdeel
        restOnderdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(onderdeel)))
            .andExpect(status().isCreated());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeCreate + 1);
        Onderdeel testOnderdeel = onderdeelList.get(onderdeelList.size() - 1);
        assertThat(testOnderdeel.getNaam()).isEqualTo(DEFAULT_NAAM);
        assertThat(testOnderdeel.getOmschrijving()).isEqualTo(DEFAULT_OMSCHRIJVING);
    }

    @Test
    @Transactional
    void createOnderdeelWithExistingId() throws Exception {
        // Create the Onderdeel with an existing ID
        onderdeel.setId(1L);

        int databaseSizeBeforeCreate = onderdeelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnderdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(onderdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = onderdeelRepository.findAll().size();
        // set the field null
        onderdeel.setNaam(null);

        // Create the Onderdeel, which fails.

        restOnderdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(onderdeel)))
            .andExpect(status().isBadRequest());

        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOnderdeels() throws Exception {
        // Initialize the database
        onderdeelRepository.saveAndFlush(onderdeel);

        // Get all the onderdeelList
        restOnderdeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onderdeel.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getOnderdeel() throws Exception {
        // Initialize the database
        onderdeelRepository.saveAndFlush(onderdeel);

        // Get the onderdeel
        restOnderdeelMockMvc
            .perform(get(ENTITY_API_URL_ID, onderdeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onderdeel.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingOnderdeel() throws Exception {
        // Get the onderdeel
        restOnderdeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOnderdeel() throws Exception {
        // Initialize the database
        onderdeelRepository.saveAndFlush(onderdeel);

        int databaseSizeBeforeUpdate = onderdeelRepository.findAll().size();

        // Update the onderdeel
        Onderdeel updatedOnderdeel = onderdeelRepository.findById(onderdeel.getId()).get();
        // Disconnect from session so that the updates on updatedOnderdeel are not directly saved in db
        em.detach(updatedOnderdeel);
        updatedOnderdeel.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restOnderdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOnderdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOnderdeel))
            )
            .andExpect(status().isOk());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeUpdate);
        Onderdeel testOnderdeel = onderdeelList.get(onderdeelList.size() - 1);
        assertThat(testOnderdeel.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testOnderdeel.getOmschrijving()).isEqualTo(UPDATED_OMSCHRIJVING);
    }

    @Test
    @Transactional
    void putNonExistingOnderdeel() throws Exception {
        int databaseSizeBeforeUpdate = onderdeelRepository.findAll().size();
        onderdeel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, onderdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(onderdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOnderdeel() throws Exception {
        int databaseSizeBeforeUpdate = onderdeelRepository.findAll().size();
        onderdeel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(onderdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOnderdeel() throws Exception {
        int databaseSizeBeforeUpdate = onderdeelRepository.findAll().size();
        onderdeel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderdeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(onderdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOnderdeelWithPatch() throws Exception {
        // Initialize the database
        onderdeelRepository.saveAndFlush(onderdeel);

        int databaseSizeBeforeUpdate = onderdeelRepository.findAll().size();

        // Update the onderdeel using partial update
        Onderdeel partialUpdatedOnderdeel = new Onderdeel();
        partialUpdatedOnderdeel.setId(onderdeel.getId());

        partialUpdatedOnderdeel.omschrijving(UPDATED_OMSCHRIJVING);

        restOnderdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOnderdeel))
            )
            .andExpect(status().isOk());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeUpdate);
        Onderdeel testOnderdeel = onderdeelList.get(onderdeelList.size() - 1);
        assertThat(testOnderdeel.getNaam()).isEqualTo(DEFAULT_NAAM);
        assertThat(testOnderdeel.getOmschrijving()).isEqualTo(UPDATED_OMSCHRIJVING);
    }

    @Test
    @Transactional
    void fullUpdateOnderdeelWithPatch() throws Exception {
        // Initialize the database
        onderdeelRepository.saveAndFlush(onderdeel);

        int databaseSizeBeforeUpdate = onderdeelRepository.findAll().size();

        // Update the onderdeel using partial update
        Onderdeel partialUpdatedOnderdeel = new Onderdeel();
        partialUpdatedOnderdeel.setId(onderdeel.getId());

        partialUpdatedOnderdeel.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restOnderdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnderdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOnderdeel))
            )
            .andExpect(status().isOk());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeUpdate);
        Onderdeel testOnderdeel = onderdeelList.get(onderdeelList.size() - 1);
        assertThat(testOnderdeel.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testOnderdeel.getOmschrijving()).isEqualTo(UPDATED_OMSCHRIJVING);
    }

    @Test
    @Transactional
    void patchNonExistingOnderdeel() throws Exception {
        int databaseSizeBeforeUpdate = onderdeelRepository.findAll().size();
        onderdeel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnderdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, onderdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(onderdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOnderdeel() throws Exception {
        int databaseSizeBeforeUpdate = onderdeelRepository.findAll().size();
        onderdeel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(onderdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOnderdeel() throws Exception {
        int databaseSizeBeforeUpdate = onderdeelRepository.findAll().size();
        onderdeel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnderdeelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(onderdeel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onderdeel in the database
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOnderdeel() throws Exception {
        // Initialize the database
        onderdeelRepository.saveAndFlush(onderdeel);

        int databaseSizeBeforeDelete = onderdeelRepository.findAll().size();

        // Delete the onderdeel
        restOnderdeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, onderdeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Onderdeel> onderdeelList = onderdeelRepository.findAll();
        assertThat(onderdeelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
