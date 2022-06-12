package nl.imvdm.hsv.contestapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import nl.imvdm.hsv.contestapp.domain.Deelnemer;
import nl.imvdm.hsv.contestapp.repository.DeelnemerRepository;
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
 * REST controller for managing {@link nl.imvdm.hsv.contestapp.domain.Deelnemer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeelnemerResource {

    private final Logger log = LoggerFactory.getLogger(DeelnemerResource.class);

    private static final String ENTITY_NAME = "deelnemer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeelnemerRepository deelnemerRepository;

    public DeelnemerResource(DeelnemerRepository deelnemerRepository) {
        this.deelnemerRepository = deelnemerRepository;
    }

    /**
     * {@code POST  /deelnemers} : Create a new deelnemer.
     *
     * @param deelnemer the deelnemer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deelnemer, or with status {@code 400 (Bad Request)} if the deelnemer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deelnemers")
    public ResponseEntity<Deelnemer> createDeelnemer(@Valid @RequestBody Deelnemer deelnemer) throws URISyntaxException {
        log.debug("REST request to save Deelnemer : {}", deelnemer);
        if (deelnemer.getId() != null) {
            throw new BadRequestAlertException("A new deelnemer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deelnemer result = deelnemerRepository.save(deelnemer);
        return ResponseEntity
            .created(new URI("/api/deelnemers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deelnemers/:id} : Updates an existing deelnemer.
     *
     * @param id the id of the deelnemer to save.
     * @param deelnemer the deelnemer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deelnemer,
     * or with status {@code 400 (Bad Request)} if the deelnemer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deelnemer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deelnemers/{id}")
    public ResponseEntity<Deelnemer> updateDeelnemer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Deelnemer deelnemer
    ) throws URISyntaxException {
        log.debug("REST request to update Deelnemer : {}, {}", id, deelnemer);
        if (deelnemer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deelnemer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deelnemerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Deelnemer result = deelnemerRepository.save(deelnemer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deelnemer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deelnemers/:id} : Partial updates given fields of an existing deelnemer, field will ignore if it is null
     *
     * @param id the id of the deelnemer to save.
     * @param deelnemer the deelnemer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deelnemer,
     * or with status {@code 400 (Bad Request)} if the deelnemer is not valid,
     * or with status {@code 404 (Not Found)} if the deelnemer is not found,
     * or with status {@code 500 (Internal Server Error)} if the deelnemer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deelnemers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Deelnemer> partialUpdateDeelnemer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Deelnemer deelnemer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Deelnemer partially : {}, {}", id, deelnemer);
        if (deelnemer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deelnemer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deelnemerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Deelnemer> result = deelnemerRepository
            .findById(deelnemer.getId())
            .map(existingDeelnemer -> {
                if (deelnemer.getNummer() != null) {
                    existingDeelnemer.setNummer(deelnemer.getNummer());
                }

                return existingDeelnemer;
            })
            .map(deelnemerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deelnemer.getId().toString())
        );
    }

    /**
     * {@code GET  /deelnemers} : get all the deelnemers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deelnemers in body.
     */
    @GetMapping("/deelnemers")
    public List<Deelnemer> getAllDeelnemers() {
        log.debug("REST request to get all Deelnemers");
        return deelnemerRepository.findAll();
    }

    /**
     * {@code GET  /deelnemers/:id} : get the "id" deelnemer.
     *
     * @param id the id of the deelnemer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deelnemer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deelnemers/{id}")
    public ResponseEntity<Deelnemer> getDeelnemer(@PathVariable Long id) {
        log.debug("REST request to get Deelnemer : {}", id);
        Optional<Deelnemer> deelnemer = deelnemerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deelnemer);
    }

    /**
     * {@code DELETE  /deelnemers/:id} : delete the "id" deelnemer.
     *
     * @param id the id of the deelnemer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deelnemers/{id}")
    public ResponseEntity<Void> deleteDeelnemer(@PathVariable Long id) {
        log.debug("REST request to delete Deelnemer : {}", id);
        deelnemerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
