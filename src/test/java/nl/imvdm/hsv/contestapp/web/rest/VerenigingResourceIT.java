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
import nl.imvdm.hsv.contestapp.domain.Vereniging;
import nl.imvdm.hsv.contestapp.repository.VerenigingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerenigingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerenigingResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verenigings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VerenigingRepository verenigingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerenigingMockMvc;

    private Vereniging vereniging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vereniging createEntity(EntityManager em) {
        Vereniging vereniging = new Vereniging().naam(DEFAULT_NAAM);
        return vereniging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vereniging createUpdatedEntity(EntityManager em) {
        Vereniging vereniging = new Vereniging().naam(UPDATED_NAAM);
        return vereniging;
    }

    @BeforeEach
    public void initTest() {
        vereniging = createEntity(em);
    }

    @Test
    @Transactional
    void createVereniging() throws Exception {
        int databaseSizeBeforeCreate = verenigingRepository.findAll().size();
        // Create the Vereniging
        restVerenigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vereniging)))
            .andExpect(status().isCreated());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeCreate + 1);
        Vereniging testVereniging = verenigingList.get(verenigingList.size() - 1);
        assertThat(testVereniging.getNaam()).isEqualTo(DEFAULT_NAAM);
    }

    @Test
    @Transactional
    void createVerenigingWithExistingId() throws Exception {
        // Create the Vereniging with an existing ID
        vereniging.setId(1L);

        int databaseSizeBeforeCreate = verenigingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerenigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vereniging)))
            .andExpect(status().isBadRequest());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = verenigingRepository.findAll().size();
        // set the field null
        vereniging.setNaam(null);

        // Create the Vereniging, which fails.

        restVerenigingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vereniging)))
            .andExpect(status().isBadRequest());

        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVerenigings() throws Exception {
        // Initialize the database
        verenigingRepository.saveAndFlush(vereniging);

        // Get all the verenigingList
        restVerenigingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vereniging.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getVereniging() throws Exception {
        // Initialize the database
        verenigingRepository.saveAndFlush(vereniging);

        // Get the vereniging
        restVerenigingMockMvc
            .perform(get(ENTITY_API_URL_ID, vereniging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vereniging.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingVereniging() throws Exception {
        // Get the vereniging
        restVerenigingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVereniging() throws Exception {
        // Initialize the database
        verenigingRepository.saveAndFlush(vereniging);

        int databaseSizeBeforeUpdate = verenigingRepository.findAll().size();

        // Update the vereniging
        Vereniging updatedVereniging = verenigingRepository.findById(vereniging.getId()).get();
        // Disconnect from session so that the updates on updatedVereniging are not directly saved in db
        em.detach(updatedVereniging);
        updatedVereniging.naam(UPDATED_NAAM);

        restVerenigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVereniging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVereniging))
            )
            .andExpect(status().isOk());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeUpdate);
        Vereniging testVereniging = verenigingList.get(verenigingList.size() - 1);
        assertThat(testVereniging.getNaam()).isEqualTo(UPDATED_NAAM);
    }

    @Test
    @Transactional
    void putNonExistingVereniging() throws Exception {
        int databaseSizeBeforeUpdate = verenigingRepository.findAll().size();
        vereniging.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerenigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vereniging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vereniging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVereniging() throws Exception {
        int databaseSizeBeforeUpdate = verenigingRepository.findAll().size();
        vereniging.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerenigingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vereniging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVereniging() throws Exception {
        int databaseSizeBeforeUpdate = verenigingRepository.findAll().size();
        vereniging.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerenigingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vereniging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerenigingWithPatch() throws Exception {
        // Initialize the database
        verenigingRepository.saveAndFlush(vereniging);

        int databaseSizeBeforeUpdate = verenigingRepository.findAll().size();

        // Update the vereniging using partial update
        Vereniging partialUpdatedVereniging = new Vereniging();
        partialUpdatedVereniging.setId(vereniging.getId());

        partialUpdatedVereniging.naam(UPDATED_NAAM);

        restVerenigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVereniging.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVereniging))
            )
            .andExpect(status().isOk());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeUpdate);
        Vereniging testVereniging = verenigingList.get(verenigingList.size() - 1);
        assertThat(testVereniging.getNaam()).isEqualTo(UPDATED_NAAM);
    }

    @Test
    @Transactional
    void fullUpdateVerenigingWithPatch() throws Exception {
        // Initialize the database
        verenigingRepository.saveAndFlush(vereniging);

        int databaseSizeBeforeUpdate = verenigingRepository.findAll().size();

        // Update the vereniging using partial update
        Vereniging partialUpdatedVereniging = new Vereniging();
        partialUpdatedVereniging.setId(vereniging.getId());

        partialUpdatedVereniging.naam(UPDATED_NAAM);

        restVerenigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVereniging.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVereniging))
            )
            .andExpect(status().isOk());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeUpdate);
        Vereniging testVereniging = verenigingList.get(verenigingList.size() - 1);
        assertThat(testVereniging.getNaam()).isEqualTo(UPDATED_NAAM);
    }

    @Test
    @Transactional
    void patchNonExistingVereniging() throws Exception {
        int databaseSizeBeforeUpdate = verenigingRepository.findAll().size();
        vereniging.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerenigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vereniging.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vereniging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVereniging() throws Exception {
        int databaseSizeBeforeUpdate = verenigingRepository.findAll().size();
        vereniging.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerenigingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vereniging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVereniging() throws Exception {
        int databaseSizeBeforeUpdate = verenigingRepository.findAll().size();
        vereniging.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerenigingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vereniging))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vereniging in the database
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVereniging() throws Exception {
        // Initialize the database
        verenigingRepository.saveAndFlush(vereniging);

        int databaseSizeBeforeDelete = verenigingRepository.findAll().size();

        // Delete the vereniging
        restVerenigingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vereniging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vereniging> verenigingList = verenigingRepository.findAll();
        assertThat(verenigingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
