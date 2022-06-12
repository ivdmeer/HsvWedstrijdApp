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
import nl.imvdm.hsv.contestapp.domain.Deelnemer;
import nl.imvdm.hsv.contestapp.repository.DeelnemerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeelnemerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeelnemerResourceIT {

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deelnemers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeelnemerRepository deelnemerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeelnemerMockMvc;

    private Deelnemer deelnemer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deelnemer createEntity(EntityManager em) {
        Deelnemer deelnemer = new Deelnemer().nummer(DEFAULT_NUMMER);
        return deelnemer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deelnemer createUpdatedEntity(EntityManager em) {
        Deelnemer deelnemer = new Deelnemer().nummer(UPDATED_NUMMER);
        return deelnemer;
    }

    @BeforeEach
    public void initTest() {
        deelnemer = createEntity(em);
    }

    @Test
    @Transactional
    void createDeelnemer() throws Exception {
        int databaseSizeBeforeCreate = deelnemerRepository.findAll().size();
        // Create the Deelnemer
        restDeelnemerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deelnemer)))
            .andExpect(status().isCreated());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeCreate + 1);
        Deelnemer testDeelnemer = deelnemerList.get(deelnemerList.size() - 1);
        assertThat(testDeelnemer.getNummer()).isEqualTo(DEFAULT_NUMMER);
    }

    @Test
    @Transactional
    void createDeelnemerWithExistingId() throws Exception {
        // Create the Deelnemer with an existing ID
        deelnemer.setId(1L);

        int databaseSizeBeforeCreate = deelnemerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeelnemerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deelnemer)))
            .andExpect(status().isBadRequest());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
        // set the field null
        deelnemer.setNummer(null);

        // Create the Deelnemer, which fails.

        restDeelnemerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deelnemer)))
            .andExpect(status().isBadRequest());

        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDeelnemers() throws Exception {
        // Initialize the database
        deelnemerRepository.saveAndFlush(deelnemer);

        // Get all the deelnemerList
        restDeelnemerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deelnemer.getId().intValue())))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)));
    }

    @Test
    @Transactional
    void getDeelnemer() throws Exception {
        // Initialize the database
        deelnemerRepository.saveAndFlush(deelnemer);

        // Get the deelnemer
        restDeelnemerMockMvc
            .perform(get(ENTITY_API_URL_ID, deelnemer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deelnemer.getId().intValue()))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER));
    }

    @Test
    @Transactional
    void getNonExistingDeelnemer() throws Exception {
        // Get the deelnemer
        restDeelnemerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeelnemer() throws Exception {
        // Initialize the database
        deelnemerRepository.saveAndFlush(deelnemer);

        int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();

        // Update the deelnemer
        Deelnemer updatedDeelnemer = deelnemerRepository.findById(deelnemer.getId()).get();
        // Disconnect from session so that the updates on updatedDeelnemer are not directly saved in db
        em.detach(updatedDeelnemer);
        updatedDeelnemer.nummer(UPDATED_NUMMER);

        restDeelnemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeelnemer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDeelnemer))
            )
            .andExpect(status().isOk());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
        Deelnemer testDeelnemer = deelnemerList.get(deelnemerList.size() - 1);
        assertThat(testDeelnemer.getNummer()).isEqualTo(UPDATED_NUMMER);
    }

    @Test
    @Transactional
    void putNonExistingDeelnemer() throws Exception {
        int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();
        deelnemer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeelnemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deelnemer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deelnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeelnemer() throws Exception {
        int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();
        deelnemer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelnemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deelnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeelnemer() throws Exception {
        int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();
        deelnemer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelnemerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deelnemer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeelnemerWithPatch() throws Exception {
        // Initialize the database
        deelnemerRepository.saveAndFlush(deelnemer);

        int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();

        // Update the deelnemer using partial update
        Deelnemer partialUpdatedDeelnemer = new Deelnemer();
        partialUpdatedDeelnemer.setId(deelnemer.getId());

        partialUpdatedDeelnemer.nummer(UPDATED_NUMMER);

        restDeelnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeelnemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeelnemer))
            )
            .andExpect(status().isOk());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
        Deelnemer testDeelnemer = deelnemerList.get(deelnemerList.size() - 1);
        assertThat(testDeelnemer.getNummer()).isEqualTo(UPDATED_NUMMER);
    }

    @Test
    @Transactional
    void fullUpdateDeelnemerWithPatch() throws Exception {
        // Initialize the database
        deelnemerRepository.saveAndFlush(deelnemer);

        int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();

        // Update the deelnemer using partial update
        Deelnemer partialUpdatedDeelnemer = new Deelnemer();
        partialUpdatedDeelnemer.setId(deelnemer.getId());

        partialUpdatedDeelnemer.nummer(UPDATED_NUMMER);

        restDeelnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeelnemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeelnemer))
            )
            .andExpect(status().isOk());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
        Deelnemer testDeelnemer = deelnemerList.get(deelnemerList.size() - 1);
        assertThat(testDeelnemer.getNummer()).isEqualTo(UPDATED_NUMMER);
    }

    @Test
    @Transactional
    void patchNonExistingDeelnemer() throws Exception {
        int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();
        deelnemer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeelnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deelnemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deelnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeelnemer() throws Exception {
        int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();
        deelnemer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelnemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deelnemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeelnemer() throws Exception {
        int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();
        deelnemer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeelnemerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deelnemer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deelnemer in the database
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeelnemer() throws Exception {
        // Initialize the database
        deelnemerRepository.saveAndFlush(deelnemer);

        int databaseSizeBeforeDelete = deelnemerRepository.findAll().size();

        // Delete the deelnemer
        restDeelnemerMockMvc
            .perform(delete(ENTITY_API_URL_ID, deelnemer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
        assertThat(deelnemerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
