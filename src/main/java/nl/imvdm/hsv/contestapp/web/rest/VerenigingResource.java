package nl.imvdm.hsv.contestapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import nl.imvdm.hsv.contestapp.domain.Vereniging;
import nl.imvdm.hsv.contestapp.repository.VerenigingRepository;
import nl.imvdm.hsv.contestapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link nl.imvdm.hsv.contestapp.domain.Vereniging}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VerenigingResource {

    private final Logger log = LoggerFactory.getLogger(VerenigingResource.class);

    private static final String ENTITY_NAME = "vereniging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerenigingRepository verenigingRepository;

    public VerenigingResource(VerenigingRepository verenigingRepository) {
        this.verenigingRepository = verenigingRepository;
    }

    /**
     * {@code POST  /verenigings} : Create a new vereniging.
     *
     * @param vereniging the vereniging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vereniging, or with status {@code 400 (Bad Request)} if the vereniging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/verenigings")
    public ResponseEntity<Vereniging> createVereniging(@Valid @RequestBody Vereniging vereniging) throws URISyntaxException {
        log.debug("REST request to save Vereniging : {}", vereniging);
        if (vereniging.getId() != null) {
            throw new BadRequestAlertException("A new vereniging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vereniging result = verenigingRepository.save(vereniging);
        return ResponseEntity
            .created(new URI("/api/verenigings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /verenigings/:id} : Updates an existing vereniging.
     *
     * @param id the id of the vereniging to save.
     * @param vereniging the vereniging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vereniging,
     * or with status {@code 400 (Bad Request)} if the vereniging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vereniging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/verenigings/{id}")
    public ResponseEntity<Vereniging> updateVereniging(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vereniging vereniging
    ) throws URISyntaxException {
        log.debug("REST request to update Vereniging : {}, {}", id, vereniging);
        if (vereniging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vereniging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verenigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Vereniging result = verenigingRepository.save(vereniging);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vereniging.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /verenigings/:id} : Partial updates given fields of an existing vereniging, field will ignore if it is null
     *
     * @param id the id of the vereniging to save.
     * @param vereniging the vereniging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vereniging,
     * or with status {@code 400 (Bad Request)} if the vereniging is not valid,
     * or with status {@code 404 (Not Found)} if the vereniging is not found,
     * or with status {@code 500 (Internal Server Error)} if the vereniging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/verenigings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vereniging> partialUpdateVereniging(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vereniging vereniging
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vereniging partially : {}, {}", id, vereniging);
        if (vereniging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vereniging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verenigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vereniging> result = verenigingRepository
            .findById(vereniging.getId())
            .map(existingVereniging -> {
                if (vereniging.getNaam() != null) {
                    existingVereniging.setNaam(vereniging.getNaam());
                }

                return existingVereniging;
            })
            .map(verenigingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vereniging.getId().toString())
        );
    }

    /**
     * {@code GET  /verenigings} : get all the verenigings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verenigings in body.
     */
    @GetMapping("/verenigings")
    public List<Vereniging> getAllVerenigings() {
        log.debug("REST request to get all Verenigings");
        return verenigingRepository.findAll();
    }

    /**
     * {@code GET  /verenigings/:id} : get the "id" vereniging.
     *
     * @param id the id of the vereniging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vereniging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/verenigings/{id}")
    public ResponseEntity<Vereniging> getVereniging(@PathVariable Long id) {
        log.debug("REST request to get Vereniging : {}", id);
        Optional<Vereniging> vereniging = verenigingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vereniging);
    }

    /**
     * {@code DELETE  /verenigings/:id} : delete the "id" vereniging.
     *
     * @param id the id of the vereniging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/verenigings/{id}")
    public ResponseEntity<Void> deleteVereniging(@PathVariable Long id) {
        log.debug("REST request to delete Vereniging : {}", id);
        verenigingRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
